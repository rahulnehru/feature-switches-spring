package uk.co.rnehru.featureswitchesspring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.rnehru.featureswitchesspring.TestApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TestApplication.class}, properties = {"spring.profiles.active=toggling"})
@AutoConfigureMockMvc
public final class SwitchToggleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testToggleFeatureSwitch_returns200FeatureSwitchStates() throws Exception {
        mockMvc.perform(put("/toggle/context/default/switchA"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "on":false
                        }
                        """));
    }

    @Test
    void testToggleFeatureSwitch_returns404IfContextNotFound() throws Exception {
        mockMvc.perform(put("/toggle/context/unknown/switchA"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testToggleFeatureSwitch_returns404IfSwitchNotFound() throws Exception {
        mockMvc.perform(put("/toggle/context/default/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testToggleFeatureSwitch_returns400IfSwitchIsNotBoolean() throws Exception {
        mockMvc.perform(put("/toggle/context/default/multilevel.switchC"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testResetFeatureSwitch_returns200FeatureSwitchStates() throws Exception {
        mockMvc.perform(put("/toggle/context/default/switchA"))
                .andDo(result -> mockMvc.perform(put("/toggle/context/default/switchA/reset")))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "on":true
                        }
                        """));
    }

    @Test
    void testResetFeatureSwitch_returns404IfContextNotFound() throws Exception {
        mockMvc.perform(put("/toggle/context/unknown/switchA/reset"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testResetFeatureSwitch_returns404IfSwitchNotFound() throws Exception {
        mockMvc.perform(put("/toggle/context/default/unknown/reset"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testResetFeatureSwitch_returns400IfSwitchIsNotBoolean() throws Exception {
        mockMvc.perform(put("/toggle/context/default/multilevel.switchC/reset"))
                .andExpect(status().isBadRequest());
    }

}
