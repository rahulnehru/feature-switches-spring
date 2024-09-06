package uk.co.rnehru.featureswitchesspring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.rnehru.featureswitchesspring.TestApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TestApplication.class})
@AutoConfigureMockMvc
public final class FeatureSwitchesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetFeatureSwitches_returns200FeatureSwitchStates() throws Exception {
        String content = """
                {
                    "multilevel.switchC":{
                        "activationDateTime":"2020-01-01T00:00:00Z",
                        "on":true
                    },
                    "multilevel.switchD":{
                        "on":false
                    },
                    "switchB":{
                        "on":false
                    },
                    "switchA":{
                        "on":true
                    }
                }
                """;
        mockMvc.perform(get("/switch/status/context/default"))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    void testGetFeatureSwitches_returns404IfContextNotFound() throws Exception {
        mockMvc.perform(get("/switch/status/context/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetFeatureSwitch_returnsStatusOfBooleanSwitchWhenFound() throws Exception {
        mockMvc.perform(get("/switch/status/context/default/switchA"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "on":true
                        }
                        """));
    }

    @Test
    void testGetFeatureSwitch_returnsStatusOfMultiLevelBooleanSwitchWhenFound() throws Exception {
        mockMvc.perform(get("/switch/status/context/default/multilevel.switchD"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "on":false
                        }
                        """));
    }

    @Test
    void testGetFeatureSwitch_returnsStatusOfMultiLevelDateSwitchWhenFound() throws Exception {
        mockMvc.perform(get("/switch/status/context/default/multilevel.switchC"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "activationDateTime":"2020-01-01T00:00:00Z",
                            "on":true
                        }
                        """));
    }


}
