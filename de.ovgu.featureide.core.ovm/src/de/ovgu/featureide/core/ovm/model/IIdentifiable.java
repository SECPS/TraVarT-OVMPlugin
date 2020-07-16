package de.ovgu.featureide.core.ovm.model;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract interface IIdentifiable {

	/**
	 * Returns the internal id of an identifiable.
	 *
	 * @return
	 */
	long getInternalId();

	String getName();

	void setName(String name);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
