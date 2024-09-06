package uk.co.rnehru.featureswitchesspring.context;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import uk.co.rnehru.featureswitches.api.FeatureSwitches;

/**
 * This class is responsible for creating the FeatureSwitches bean.
 * This bean is used to manage the feature switches.
 */
@Component
public final class SwitchesContext {

    /**
     * This method creates the FeatureSwitches bean which can be autowired into other classes.
     * @return the FeatureSwitches bean
     */
    @Bean
    public FeatureSwitches switches() {
        return FeatureSwitches.getInstance();
    }

    /**
     * Default constructor for the SwitchesContext class.
     */
    public SwitchesContext() {

    }

}
