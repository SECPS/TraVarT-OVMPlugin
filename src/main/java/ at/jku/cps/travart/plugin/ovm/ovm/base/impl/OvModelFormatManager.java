import de.ovgu.featureide.fm.core.base.impl.FormatManager;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;

/**
 * Extends the abstract {@link FormatManager} from FeatureIDE for OVModels. It returns the default persistence of {@link IOvModel}.
 *
 * @author johannstoebich
 */
public class OvModelFormatManager extends FormatManager<IOvModel> {

    private static final OvModelFormatManager instance = new OvModelFormatManager();

    private OvModelFormatManager() {
    }

    public static OvModelFormatManager getInstance() {
        return instance;
    }

    ;

    public static IPersistentFormat<IOvModel> getDefaultFormat() {
        return new OvModelXmlPersistance();
    }

    @Override
    public boolean addExtension(IPersistentFormat<IOvModel> extension) {
        return (extension instanceof IPersistentFormat) ? super.addExtension(extension) : false;
    }

}
