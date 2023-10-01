package v3.furry_friend_notification.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import v3.furry_friend_notification.entity.Notification;

@ActiveProfiles("test")
@SpringBootTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @DisplayName("확인하지 않은 상태를 가진 알림들을 조회한다.")
    @Test
    void findNotificationByRecipientId() {

        // given
        Notification notification1 = createNotification(3L, "soo", "댓글 내용1", "https://furry", false, 1L);
        Notification notification2 = createNotification(3L, "park", "채팅 내용1", "https://furry", false, 1L);

        notificationRepository.saveAll(List.of(notification1, notification2));
        // when
        List<Notification> notificationList = notificationRepository.findNotificationByRecipientId(1L);

        // then
        assertThat(notificationList).hasSize(2)
            .extracting("senderId", "senderName", "content", "notificationLink", "recipientId")
            .containsExactlyInAnyOrder(
                tuple(3L, "soo", "댓글 내용1", "https://furry", 1L),
                tuple(3L, "park", "채팅 내용1", "https://furry", 1L)
            );

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