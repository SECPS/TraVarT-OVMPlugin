package de.ovgu.featureide.core.ovm.model.impl;

import java.util.Objects;

import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract class OvModelElement extends Identifyable implements IOvModelElement {

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelElement#getElement(de.ovgu.featureide.core.ovm.model.IIdentifyable)
	 */
	@Override
	public IOvModelElement getElement(IIdentifyable identifyable) {
		if (identifyable == null) {
			return null;
		} else if (identifyable == this) {
			return this;
		} else if (Objects.equals(identifyable.getName(), getName())) {
			return this;
		}
		return null;
	}

}
