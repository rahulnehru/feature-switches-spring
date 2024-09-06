package uk.co.rnehru.featureswitchesspring.controller.errorhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uk.co.rnehru.featureswitches.api.ContextNotFoundException;
import uk.co.rnehru.featureswitches.api.SwitchNotFoundException;
import uk.co.rnehru.featureswitchesspring.controller.FeatureSwitchesController;
import uk.co.rnehru.featureswitchesspring.controller.SwitchToggleController;

import java.util.function.Supplier;

/**
 * This class is responsible for handling exceptions thrown by the FeatureSwitchesController and SwitchToggleController.
 */
@ControllerAdvice(assignableTypes = {FeatureSwitchesController.class, SwitchToggleController.class})
public final class ContextOrSwitchNotFound {

    /**
     * Default constructor for the ContextOrSwitchNotFound class.
     */
    public ContextOrSwitchNotFound() {

    }

    /**
     * Handles when a context is not found.
     * @param ex     the exception that was thrown
     * @param request the request that was made
     * @return a response entity with a 404 status code
     */
    @ExceptionHandler(ContextNotFoundException.class)
    public ResponseEntity<Object> handleContextNotFound(final RuntimeException ex,
                                                        final WebRequest request) {
        return returnNotFound.get();
    }

    /**
     * Handles when a switch is not found.
     * @param ex     the exception that was thrown
     * @param request the request that was made
     * @return a response entity with a 404 status code
     */
    @ExceptionHandler(SwitchNotFoundException.class)
    public ResponseEntity<Object> handleSwitchNotFound(final RuntimeException ex,
                                                       final WebRequest request) {
        return returnNotFound.get();
    }

    private final Supplier<ResponseEntity<Object>> returnNotFound = () ->
            ResponseEntity
                    .status(404)
                    .body("Could not find the feature switch");
}
