package CoCoNut_was.domains.submission.service;

import CoCoNut_was.domains.submission.resdto.SubmissionAiAssistanceDto;
import CoCoNut_was.openai.OpenAiReqDto;
import CoCoNut_was.openai.OpenAiResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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
        String systemPrompt = "당신은 공모전에 대한 제출물 기획 전문가입니다. 사용자가 제공하는 공모전 제출물에 관한 정보를 바탕으로 공모전 제출 글을 써주세요., 반드시 아래와 같은 JSON 형식으로만 응답해야 합니다. 다른 설명은 절대 추가하지 마세요.\n" +
                "{\n" +
                "  \"description\": \"(사용자의 설명을 바탕으로 공모전 상세 설명을 200자 내외로 재생성)\",\n" +
                "}\n" +
                "만약 사용자의 입력이 부적절하거나 정보가 부족하여 추천이 불가능할 경우, 'error' 필드를 포함한 JSON으로 응답해주세요.\n" +
                "{\"error\": \"(오류 사유)\"}";

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

        // 7. JSON 응답을 DTO로 파싱
        return objectMapper.readValue(jsonResponse, SubmissionAiAssistanceDto.class);
    }

}
