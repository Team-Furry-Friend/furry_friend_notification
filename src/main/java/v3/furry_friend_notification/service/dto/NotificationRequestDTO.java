package v3.furry_friend_notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class NotificationRequestDTO {

    // 발신자 아이디, 예시: 채팅 발신자, 댓글 작성자
    private String senderId;
    private String content;
}
