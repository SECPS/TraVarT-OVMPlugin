package at.jku.cps.travart.plugin.ovm.ovm.transformation;

import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.core.helpers.TraVarTUtils;
import at.jku.cps.travart.core.transformation.DefaultModelTransformationProperties;
import at.jku.cps.travart.plugin.ovm.ovm.common.OvModelUtils;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariant;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelExcludesConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelRequiresConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.exc.NotSupportedTransformationException;
import de.vill.model.Attribute;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ImplicationConstraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NotConstraint;
import org.logicng.formulas.FormulaFactory;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static at.jku.cps.travart.core.transformation.DefaultModelTransformationProperties.ABSTRACT_ATTRIBUTE;
import static at.jku.cps.travart.core.transformation.DefaultModelTransformationProperties.HIDDEN_ATTRIBUTE;

/**
 * This transformer transforms an {@link IOvModel} to an {@link FeatureModel}.
 *
 * @author johannstoebich
 */
public class OvModelToFeatureModelTransformer {
    private FeatureModel featureModel;

    /**
     * This method transforms an {@link IOvModel} to an {@link FeatureModel}. The
     * factory which is used for creating the new feature model must be passed in.
     *
     * @param ovModel   the model which should be transformed.
     * @param modelName name of the model
     * @return the new feature model
     */
    public FeatureModel transform(final IOvModel ovModel, final String modelName)
            throws NotSupportedVariabilityTypeException {
        try {
            this.featureModel = new FeatureModel();
            // a constraint memory can be used by virtual constraints because each constraint has to be unique
            final Map<String, Constraint> constraintMemory = new HashMap<>();
            final Map<String, IOvModelVariationPoint> featureConstraintMemory = new HashMap<>();

            for (final IOvModelVariationPoint ovModelVariationPoint : OvModelUtils.getVariationPoints(ovModel)) {
                if (OvModelUtils.isPartOfOvModelRoot(ovModelVariationPoint)) {
                    final Feature feature = this.ovModelElementToFeature(ovModelVariationPoint, this.featureModel);
                    if (feature != null) {
                        this.featureModel.getFeatureMap().put(feature.getFeatureName(), feature);
                    }
                    if (this.featureModel.getRootFeature() != null
                            && this.featureModel.getRootFeature().getFeatureName().equals(DefaultModelTransformationProperties.ARTIFICIAL_MODEL_NAME)
                    ) {
                        final Feature rootFeature = this.featureModel.getRootFeature();
                        final Group optionalGroup = new Group(Group.GroupType.OPTIONAL);
                        optionalGroup.getFeatures().add(feature);
                        rootFeature.addChildren(optionalGroup);
                        this.featureModel.getFeatureMap().put(feature.getFeatureName(), feature);
                        this.featureModel.getFeatureMap().put(rootFeature.getFeatureName(), rootFeature);
                    } else if (this.featureModel.getRootFeature() != null) {
                        final Feature rootFeature = new Feature(DefaultModelTransformationProperties.ARTIFICIAL_MODEL_NAME);
                        rootFeature.getAttributes().put(
                                ABSTRACT_ATTRIBUTE,
                                new Attribute<Boolean>(
                                        ABSTRACT_ATTRIBUTE,
                                        Boolean.TRUE
                                )
                        );
                        rootFeature.getAttributes().put(
                                HIDDEN_ATTRIBUTE,
                                new Attribute<Boolean>(
                                        HIDDEN_ATTRIBUTE,
                                        Boolean.TRUE
                                )
                        );
                        final Group orGroup = new Group(Group.GroupType.OR);
                        orGroup.getFeatures().add(feature);
                        orGroup.getFeatures().add(this.featureModel.getRootFeature());
                        rootFeature.addChildren(orGroup);
                        this.featureModel.getFeatureMap().put(rootFeature.getFeatureName(), rootFeature);
                        this.featureModel.setRootFeature(rootFeature);
                    } else {
                        this.featureModel.setRootFeature(feature);
                    }
                } else {
                    featureConstraintMemory.put(ovModelVariationPoint.getName(), ovModelVariationPoint);
                }
            }

            for (final IOvModelConstraint ovModelConstraint : OvModelUtils.getConstraints(ovModel)) {
                final Constraint constraint = this.elementToNode(ovModelConstraint, this.featureModel, constraintMemory);

                if (constraint != null && !constraintMemory.containsValue(constraint)) {
                    this.featureModel.getConstraints().add(constraint);
                }
            }

            for (final IOvModelVariationPoint ovModelVariationPoint : featureConstraintMemory.values()) {
                final Constraint constraint = this.elementToNode(ovModelVariationPoint, this.featureModel, constraintMemory);

                if (constraint != null && !constraintMemory.containsValue(constraint)) {
                    this.featureModel.getConstraints().add(constraint);
                }
            }

            return this.featureModel;
        } catch (final NotSupportedTransformationException e) {
            throw new NotSupportedVariabilityTypeException(e);
        }
    }

