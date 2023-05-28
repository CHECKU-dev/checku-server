package dev.checku.checkuserver.domain.notification.api;

import dev.checku.checkuserver.ControllerTestSupport;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationRegisterReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationSearchDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationSendReq;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationApiTest extends ControllerTestSupport {

    @Autowired
    NotificationRepository notificationRepository;

    @Test
    @DisplayName("신규 알림을 등록한다.")
    void registerNotification() throws Exception {
        // given
        NotificationRegisterReq request = NotificationRegisterReq.builder()
                .userId(1L)
                .subjectNumber("0001")
                .subjectName("모바일 프로그래밍")
                .professor("professor")
                .build();

        // when then
        mockMvc.perform(
                        post("/api/notification")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("토픽을 구독한 사용자에게 알림을 전송한다.")
    void sendNotification() throws Exception {
        // given
        NotificationSendReq request = NotificationSendReq.builder()
                .topic("dummy")
                .build();

        // when then
        mockMvc.perform(post("/api/notification/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @DisplayName("유저가 신청한 알림을 가져온다.")
    void getNotifications() throws Exception {
        // given
        List<NotificationSearchDto.Response> result = List.of();
        given(notificationService.getNotificationsByUserId(any())).willReturn(result);

        // when & then
        mockMvc.perform(
                        get("/api/notification?userId={userId}", "test")
//                                .queryParam("userId", "1L")
                )
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data").isArray());
    }


}