package CoCoNut_was.openai;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api.key}")
    private String key;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public OpenAiResDto getChatResponse(List<OpenAiReqDto.Message> history) {
        // 1. 요청 객체 생성
        OpenAiReqDto req = new OpenAiReqDto("gpt-4.1-mini", history);

        // 2. HTTP 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(key);

        // 3. HTTP 요청 생성
        HttpEntity<OpenAiReqDto> httpEntity = new HttpEntity<>(req, headers);

        // 4. API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenAiResDto> res = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                httpEntity,
                OpenAiResDto.class
        );

        // 5. 예외 방어
        if(res.getBody() == null)
            throw new IllegalArgumentException("응답이 비어있거나 잘못됨");

        // 6. 응답에서 content 추출
        return res.getBody();
    }
}
