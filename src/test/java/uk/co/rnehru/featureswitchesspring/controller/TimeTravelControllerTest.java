package uk.co.rnehru.featureswitchesspring.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.rnehru.featureswitches.clock.TimeTravel;
import uk.co.rnehru.featureswitchesspring.TestApplication;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.allOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestApplication.class, properties = {"spring.profiles.active=toggling"})
@AutoConfigureMockMvc
public final class TimeTravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TimeTravel.CLOCK.reset();
    }

    @Test
    void testGetCurrentTime_returns200WhenNotSet() throws Exception {
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        mockMvc.perform(get("/time-travel/now"))
                .andExpect(status().isOk())
                .andExpect(content().string(allOf(
                        Matchers.containsString("{\"time\":\""),
                        Matchers.containsString("\",\"running\":true}"),
                        Matchers.containsString(now)
                )));
    }

    @Test
    void testSetCurrentTime_returns200WhenSetWithRunningFalse() throws Exception {

        String content = "{\"time\": \"2021-01-01T11:11:11.111Z\", \"running\": false}";
        mockMvc.perform(put("/time-travel/set").content(content).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
        Thread.sleep(1000);
        mockMvc.perform(get("/time-travel/now"))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    void testSetCurrentTime_returns200WhenSetWithRunningTrue() throws Exception {
        String date = "2021-01-01T11:11:11.111Z";
        String content = "{\"time\": \"" + date + "\", \"running\": true}";
        mockMvc.perform(put("/time-travel/set").content(content).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(allOf(
                        Matchers.containsString("{\"time\":\"2021-01-01T11:11"),
                        Matchers.containsString("\",\"running\":true}")
                )));
    }

    @Test
    void testSetTime_returns200WhenInTheFuture() throws Exception {
        String date = "2030-01-01T11:11:11.111Z";
        String content = "{\"time\": \"" + date + "\", \"running\": false}";
        mockMvc.perform(put("/time-travel/set").content(content).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(allOf(
                        Matchers.containsString("{\"time\":\""),
                        Matchers.containsString("\",\"running\":false}"),
                        Matchers.containsString(date)
                )));
    }

    @Test
    void testSetTime_returns400WhenBadTime() throws Exception {
        String content = "foo";
        mockMvc.perform(put("/time-travel/set").content(content).contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testResetTime_returns200() throws Exception {
        String now = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        mockMvc.perform(delete("/time-travel/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string(allOf(
                        Matchers.containsString("{\"time\":\""),
                        Matchers.containsString("\",\"running\":true}"),
                        Matchers.containsString(now)
                )));
    }


}
