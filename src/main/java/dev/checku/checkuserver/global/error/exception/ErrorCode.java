package dev.checku.checkuserver.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 알림
    NOTIFICATION_SEND_FAILED(400, "알림 전송에 실패하였습니다."),
    NOTIFICATION_NOT_FOUND(400, "해당 알림은 존재하지 않습니다."),
    NOTIFICATION_ALREADY_REGISTERED(400, "해당 과목은 이미 신청하였습니다."),
    SUBJECT_HAS_VACANCY(400, "해당 과목은 빈 자리가 남아있습니다."),

    // 구독
    TOPIC_SUBSCRIBE_FAILED(400, "구독에 실패하였습니다."),
    TOPIC_UNSUBSCRIBE_FAILED(400, "구독 취소에 실패하였습니다."),

    // 유저
    USER_NOT_FOUND(400, "해당 회원은 존재하지 않습니다."),

    // 토픽
    TOPIC_NOT_FOUND(400, "해당 토픽은 존재하지 않습니다"),

    // 과목
    SUBJECT_NOT_FOUND(400, "해당 과목을 찾을 수 없습니다."),
    MY_SUBJECT_NOT_FOUND(400, "해당 과목을 찾을 수 없습니다."),

    NETWORK_ERROR(400, "네트워크 연결이 원활하지 않습니다. 다시 시도해주세요."),

    // 세션
    SESSION_NOT_FOUND(400, "해당 세션을 찾을 수 없습니다.");


    private final int status;
    private final String message;

}

