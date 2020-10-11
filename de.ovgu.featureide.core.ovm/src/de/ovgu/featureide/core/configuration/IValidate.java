package de.ovgu.featureide.core.configuration;

/**
 * This is an interface which is implemented by the OvModel in order to validated it.
 *
 * @author johannstoebich
 */
public interface IValidate {

	/**
	 * This interface returns whether an configuration is valid or not.
	 *
	 * @return true whenever the configuration is valid.
	 */
	boolean isValid();
}
