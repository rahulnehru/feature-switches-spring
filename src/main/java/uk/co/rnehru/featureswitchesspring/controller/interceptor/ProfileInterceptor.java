package uk.co.rnehru.featureswitchesspring.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for intercepting requests and checking if the `toggling` profile is active.
 */
public class ProfileInterceptor implements HandlerInterceptor {

    private final boolean togglingEnabled;
    static final String INVALID_PROFILE_ERR =
            "Feature `toggling` is not enabled. Please enable it in Spring profiles to use this endpoint.";

    /**
     * This constructor autowires the Environment bean and checks if the `toggling` profile is active.
     * @param togglingEnabled whether the `toggling` profile is active
     */
    public ProfileInterceptor(boolean togglingEnabled) {
        this.togglingEnabled = togglingEnabled;
    }

    private final List<String> lockedEndpoints = List.of("time-travel", "toggle");

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if (!this.togglingEnabled && uriIsLockedPath(req.getRequestURI())) {
            res.setStatus(501);
            res.getWriter().write(INVALID_PROFILE_ERR);
            return false;
        } else {
            return true;
        }
    }

    private boolean uriIsLockedPath(String uri) {
        return Arrays.stream(uri.split("/")).anyMatch(lockedEndpoints::contains);
    }

}
