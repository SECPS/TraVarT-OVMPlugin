package de.ovgu.featureide.core.ovm.base.impl;

import java.nio.file.Path;

import de.ovgu.featureide.core.ovm.factory.IOvModelFactory;
import de.ovgu.featureide.core.ovm.factory.impl.OvModelFactory;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.fm.core.Logger;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.impl.FactoryManager;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;

/**
 *
 * This class represents an factory manager for OvModels. It extends the abstract factory manager of FeatureIDE. Only the method <code>getFactory</code> is
 * overridden. The other ones cast only the return value correctly.
 * 
 * @author johannstoebich
 */
public final class OvModelFactoryManager extends FactoryManager<IOvModel> {

	@Override
	protected String getDefaultID() {
		return OvModelFactory.ID;
	}

	private static OvModelFactoryManager instance = new OvModelFactoryManager();

	public static OvModelFactoryManager getInstance() {
		return instance;
	}

	private OvModelFactoryManager() {}

	/**
	 * Returns the ov model factory that was used to create the given model (if the factory is not available the default factory is returned).
	 *
	 * @param object the ov model.
	 *
	 * @return Returns the OV-Model factory for the given ov model.
	 */
	@Override
	public IOvModelFactory getFactory(IOvModel object) {
		try {
			return getFactory(object.getFactoryId());
		} catch (final NoSuchExtensionException e) {
			Logger.logError(e);
			return OvModelFactory.getInstance();
		}
	}

	@Override
	public IOvModelFactory getFactory(String id) throws NoSuchExtensionException {
		return (IOvModelFactory) super.getFactory(id);
	}

	@Override
	public IOvModelFactory getFactory(Path path, IPersistentFormat<? extends IOvModel> format) throws NoSuchExtensionException {
		return (IOvModelFactory) super.getFactory(path, format);
	}

	@Override
	public IOvModelFactory getFactory(IPersistentFormat<? extends IOvModel> format) throws NoSuchExtensionException {
		return (IOvModelFactory) super.getFactory(format);
	}

	@Override
	public boolean addExtension(IFactory<IOvModel> extension) {
		return (extension instanceof IOvModelFactory) ? super.addExtension(extension) : false;
	}

}
