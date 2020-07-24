package de.ovgu.featureide.core.ovm.format.impl.exc;

/**
 * This exception should be thrown whenever a specific type is expected however a wrong type has been returned.
 *
 * @author johannstoebich
 */
public class OvModelWrongElementException extends OvModelSerialisationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OvModelWrongElementException(String type) {
		super("An element of type " + type + " was not found.");
	}

	public OvModelWrongElementException(String type, String name) {
		super("The element \"" + name + "\" of type " + type + " was not found.");
	}
}
