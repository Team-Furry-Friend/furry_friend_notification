package v3.furry_friend_notification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import v3.furry_friend_notification.common.ApiResponse;
import v3.furry_friend_notification.service.NotificationService;
import v3.furry_friend_notification.service.dto.NotificationRequestDTO;
import v3.furry_friend_notification.service.dto.NotificationResponseDTO;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public ApiResponse<List<NotificationResponseDTO>> getNotification(@RequestHeader(value = "Authorization") String accessToken){

        try {
            List<NotificationResponseDTO> notificationResponseDTOList = notificationService.getMemberNotification(accessToken);
            return ApiResponse.success("알림 조회 성공", notificationResponseDTOList);
        }catch (Exception e){
            log.error("알림 조회 실패: " + e.getMessage(), e);
            return ApiResponse.fail(400, "알림 조회 실패: " + e.getMessage());
        }

    }

    @PostMapping("")
    public ApiResponse sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {

        try {
            notificationService.saveNotification(notificationRequestDTO);
            return ApiResponse.success("알림 저장 성공");
        }catch (Exception e){
            log.error("알림 저장 실패: " + e.getMessage(), e);
            return ApiResponse.fail(400, "알림 저장 실패: " + e.getMessage());
        }
    }
}
