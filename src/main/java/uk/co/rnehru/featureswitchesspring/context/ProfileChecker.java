package uk.co.rnehru.featureswitchesspring.context;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.co.rnehru.featureswitchesspring.controller.interceptor.ProfileInterceptor;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Configuration checks if the `toggling` profile is active and adds an interceptor to the application.
 */
@AutoConfiguration
public class ProfileChecker implements WebMvcConfigurer {

    private static final Logger LOGGER = getLogger(ProfileChecker.class);

    private final boolean isTogglingActive;

    /**
     * This constructor autowires the Environment bean and checks if the `toggling` profile is active.
     * @param env the Environment bean
     */
    public ProfileChecker(@Autowired final Environment env) {
        this.isTogglingActive = env.matchesProfiles("toggling");
        if (this.isTogglingActive) {
            LOGGER.info("Feature `toggling` is enabled.");
        }
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new ProfileInterceptor(this.isTogglingActive));
    }

}
