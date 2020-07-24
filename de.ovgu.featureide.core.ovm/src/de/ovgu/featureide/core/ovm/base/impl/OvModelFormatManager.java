package de.ovgu.featureide.core.ovm.base.impl;

import de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistance;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.fm.core.base.impl.FormatManager;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;

/**
 * Extends the abstract FormatManager from FeatureIDE for OVModels. It returns the default persistence of OvModels.
 *
 * @author johannstoebich
 */
public class OvModelFormatManager extends FormatManager<IOvModel> {

	private static OvModelFormatManager instance = new OvModelFormatManager();

	public static OvModelFormatManager getInstance() {
		return instance;
	}

	private OvModelFormatManager() {};

	public static IPersistentFormat<IOvModel> getDefaultFormat() {
		return new OvModelXmlPersistance();
	}

	@Override
	public boolean addExtension(IPersistentFormat<IOvModel> extension) {
		return (extension instanceof IPersistentFormat) ? super.addExtension(extension) : false;
	}

}
