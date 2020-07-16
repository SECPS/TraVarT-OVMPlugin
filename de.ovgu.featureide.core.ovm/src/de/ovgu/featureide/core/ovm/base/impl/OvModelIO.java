package de.ovgu.featureide.core.ovm.base.impl;

import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.fm.core.base.impl.FactoryManager;
import de.ovgu.featureide.fm.core.base.impl.FormatManager;
import de.ovgu.featureide.fm.core.io.manager.AbstractIO;

/**
 * File handling operations for ov-models
 *
 */
public class OvModelIO extends AbstractIO<IOvModel> {

	private static final OvModelIO INSTANCE = new OvModelIO();

	public static OvModelIO getInstance() {
		return INSTANCE;
	}

	private OvModelIO() {}

	@Override
	protected FormatManager<IOvModel> getFormatManager() {
		return OvModelFormatManager.getInstance();
	}

	@Override
	protected FactoryManager<IOvModel> getFactoryManager() {
		return OvModelFactoryManager.getInstance();
	}

}
