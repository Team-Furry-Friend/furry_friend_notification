package v3.furry_friend_notification.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long notificationId;

    private Long senderId;
    private Long senderName;
    private String content;
    private String notificationLink;

    private boolean read;
    private Long recipientId;

    private LocalDateTime regDate;
}