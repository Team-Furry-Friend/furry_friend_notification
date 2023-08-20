package v3.furry_friend_notification.service;

import java.util.ArrayList;
import java.util.List;

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

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final TokenService tokenService;

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

    }
}
