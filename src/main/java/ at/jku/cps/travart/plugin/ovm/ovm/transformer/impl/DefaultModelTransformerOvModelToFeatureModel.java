import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import org.prop4j.And;
import org.prop4j.AtLeast;
import org.prop4j.AtMost;
import org.prop4j.Choose;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Not;
import org.prop4j.Or;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX;
import static DefaultModelTransformerProperties.VARIANT_PREFIX;

/**
 * This transformer transforms an {@link IOvModel} to an {@link IFeatureModel}.
 *
 * @author johannstoebich
 */
public class DefaultModelTransformerOvModelToFeatureModel implements IModelTransformer<IOvModel, IFeatureModel> {

    /**
     * This method transforms an {@link IOvModel} to an {@link IFeatureModel}. The factory which is used for creating the new feature model must be passed in.
     *
     * @param ovModel   the model which should be transformed.
     * @param factoryTo the factory which is used to create the model.
     * @return the new feature model
     */
    @Override
    public IFeatureModel transform(IOvModel ovModel, IFactory<IFeatureModel> factoryTo) throws NotSupportedTransformationException {
        // a constraint memory can be used by virutal constraints because each constraint has to be unique
        Map<String, Node> constraintMemory = new HashMap<String, Node>();
        Map<String, IOvModelVariationPoint> featureConstraintMemory = new HashMap<String, IOvModelVariationPoint>();

        IFeatureModelFactory factory = (IFeatureModelFactory) factoryTo;
        IFeatureModel featureModel = factory.create();
        featureModel.getProperty().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModel));

        for (IOvModelVariationPoint ovModelVariationPoint : OvModelUtils.getVariationPoints(ovModel)) {
            if (OvModelUtils.isPartOfOvModelRoot(ovModelVariationPoint)) {
                IFeature feature = this.ovModelElementToFeature(ovModelVariationPoint, factory, featureModel);
                if (feature != null) {
                    FeatureUtils.addFeature(featureModel, feature);
                }
                if ((featureModel.getStructure().getRoot() != null)
                        && FeatureUtils.getName(featureModel.getStructure().getRoot().getFeature()).equals(DefaultModelTransformerProperties.ROOT_FEATURE_NAME)) {
                    IFeature rootFeature = featureModel.getStructure().getRoot().getFeature();
                    FeatureUtils.addChild(rootFeature, feature);

                } else if ((featureModel.getStructure().getRoot() != null)) {
                    IFeature rootFeature = factory.createFeature(featureModel, DefaultModelTransformerProperties.ROOT_FEATURE_NAME);
                    FeatureUtils.addFeature(featureModel, rootFeature);
                    FeatureUtils.setOr(rootFeature);
                    FeatureUtils.setAbstract(rootFeature, true);
                    FeatureUtils.setHidden(rootFeature, true);
                    FeatureUtils.addChild(rootFeature, featureModel.getStructure().getRoot().getFeature());
                    FeatureUtils.addChild(rootFeature, feature);
                    featureModel.getStructure().setRoot(rootFeature.getStructure());
                } else {
                    featureModel.getStructure().setRoot(feature.getStructure());
                }
            } else {
                featureConstraintMemory.put(ovModelVariationPoint.getName(), ovModelVariationPoint);
            }
        }

        for (IOvModelConstraint ovModelConstraint : OvModelUtils.getConstraints(ovModel)) {
            Node node = this.elementToNode(ovModelConstraint, factory, featureModel, constraintMemory);

            if (!constraintMemory.containsValue(node)) {
                IConstraint constraint = factory.createConstraint(featureModel, node);
                constraint.setDescription(OvModelUtils.getDescription(ovModelConstraint));
                constraint.setName(OvModelUtils.getName(ovModelConstraint));
                constraint.getCustomProperties().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModelConstraint));

                FeatureUtils.addConstraint(featureModel, constraint);
            }
        }

        for (IOvModelVariationPoint ovModelVariationPoint : featureConstraintMemory.values()) {
            Node node = this.elementToNode(ovModelVariationPoint, factory, featureModel, constraintMemory);

            if (!constraintMemory.containsValue(node)) {
                IConstraint constraint = factory.createConstraint(featureModel, node);
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
     * This method transforms an {@link IOvModelVariationBase} to an {@link IFeature}. The {@link IOvModelVariationBase} should be part of the root feature
     * tree. Otherwise the method {@link #variationBaseToNode(IOvModelVariationBase, IFeatureModelFactory, IFeatureModel, Map)} should be used.
     *
     * @param ovModelVariationBase the variation base which should be transformed to a feature.
     * @param factory              the factory which is used to create the corresponding features.
     * @param featureModel         the feature model which is build up.
     * @return the new feature
     */
    private IFeature ovModelElementToFeature(IOvModelVariationBase ovModelVariationBase, IFeatureModelFactory factory, IFeatureModel featureModel) {

        IFeature feature = this.findFeatureByName(featureModel.getFeatures(), ovModelVariationBase);
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
            IOvModelVariationPoint ovModelVariantionPoint = (IOvModelVariationPoint) ovModelVariationBase;

            for (IOvModelVariationBase ovModelVariationBaseMandatoryChild : OvModelUtils.getMandatoryChildren(ovModelVariantionPoint)) {
                IFeature mandatoryChild = this.ovModelElementToFeature(ovModelVariationBaseMandatoryChild, factory, featureModel);
                if (mandatoryChild != null) {
                    FeatureUtils.addChild(feature, mandatoryChild);
                }
            }
            for (IOvModelVariationBase ovModelVariationBaseOptionalChild : OvModelUtils.getOptionalChildren(ovModelVariantionPoint)) {
                IFeature optionalChild = this.ovModelElementToFeature(ovModelVariationBaseOptionalChild, factory, featureModel);
                if (optionalChild != null) {
                    FeatureUtils.addChild(feature, optionalChild);
                }
            }

            if (OvModelUtils.isAlternative(ovModelVariantionPoint)) {
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

        this.setOvModelVariationBaseProperties(feature, ovModelVariationBase);

        return feature;
    }

    /**
     * This is a utility function which either calls {@link #constraintToNode(IOvModelConstraint, IFeatureModelFactory, IFeatureModel, Map)} or
     * {@link #variationBaseToNode(IOvModelVariationBase, IFeatureModelFactory, IFeatureModel, Map)} depending on whether it is a constraint, variation point or
     * variant.
     *
     * @param ovModelElement   the element which should be transformed.
     * @param factory          the factory which is used to create the new features.
     * @param featureModel     the feature model where the already transformed constraints are stored.
     * @param constraintMemory a memory which contains the already transformed constraints.
     * @return the transformed node.
     * @throws NotSupportedTransformationException
     */
    private Node elementToNode(IOvModelElement ovModelElement, IFeatureModelFactory factory, IFeatureModel featureModel,
                               Map<String, Node> constraintMemory) throws NotSupportedTransformationException {
        if (ovModelElement instanceof IOvModelConstraint) {
            return this.constraintToNode((IOvModelConstraint) ovModelElement, factory, featureModel, constraintMemory);
        } else {
            return this.variationBaseToNode((IOvModelVariationBase) ovModelElement, factory, featureModel, constraintMemory);
        }
    }

    /**
     * This method converts a constraint to a node.
     *
     * @param ovModelConstraint the constraint which should be transformed.
     * @param factory           the factory which is used to create the new features.
     * @param featureModel      the feature model where the already transformed constraints are stored.
     * @param constraintMemory  a memory which contains the already transformed constraints.
     * @return the transformed node.
     * @throws NotSupportedTransformationException
     */
    private Node constraintToNode(IOvModelConstraint ovModelConstraint, IFeatureModelFactory factory, IFeatureModel featureModel,
                                  Map<String, Node> constraintMemory) throws NotSupportedTransformationException {
        String sourceName = OvModelUtils.getName(OvModelUtils.getSource(ovModelConstraint));
        String targetName = OvModelUtils.getName(OvModelUtils.getTarget(ovModelConstraint));
        Node source = this.elementToNode(OvModelUtils.getSource(ovModelConstraint), factory, featureModel, constraintMemory);
        Node target = this.elementToNode(OvModelUtils.getTarget(ovModelConstraint), factory, featureModel, constraintMemory);

        if (ovModelConstraint instanceof IOvModelExcludesConstraint) {
            // Drop the source if it is an artificial variation point.
            if (sourceName.contains(CONSTRAINT_VARIATION_POINT_PREFIX)) {
                Not not = new Not(target);
                constraintMemory.put(OvModelUtils.getName(OvModelUtils.getSource(ovModelConstraint)), not);
                return not;
            } else {
                return new Implies(source, new Not(target));
            }
        } else if (ovModelConstraint instanceof IOvModelRequiresConstraint) {
            // get source and target from the constraint memory. They will only be found if they are as well constraints. Otherwise use the alredy
            // converted source and targets.
            if (sourceName.contains(CONSTRAINT_VARIATION_POINT_PREFIX) && constraintMemory.containsKey(sourceName)) {
                source = constraintMemory.get(sourceName);
                constraintMemory.remove(targetName);
            }
            if (targetName.contains(CONSTRAINT_VARIATION_POINT_PREFIX) && constraintMemory.containsKey(targetName)) {
                target = constraintMemory.get(targetName);
                constraintMemory.remove(targetName);
            }
            Implies implies = new Implies(source, target);
            return implies;

        } else {
            throw new NotSupportedTransformationException(ovModelConstraint.getClass(), Node.class.getClass());
        }
    }

    /**
     * This method transforms a variation base to a node. The variation base should not be part of the model root. For features which are part of the model root
     * the method {@link #ovModelElementToFeature(IOvModelVariationBase, IFeatureModelFactory, IFeatureModel)} should be used.
     *
     * @param ovModelVariationBase the variation base which should be transformed.
     * @param factory              the factory which is used to create the new features.
     * @param featureModel         the feature model where the already transformed constraints are stored.
     * @param constraintMemory     a memory which contains the already transformed constraints.
     * @return the corresponding node of a variation point base.
     * @throws NotSupportedTransformationException
     */
    private Node variationBaseToNode(IOvModelVariationBase ovModelVariationBase, IFeatureModelFactory factory, IFeatureModel featureModel,
                                     Map<String, Node> constraintMemory) throws NotSupportedTransformationException {
        if (ovModelVariationBase instanceof IOvModelVariant) {

            IFeature var = this.ovModelElementToFeature(ovModelVariationBase, factory, featureModel);
            if (var != null) {
                Literal literal = new Literal(var);
                return literal;
            } else {
                return null;
            }

        } else if (ovModelVariationBase instanceof IOvModelVariationPoint) {

            IOvModelVariationPoint ovModelVariationPoint = (IOvModelVariationPoint) ovModelVariationBase;
            // if there is a variation point which is part of root it must be a literal (cannot come from an constraint).
            if (OvModelUtils.isPartOfOvModelRoot(ovModelVariationPoint)) {
                IFeature var = this.ovModelElementToFeature(ovModelVariationBase, factory, featureModel);
                if (var != null) {
                    Literal literal = new Literal(var);
                    return literal;
                } else {
                    throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
                }
            }

            List<IOvModelVariationBase> ovModelChildren = new ArrayList<IOvModelVariationBase>();
            ovModelChildren.addAll(OvModelUtils.getMandatoryChildren(ovModelVariationPoint));
            ovModelChildren.addAll(OvModelUtils.getOptionalChildren(ovModelVariationPoint));

            List<Node> children = new ArrayList<Node>();
            List<IOvModelConstraint> foundConstraints = new ArrayList<IOvModelConstraint>();
            for (IOvModelVariationBase ovModelChild : ovModelChildren) {

                Node element;
                if (constraintMemory.containsKey(ovModelChild.getName())) {
                    element = constraintMemory.get(ovModelChild.getName());
                    constraintMemory.remove(ovModelChild.getName());
                } else {
                    element = this.elementToNode(ovModelChild, factory, featureModel, constraintMemory);
                }

                // on suspicion if there is a constraint used this. Either of these two cases can lead to invalid models.
                List<IConstraint> constraintsWhereElementIsContained =
                        this.getConstraintsWhereElementIsContained(featureModel.getConstraints(), element, true);

                boolean found = false;
                for (IOvModelConstraint ovModelConstraint : OvModelUtils.getReferencedConstraints(ovModelVariationPoint)) {
                    for (IConstraint constraint : constraintsWhereElementIsContained) {
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

            // determine type of the node
            if ((OvModelUtils.getMinChoices(ovModelVariationPoint) == children.size())
                    && (OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size())) {
                return new And(children);
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
                IFeature var = this.ovModelElementToFeature(ovModelVariationBase, factory, featureModel);
                if (var != null) {
                    Literal literal = new Literal(var);
                    return literal;
                } else {
                    throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
                }
            } else {
                throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
            }
        } else {
            throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Node.class);
        }
    }

    /**
     * This method overtakes the OvModel variation base properties to a feature.
     *
     * @param feature              the feature for which the properties should be set.
     * @param ovModelVariationBase the variation base.
     */
    private void setOvModelVariationBaseProperties(IFeature feature, IOvModelVariationBase ovModelVariationBase) {
        FeatureUtils.setMandatory(feature, !OvModelUtils.isOptional(ovModelVariationBase));
        FeatureUtils.setHidden(feature, OvModelUtils.isHidden(ovModelVariationBase));

        FeatureUtils.setDescription(feature, OvModelUtils.getDescription(ovModelVariationBase));
        FeatureUtils.setAbstract(feature, OvModelUtils.isAbstract(ovModelVariationBase));
        feature.getCustomProperties().setProperties(OvModelUtils.getCustomPropertiesEntries(ovModelVariationBase));
    }

    /**
     * This method iterates over all given features to find a feature which matches a specific identifier.
     *
     * @param features             the features which should be looked threw.
     * @param ovModelVariationBase the identifier for which the feature should be found.
     * @return returns the searched feature or null if the feature is not found.
     */
    private IFeature findFeatureByName(Collection<IFeature> features, IIdentifiable ovModelVariationBase) {

        Deque<IFeature> stack = new ArrayDeque<IFeature>();
        stack.addAll(features);

        while (!stack.isEmpty()) {
            IFeature featureToInspect = stack.pop();

            if (featureToInspect.getStructure().getChildren() != null) {
                for (IFeatureStructure childFeatureStructureToInspect : featureToInspect.getStructure().getChildren()) {
                    IFeature childStructureToInspect = childFeatureStructureToInspect.getFeature();
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
     * This method returns all constraints which contain a certain element.
     *
     * @param constraints the constraints which should be searched threw.
     * @param element     the element which should be found.
     * @param isSource    if <code>true</code>, only the sources of the constraints will be checked. Otherwise source and target will be checked.
     * @return The constraints where the element occurs.
     */
    private List<IConstraint> getConstraintsWhereElementIsContained(List<IConstraint> constraints, Node element, boolean isSource) {
        List<IConstraint> constraintsWithElement = new ArrayList<IConstraint>();

        constraintLoop:
        for (IConstraint constraint : constraints) {

            Deque<Node> stack = new ArrayDeque<Node>();
            stack.push(constraint.getNode());

            nodeLoop:
            while (!stack.isEmpty()) {
                Node nodeToInspect = stack.pop();

                if (nodeToInspect.getChildren() != null) {
                    for (Node childToInspect : nodeToInspect.getChildren()) {
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
