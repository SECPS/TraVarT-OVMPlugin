package de.ovgu.featureide.core.ovm.format.impl.exception;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract class OvModelSerialisationException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 */
	public OvModelSerialisationException(String message) {
		super(message);
	}
}
