package uk.co.rnehru.featureswitchesspring.controller.errorhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uk.co.rnehru.featureswitchesspring.controller.SwitchToggleController;

/**
 * This class is responsible for handling exceptions thrown by the SwitchToggleController.
 */
@ControllerAdvice(assignableTypes = SwitchToggleController.class)
public final class ValidationError {

    /**
     * Default constructor for the ValidationError class.
     */
    public ValidationError() {

    }

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

}
