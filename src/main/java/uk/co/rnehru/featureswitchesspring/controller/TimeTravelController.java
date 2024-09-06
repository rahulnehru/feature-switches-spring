package uk.co.rnehru.featureswitchesspring.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.rnehru.featureswitches.api.FeatureSwitches;
import uk.co.rnehru.featureswitches.model.DateTimeSwitch;
import uk.co.rnehru.featureswitches.model.Switch;

import java.time.ZonedDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;
import static uk.co.rnehru.featureswitches.clock.TimeTravel.CLOCK;
import static uk.co.rnehru.featureswitchesspring.controller.TimeTravelController.TimeTravelDto.returnCurrentClock;

/**
 * This class is responsible for handling requests related to time travel.
 */
@RestController
@RequestMapping("/time-travel")
public final class TimeTravelController {

    private final FeatureSwitches switches;
    private static final Logger LOGGER = getLogger(TimeTravelController.class);

    /**
     * This constructor autowires the FeatureSwitches bean.
     * @param switches the FeatureSwitches bean
     */
    @Autowired
    public TimeTravelController(final FeatureSwitches switches) {
        this.switches = switches;
    }

    /**
     * Returns the current time for time based feature switches.
     * @return the response entity with the current time.
     */
    @GetMapping("/now")
    public ResponseEntity<TimeTravelDto> getCurrentTime() {
        return ok(returnCurrentClock());
    }

    /**
     * This method sets the time to activate or deactivate time based feature switches.
     * @param request the time to set, and whether the clock should be running
     * @return a response entity with the time that was set
     */
    @PutMapping("/set")
    @Profile("toggling")
    public ResponseEntity<TimeTravelDto> setTime(@RequestBody TimeTravelDto request) {
        LOGGER.info("Setting time to " + request.time() + " and clock running" + (request.running() ? "on" : "off"));
        CLOCK.setTime(request.time(), request.running());
        logSwitchesActivated();
        return ok(returnCurrentClock());
    }

    /**
     * This method resets the time to the default server time and turns the clock back on.
     * @return a response entity with the current time
     */
    @DeleteMapping("/reset")
    @Profile("toggling")
    public ResponseEntity<TimeTravelDto> resetTime() {
        LOGGER.info("Resetting the clock to server time");
        CLOCK.reset();
        logSwitchesActivated();
        return ok(returnCurrentClock());
    }


    /**
     * This class is a data transfer object for the time travel controller.
     * @param time the time to set
     * @param running whether the clock should be running
     */
    public record TimeTravelDto(ZonedDateTime time, boolean running) {
        static TimeTravelDto returnCurrentClock() {
            return new TimeTravelDto(CLOCK.getTime(), CLOCK.isRunning());
        }
    }

    private void logSwitchesActivated() {
        switches.getAllSwitches().values().stream()
                .filter(s -> s instanceof DateTimeSwitch)
                .filter(Switch::isOn)
                .forEach(s -> LOGGER.info("Switch " + s.getName() + " is on"));
    }

}
