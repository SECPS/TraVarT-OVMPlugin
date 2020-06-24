package de.ovgu.featureide.core.ovm.transformer.impl;

import static de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerProperties.CONSTRAINT_PREFIX;
import static de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX;
import static de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerProperties.VARIANT_PREFIX;

import java.util.ArrayList;
import java.util.List;

import org.prop4j.And;
import org.prop4j.AtLeast;
import org.prop4j.AtMost;
import org.prop4j.Choose;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Not;
import org.prop4j.Or;

import de.ovgu.featureide.core.ovm.base.impl.OvModelUtils;
import de.ovgu.featureide.core.ovm.factory.IOvModelFactory;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariant;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.core.ovm.transformer.IModelTransformer;
import de.ovgu.featureide.core.ovm.transformer.impl.exc.NotSupportedTransformationException;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class DefaultModelTransformerFeatureModelToOvModel implements IModelTransformer<IFeatureModel, IOvModel> {

	private Integer sequence = 0;

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureid.core.ovm.transformer.IModelTransformer#transform(de.ovgu.featureide.fm.core.base.IFeatureModel,
	 * de.ovgu.featureide.core.ovm.factory.IOvModelFactory)
	 */
	@Override
	public IOvModel transform(IFeatureModel featureModel, IFactory<IOvModel> factoryTo) throws NotSupportedTransformationException {
		final IOvModelFactory factory = (IOvModelFactory) factoryTo;
		final IOvModel ovModel = factory.create();
		OvModelUtils.setCustomPropertiesEntries(ovModel, featureModel.getProperty().getProperties());

		final IOvModelVariationPoint modelVariantPoint =
			(IOvModelVariationPoint) featureToOvModelElement(FeatureUtils.getRoot(featureModel), featureModel, factory, ovModel);
		OvModelUtils.addVariationPoint(ovModel, modelVariantPoint);

		for (final IConstraint constraint : FeatureUtils.getConstraints(featureModel)) {
			final IOvModelElement ovModelElement = nodeToConstraints(FeatureUtils.getNode(constraint), featureModel, factory, ovModel);

			if (ovModelElement instanceof IOvModelConstraint) {
				final IOvModelConstraint ovModelConstraint = (IOvModelConstraint) ovModelElement;
				OvModelUtils.setDescription(ovModelConstraint, constraint.getDescription());
				OvModelUtils.setName(ovModelConstraint, constraint.getName());

				OvModelUtils.addConstraint(ovModel, ovModelConstraint);
				OvModelUtils.setCustomPropertiesEntries(ovModelConstraint, constraint.getCustomProperties().getProperties());

			} else if (ovModelElement instanceof IOvModelVariationPoint) {
				final IOvModelVariationPoint ovModelVariationBase = (IOvModelVariationPoint) ovModelElement;

				OvModelUtils.setDescription(ovModelVariationBase, constraint.getDescription());
				OvModelUtils.setName(ovModelVariationBase, constraint.getName());

				OvModelUtils.addVariationPoint(ovModel, ovModelVariationBase);
				OvModelUtils.setCustomPropertiesEntries(ovModelVariationBase, constraint.getCustomProperties().getProperties());
			}
		}

		return ovModel;
	}

	private IOvModelVariationBase featureToOvModelElement(final IFeature feature, IFeatureModel featureModel, final IOvModelFactory factory,
			final IOvModel ovModel) {

		IOvModelVariationBase ovModelVariationBase = findFeatureInOvModel(feature, featureModel, factory, ovModel);
		if (ovModelVariationBase != null) {
			return ovModelVariationBase;
		}

		if (!FeatureUtils.hasChildren(feature)) {
			final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(ovModel, FeatureUtils.getName(feature));
			ovModelVariationBase = ovModelVariationPoint;

			final IOvModelVariant ovModelVariant = factory.createVariant(ovModel, VARIANT_PREFIX + FeatureUtils.getName(feature));
			OvModelUtils.setPartOfOvModelRoot(ovModelVariant, true);
			OvModelUtils.setMaxChoices(ovModelVariationPoint, 1);
			OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
			OvModelUtils.addMandatoryChild(ovModelVariationPoint, ovModelVariant);

		} else if (FeatureUtils.hasChildren(feature)) {
			final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(ovModel, FeatureUtils.getName(feature));
			ovModelVariationBase = ovModelVariationPoint;

			for (final IFeature child : FeatureUtils.getChildren(feature)) {
				final IOvModelVariationBase ovModelChild = featureToOvModelElement(child, featureModel, factory, ovModel);

				if (FeatureUtils.isOr(feature)) {
					OvModelUtils.addOptionalChild(ovModelVariationPoint, ovModelChild);
				} else if (FeatureUtils.isAlternative(feature)) {
					OvModelUtils.addOptionalChild(ovModelVariationPoint, ovModelChild);
				} else if (FeatureUtils.isAnd(feature)) {
					if (FeatureUtils.isMandatory(child)) {
						OvModelUtils.addMandatoryChild(ovModelVariationPoint, ovModelChild);
					} else {
						OvModelUtils.addOptionalChild(ovModelVariationPoint, ovModelChild);
					}
				}
			}

			if (FeatureUtils.isAlternative(feature)) {
				OvModelUtils.setAlternative(ovModelVariationPoint, true);
				OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
				OvModelUtils.setMaxChoices(ovModelVariationPoint, 1);
			} else if (FeatureUtils.isOr(feature)) {
				// 23.06 OvModelUtils.setMinChoices(ovModelVariationPoint, 0);
				OvModelUtils.setAlternative(ovModelVariationPoint, true);
				OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
				OvModelUtils.setMaxChoices(ovModelVariationPoint, OvModelUtils.getOptionalChildrenCount(ovModelVariationPoint));
			}
		}

		setOvModelVariationBaseProperties(ovModelVariationBase, feature, featureModel);

		return ovModelVariationBase;
	}

	private IOvModelVariationBase findFeatureInOvModel(final IFeature feature, IFeatureModel featureModel, final IOvModelFactory factory,
			final IOvModel ovModel) {
		return (IOvModelVariationBase) ovModel.getElement(factory.createIdentifyable(0, feature.getName()));
	}

	private IOvModelElement nodeToConstraints(Node node, IFeatureModel featureModel, IOvModelFactory factory, IOvModel ovModel)
			throws NotSupportedTransformationException {
		IOvModelElement constraint = null;

		if ((node instanceof And) || (node instanceof Or) || (node instanceof AtLeast) || (node instanceof AtMost) || (node instanceof Choose)) {
			final List<IOvModelVariationBase> ovChildren = new ArrayList<IOvModelVariationBase>();

			final IOvModelVariationPoint ovModelVariationPoint =
				factory.createVariationPoint(ovModel, CONSTRAINT_VARIATION_POINT_PREFIX + nodeToUniqueString(node));
			constraint = ovModelVariationPoint;

			boolean hasConstraint = false;

			for (final Node child : node.getChildren()) {
				final IOvModelElement childElement = nodeToConstraints(child, featureModel, factory, ovModel);
				if (childElement instanceof IOvModelVariationBase) {
					ovChildren.add((IOvModelVariationBase) childElement);
				} else if (childElement instanceof IOvModelConstraint) {
					final IOvModelConstraint ovModelConstraint = (IOvModelConstraint) childElement;
					OvModelUtils.addConstraint(ovModel, ovModelConstraint);
					OvModelUtils.addReferencedConstraint(ovModelVariationPoint, ovModelConstraint);
					hasConstraint = true;
					ovChildren.add(ovModelConstraint.getSource());
				} else {
					throw new NotSupportedTransformationException(child.getClass(), childElement.getClass());
				}
			}

			setOvModelVariationBaseProperties(ovModelVariationPoint, node, featureModel);

			// 22.06. if ((node instanceof And) && (node instanceof Or)) {
			if (node instanceof And) {
				OvModelUtils.setAlternative(ovModelVariationPoint, false);
			} else {
				OvModelUtils.setAlternative(ovModelVariationPoint, true);
			}

			if (hasConstraint) {
				OvModelUtils.setOptional(ovModelVariationPoint, true);
			}

			if (node instanceof And) {
				OvModelUtils.setMandatoryChildren(ovModelVariationPoint, ovChildren);
			} else if (node instanceof Or) {
				OvModelUtils.setOptionalChildren(ovModelVariationPoint, ovChildren);
			} else {
				OvModelUtils.setOptionalChildren(ovModelVariationPoint, ovChildren);
			}

			if (node instanceof And) {
				OvModelUtils.setMinChoices(ovModelVariationPoint, ovChildren.size());
				OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
			} else if (node instanceof Or) {
				// 22.06. OvModelUtils.setMinChoices(ovModelVariationPoint, 0);
				OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
				OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
			} else if (node instanceof AtLeast) {
				final AtLeast atLeast = (AtLeast) node;
				OvModelUtils.setMinChoices(ovModelVariationPoint, atLeast.min);
				OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
			} else if (node instanceof AtMost) {
				final AtMost atMost = (AtMost) node;
				OvModelUtils.setMinChoices(ovModelVariationPoint, 0);
				OvModelUtils.setMaxChoices(ovModelVariationPoint, atMost.max);
			} else if (node instanceof Choose) {
				final Choose choose = (Choose) node;
				OvModelUtils.setMinChoices(ovModelVariationPoint, choose.n);
				OvModelUtils.setMaxChoices(ovModelVariationPoint, choose.n);
			}
		} else if (node instanceof Literal) {
			final Literal literal = (Literal) node;

			final IFeature feature;

			if (literal.var instanceof IFeature) {
				feature = (IFeature) literal.var;
			} else if (literal.var instanceof String) {
				feature = FeatureUtils.getFeature(featureModel, (String) literal.var);
				if (feature == null) {
					throw new NotSupportedTransformationException(node.getClass(), literal.var.getClass());
				}
			} else {
				throw new NotSupportedTransformationException(node.getClass(), literal.var.getClass());
			}

			final boolean isPartOfOvModelRoot = findFeatureInOvModel(feature, featureModel, factory, ovModel) != null;
			final IOvModelVariationBase ovModelVariationBase = featureToOvModelElement(feature, featureModel, factory, ovModel);
			constraint = ovModelVariationBase;

			OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, isPartOfOvModelRoot);

		} else if (node instanceof Not) {
			final IOvModelVariationPoint ovModelVariationPoint =
				factory.createVariationPoint(ovModel, CONSTRAINT_VARIATION_POINT_PREFIX + nodeToUniqueString(node));
			constraint = ovModelVariationPoint;
			setOvModelVariationBaseProperties(ovModelVariationPoint, node, featureModel);
			OvModelUtils.setAlternative(ovModelVariationPoint, false);
			OvModelUtils.setMaxChoices(ovModelVariationPoint, 1);
			OvModelUtils.setMinChoices(ovModelVariationPoint, 1);

			final IOvModelVariant ovModelVariant =
				factory.createVariant(ovModel, CONSTRAINT_VARIATION_POINT_PREFIX + VARIANT_PREFIX + nodeToUniqueString(node));
			setOvModelVariationBaseProperties(ovModelVariant, node, featureModel);
			ovModelVariationPoint.addMandatoryChild(ovModelVariant);

			final IOvModelConstraint ovModelExcludesConstraint = factory.createExcludesConstraint(ovModel);
			OvModelUtils.setName(ovModelExcludesConstraint, "");
			OvModelUtils.setDescription(ovModelExcludesConstraint, "This excludes constraint arises from the constraint " + node.toString() + ".");
			setOvModelConstraintProperties(node, ovModelExcludesConstraint, ovModelVariationPoint,
					(IOvModelVariationBase) nodeToConstraints(node.getChildren()[0], featureModel, factory, ovModel));
			ovModel.addConstraint(ovModelExcludesConstraint);

		} else if (node instanceof Implies) {
			final IOvModelConstraint ovModelRequiresConstraint;

			ovModelRequiresConstraint = factory.createRequiresConstraint(ovModel);
			OvModelUtils.setDescription(ovModelRequiresConstraint, "This requires constraint arises from the constraint " + node.toString() + ".");
			setOvModelConstraintProperties(node, ovModelRequiresConstraint,
					(IOvModelVariationBase) nodeToConstraints(node.getChildren()[0], featureModel, factory, ovModel),
					(IOvModelVariationBase) nodeToConstraints(node.getChildren()[1], featureModel, factory, ovModel));

			constraint = ovModelRequiresConstraint;

		} else {
			throw new NotSupportedTransformationException(node.getClass(), IOvModelElement.class);
		}
		return constraint;
	}

	private void setOvModelConstraintProperties(Node node, final IOvModelConstraint ovModelConstraint, final IOvModelVariationBase source,
			final IOvModelVariationBase target) {
		OvModelUtils.setName(ovModelConstraint, CONSTRAINT_PREFIX + nodeToUniqueString(node));
		OvModelUtils.setSource(ovModelConstraint, source);
		OvModelUtils.setTarget(ovModelConstraint, target);
	}

	private void setOvModelVariationBaseProperties(final IOvModelVariationBase ovModelVariationBase, final IFeature feature, IFeatureModel featureModel) {
		OvModelUtils.setOptional(ovModelVariationBase, !FeatureUtils.isMandatory(feature));
		OvModelUtils.setHidden(ovModelVariationBase, FeatureUtils.isHidden(feature));

		OvModelUtils.setDescription(ovModelVariationBase, FeatureUtils.getDescription(feature));
		OvModelUtils.setAbstract(ovModelVariationBase, FeatureUtils.isAbstract(feature));
		OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, true);
		OvModelUtils.setCustomPropertiesEntries(ovModelVariationBase, feature.getCustomProperties().getProperties());
	}

	private void setOvModelVariationBaseProperties(final IOvModelVariationBase ovModelVariationPoint, final Node node, IFeatureModel featureModel) {
		OvModelUtils.setOptional(ovModelVariationPoint, false);
		OvModelUtils.setHidden(ovModelVariationPoint, false);
		OvModelUtils.setAbstract(ovModelVariationPoint, false);
		OvModelUtils.setDescription(ovModelVariationPoint, "This variant point arises from the constraint " + node.toString() + ".");
		OvModelUtils.setPartOfOvModelRoot(ovModelVariationPoint, false);
	}

	private String nodeToUniqueString(Node node) {
		sequence++;
		return node.getClass().getSimpleName().toUpperCase() + "_CONSTRAINT_"
			+ node.getLiterals().toString().toUpperCase().replace("[", "").replace("]", "").replace(",", "_").replace(".", "").replace(" ", "") + "_"
			+ sequence;
	}
}
