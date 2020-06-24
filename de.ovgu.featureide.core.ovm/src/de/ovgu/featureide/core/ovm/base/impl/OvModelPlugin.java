package de.ovgu.featureide.core.ovm.base.impl;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import de.ovgu.featureide.fm.core.init.LibraryManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class OvModelPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.ovgu.featureide.core.ovm"; //$NON-NLS-1$

	// The shared instance
	private static OvModelPlugin plugin;

	public String getID() {
		return PLUGIN_ID;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		LibraryManager.registerLibrary(OvModelCoreLibrary.getInstance());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		LibraryManager.deregisterLibrary(OvModelCoreLibrary.getInstance());
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static OvModelPlugin getDefault() {
		return plugin;
	}

}
