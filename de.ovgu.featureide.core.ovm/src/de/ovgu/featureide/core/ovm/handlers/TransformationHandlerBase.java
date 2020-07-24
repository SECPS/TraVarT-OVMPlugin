package de.ovgu.featureide.core.ovm.handlers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.ovgu.featureide.core.ovm.transformer.IModelTransformer;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.io.EclipseFileSystem;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.ui.handlers.base.AFileHandler;

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
		final FileDialog fileDialog = new FileDialog(new Shell(), SWT.SAVE);
		configureFileDialog(fileDialog);
		final String outputFile = fileDialog.open();
		if (outputFile == null) {
			return;
		}
		// get import and export paths from file names
		final Path importPath = EclipseFileSystem.getPath(inputFile);
		final Path exportPath = Paths.get(outputFile);

		// import object from file
		final FileHandler<FROM> importHandler = getImportFileHandler(importPath);
		final FROM importObject = importHandler.getObject();

		// transform object
		final TO exportObject = getTransformation().transform(importObject, getExportFactory(exportPath));

		// write transformed object to export file
		final FileHandler<TO> exportHanlder = getExportFileHandler(exportPath);
		exportHanlder.setObject(exportObject);
		exportHanlder.write(exportPath);
	}

	/**
	 * This method returns the import handler which is able to read a model.
	 *
	 * @param path
	 * @return
	 */
	protected abstract FileHandler<FROM> getImportFileHandler(Path path);

	/**
	 * This method returns transformation which is able to transform the source model to a new target datatype.
	 *
	 * @return the transformed mdoel.
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
	 * The file handler which is able to write a target model.
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
