package CoCoNut_was.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/openai")
public class OpenAiController {
    private final OpenAiService openAiService;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(
            @RequestBody Map<String,String> userReq
    ) throws JsonProcessingException {
        OpenAiReqDto aiReq = new OpenAiReqDto("gpt-4.1-mini", new ArrayList<>());

        String userMsg = userReq.get("message");
        aiReq.getMessages().add(new OpenAiReqDto.Message("user", userMsg));

        OpenAiResDto aiRes = openAiService.getChatResponse(aiReq.getMessages());

        return ResponseEntity.ok(aiRes);
    }
}
