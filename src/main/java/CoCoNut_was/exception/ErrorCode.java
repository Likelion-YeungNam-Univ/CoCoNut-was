package CoCoNut_was.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 유저 관련
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    EMAIL_ALREADY_EXIST(409, "해당 이메일은 이미 존재합니다"),
    NICKNAME_ALREADY_EXIST(409, "해당 닉네임은 이미 존재합니다."),
    INCORRECT_PASSWORD(401, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류입니다."),

    // 공모전 관련
    PROJECT_NOT_FOUND(404, "해당 공모전을 찾을 수 없습니다."),
    AI_GENERATION_FAILED(400, "AI Assistance 응답 생성에 실패하였습니다. 입력한 정보가 정확한지 확인해주세요"),


    // 공모전 제출물 관련
    SUBMISSION_NOT_FOUND(404, "해당 작품은 존재하지 않습니다."),
    SUBMISSION_USER_NOT_MATCHED(403, "작품의 유저정보와 로그인 정보가 일치하지 않습니다."),
    REQUIRED_SUBMISSION_INFO(400, "제출물 형태에 대해 누락이 있습니다. 반드시 KEY에 info를 포함하고, " +
            "VALUE에 JSON값을, Content-Type를 application/json으로 설정해주세요."),
    IMAGE_UPLOAD_FAILED(500, "제출한 이미지 업로드에 예기치 않은 문제가 발생하였습니다."),


    // AI 관련
    PROMPT_IS_BLANK(400, "프롬프트 내용이 없습니다. 제대로 입력해주세요.");



    private final int status;
    private final String message;
}
