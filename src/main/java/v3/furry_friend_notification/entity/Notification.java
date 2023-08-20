package v3.furry_friend_notification.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import v3.furry_friend_notification.common.entity.BaseEntity;
import v3.furry_friend_notification.service.dto.NotificationResponseDTO;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private Long senderId;
    private Long senderName;
    private String content;
    private String notificationLink;
    private boolean read = false;
    private Long recipientId;

    public NotificationResponseDTO entityToDTO(Notification notification){

        return NotificationResponseDTO.builder()
            .notificationId(notification.getNotificationId())
            .content(notification.getContent())
            .senderId(notification.getSenderId())
            .senderName(notification.getSenderName())
            .recipientId(notification.getRecipientId())
            .notificationLink(notification.getNotificationLink())
            .read(notification.isRead())
            .regDate(notification.getRegDate())
            .build();
    }

    public void setRead(boolean read){
        this.read = read;
    }
}
