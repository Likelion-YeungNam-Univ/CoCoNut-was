package CoCoNut_was.domains.project.service;

import CoCoNut_was.domains.submission.resdto.SubmissionAiAssistanceDto;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import CoCoNut_was.openai.OpenAiReqDto;
import CoCoNut_was.openai.OpenAiResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import CoCoNut_was.domains.project.dto.ProjectAiAssistanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectAiAssistanceService {

    @Value("${openai.api.key}")
    private String key;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public ProjectAiAssistanceDto getAssistance(String userPrompt) throws JsonProcessingException {
        // 1. 시스템 메시지 (AI 역할 및 응답 형식 지정)
        String systemPrompt = "### 페르소나 (Persona)\n" +
                "당신은 대한민국 최고의 공모전 기획 전문가이자, 참가자들의 창의력을 자극하는 스토리텔러입니다. 당신의 목표는 단순한 아이디어를 모든 이가 참여하고 싶어 하는 성공적인 공모전 '이벤트'로 구체화하여, 그 가치를 극대화하는 것입니다.\n\n" +
                "### 임무 (Mission)\n" +
                "사용자가 제공하는 핵심 아이디어와 '오늘 날짜'를 바탕으로, 잠재적 참가자들의 마음을 사로잡을 매우 구체적이고 전문적인 공모전 기획안을 작성하세요. 모든 결과물은 아래에 명시된 JSON 형식만을 사용해야 하며, 어떠한 추가 설명이나 인사말도 포함해서는 안 됩니다.\n\n" +
                "### JSON 출력 형식 (JSON Output Format)\n" +
                "{\n" +
                "  \"description\": \"(공모전 상세 설명)\",\n" +
                "  \"rewardAmount\": \"(총상금액)\",\n" +
                "  \"deadline\": \"(공모 마감일)\",\n" +
                "  \"summary\": \"(한 문장 슬로건)\"\n" +
                "}\n\n" +
                "### 필드별 상세 작성 지침 (Detailed Instructions for Each Field)\n" +
                "* **description**: 사용자의 아이디어를 바탕으로, 이 공모전의 **궁극적인 목표, 비전, 타겟 참가자, 그리고 핵심 심사 기준**이 명확히 드러나도록 풍부하고 설득력 있는 설명으로 재구성해주세요. 단순한 정보 나열이 아닌, 읽는 이의 참여 욕구를 자극하는 스토리가 담긴 소개글이어야 합니다. 그리고 반드시 줄바꿈을 포함하여 가독성있게 글을 정리해서 보내주세요.\n" +
                "* **rewardAmount**: 공모전의 주제, 기간, 예상 난이도를 고려하여, **해당 과업의 시장 가치에 부합하는 현실적인 총상금액**을 숫자로만 제시해주세요. **공모전의 규모와 난이도에 따라 금액을 신중하게 조절해야 합니다.** (예시: 간단한 슬로건/로고 공모전은 20만원~60만원, 앱 개발 공모전은 100만원~200만원 사이에서 책정)\n" +
                "현재 한국의 물가를 생각해서 유동적으로 잘 조절해주세요. 소상공인은 저렴한 가격에 인력을 쓰는 느낌으로 해야만 합니다.\n" +
                "* **deadline**: '오늘 날짜'인 " + LocalDate.now() + "를 기준으로, 공모전의 성격과 규모에 가장 적합한 접수 기간(통상 4주에서 16주 사이)을 현실적으로 판단하여 마감일을 'yyyy-MM-dd' 형식으로 계산해주세요.\n" +
                "* **summary**: 공모전 전체의 핵심 가치와 매력을 한눈에 보여줄 수 있는, 간결하면서도 강력한 **한 문장 슬로건**으로 요약해주세요.\n\n" +
                "### 중요 규칙 (Critical Rules)\n" +
                "- 오늘 날짜는 " + LocalDate.now() + "입니다. 날짜 계산에 착오가 없도록 주의하세요.\n" +
                "- 만약 사용자의 입력이 공모전 기획에 부적절하거나 정보가 현저히 부족할 경우, 아래와 같은 'error' 필드를 포함한 JSON으로만 응답해야 합니다.\n" +
                "    {\n" +
                "      \"error\": \"(구체적인 오류 사유)\"\n" +
                "    }\n" +
                "- **명심하세요: 당신의 최종 응답은 JSON 객체 외에 어떠한 텍스트도 포함하면 안 됩니다.**";
        // 2. 메시지 리스트 생성 및 프롬프트 추가
        List<OpenAiReqDto.Message> messages = new ArrayList<>();
        messages.add(new OpenAiReqDto.Message("system", systemPrompt));
        messages.add(new OpenAiReqDto.Message("user", userPrompt));

        // 3. 요청 객체 생성
        OpenAiReqDto req = new OpenAiReqDto("gpt-4.1-mini", messages);

        // 4. HTTP 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(key);

        // 5. HTTP 요청 생성
        HttpEntity<OpenAiReqDto> httpEntity = new HttpEntity<>(req, headers);

        // 6. API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenAiResDto> res = restTemplate.exchange(
                API_URL, HttpMethod.POST, httpEntity, OpenAiResDto.class);

        if (res.getBody() == null || res.getBody().getChoices().isEmpty()) {
            throw new IllegalArgumentException("OpenAI 응답이 비어있습니다.");
        }

        String jsonResponse = res.getBody().getChoices().get(0).getMessage().getContent();

        // 7. JSON 응답을 DTO로 파싱, 에러 필드 확인하기
        JsonNode responseNode = objectMapper.readTree(jsonResponse);
        if (responseNode.has("error"))
            throw new CustomException(ErrorCode.AI_GENERATION_FAILED);
        else
            return objectMapper.readValue(jsonResponse, ProjectAiAssistanceDto.class);
    }
}
