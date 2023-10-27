package v3.furry_friend_notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v3.furry_friend_notification.common.dto.JwtResponse;
import v3.furry_friend_notification.entity.Notification;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotificationRequestDTO {

    // 발신자 아이디, 예시: 채팅 수신자, 게시글 작성자
    private Long recipientId;
    private String content;
    private String notificationLink;
    private String accessToken;
    private String firebaseToken;

    @Builder
    public Notification dtoToEntity(NotificationRequestDTO notificationRequestDTO, JwtResponse jwtResponse){

        return Notification.builder()
            .senderId(jwtResponse.getMemberId())
            .senderName(jwtResponse.getMemberName())
            .recipientId(notificationRequestDTO.getRecipientId())
            .content(notificationRequestDTO.getContent())
            .notificationLink(notificationRequestDTO.getNotificationLink())
            .notificationRead(false)
            .build();
    }
}
