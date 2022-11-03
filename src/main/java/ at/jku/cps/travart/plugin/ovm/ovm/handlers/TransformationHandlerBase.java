import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.io.EclipseFileSystem;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.ui.handlers.base.AFileHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class defines a transformation. It is referenced directly from the eclipse's plug-in definition (plugin.xml). It defines which handler should be used to
 * serialize or deserialize a file, how the file hander has to be set up and which transformation should be used.
 *
 * @author johannstoebich
 */
public abstract class TransformationHandlerBase<FROM, TO> extends AFileHandler {

    /**
     * This method is called whenever an action on a file is executed.
     */
    @Override
    protected final void singleAction(IFile inputFile) {
        // Ask for output file name
        FileDialog fileDialog = new FileDialog(new Shell(), SWT.SAVE);
        this.configureFileDialog(fileDialog);
        String outputFile = fileDialog.open();
        if (outputFile == null) {
            return;
        }
        // get import and export paths from file names
        Path importPath = EclipseFileSystem.getPath(inputFile);
        Path exportPath = Paths.get(outputFile);

        // import object from file
        FileHandler<FROM> importHandler = this.getImportFileHandler(importPath);
        FROM importObject = importHandler.getObject();

        // transform object
        TO exportObject = this.getTransformation().transform(importObject, this.getExportFactory(exportPath));

        // write transformed object to export file
        FileHandler<TO> exportHanlder = this.getExportFileHandler(exportPath);
        exportHanlder.setObject(exportObject);
        exportHanlder.write(exportPath);
    }

    /**
     * This method returns the import handler which is able to read a model from a path.
     *
     * @param path the path where the file should be read.
     * @return
     */
    protected abstract FileHandler<FROM> getImportFileHandler(Path path);

    /**
     * This method returns a transformation which is able to transform the source model into a new target model.
     *
     * @return the transformed model.
     */
    protected abstract IModelTransformer<FROM, TO> getTransformation();

    /**
     * The factory which is used during the transformation process to create the target model.
     *
     * @param path
     * @return
     */
    protected abstract IFactory<TO> getExportFactory(Path path);

    /**
     * The file handler which is able to write a target model into a given location.
     *
     * @param path the path where the file should be written.
     * @return
     */
    protected abstract FileHandler<TO> getExportFileHandler(Path path);

    /**
     * The possible formats of the target model.
     *
     * @param path the path where the file should be written.
     * @return
     */
    protected abstract IPersistentFormat<TO> getExportPersistance(Path path);

    /**
     * In this method the files which are allowed to be opened can be set.
     *
     * @param fileDialog the file dialog which is used to open the file.
     */
    protected void configureFileDialog(FileDialog fileDialog) {
        fileDialog.setOverwrite(true);
    }
}
