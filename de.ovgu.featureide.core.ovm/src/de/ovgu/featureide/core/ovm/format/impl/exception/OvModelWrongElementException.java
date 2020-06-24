package de.ovgu.featureide.core.ovm.format.impl.exception;

/**
 * TODO description
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
