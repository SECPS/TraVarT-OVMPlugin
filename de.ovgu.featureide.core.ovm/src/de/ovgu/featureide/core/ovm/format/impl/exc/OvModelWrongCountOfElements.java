package de.ovgu.featureide.core.ovm.format.impl.exc;

/**
 * This exception should be thrown whenever a certain amount of elements is required however not given.
 *
 * @author johannstoebich
 */
public class OvModelWrongCountOfElements extends OvModelSerialisationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public OvModelWrongCountOfElements(String type, int expected, int actual) {
		super("The wrong size of " + type + ". Expected: " + expected + "; Actual: " + actual + ".");
	}

}
