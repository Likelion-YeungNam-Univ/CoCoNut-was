package CoCoNut_was.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
    EMAIL_ALREADY_EXIST(409, "해당 이메일은 이미 존재합니다"),
    NICKNAME_ALREADY_EXIST(409, "해당 닉네임은 이미 존재합니다."),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류입니다.");

    private final int status;
    private final String message;
}
