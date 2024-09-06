package uk.co.rnehru.featureswitchesspring.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.function.ThrowingConsumer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.rnehru.featureswitches.api.FeatureSwitches;
import uk.co.rnehru.featureswitches.model.BooleanSwitch;
import uk.co.rnehru.featureswitches.model.Switch;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This class is responsible for handling requests related to toggling the state of the feature switches.
 */
@RestController
@RequestMapping("/toggle")
public final class SwitchToggleController extends AbstractFeatureSwitchController {

    private final FeatureSwitches switches;
    private static final Logger LOGGER = getLogger(SwitchToggleController.class);


    /**
     * This constructor autowires the FeatureSwitches bean.
     *
     * @param switches the FeatureSwitches bean
     */
    @Autowired
    public SwitchToggleController(final FeatureSwitches switches) {
        this.switches = switches;
    }

    /**
     * This method toggles a feature switch.
     * @param context    the context for which the feature switch is required
     * @param switchName the name of the feature switch
     * @return a response entity with the feature switch
     */
    @PutMapping("/context/{context}/{switchName}")
    public ResponseEntity<Switch> toggleFeatureSwitch(@PathVariable final String context,
                                                      @PathVariable final String switchName) {
        Switch featureSwitch = switches.getSwitch(appendSwitchesToContextName(context), switchName);
        validateIsBoolean.accept(featureSwitch);
        if (featureSwitch.isOn()) {
            ((BooleanSwitch) featureSwitch).turnOff();
            LOGGER.info("Switch {} in context {} has been turned off", switchName, context);
        } else {
            ((BooleanSwitch) featureSwitch).turnOn();
            LOGGER.info("Switch {} in context {} has been turned on", switchName, context);
        }
        return ResponseEntity.ok(featureSwitch);
    }

    /**
     * This method resets a feature switch to its default state.
     * @param context the context for which the feature switch is required
     * @param switchName the name of the feature switch
     * @return a response entity with the feature switch
     */
    @PutMapping("/context/{context}/{switchName}/reset")
    public ResponseEntity<Switch> resetFeatureSwitch(@PathVariable final String context,
                                                     @PathVariable final String switchName) {
        Switch featureSwitch = switches.getSwitch(appendSwitchesToContextName(context), switchName);
        validateIsBoolean.accept(featureSwitch);
        ((BooleanSwitch) featureSwitch).reset();
        LOGGER.info("Switch {} in context {} has been reset", switchName, context);
        return ResponseEntity.ok(featureSwitch);
    }

    private final ThrowingConsumer<Switch> validateIsBoolean = (Switch s) -> {
        assert s instanceof BooleanSwitch;
    };

}
