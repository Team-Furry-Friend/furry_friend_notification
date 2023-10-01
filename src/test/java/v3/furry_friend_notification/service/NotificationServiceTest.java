package v3.furry_friend_notification.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import v3.furry_friend_notification.entity.Notification;
import v3.furry_friend_notification.repository.NotificationRepository;

@SpringBootTest
@ActiveProfiles("test")
class NotificationServiceTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAllInBatch();
    }

    @DisplayName("사용자가 알림을 조회할 때 알림 읽음 처리와 함께 알림을 반환한다.")
    @Test
    void getMemberNotification() {

        // given
        Notification notification1 = createNotification(3L, "soo", "댓글 내용1", "https://furry", false, 1L);
        Notification notification2 = createNotification(3L, "park", "채팅 내용1", "https://furry", false, 1L);
        notificationRepository.saveAll(List.of(notification1, notification2));

        List<Notification> notificationList = notificationRepository.findNotificationByRecipientId(1L);

        // when
        for (Notification value : notificationList) {
            value.setRead(true);
        }

        // then
        assertThat(notificationList).hasSize(2)
            .extracting("senderName", "notificationRead")
            .contains(
                tuple("soo", true),
                tuple("park", true)
            );
    }

    @DisplayName("다른 애플리케이션으로부터 요청을 받아 새로운 알림을 생성한다.")
    @Test
    void saveNotification() {

        // given
        Notification notification1 = createNotification(3L, "soo", "댓글 내용1", "https://furry", false, 1L);
        Notification notification2 = createNotification(3L, "park", "채팅 내용1", "https://furry", false, 1L);

        // when
        notificationRepository.saveAll(List.of(notification1, notification2));
        List<Notification> notificationList = notificationRepository.findNotificationByRecipientId(1L);

        // then
        assertThat(notificationList).hasSize(2);
    }

    private Notification createNotification(Long senderId, String senderName, String content, String notificationLink, boolean notificationRead, Long recipiendId){
        return Notification.builder()
            .senderId(senderId)
            .senderName(senderName)
            .content(content)
            .notificationLink(notificationLink)
            .notificationRead(notificationRead)
            .recipientId(recipiendId)
            .build();
    }
}