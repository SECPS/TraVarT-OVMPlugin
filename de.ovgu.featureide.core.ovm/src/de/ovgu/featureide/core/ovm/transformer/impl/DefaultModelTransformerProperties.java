package de.ovgu.featureide.core.ovm.transformer.impl;

/**
 * This class contains some static final properties which are used in the transformation from an OVM to a Feature Model and backwards.
 *
 * @author johannstoebich
 */
public class DefaultModelTransformerProperties {

	/**
	 * The prefix additional variants will get.
	 */
	public static final String VARIANT_PREFIX = "VARIANT_";

	/**
	 * The prefix constraint will get.
	 */
	public static final String CONSTRAINT_PREFIX = "CONSTRAINT_";

	/**
	 * The prefix new variation points during the constraint transformation will get.
	 */
	public static final String CONSTRAINT_VARIATION_POINT_PREFIX = "VIRTUAL_";
}