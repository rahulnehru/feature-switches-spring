package uk.co.rnehru.featureswitchesspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.rnehru.featureswitches.api.FeatureSwitches;
import uk.co.rnehru.featureswitches.model.Switch;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * This class is responsible for handling requests related to tne current state of the feature switches.
 */
@RestController
@RequestMapping("/switch/status")
public final class FeatureSwitchesController extends AbstractFeatureSwitchController {

    private final FeatureSwitches switches;

    /**
     * This constructor autowires the FeatureSwitches bean.
     * @param switches the FeatureSwitches bean.
     */
    @Autowired
    public FeatureSwitchesController(final FeatureSwitches switches) {
        this.switches = switches;
    }

    /**
     * Returns all the feature switches for a given context.
     * @param context the context for which the feature switches are required
     * @return the response entity with the feature switches
     */
    @GetMapping("/context/{context}")
    public ResponseEntity<Map<String, Switch>> getFeatureSwitches(@PathVariable final String context) {
        return ok(switches.getAllSwitches(appendSwitchesToContextName(context)));
    }

    /**
     * Returns a specific feature switch for a given context.
     * @param context the context for which the feature switch is required
     * @param switchName the name of the feature switch
     * @return the response entity with the feature switch
     */
    @GetMapping("context/{context}/{switchName}")
    public ResponseEntity<Switch> getFeatureSwitch(@PathVariable final String context,
                                                   @PathVariable final String switchName) {
        return ok(switches.getSwitch(appendSwitchesToContextName(context), switchName));
    }

}
