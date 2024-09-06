package uk.co.rnehru.featureswitchesspring.controller.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.rnehru.featureswitchesspring.TestApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.rnehru.featureswitchesspring.controller.interceptor.ProfileInterceptor.INVALID_PROFILE_ERR;

@SpringBootTest(classes = TestApplication.class, properties = {"spring.profiles.active=production"})
@AutoConfigureMockMvc
public final class ProfileTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void toggleFeatureSwitch_returns501WhenProfileIsNotToggling() throws Exception {
        mockMvc.perform(put("/toggle/context/default/switchA"))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string(INVALID_PROFILE_ERR));
    }

    @Test
    void resetFeatureSwitch_returns501WhenProfileIsNotToggling() throws Exception {
        mockMvc.perform(put("/toggle/context/default/switchA/reset"))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string(INVALID_PROFILE_ERR));
    }

    @Test
    void setTime_returns501WhenProfileIsNotToggling() throws Exception {
        String content = "{\"time\": \"2021-01-01T11:11:11.111Z\", \"running\": false}";
        mockMvc.perform(put("/time-travel/set").content(content).contentType("application/json"))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string(INVALID_PROFILE_ERR));
    }

    @Test
    void resetTime_returns501WhenProfileIsNotToggling() throws Exception {
        mockMvc.perform(delete("/time-travel/reset"))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string(INVALID_PROFILE_ERR));
    }

}
