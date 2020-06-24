package de.ovgu.featureide.core.ovm.model;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract interface IIdentifyable {

	long getInternalId();

	String getName();

	void setName(String name);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
