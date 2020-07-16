package de.ovgu.featureide.core.ovm.handlers.impl;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.swt.widgets.FileDialog;

import de.ovgu.featureide.core.ovm.base.impl.OvModelFactoryManager;
import de.ovgu.featureide.core.ovm.base.impl.OvModelFormatManager;
import de.ovgu.featureide.core.ovm.factory.impl.OvModelFactory;
import de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistance;
import de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.transformer.IModelTransformer;
import de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerFeatureModelToOvModel;
import de.ovgu.featureide.fm.core.ExtensionManager.NoSuchExtensionException;
import de.ovgu.featureide.fm.core.Logger;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelIO;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class ExportOvModelXmlHandler extends TransformationHandlerBase<IFeatureModel, IOvModel> {

	@Override
	protected void configureFileDialog(FileDialog fileDialog) {
		super.configureFileDialog(fileDialog);
		fileDialog.setFileName("ovModel." + OvModelXmlPersistance.SUFFIX);
		fileDialog.setFilterExtensions(new String[] { "*." + OvModelXmlPersistance.SUFFIX });
		fileDialog.setFilterNames(new String[] { OvModelXmlPersistance.NAME + " *." + OvModelXmlPersistance.SUFFIX });
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getImportFileHandler(java.nio.file.Path)
	 */
	@Override
	protected FileHandler<IFeatureModel> getImportFileHandler(Path path) {
		return FeatureModelIO.getInstance().getFileHandler(path);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getTransformation()
	 */
	@Override
	protected IModelTransformer<IFeatureModel, IOvModel> getTransformation() {
		return new DefaultModelTransformerFeatureModelToOvModel();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getToFactory()
	 */
	@Override
	protected IFactory<IOvModel> getExportFactory(Path path) {
		IFactory<IOvModel> factory;

		final OvModelFactoryManager factoryManager = OvModelFactoryManager.getInstance();
		try {
			final IPersistentFormat<IOvModel> format = getExportPersistance(path);
			factory = factoryManager.getFactory(format);
		} catch (final NoSuchExtensionException e) {
			Logger.logError(e);
			factory = OvModelFactory.getInstance();
		}
		return factory;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getExportFileHandler(java.nio.file.Path)
	 */
	@Override
	protected FileHandler<IOvModel> getExportFileHandler(Path path) {
		final FileHandler<IOvModel> fileHandler = new FileHandler<>(path, getExportFactory(path).create(), getExportPersistance(path));
		return fileHandler;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see de.ovgu.featureide.core.ovm.handlers.TransformationHandlerBase#getExportPersistance(java.nio.file.Path)
	 */
	@Override
	protected IPersistentFormat<IOvModel> getExportPersistance(Path path) {
		final List<IPersistentFormat<IOvModel>> formats = OvModelFormatManager.getInstance().getFormatListForExtension(path.getFileName());
		final IPersistentFormat<IOvModel> format = formats.isEmpty() ? null : formats.get(0);
		return format;
	}

}
