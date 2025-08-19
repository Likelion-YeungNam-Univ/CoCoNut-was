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
    ROLE_NOT_MATCHED(403, "로그인된 계정이 해당 작업을 수행할 수 있는 역할이 아닙니다."),
    FORBIDDEN_USER(403, "해당 유저는 해당 요청에 대한 권한이 없습니다."),

    // 공모전 관련
    PROJECT_NOT_FOUND(404, "해당 공모전을 찾을 수 없습니다."),

    // 작품 관련
    SUBMISSION_NOT_FOUND(404, "해당 작품은 존재하지 않습니다."),
    SUBMISSION_USER_NOT_MATCHED(403, "작품의 유저정보와 로그인 정보가 일치하지 않습니다."),
    REQUIRED_SUBMISSION_INFO(400, "작품 정보 JSON과 Image 파일 모두 제공되지 않았습니다. 누락된 부분이 있는지 확인해주세요."),
    NOT_POSSIBLE_MORE_SUBMISSION(400, "이미 지원하신 공모전에 다시 지원할 수 없습니다."),
    IMAGE_UPLOAD_FAILED(500, "제출한 이미지 업로드에 예기치 않은 문제가 발생하였습니다."),
    DEADLINE_EXPIRED(400, "제출기한이 이미 지났습니다."),


    // 투표 관련
    INVALID_DUPLICATE_VOTE(400, "한 공모전에 대한 작품에 이미 투표하셨습니다."),
    INVALID_OWN_PROJECT_VOTE(400, "공모전을 올린 소상공인은 해당 작품들에 대해 투표할 수 없습니다."),
    INVALID_SELF_VOTE(400, "참여자는 자신이 제출한 작품에 투표할 수 없습니다."),

    // AI 관련
    AI_GENERATION_FAILED(400, "AI Assistance 응답 생성에 실패하였습니다. 입력한 정보가 정확한지 확인해주세요"),
    PROMPT_IS_BLANK(400, "프롬프트 내용이 없습니다. 제대로 입력해주세요.");



    private final int status;
    private final String message;
}
