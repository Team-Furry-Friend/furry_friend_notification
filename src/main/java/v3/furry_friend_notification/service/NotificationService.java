package v3.furry_friend_notification.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import v3.furry_friend_notification.common.dto.JwtResponse;
import v3.furry_friend_notification.common.service.TokenService;
import v3.furry_friend_notification.entity.Notification;
import v3.furry_friend_notification.repository.NotificationRepository;
import v3.furry_friend_notification.service.dto.NotificationRequestDTO;
import v3.furry_friend_notification.service.dto.NotificationResponseDTO;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final TokenService tokenService;

    private final FirebaseMessaging firebaseMessaging;

    // 알림 조회
    @Async
    public List<NotificationResponseDTO> getMemberNotification(String accessToken){

        try {
            JwtResponse jwtResponse = tokenService.getMember(accessToken);
            List<Notification> notificationList = notificationRepository.findNotificationByRecipientId(jwtResponse.getMemberId());

            // 조회 시 읽음 처리
            notificationRead(notificationList);

            List<NotificationResponseDTO> notificationResponseDTOList = new ArrayList<>();

            notificationList.forEach(notification -> {
                notificationResponseDTOList.add(notification.entityToDTO(notification));
            });

            return notificationResponseDTOList;
        }catch (Exception e){
            log.error("알림 조회 실패: " + e.getMessage(), e);
            throw new RuntimeException("알림 조회 실패: " + e.getMessage());
        }
    }

    // 읽음 처리
    @Async
    public void notificationRead(List<Notification> notificationIdList){

        try {

            List<Notification> notificationList = new ArrayList<>();

            notificationIdList.forEach(notification -> {
                notification.setRead(true);
                notificationList.add(notification);
            });

            notificationRepository.saveAll(notificationList);
        }catch (Exception e){
            log.error("읽음 처리 실패: " + e.getMessage(), e);
            throw new RuntimeException("읽음 처리 실패: " + e.getMessage());
        }
    }

    // 알림 저장
    public void saveNotification(NotificationRequestDTO notificationRequestDTO){

        try {

            // 토큰 검증 및 알림 생성
            JwtResponse jwtResponse = tokenService.getMember(notificationRequestDTO.getAccessToken());
            Notification notification = notificationRequestDTO.dtoToEntity(notificationRequestDTO, jwtResponse);

            // 알림 저장
            notificationRepository.save(notification);

            // 알림 전송
            sendNotification(notificationRequestDTO);
        }catch (Exception e){

            log.error("" + e.getMessage(), e);
            throw new RuntimeException("" + e.getMessage());
        }
    }

    public void sendNotification(NotificationRequestDTO notificationRequestDTO) throws
        Exception {
        Message message = Message.builder()
            .setToken(notificationRequestDTO.getFirebaseToken())
            .putData("recipientId", Long.toString(notificationRequestDTO.getRecipientId()))
            .putData("content", notificationRequestDTO.getContent())
            .putData("link", notificationRequestDTO.getNotificationLink())
            .putData("time", String.valueOf(LocalDateTime.now()))
            .build();

        try {
            firebaseMessaging.send(message);
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            log.info("알림 전송: " + response);
        }catch (FirebaseMessagingException e){
            log.error("FirebaseMessaging 에러" + e.getMessage(), e);
            throw new Exception("FirebaseMessaging 에러" + e.getMessage());
        }
    }
}
