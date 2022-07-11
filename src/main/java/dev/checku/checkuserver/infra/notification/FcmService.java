package dev.checku.checkuserver.infra.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import dev.checku.checkuserver.global.exception.ErrorCode;
import dev.checku.checkuserver.infra.notification.exception.NotificationFailedException;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FcmClient fcmClient;

    private final String CONTENT_TYPE = "application/json; UTF-8";

    private FirebaseMessaging firebaseMessaging;

    public void subscribeToTopic(String fcmToken, String subjectNumber) {

        List<String> registrationTokens = new ArrayList<>();

        try {
            registrationTokens.add(fcmToken);
            firebaseMessaging.subscribeToTopic(registrationTokens, subjectNumber);
        } catch (FirebaseMessagingException e) {
            throw new NotificationFailedException(ErrorCode.SUBSCRIBE_FAILED);
        }
    }

    public void sendTopicMessage(String topic, String title, String body, List<String> tokens) {
        try {
//            Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
            Notification notification = Notification.builder().setTitle(title).setBody(body).build();

            Message msg = Message.builder().setTopic(topic).setNotification(notification).build();

            firebaseMessaging.send(msg);
            firebaseMessaging.unsubscribeFromTopic(tokens, topic);

        }
        catch (FirebaseMessagingException e) {
            throw new NotificationFailedException(ErrorCode.NOTIFICATION_FAILED);
        }

    }

    @PostConstruct
    public void firebaseSetting() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

//    public void sendMessageTo(String targetToken, String title, String body, String type) {
//
//        try {
//            FcmMessage message = makeMessage(targetToken, title, body, type);
//            fcmClient.requestNotification(CONTENT_TYPE, "Bearer " + getAccessToken(), message);
//        } catch (IOException e) {
//            throw new NotificationFailedException(ErrorCode.NOTIFICATION_FAILED);
//        }
//
//    }

//    private FcmMessage makeMessage(String targetToken, String title, String body, String type) {
//        FcmMessage message = FcmMessage.of(targetToken, title, body, type);
//        return message;
//    }

//    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase/firebase_service_key.json";
//
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }

}