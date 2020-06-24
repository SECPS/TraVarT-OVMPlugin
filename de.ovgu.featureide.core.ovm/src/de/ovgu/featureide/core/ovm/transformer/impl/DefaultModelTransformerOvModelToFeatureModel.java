package de.ovgu.featureide.core.ovm.transformer.impl;

import static de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX;
import static de.ovgu.featureide.core.ovm.transformer.impl.DefaultModelTransformerProperties.VARIANT_PREFIX;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariant;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelExcludesConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelRequiresConstraint;
import de.ovgu.featureide.core.ovm.transformer.IModelTransformer;
import de.ovgu.featureide.core.ovm.transformer.impl.exc.NotSupportedTransformationException;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class DefaultModelTransformerOvModelToFeatureModel implements IModelTransformer<IOvModel, IFeatureModel> {

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureid.core.ovm.transformer.IModelTransformer#transform(de.ovgu.featureide.core.ovm.model.IOvModel,
	 * de.ovgu.featureide.fm.core.base.IFeatureModelFactory)
	 */
	@Override
	public IFeatureModel transform(IOvModel ovModel, IFactory<IFeatureModel> factoryTo) throws NotSupportedTransformationException {
		// a constraint memory can be used by virutal constraints because each constraint has to be unique
		final Map<String, Node> constraintMemory = new HashMap<String, Node>();
		final Map<String, IOvModelVariationPoint> featureConstraintMemory = new HashMap<String, IOvModelVariationPoint>();

		final IFeatureModelFactory factory = (IFeatureModelFactory) factoryTo;
		final IFeatureModel featureModel = factory.create();
		featureModel.getProperty().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModel));

		for (final IOvModelVariationPoint ovModelVariationPoint : OvModelUtils.getVariationPoints(ovModel)) {
			if (OvModelUtils.isPartOfOvModelRoot(ovModelVariationPoint)) {
				final IFeature feature = ovModelElementToFeature(ovModelVariationPoint, factory, featureModel);
				if (feature != null) {
					FeatureUtils.addFeature(featureModel, feature);
				}
				if (featureModel.getStructure().getRoot() != null) {
					throw new NotSupportedTransformationException(null, null);
				}
				featureModel.getStructure().setRoot(feature.getStructure());
			} else {
				featureConstraintMemory.put(ovModelVariationPoint.getName(), ovModelVariationPoint);
			}
		}

		for (final IOvModelConstraint ovModelConstraint : OvModelUtils.getConstraints(ovModel)) {
			final Node node = elementToNode(ovModelConstraint, factory, featureModel, constraintMemory);

			if (!constraintMemory.containsValue(node)) {
				final IConstraint constraint = factory.createConstraint(featureModel, node);
				constraint.setDescription(OvModelUtils.getDescription(ovModelConstraint));
				constraint.setName(OvModelUtils.getName(ovModelConstraint));
				constraint.getCustomProperties().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModelConstraint));

				FeatureUtils.addConstraint(featureModel, constraint);
			}
		}

		for (final IOvModelVariationPoint ovModelVariationPoint : featureConstraintMemory.values()) {
			final Node node = elementToNode(ovModelVariationPoint, factory, featureModel, constraintMemory);

			if (!constraintMemory.containsValue(node)) {
				final IConstraint constraint = factory.createConstraint(featureModel, node);
				constraint.setDescription(OvModelUtils.getDescription(ovModelVariationPoint));
				constraint.setName(OvModelUtils.getName(ovModelVariationPoint));
				constraint.getCustomProperties().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModelVariationPoint));

				FeatureUtils.addConstraint(featureModel, constraint);
			}
		}

		if (constraintMemory.size() != 0) {
			throw new NotSupportedTransformationException(IOvModelConstraint.class, Node.class);
		}
		return featureModel;
	}

	/**
	 * @param ovModelVariationBase
	 * @param factory
	 * @param featureModel
	 * @return
	 */
	private IFeature ovModelElementToFeature(final IOvModelVariationBase ovModelVariationBase, IFeatureModelFactory factory, final IFeatureModel featureModel) {

		IFeature feature = findFeatureByName(featureModel.getFeatures(), ovModelVariationBase);
		if (feature != null) {
			return feature;
		}

		feature = factory.createFeature(featureModel, OvModelUtils.getName(ovModelVariationBase));
		if (ovModelVariationBase instanceof IOvModelVariant) {
			// Variants added with variant prefix are additional variants added to the model.
			if (OvModelUtils.getName(ovModelVariationBase).contains(VARIANT_PREFIX)) {
				return null;
			}
		} else if (ovModelVariationBase instanceof IOvModelVariationPoint) {
			final IOvModelVariationPoint ovModelVariantionPoint = (IOvModelVariationPoint) ovModelVariationBase;

			for (final IOvModelVariationBase ovModelVariationBaseMandatoryChild : OvModelUtils.getMandatoryChildren(ovModelVariantionPoint)) {
				final IFeature mandatoryChild = ovModelElementToFeature(ovModelVariationBaseMandatoryChild, factory, featureModel);
				if (mandatoryChild != null) {
					FeatureUtils.addChild(feature, mandatoryChild);
				}
			}
			for (final IOvModelVariationBase ovModelVariationBaseOptionalChild : OvModelUtils.getOptionalChildren(ovModelVariantionPoint)) {
				final IFeature optionalChild = ovModelElementToFeature(ovModelVariationBaseOptionalChild, factory, featureModel);
				if (optionalChild != null) {
					FeatureUtils.addChild(feature, optionalChild);
				}
			}

			if (OvModelUtils.isAlternative(ovModelVariantionPoint)) {
				// 23.06.
				// if ((OvModelUtils.getMandatoryChildrenCount(ovModelVariantionPoint) == 0) && (OvModelUtils.getMinChoices(ovModelVariantionPoint) == 0)
				// && (OvModelUtils.getMaxChoices(ovModelVariantionPoint) == OvModelUtils.getOptionalChildrenCount(ovModelVariantionPoint))) {
				// FeatureUtils.setOr(feature);
				// }
				if ((OvModelUtils.getMinChoices(ovModelVariantionPoint) == 1) && (OvModelUtils.getMaxChoices(ovModelVariantionPoint) == 1)) {
					FeatureUtils.setAlternative(feature);
				} else {
					FeatureUtils.setOr(feature);
				}
			} else {
				FeatureUtils.setAnd(feature);
			}
		} else {
			throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), IOvModelVariationBase.class);
		}

		setOvModelVariationBaseProperties(feature, factory, ovModelVariationBase);

		return feature;
	}

	private Node elementToNode(IOvModelElement ovModelElement, IFeatureModelFactory factory, final IFeatureModel featureModel,
			final Map<String, Node> constraintMemory) throws NotSupportedTransformationException {
		if (ovModelElement instanceof IOvModelConstraint) {
			return constraintToNode((IOvModelConstraint) ovModelElement, factory, featureModel, constraintMemory);
		} else {
			return variationBaseToNode((IOvModelVariationBase) ovModelElement, factory, featureModel, constraintMemory);
		}
	}

	private Node constraintToNode(IOvModelConstraint ovModelConstraint, IFeatureModelFactory factory, final IFeatureModel featureModel,
			final Map<String, Node> constraintMemory) throws NotSupportedTransformationException {
		final String sourceName = OvModelUtils.getName(OvModelUtils.getSource(ovModelConstraint));
		final String targetName = OvModelUtils.getName(OvModelUtils.getTarget(ovModelConstraint));
		Node source = elementToNode(OvModelUtils.getSource(ovModelConstraint), factory, featureModel, constraintMemory);
		Node target = elementToNode(OvModelUtils.getTarget(ovModelConstraint), factory, featureModel, constraintMemory);

		if (ovModelConstraint instanceof IOvModelExcludesConstraint) {
			// The source must be able to ignorable to be transformed.
			if (sourceName.contains(CONSTRAINT_VARIATION_POINT_PREFIX)) {
				final Not not = new Not(target);
				constraintMemory.put(OvModelUtils.getName(OvModelUtils.getSource(ovModelConstraint)), not);
				return not;
			} else {
				return new Implies(source, new Not(target));
			}
		} else if (ovModelConstraint instanceof IOvModelRequiresConstraint) {
			if (sourceName.contains(CONSTRAINT_VARIATION_POINT_PREFIX) && constraintMemory.containsKey(sourceName)) {
				source = constraintMemory.get(sourceName);
				constraintMemory.remove(targetName);
			}
			if (targetName.contains(CONSTRAINT_VARIATION_POINT_PREFIX) && constraintMemory.containsKey(targetName)) {
				target = constraintMemory.get(targetName);
				constraintMemory.remove(targetName);
			}
			final Implies implies = new Implies(source, target);
			return implies;

		} else {
			throw new NotSupportedTransformationException(ovModelConstraint.getClass(), Node.class.getClass());
		}
	}

	private Node variationBaseToNode(IOvModelVariationBase ovModelVariationBase, IFeatureModelFactory factory, final IFeatureModel featureModel,
			final Map<String, Node> constraintMemory) throws NotSupportedTransformationException {
		if (ovModelVariationBase instanceof IOvModelVariant) {

			final IFeature var = ovModelElementToFeature(ovModelVariationBase, factory, featureModel);
			if (var != null) {
				final Literal literal = new Literal(var);
				return literal;
			} else {
				return null;
			}

		} else if (ovModelVariationBase instanceof IOvModelVariationPoint) {

			final IOvModelVariationPoint ovModelVariationPoint = (IOvModelVariationPoint) ovModelVariationBase;
			if (OvModelUtils.isPartOfOvModelRoot(ovModelVariationPoint)) {
				final IFeature var = ovModelElementToFeature(ovModelVariationBase, factory, featureModel);
				if (var != null) {
					final Literal literal = new Literal(var);
					return literal;
				} else {
					throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
				}
			}

			final List<IOvModelVariationBase> ovModelChildren = new ArrayList<IOvModelVariationBase>();
			ovModelChildren.addAll(OvModelUtils.getMandatoryChildren(ovModelVariationPoint));
			ovModelChildren.addAll(OvModelUtils.getOptionalChildren(ovModelVariationPoint));

			final List<Node> children = new ArrayList<Node>();
			final List<IOvModelConstraint> foundConstraints = new ArrayList<IOvModelConstraint>();
			for (final IOvModelVariationBase ovModelChild : ovModelChildren) {

				final Node element = elementToNode(ovModelChild, factory, featureModel, constraintMemory);
				// on suspicion if there is a constraint used this. Either of these two cases can lead to invalid models.
				final List<IConstraint> constraintsWhereElementIsContained =
					getConstraintsWhereElementIsContained(featureModel.getConstraints(), element, true);

				boolean found = false;
				for (final IOvModelConstraint ovModelConstraint : OvModelUtils.getReferencedConstraints(ovModelVariationPoint)) {
					for (final IConstraint constraint : constraintsWhereElementIsContained) {
						if (Objects.equals(constraint.getName(), OvModelUtils.getName(ovModelConstraint))) {
							if (found) { // for each ovModelChild at maximum one constraint should be found.
								throw new NotSupportedTransformationException(IOvModelConstraint.class, IConstraint.class);
							}
							children.add(constraint.getNode());
							featureModel.removeConstraint(constraint);
							found = true;
							foundConstraints.add(ovModelConstraint);
						}
					}
				}
				if (!found) {
					children.add(element);
				}
			}

			// all referenced constraints have to be found
			if (foundConstraints.size() != OvModelUtils.getReferencedConstraints(ovModelVariationPoint).size()) {
				throw new NotSupportedTransformationException(IOvModelConstraint.class, IConstraint.class);
			}

			if ((OvModelUtils.getMinChoices(ovModelVariationPoint) == children.size())
				&& (OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size())) {
				return new And(children);
				// 22.05 OvModelUtils.getMinChoices(ovModelVariationPoint) == 0)
			} else if ((OvModelUtils.getMinChoices(ovModelVariationPoint) == 1) && (OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size())) {
				return new Or(children);
			} else if (OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size()) {
				return new AtLeast(OvModelUtils.getMinChoices(ovModelVariationPoint), children);
			} else if (OvModelUtils.getMinChoices(ovModelVariationPoint) == 0) {
				return new AtMost(OvModelUtils.getMaxChoices(ovModelVariationPoint), children);
			} else if ((OvModelUtils.getMinChoices(ovModelVariationPoint) == OvModelUtils.getMaxChoices(ovModelVariationPoint))
				&& (OvModelUtils.getMinChoices(ovModelVariationPoint) != -1)) {
					return new Choose(OvModelUtils.getMinChoices(ovModelVariationPoint), children);
				} else if ((children.size() == 1) && children.stream().allMatch(t -> t == null)) {
					final IFeature var = ovModelElementToFeature(ovModelVariationBase, factory, featureModel);
					if (var != null) {
						final Literal literal = new Literal(var);
						return literal;
					} else {
						throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
					}
				} else {
					throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
				}
		} else

		{
			throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
		}
	}

	private void setOvModelVariationBaseProperties(final IFeature feature, IFeatureModelFactory factory, final IOvModelVariationBase ovModelVariationBase) {
		FeatureUtils.setMandatory(feature, !OvModelUtils.isOptional(ovModelVariationBase));
		FeatureUtils.setHidden(feature, OvModelUtils.isHidden(ovModelVariationBase));

		FeatureUtils.setDescription(feature, OvModelUtils.getDescription(ovModelVariationBase));
		FeatureUtils.setAbstract(feature, OvModelUtils.isAbstract(ovModelVariationBase));
		feature.getCustomProperties().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModelVariationBase));
	}

	/**
	 * @param featureModel
	 * @return returns null if the feature is not found
	 */
	private IFeature findFeatureByName(final Collection<IFeature> features, IIdentifyable ovModelVariationBase) {

		final Deque<IFeature> stack = new ArrayDeque<IFeature>();
		stack.addAll(features);

		while (!stack.isEmpty()) {
			final IFeature featureToInspect = stack.pop();

			if (featureToInspect.getStructure().getChildren() != null) {
				for (final IFeatureStructure childFeatureStructureToInspect : featureToInspect.getStructure().getChildren()) {
					final IFeature childStructureToInspect = childFeatureStructureToInspect.getFeature();
					stack.push(childStructureToInspect);

					if (Objects.equals(FeatureUtils.getName(childStructureToInspect), OvModelUtils.getName(ovModelVariationBase))) {
						return childStructureToInspect;
					}
				}
			}

		}

		return null;
	}

	/**
	 * This method returns all constraints which contains a certain element.
	 *
	 * @param featureModel
	 * @param constraints
	 * @return
	 */
	private List<IConstraint> getConstraintsWhereElementIsContained(final List<IConstraint> constraints, final Node element, boolean isSource) {
		final List<IConstraint> constraintsWithElement = new ArrayList<IConstraint>();

		constraintLoop: for (final IConstraint constraint : constraints) {

			final Deque<Node> stack = new ArrayDeque<Node>();
			stack.push(constraint.getNode());

			nodeLoop: while (!stack.isEmpty()) {
				final Node nodeToInspect = stack.pop();

				if (nodeToInspect.getChildren() != null) {
					for (final Node childToInspect : nodeToInspect.getChildren()) {
						stack.push(childToInspect);

						if (childToInspect.equals(element)) {
							constraintsWithElement.add(constraint);
							continue constraintLoop;
						}

						if (isSource) {
							continue nodeLoop; // only look at first node.
						}
					}
				}

			}
		}

		return constraintsWithElement;
	}

}
