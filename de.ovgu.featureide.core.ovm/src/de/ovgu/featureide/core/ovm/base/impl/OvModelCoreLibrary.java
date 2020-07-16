package de.ovgu.featureide.core.ovm.base.impl;

import de.ovgu.featureide.core.ovm.factory.impl.OvModelFactory;
import de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistance;
import de.ovgu.featureide.fm.core.JavaLogger;
import de.ovgu.featureide.fm.core.Logger;
import de.ovgu.featureide.fm.core.base.impl.EclipseFactoryWorkspaceProvider;
import de.ovgu.featureide.fm.core.init.ILibrary;
import de.ovgu.featureide.fm.core.io.FileSystem;
import de.ovgu.featureide.fm.core.io.JavaFileSystem;
import de.ovgu.featureide.fm.core.job.LongRunningCore;
import de.ovgu.featureide.fm.core.job.LongRunningWrapper;

/**
 * The methods in this class are called from the OvModelPlugin on startup and on stop.
 *
 * @author johannstoebich
 */
public final class OvModelCoreLibrary implements ILibrary {

	private static OvModelCoreLibrary instance;

	public static OvModelCoreLibrary getInstance() {
		if (instance == null) {
			instance = new OvModelCoreLibrary();
		}
		return instance;
	}

	private OvModelCoreLibrary() {}

	@Override
	public void install() {
		FileSystem.INSTANCE = new JavaFileSystem();
		LongRunningWrapper.INSTANCE = new LongRunningCore();
		Logger.logger = new JavaLogger();

		OvModelFactoryManager.getInstance().addExtension(OvModelFactory.getInstance());
		OvModelFactoryManager.getInstance().setWorkspaceLoader(new EclipseFactoryWorkspaceProvider("ovModel"));

		OvModelFormatManager.getInstance().addExtension(new OvModelXmlPersistance());
	}

	@Override
	public void uninstall() {
		OvModelFactoryManager.getInstance().save();
	}

}
