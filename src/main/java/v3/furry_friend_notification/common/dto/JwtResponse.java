package v3.furry_friend_notification.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {

    private Long memberId;
    private String memberName;
}
