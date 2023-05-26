package dev.checku.checkuserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.checku.checkuserver.domain.notification.api.NotificationApi;
import dev.checku.checkuserver.domain.notification.application.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        NotificationApi.class,
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected NotificationService notificationService;
}
