package v3.furry_friend_notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FurryFriendNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurryFriendNotificationApplication.class, args);
    }

}
