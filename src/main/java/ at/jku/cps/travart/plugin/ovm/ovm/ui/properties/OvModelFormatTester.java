import de.ovgu.featureide.fm.core.base.impl.FormatManager;
import de.ovgu.featureide.fm.core.io.EclipseFileSystem;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;
import de.ovgu.featureide.fm.ui.handlers.base.SelectionWrapper;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;

/**
 * This tests whether a given file contains an {@link IOvModel} by using the files file extension. This tester can be used in the eclipse's plug-in handlers
 * (plug-in.xml).
 *
 * @author johannstoebich
 */
public class OvModelFormatTester extends PropertyTester {

    @Override
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        return this.checkFormat(this.getFormatManager(property), SelectionWrapper.checkClass(receiver, IFile.class), expectedValue);
    }

    protected boolean checkFormat(FormatManager<?> formatManager, IFile res, Object expectedValue) {
        if ((res != null) && (formatManager != null)) {
            IPersistentFormat<?> formatByContent = formatManager.getFormatByContent(EclipseFileSystem.getPath(res));
            return (expectedValue == null) ? formatByContent != null : expectedValue.equals(formatByContent.getId());
        }
        return false;
    }

    private FormatManager<?> getFormatManager(String property) {
        switch (property) {
            case "ovmodel":
                return OvModelFormatManager.getInstance();
            default:
                return null;
        }
    }
}
