package de.ovgu.featureide.core.ovm.model;

/**
 * An identifiable represents a basic interface for all OvModel elements which must be uniquely identified. Currently the name must be unique and therefore each
 * element can be identified by a name. However this might change in future. Therefore an internalId is provided.
 *
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

	/**
	 * This method returns the name of an IIdentifiable. The name must be unique within an OvModel.
	 *
	 * @return the name of the identifiable.
	 */
	String getName();

	/**
	 * This method sets the name of an IIdentifiable. The name must be unique within an OvModel.
	 *
	 * @param name the name.
	 */
	void setName(String name);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
