package v3.furry_friend_notification.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v3.furry_friend_notification.common.entity.BaseEntity;
import v3.furry_friend_notification.service.dto.NotificationResponseDTO;

@Entity
@Getter
@NoArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private Long senderId;
    private String senderName;
    private String content;
    private String notificationLink;

    private boolean notificationRead;
    private Long recipientId;

    @Builder
    public Notification(Long senderId, String senderName, String content, String notificationLink, boolean notificationRead, Long recipientId) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.content = content;
        this.notificationLink = notificationLink;
        this.notificationRead = notificationRead;
        this.recipientId = recipientId;
    }

    public NotificationResponseDTO entityToDTO(Notification notification){

        return NotificationResponseDTO.builder()
            .notificationId(notification.getNotificationId())
            .content(notification.getContent())
            .senderId(notification.getSenderId())
            .senderName(notification.getSenderName())
            .recipientId(notification.getRecipientId())
            .notificationLink(notification.getNotificationLink())
            .notificationRead(notification.isNotificationRead())
            .regDate(notification.getRegDate())
            .build();
    }

    public void setRead(boolean notificationRead){
        this.notificationRead = notificationRead;
    }
}
