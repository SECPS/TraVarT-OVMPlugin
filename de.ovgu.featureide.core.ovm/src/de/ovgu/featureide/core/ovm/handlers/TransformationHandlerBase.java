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
 * TODO description
 *
 * @author johannstoebich
 */
public abstract class TransformationHandlerBase<FROM, TO> extends AFileHandler {

	@Override
	protected final void singleAction(IFile inputFile) {
		// Ask for file name
		final FileDialog fileDialog = new FileDialog(new Shell(), SWT.SAVE);
		configureFileDialog(fileDialog);
		final String outputFile = fileDialog.open();
		if (outputFile == null) {
			return;
		}

		final Path importPath = EclipseFileSystem.getPath(inputFile);

		final Path exportPath = Paths.get(outputFile);

		final FileHandler<FROM> importHandler = getImportFileHandler(importPath);
		final FROM importObject = importHandler.getObject();

		final TO exportObject = getTransformation().transform(importObject, getExportFactory(exportPath));

		final FileHandler<TO> exportHanlder = getExportFileHandler(exportPath);
		exportHanlder.setObject(exportObject);
		exportHanlder.write(exportPath);
	}

	protected abstract FileHandler<FROM> getImportFileHandler(Path path);

	protected abstract IModelTransformer<FROM, TO> getTransformation();

	protected abstract IFactory<TO> getExportFactory(Path path);

	protected abstract FileHandler<TO> getExportFileHandler(Path path);

	protected abstract IPersistentFormat<TO> getExportPersistance(Path path);

	protected void configureFileDialog(FileDialog fileDialog) {
		fileDialog.setOverwrite(true);
	}
}
