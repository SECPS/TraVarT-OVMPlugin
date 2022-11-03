import de.ovgu.featureide.fm.core.init.LibraryManager;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle. This class provides the setup functions of the plug-in. They are called when the plug-in is first
 * started and when the plug-in is stopped.
 */
public class OvModelPlugin extends Plugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "de.ovgu.featureide.core.ovm"; //$NON-NLS-1$

    // The shared instance
    private static OvModelPlugin plugin;

    /**
     * Returns the shared instance.
     *
     * @return the shared instance
     */
    public static OvModelPlugin getDefault() {
        return plugin;
    }

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

}
