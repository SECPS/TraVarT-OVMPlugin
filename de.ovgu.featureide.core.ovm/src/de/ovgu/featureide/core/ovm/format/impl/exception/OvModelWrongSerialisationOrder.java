package de.ovgu.featureide.core.ovm.format.impl.exception;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class OvModelWrongSerialisationOrder extends OvModelSerialisationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OvModelWrongSerialisationOrder(String name) {
		super("An element of type " + name + " was in the wrong order.");
	}
}