    /**
     * This method transforms an {@link IOvModelVariationBase} to an
     * {@link Feature}. The {@link IOvModelVariationBase} should be part of the
     * root feature tree. Otherwise, the method
     * {@link #variationBaseToNode(IOvModelVariationBase, FeatureModel, Map)}
     * should be used.
     *
     * @param ovModelVariationBase the variation base which should be transformed to
     *                             a feature.
     * @param featureModel         the feature model which is build up.
     * @return the new feature
     */
    private Feature ovModelElementToFeature(
            final IOvModelVariationBase ovModelVariationBase,
            final FeatureModel featureModel
    ) {
        Feature feature = featureModel.getFeatureMap().get(OvModelUtils.getName(ovModelVariationBase));
        if (feature != null) {
            return feature;
        }

        feature = new Feature(OvModelUtils.getName(ovModelVariationBase));
        if (ovModelVariationBase instanceof IOvModelVariant) {
            // Variants added with variant prefix are additional variants added to the model.
            if (OvModelUtils.getName(ovModelVariationBase).contains(DefaultOvModelTransformationProperties.VARIANT_PREFIX)) {
                return null;
            }
        } else if (ovModelVariationBase instanceof IOvModelVariationPoint) {
            final IOvModelVariationPoint ovModelVariationPoint = (IOvModelVariationPoint) ovModelVariationBase;
            for (final IOvModelVariationBase ovModelVariationBaseMandatoryChild : OvModelUtils.getMandatoryChildren(ovModelVariationPoint)) {
                final Feature mandatoryChild = this.ovModelElementToFeature(
                        ovModelVariationBaseMandatoryChild,
                        featureModel
                );
                if (mandatoryChild != null) {
                    final Group mandatoryGroup = new Group(Group.GroupType.MANDATORY);
                    mandatoryGroup.getFeatures().add(mandatoryChild);
                    feature.addChildren(mandatoryGroup);
                    mandatoryChild.setParentGroup(mandatoryGroup);
                    featureModel.getFeatureMap().put(mandatoryChild.getFeatureName(), mandatoryChild);
                    featureModel.getFeatureMap().put(feature.getFeatureName(), feature);
                }
            }
            for (final IOvModelVariationBase ovModelVariationBaseOptionalChild : OvModelUtils.getOptionalChildren(ovModelVariationPoint)) {
                final Feature optionalChild = this.ovModelElementToFeature(
                        ovModelVariationBaseOptionalChild,
                        featureModel
                );
                if (optionalChild != null) {
                    final Group optionalGroup = new Group(Group.GroupType.OPTIONAL);
                    optionalGroup.getFeatures().add(optionalChild);
                    feature.addChildren(optionalGroup);
                    optionalChild.setParentGroup(optionalGroup);
                    featureModel.getFeatureMap().put(optionalChild.getFeatureName(), optionalChild);
                    featureModel.getFeatureMap().put(feature.getFeatureName(), feature);
                }
            }

            if (OvModelUtils.isAlternative(ovModelVariationPoint)) {
                if (OvModelUtils.getMinChoices(ovModelVariationPoint) == 1 && OvModelUtils.getMaxChoices(ovModelVariationPoint) == 1) {
                    TraVarTUtils.setGroup(
                            featureModel,
                            feature,
                            feature.getParentFeature(),
                            Group.GroupType.ALTERNATIVE

                    );
                } else {
                    TraVarTUtils.setGroup(
                            featureModel,
                            feature,
                            feature.getParentFeature(),
                            Group.GroupType.OR

                    );

                }
            } else {
                TraVarTUtils.setGroup(
                        featureModel,
                        feature,
                        feature.getParentFeature(),
                        Group.GroupType.MANDATORY

                );
            }
        } else {
            throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), IOvModelVariationBase.class);
        }

        this.setOvModelVariationBaseProperties(feature, ovModelVariationBase);
        featureModel.getFeatureMap().put(feature.getFeatureName(), feature);

        return feature;
    }

    /**
     * This is a utility function which either calls
     * {@link #constraintToNode(IOvModelConstraint, FeatureModel, Map)}
     * or
     * {@link #variationBaseToNode(IOvModelVariationBase, FeatureModel, Map)}
     * depending on whether it is a constraint, variation point or variant.
     *
     * @param ovModelElement   the element which should be transformed.
     * @param featureModel     the feature model where the already transformed
     *                         constraints are stored.
     * @param constraintMemory a memory which contains the already transformed
     *                         constraints.
     * @return the transformed node.
     * @throws NotSupportedTransformationException exception
     */
    private Constraint elementToNode(
            final IOvModelElement ovModelElement,
            final FeatureModel featureModel,
            final Map<String, Constraint> constraintMemory
    ) throws NotSupportedTransformationException {
        if (ovModelElement instanceof IOvModelConstraint) {
            return this.constraintToNode((IOvModelConstraint) ovModelElement, featureModel, constraintMemory);
        }
        return this.variationBaseToNode((IOvModelVariationBase) ovModelElement, featureModel, constraintMemory);
    }

    /**
     * This method converts a constraint to a node.
     *
     * @param ovModelConstraint the constraint which should be transformed.
     * @param featureModel      the feature model where the already transformed
     *                          constraints are stored.
     * @param constraintMemory  a memory which contains the already transformed
     *                          constraints.
     * @return the transformed node.
     * @throws NotSupportedTransformationException exception
     */
    private Constraint constraintToNode(
            final IOvModelConstraint ovModelConstraint,
            final FeatureModel featureModel,
            final Map<String, Constraint> constraintMemory
    ) throws NotSupportedTransformationException {
        final String sourceName = OvModelUtils.getName(OvModelUtils.getSource(ovModelConstraint));
        final String targetName = OvModelUtils.getName(OvModelUtils.getTarget(ovModelConstraint));
        Constraint source = this.elementToNode(OvModelUtils.getSource(ovModelConstraint), featureModel, constraintMemory);
        Constraint target = this.elementToNode(OvModelUtils.getTarget(ovModelConstraint), featureModel, constraintMemory);

        if (ovModelConstraint instanceof IOvModelExcludesConstraint) {
            // Drop the source if it is an artificial variation point.
            if (sourceName.contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
                final NotConstraint not = new NotConstraint(target);
                constraintMemory.put(OvModelUtils.getName(OvModelUtils.getSource(ovModelConstraint)), not);

                return not;
            }
            return new ImplicationConstraint(source, new NotConstraint(target));
        }
        if (!(ovModelConstraint instanceof IOvModelRequiresConstraint)) {
            throw new NotSupportedTransformationException(ovModelConstraint.getClass(), Constraint.class);
        }
        // get source and target from the constraint memory. They will only be found if
        // they are as well constraints. Otherwise, use the already
        // converted source and targets.
        if (sourceName.contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)
                && constraintMemory.containsKey(sourceName)) {
            source = constraintMemory.get(sourceName);
            constraintMemory.remove(targetName);
        }
        if (targetName.contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)
                && constraintMemory.containsKey(targetName)) {
            target = constraintMemory.get(targetName);
            constraintMemory.remove(targetName);
        }
        return new ImplicationConstraint(source, target);
    }

    /**
     * This method transforms a variation base to a node. The variation base should
     * not be part of the model root. For features which are part of the model root
     * the method
     * {@link #ovModelElementToFeature(IOvModelVariationBase, FeatureModel)}
     * should be used.
     *
     * @param ovModelVariationBase the variation base which should be transformed.
     * @param featureModel         the feature model where the already transformed
     *                             constraints are stored.
     * @param constraintMemory     a memory which contains the already transformed
     *                             constraints.
     * @return the corresponding node of a variation point base.
     * @throws NotSupportedTransformationException exception
     */
    private Constraint variationBaseToNode(
            final IOvModelVariationBase ovModelVariationBase,
            final FeatureModel featureModel,
            final Map<String, Constraint> constraintMemory
    ) throws NotSupportedTransformationException {
        if (ovModelVariationBase instanceof IOvModelVariant) {
            final Feature var = this.ovModelElementToFeature(ovModelVariationBase, featureModel);
            if (var != null) {
                return new LiteralConstraint(var.getFeatureName());
            }
            return null;

        }
        if (!(ovModelVariationBase instanceof IOvModelVariationPoint)) {
            throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Constraint.class);
        }
        final IOvModelVariationPoint ovModelVariationPoint = (IOvModelVariationPoint) ovModelVariationBase;
        // if there is a variation point which is part of root it must be a literal
        // (cannot come from a constraint).
        if (OvModelUtils.isPartOfOvModelRoot(ovModelVariationPoint)) {
            final Feature var = this.ovModelElementToFeature(ovModelVariationBase, featureModel);
            if (var != null) {
                return new LiteralConstraint(var.getFeatureName());
            }
            throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Constraint.class);
        }

        final List<IOvModelVariationBase> ovModelChildren = new ArrayList<>();
        ovModelChildren.addAll(OvModelUtils.getMandatoryChildren(ovModelVariationPoint));
        ovModelChildren.addAll(OvModelUtils.getOptionalChildren(ovModelVariationPoint));

        final List<Constraint> children = new ArrayList<>();
        final List<IOvModelConstraint> foundConstraints = new ArrayList<>();
        for (final IOvModelVariationBase ovModelChild : ovModelChildren) {

            final Constraint element;
            if (constraintMemory.containsKey(ovModelChild.getName())) {
                element = constraintMemory.get(ovModelChild.getName());
                constraintMemory.remove(ovModelChild.getName());
            } else {
                element = this.elementToNode(ovModelChild, featureModel, constraintMemory);
            }
            // on suspicion if there is a constraint used this. Either of these two cases can lead to invalid models.
            final List<Constraint> constraintsWhereElementIsContained = this.getConstraintsWhereFeatureIsContained(
                    featureModel.getConstraints(),
                    element,
                    Boolean.TRUE
            );

            boolean found = false;
            for (final IOvModelConstraint ovModelConstraint : OvModelUtils.getReferencedConstraints(ovModelVariationPoint)) {
                for (final Constraint constraint : constraintsWhereElementIsContained) {
                    if (Objects.equals(constraint.toString(), OvModelUtils.getName(ovModelConstraint))) {
                        if (found) {
                            // for each ovModelChild at maximum one constraint should be found.
                            throw new NotSupportedTransformationException(IOvModelConstraint.class, Constraint.class);
                        }
                        children.add(constraint);
                        featureModel.getConstraints().remove(constraint);
                        found = true;
                        foundConstraints.add(ovModelConstraint);
                    }
                }
            }
            if (element != null && !found) {
                children.add(element);
            }
        }

        // all referenced constraints have to be found
        if (foundConstraints.size() != OvModelUtils.getReferencedConstraints(ovModelVariationPoint).size()) {
            throw new NotSupportedTransformationException(IOvModelConstraint.class, Constraint.class);
        }

        // determine type of the node
        if (OvModelUtils.getMinChoices(ovModelVariationPoint) == children.size() && OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size()) {
            // if children size == 1 then mandatory
            return new And(children);
        }
        if (OvModelUtils.getMinChoices(ovModelVariationPoint) == 1 && OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size()) {
            return new Or(children);
        }
        if (OvModelUtils.getMaxChoices(ovModelVariationPoint) == children.size()) {
            return new AtLeast(OvModelUtils.getMinChoices(ovModelVariationPoint), children);
        }
        if (OvModelUtils.getMinChoices(ovModelVariationPoint) == 0) {
            return new AtMost(OvModelUtils.getMaxChoices(ovModelVariationPoint), children);
        }
        if (OvModelUtils.getMinChoices(ovModelVariationPoint) == OvModelUtils.getMaxChoices(ovModelVariationPoint)
                && OvModelUtils.getMinChoices(ovModelVariationPoint) != -1) {
            return new Choose(OvModelUtils.getMinChoices(ovModelVariationPoint), children);
        } else if (children.size() == 1 && children.stream().allMatch(Objects::isNull)) {
            final Feature var = this.ovModelElementToFeature(ovModelVariationBase, featureModel);
            if (var != null) {
                return new LiteralConstraint(var.getFeatureName());
            } else {
                throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Constraint.class);
            }
        } else {
            throw new NotSupportedTransformationException(ovModelVariationBase.getClass(), Constraint.class);
        }
    }

    /**
     * This method overtakes the OvModel variation base properties to a feature.
     *
     * @param feature              the feature for which the properties should be
     *                             set.
     * @param ovModelVariationBase the variation base.
     */
    private void setOvModelVariationBaseProperties(
            final Feature feature, final IOvModelVariationBase ovModelVariationBase
    ) {

        if (!OvModelUtils.isOptional(ovModelVariationBase)) {
            final Feature parent = feature.getParentFeature();
            feature.getParentGroup().getFeatures().remove(feature);
            final Group mandatoryGroup = new Group(Group.GroupType.MANDATORY);
            mandatoryGroup.getFeatures().add(feature);
            parent.addChildren(mandatoryGroup);
            this.featureModel.getFeatureMap().put(feature.getFeatureName(), feature);
            this.featureModel.getFeatureMap().put(parent.getFeatureName(), parent);
        }
        feature.getAttributes().put(
                HIDDEN_ATTRIBUTE,
                new Attribute<Boolean>(
                        HIDDEN_ATTRIBUTE,
                        OvModelUtils.isHidden(ovModelVariationBase)
                )
        );

        feature.getAttributes().put(
                ABSTRACT_ATTRIBUTE,
                new Attribute<Boolean>(
                        ABSTRACT_ATTRIBUTE,
                        OvModelUtils.isAbstract(ovModelVariationBase)
                )
        );
    }

    /**
     * This method returns all constraints which contain a certain element.
     *
     * @param constraints the constraints which should be searched threw.
     * @param element     the element which should be found.
     * @param isSource    if <code>true</code>, only the sources of the constraints
     *                    will be checked. Otherwise, source and target will be
     *                    checked.
     * @return The constraints where the element occurs.
     */
    private List<Constraint> getConstraintsWhereFeatureIsContained(
            final List<Constraint> constraints,

            final Constraint element,
            final boolean isSource
    ) {
        final List<Constraint> constraintsWithElement = new ArrayList<>();

        // todo: check if works properly or not doesn't make much sense tbh

        constraintLoop:
        for (final Constraint constraint : constraints) {
            final Constraint cnfConstraint = TraVarTUtils.buildConstraintFromFormula(
                    TraVarTUtils.buildFormulaFromConstraint(
                            constraint,
                            new FormulaFactory()
                    ).cnf()
            );

            final Deque<Constraint> stack = new LinkedList<>();
            stack.push(cnfConstraint);

            nodeLoop:
            while (!stack.isEmpty()) {
                final Constraint nodeToInspect = stack.pop();

                if (nodeToInspect.getConstraintSubParts() != null) {
                    for (final Constraint childToInspect : nodeToInspect.getConstraintSubParts()) {
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
