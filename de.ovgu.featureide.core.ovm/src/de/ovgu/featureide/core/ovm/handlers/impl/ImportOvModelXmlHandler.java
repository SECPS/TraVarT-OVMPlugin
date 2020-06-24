package de.ovgu.featureide.core.ovm.handlers.impl;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.swt.widgets.FileDialog;

import de.ovgu.featureide.core.ovm.base.impl.OvModelIO;
import de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.transformer.IModelTransformer;
import de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerOvModelToFeatureModel;
import de.ovgu.featureide.fm.core.ExtensionManager.NoSuchExtensionException;
import de.ovgu.featureide.fm.core.Logger;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.FMFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.FMFormatManager;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class ImportOvModelXmlHandler extends TransformationHandlerBase<IOvModel, IFeatureModel> {

	@Override
	protected void configureFileDialog(FileDialog fileDialog) {
		super.configureFileDialog(fileDialog);
		final String[][] filter = getFilter(FMFormatManager.getInstance().getExtensions());
		fileDialog.setFilterNames(filter[0]);
		fileDialog.setFilterExtensions(filter[1]);
	}

	protected String[][] getFilter(List<? extends IPersistentFormat<IFeatureModel>> formatExtensions) {
		int countWritableFormats = 0;
		for (final IPersistentFormat<?> format : formatExtensions) {
			if (format.supportsWrite()) {
				countWritableFormats++;
			}
		}
		final String[][] filterArray = new String[2][countWritableFormats];
		final String[] names = filterArray[0];
		final String[] extensions = filterArray[1];
		int index = 0;
		for (final IPersistentFormat<?> format : formatExtensions) {
			if (format.supportsWrite()) {
				names[index] = format.getName() + " " + ("*." + format.getSuffix());
				extensions[index++] = "*." + format.getSuffix();
			}
		}

		return filterArray;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getImportFileHandler(java.nio.file.Path)
	 */
	@Override
	protected FileHandler<IOvModel> getImportFileHandler(Path path) {
		return OvModelIO.getInstance().getFileHandler(path);
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getTransformation()
	 */
	@Override
	protected IModelTransformer<IOvModel, IFeatureModel> getTransformation() {
		return new DefaultModelTransformerOvModelToFeatureModel();
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getToFactory()
	 */
	@Override
	protected IFactory<IFeatureModel> getExportFactory(Path path) {
		IFactory<IFeatureModel> factory;

		final FMFactoryManager factoryManager = FMFactoryManager.getInstance();
		try {
			factory = factoryManager.getFactory(getExportPersistance(path));
		} catch (final NoSuchExtensionException e) {
			Logger.logError(e);
			factory = DefaultFeatureModelFactory.getInstance();
		}
		return factory;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getExportFileHandler(java.nio.file.Path)
	 */
	@Override
	protected FileHandler<IFeatureModel> getExportFileHandler(Path path) {
		final FileHandler<IFeatureModel> fileHandler = new FileHandler<>(path, getExportFactory(path).create(), getExportPersistance(path));
		return fileHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getExportPersistance(java.nio.file.Path)
	 */
	@Override
	protected IPersistentFormat<IFeatureModel> getExportPersistance(Path path) {
		final List<IPersistentFormat<IFeatureModel>> formats = FMFormatManager.getInstance().getFormatListForExtension(path.getFileName());
		final IPersistentFormat<IFeatureModel> format = formats.isEmpty() ? null : formats.get(0);
		return format;
	}

}
