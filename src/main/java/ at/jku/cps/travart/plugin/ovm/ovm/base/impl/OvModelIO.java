import de.ovgu.featureide.fm.core.base.impl.FactoryManager;
import de.ovgu.featureide.fm.core.base.impl.FormatManager;
import de.ovgu.featureide.fm.core.io.manager.AbstractIO;

/**
 * This class constrains the file handling operations for OvModels. It returns the format manager and the factory manager.
 */
public class OvModelIO extends AbstractIO<IOvModel> {

    private static final OvModelIO INSTANCE = new OvModelIO();

    private OvModelIO() {
    }

    public static OvModelIO getInstance() {
        return INSTANCE;
    }

    @Override
    protected FormatManager<IOvModel> getFormatManager() {
        return OvModelFormatManager.getInstance();
    }

    @Override
    protected FactoryManager<IOvModel> getFactoryManager() {
        return OvModelFactoryManager.getInstance();
    }

}
