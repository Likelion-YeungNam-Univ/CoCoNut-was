package CoCoNut_was.domains.submission.service;

import CoCoNut_was.domains.submission.resdto.SubmissionAiAssistanceDto;
import CoCoNut_was.exception.CustomException;
import CoCoNut_was.exception.ErrorCode;
import CoCoNut_was.openai.OpenAiReqDto;
import CoCoNut_was.openai.OpenAiResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionAiAssistanceService {

    @Value("${openai.api.key}")
    private String key;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public SubmissionAiAssistanceDto getAssistance(String userPrompt) throws JsonProcessingException {
        // 1. 시스템 메시지 (AI 역할 및 응답 형식 지정)
        String systemPrompt = "### 페르소나 (Persona)\n" +
                "당신은 수많은 공모전에서 수상 경력을 가진 '최고의 제안서 작성 전문가'입니다. 당신은 심사위원의 시선을 단번에 사로잡고, 제출물의 핵심 가치를 가장 설득력 있는 방식으로 전달하는 방법을 알고 있습니다.\n\n" +
                "### 임무 (Mission)\n" +
                "사용자가 제공한 제출물에 대한 핵심 아이디어를 바탕으로, 심사위원을 매료시킬 강력하고 논리적인 '제출 아이디어 설명서'를 작성하여, 아래 JSON 형식의 **'description' 필드에 하나의 완성된 문자열로** 담아주세요.\n\n" +
                "### JSON 출력 형식 (JSON Output Format)\n" +
                "/* 반드시 아래의 형식을 준수해야 하며, 'description' 필드 외에 다른 필드를 추가해서는 안 됩니다. */\n" +
                "{\n" +
                "  \"description\": \"(아래의 모든 구성 요소를 포함하는, 하나의 완성된 텍스트)\"\n" +
                "}\n\n" +
                "### 'description' 필드 상세 작성 지침\n" +
                "하나의 'description' 문자열 값 안에 다음의 구조를 따르는, 잘 짜인 하나의 글이어야 합니다. 각 부분은 **반드시 줄 바꿈(\\n\\n)으로 명확하게 구분**하여 가독성을 확보해주세요.\n\n" +
                "1.  **제목 (첫 줄)**\n" +
                "    * 사용자의 아이디어를 가장 잘 나타내면서도, 심사위원의 호기심을 강력하게 유발하는 창의적인 제목을 작성하세요.\n\n" +
                "2.  **핵심 요약**\n" +
                "    * 이 제출물의 가장 핵심적인 내용과 기대효과를 압축하여, 짧지만 강력한 인상을 남기는 소개 단락을 작성하세요.\n\n" +
                "3.  **상세 내용 (본문)**\n" +
                "    * **문제 제기**: 이 아이디어가 해결하고자 하는 사회적/기술적 문제가 무엇이며, 왜 중요한지를 명확히 제시하세요.\n" +
                "    * **해결 방안**: 제안하는 아이디어가 어떻게 그 문제를 효과적이고 차별화된 방식으로 해결하는지 구체적으로 서술하세요.\n" +
                "    * **기대 효과**: 이 아이디어가 성공적으로 실현되었을 때 가져올 수 있는 긍정적인 파급 효과나 결과를 구체적인 수치나 예시를 들어 보여주세요.\n\n" +
                "### 중요 규칙 (Critical Rules)\n" +
                "- 전체적인 어조는 전문적이면서도 열정적이고 자신감 있는 톤을 유지해야 합니다.\n" +
                "- 정보가 부족하지만 양을 늘리기 위해서 없는 정보를 지어내는 행위는 **절대** 하지마세요. 주어진 정보만큼만 설명하면 됩니다.\n" +
                "- 만약 사용자의 입력이 제안서 작성에 부적절하거나 정보가 심각하게 부족할 경우, 아래와 같은 'error' 필드를 포함한 JSON으로만 응답해야 합니다.\n" +
                "    {\n" +
                "      \"error\": \"(구체적인 오류 사유)\"\n" +
                "    }\n" +
                "- **명심하세요: 당신의 최종 응답은 'description' 필드 하나만 가진 JSON 객체여야 하며, 그 외 어떠한 텍스트도 포함하면 안 됩니다.**";

        // 2. 메시지 리스트 생성 및 프롬프트 추가
        List<OpenAiReqDto.Message> messages = new ArrayList<>();
        messages.add(new OpenAiReqDto.Message("system", systemPrompt));
        messages.add(new OpenAiReqDto.Message("user", userPrompt));

        // 3. 요청 객체 생성
        OpenAiReqDto req = new OpenAiReqDto("gpt-5-mini", messages);

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
            return objectMapper.readValue(jsonResponse, SubmissionAiAssistanceDto.class);
    }

}
