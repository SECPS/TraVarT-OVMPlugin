package de.ovgu.featureide.core.ovm.format.impl;

import java.util.List;

import org.w3c.dom.Document;

import de.ovgu.featureide.core.ovm.base.impl.OvModelFactoryManager;
import de.ovgu.featureide.core.ovm.factory.IOvModelFactory;
import de.ovgu.featureide.core.ovm.format.impl.exception.OvModelSerialisationException;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.fm.core.ExtensionManager.NoSuchExtensionException;
import de.ovgu.featureide.fm.core.io.Problem;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.xml.AXMLFormat;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class OvModelXmlPersistance extends AXMLFormat<IOvModel> {

	public static final String ID = "de.ovgu.featureid.core.ovm.format.OvmXmlPersistance";

	public static final String NAME = "OvModel XML format";

	public static final String SUFFIX = "ovm";

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.fm.core.io.IPersistentFormat#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.fm.core.IExtension#getId()
	 */
	@Override
	public String getId() {
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.fm.core.io.xml.AXMLFormat#getSuffix()
	 */
	@Override
	public String getSuffix() {
		return SUFFIX;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.fm.core.io.xml.AXMLFormat#readDocument(org.w3c.dom.Document, java.util.List)
	 */
	@Override
	protected void readDocument(Document doc, List<Problem> warnings) throws UnsupportedModelException {
		try {
			final IOvModelFactory factory = OvModelFactoryManager.getInstance().getFactory(this);
			ReadHelper.readModel(doc, object, factory);
		} catch (final NoSuchExtensionException | OvModelSerialisationException e) {
			warnings.add(new Problem(e));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.fm.core.io.xml.AXMLFormat#writeDocument(org.w3c.dom.Document)
	 */
	@Override
	protected void writeDocument(Document doc) {
		try {
			WriteHelper.writeModel(object, doc);
		} catch (final OvModelSerialisationException e) {
			throw new RuntimeException(e);
		}
	}

}
