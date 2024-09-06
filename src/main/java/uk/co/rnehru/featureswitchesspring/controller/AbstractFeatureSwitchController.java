package uk.co.rnehru.featureswitchesspring.controller;

/**
 * Abstract controller for feature switches.
 */
public abstract class AbstractFeatureSwitchController {

    /**
     * Default constructor for the abstract AbstractFeatureSwitchController class.
     */
    public AbstractFeatureSwitchController() {

    }

    /**
     * Appends the given context to the switch's context.
     * @param context name of the feature switches
     * @return the context with the switches context appended
     */
    protected final String appendSwitchesToContextName(final String context) {
        return "switches." + context;
    }

}
