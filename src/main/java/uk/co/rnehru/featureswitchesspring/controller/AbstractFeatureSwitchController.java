package uk.co.rnehru.featureswitchesspring.controller;

/**
 * Abstract controller for feature switches.
 */
public abstract class AbstractFeatureSwitchController {

    private static final String SWITCHES_PREFIX = "switches.";

    /**
     * Appends the given context to the switch's context.
     * @param context name of the feature switches
     * @return the context with the switches context appended
     */
    protected final String appendSwitchesToContextName(final String context) {
        return SWITCHES_PREFIX + context;
    }

}
