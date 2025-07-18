package uk.co.rnehru.featureswitchesspring.controller.errorhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uk.co.rnehru.featureswitchesspring.controller.SwitchToggleController;
import uk.co.rnehru.featureswitchesspring.controller.TimeTravelController;

/**
 * This class is responsible for handling exceptions thrown by the SwitchToggleController and TimeTravelController.
 */
@ControllerAdvice(assignableTypes = {SwitchToggleController.class, TimeTravelController.class})
public final class ValidationError {

    /**
     * Handles when a validation error occurs, i.e. when a user tries to turn on a feature switch that is time based.
     *
     * @param ae      the exception that was thrown
     * @param request the request that was made
     * @return a response entity with a 400 status code
     */
    @ExceptionHandler(AssertionError.class)
    public ResponseEntity<Object> handleValidationError(final AssertionError ae,
                                                        final WebRequest request) {
        return ResponseEntity
                .status(400)
                .body("Cannot use this endpoint to turn on time based switches, use time travelling endpoint");
    }

    /**
     * Handles IllegalArgumentException for invalid arguments.
     *
     * @param ex      the exception that was thrown
     * @param request the request that was made
     * @return a response entity with a 400 status code
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException ex,
                                                        final WebRequest request) {
        return ResponseEntity
                .status(400)
                .body(ex.getMessage());
    }

}
