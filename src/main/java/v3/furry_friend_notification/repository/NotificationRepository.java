package v3.furry_friend_notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import v3.furry_friend_notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.recipientId = :recipientId and n.notificationRead = false")
    List<Notification> findNotificationByRecipientId(@Param("recipientId") Long recipientId);

}
