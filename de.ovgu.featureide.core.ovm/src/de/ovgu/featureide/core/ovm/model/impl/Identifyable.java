package de.ovgu.featureide.core.ovm.model.impl;

import de.ovgu.featureide.core.ovm.model.IIdentifyable;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract class Identifyable implements IIdentifyable {

	private static long NEXT_ID = 0;

	protected long internalId;
	protected String name;

	public Identifyable() {
		internalId = getNextId();
	}

	public Identifyable(long internalId) {
		this.internalId = internalId;
	}

	@Override
	public long getInternalId() {
		return internalId;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.IIdentifyable#setName(java.lang.String)
	 */
	public void setInternalId(long internalId) {
		this.internalId = internalId;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.IIdentifyable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.IIdentifyable#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	protected static final synchronized long getNextId() {
		return NEXT_ID++;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Identifyable other = (Identifyable) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
