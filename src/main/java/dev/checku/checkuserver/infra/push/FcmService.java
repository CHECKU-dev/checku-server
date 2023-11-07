package dev.checku.checkuserver.infra.push;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.infra.push.exception.NotificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmService {

    private FirebaseMessaging firebaseMessaging;

    public boolean subscribeToTopic(String fcmToken, String subjectNumber) {

        try {
            firebaseMessaging.subscribeToTopic(List.of(fcmToken), subjectNumber);
        } catch (FirebaseMessagingException e) {
            throw new NotificationFailedException(ErrorCode.TOPIC_SUBSCRIBE_FAILED);
        }
        return true;
    }

    public void unsubscribeToTopic(String fcmToken, SubjectNumber subjectNumber) {
        try {
            firebaseMessaging.unsubscribeFromTopic(List.of(fcmToken), subjectNumber.getValue());
        } catch (FirebaseMessagingException e) {
            throw new NotificationFailedException(ErrorCode.TOPIC_UNSUBSCRIBE_FAILED);
        }
    }

    public boolean sendMessageToSubscriber(String topic, String title, String body, List<String> tokens) {
        try {
            Notification notification = Notification.builder().setTitle(title).setBody(body).build();
            Message msg = Message.builder().setTopic(topic).putData("body", body).setNotification(notification).build();

            firebaseMessaging.send(msg);
            firebaseMessaging.unsubscribeFromTopic(tokens, topic);
        }
        catch (FirebaseMessagingException e) {
            throw new NotificationFailedException(ErrorCode.NOTIFICATION_SEND_FAILED);
        }
        return true;
    }

    @PostConstruct
    public void firebaseSetting() throws IOException {
//        String firebaseConfigPath = "firebase/firebase_service_key.json";
//
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
//                .setCredentials(googleCredentials)
//                .build();
//        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
//        this.firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

    public void sendMessageTo(String targetToken, String title, String body) {

        try {
            Notification notification = Notification.builder().setTitle(title).setBody(body).build();

            Message msg = Message.builder().putData("body", body).setToken(targetToken).setNotification(notification).build();
            this.firebaseMessaging.send(msg);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

    }

}
