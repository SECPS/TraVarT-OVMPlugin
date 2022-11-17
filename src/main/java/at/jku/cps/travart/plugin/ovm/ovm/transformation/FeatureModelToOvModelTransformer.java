package at.jku.cps.travart.plugin.ovm.ovm.transformation;

import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.core.helpers.TraVarTUtils;
import at.jku.cps.travart.core.transformation.DefaultModelTransformationProperties;
import at.jku.cps.travart.plugin.ovm.ovm.common.OvModelUtils;
import at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory;
import at.jku.cps.travart.plugin.ovm.ovm.factory.impl.OvModelFactory;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariant;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.exc.NotSupportedTransformationException;
import de.vill.model.Feature;
import de.vill.model.FeatureModel;
import de.vill.model.Group;
import de.vill.model.constraint.AndConstraint;
import de.vill.model.constraint.Constraint;
import de.vill.model.constraint.ImplicationConstraint;
import de.vill.model.constraint.LiteralConstraint;
import de.vill.model.constraint.NotConstraint;
import de.vill.model.constraint.OrConstraint;
import org.logicng.formulas.FormulaFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This transformer transforms an {@link FeatureModel} to an {@link IOvModel}.
 *
 * @author johannstoebich
 */
public class FeatureModelToOvModelTransformer {

    private Integer sequence = 0;

    /**
     * This method transforms an {@link FeatureModel} to an {@link IOvModel}. The
     * method
     * {@link #featureToOvModelElement(Feature, IOvModelFactory, IOvModel)}
     * is used to transform all variation points and the method
     * {@link #nodeToConstraints(Constraint, IOvModelFactory, IOvModel)} is
     * used to transform the constraints.
     *
     * @param featureModel the feature model which should be transformed.
     * @param modelName    name of the model
     * @return a new ovModel
     */
    public IOvModel transform(final FeatureModel featureModel, final String modelName)
            throws NotSupportedVariabilityTypeException {
        final IOvModelFactory factory = OvModelFactory.getInstance();
        final IOvModel ovModel = factory.create();

        if (featureModel.getRootFeature().getFeatureName().contentEquals(DefaultModelTransformationProperties.ARTIFICIAL_MODEL_NAME)) {
            for (final Feature feature : TraVarTUtils.getChildren(featureModel.getRootFeature())) {
                final IOvModelVariationPoint modelVariantPoint = (IOvModelVariationPoint) this.featureToOvModelElement(
                        feature,
                        factory,
                        ovModel
                );
                OvModelUtils.addVariationPoint(ovModel, modelVariantPoint);
            }
        } else {
            final IOvModelVariationPoint modelVariantPoint = (IOvModelVariationPoint) this.featureToOvModelElement(
                    featureModel.getRootFeature(),
                    factory,
                    ovModel
            );
            OvModelUtils.addVariationPoint(ovModel, modelVariantPoint);
        }

        for (final Constraint constraint : featureModel.getConstraints()) {
            final IOvModelElement ovModelElement = this.nodeToConstraints(
                    constraint,
                    factory,
                    ovModel
            );

            if (ovModelElement instanceof IOvModelConstraint) {
                final IOvModelConstraint ovModelConstraint = (IOvModelConstraint) ovModelElement;
                OvModelUtils.setName(ovModelConstraint, constraint.toString());

                OvModelUtils.addConstraint(ovModel, ovModelConstraint);
            } else if (ovModelElement instanceof IOvModelVariationPoint) {
                final IOvModelVariationPoint ovModelVariationBase = (IOvModelVariationPoint) ovModelElement;
                OvModelUtils.setName(ovModelVariationBase, constraint.toString());
                OvModelUtils.addVariationPoint(ovModel, ovModelVariationBase);
            }
        }
        return ovModel;
    }

    /**
     * This method transforms a feature to a variation point. It uses the method
     * recursively to transform the children as well. The new elements will be
     * created by the factory.
     *
     * @param feature The feature which should be transformed.
     * @param ovModel The new {@link IOvModel} containing the already transformed
     *                variation points and variants.
     * @return The new variation point or variant.
     */
    private IOvModelVariationBase featureToOvModelElement(final Feature feature, final IOvModelFactory factory, final IOvModel ovModel) {
        IOvModelVariationBase ovModelVariationBase = this.findFeatureInOvModel(feature, factory, ovModel);
        if (ovModelVariationBase != null) {
            return ovModelVariationBase;
        }

        if (TraVarTUtils.getChildren(feature).isEmpty()) {
            final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(
                    ovModel,
                    feature.getFeatureName()
            );
            ovModelVariationBase = ovModelVariationPoint;

            final IOvModelVariant ovModelVariant = factory.createVariant(
                    ovModel,
                    DefaultOvModelTransformationProperties.VARIANT_PREFIX + feature.getFeatureName()
            );
            OvModelUtils.setPartOfOvModelRoot(ovModelVariant, true);
            OvModelUtils.setMaxChoices(ovModelVariationPoint, -1);
            OvModelUtils.setMinChoices(ovModelVariationPoint, -1);
            OvModelUtils.addMandatoryChild(ovModelVariationPoint, ovModelVariant);

        } else if (!TraVarTUtils.getChildren(feature).isEmpty()) {
            final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(
                    ovModel,
                    feature.getFeatureName()
            );
            ovModelVariationBase = ovModelVariationPoint;

            for (final Feature child : TraVarTUtils.getChildren(feature)) {
                final IOvModelVariationBase ovModelChild = this.featureToOvModelElement(child, factory, ovModel);

                if (TraVarTUtils.checkGroupType(feature, Group.GroupType.OR)) {
                    OvModelUtils.addOptionalChild(ovModelVariationPoint, ovModelChild);
                } else if (TraVarTUtils.checkGroupType(feature, Group.GroupType.ALTERNATIVE)) {
                    OvModelUtils.addOptionalChild(ovModelVariationPoint, ovModelChild);
                } else if (TraVarTUtils.checkGroupType(feature, Group.GroupType.MANDATORY)) { // AND
                    // TODO: check if it'll work or not
                    if (TraVarTUtils.checkGroupType(child, Group.GroupType.MANDATORY)) {
                        OvModelUtils.addMandatoryChild(ovModelVariationPoint, ovModelChild);
                    } else {
                        OvModelUtils.addOptionalChild(ovModelVariationPoint, ovModelChild);
                    }
                }
            }

            if (TraVarTUtils.checkGroupType(feature, Group.GroupType.ALTERNATIVE)) {
                OvModelUtils.setAlternative(ovModelVariationPoint, true);
                OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
                OvModelUtils.setMaxChoices(ovModelVariationPoint, 1);
            } else if (TraVarTUtils.checkGroupType(feature, Group.GroupType.OR)) {
                OvModelUtils.setAlternative(ovModelVariationPoint, true);
                OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
                OvModelUtils.setMaxChoices(
                        ovModelVariationPoint,
                        OvModelUtils.getOptionalChildrenCount(ovModelVariationPoint)
                );
            }
        }

        this.setOvModelVariationBaseProperties(ovModelVariationBase, feature);

        return ovModelVariationBase;
    }

    /**
     * This method searches for a variation point or variant in the given
     * {@link IOvModel} by using the feature's name.
     *
     * @param feature The feature which should be found by the given name.
     * @param factory The factory used to create an identifiable for searching in
     *                the {@link IOvModel}.
     * @param ovModel The model which should be searched threw.
     * @return IOvModelVariationBase
     */
    private IOvModelVariationBase findFeatureInOvModel(final Feature feature, final IOvModelFactory factory, final IOvModel ovModel) {
        return (IOvModelVariationBase) ovModel.getElement(factory.createIdentifiable(0, feature.getFeatureName()));
    }

    /**
     * This method transforms a node to a constraint or variation point. It uses the
     * method recursively to transform the children as well. The new elements will
     * be created by the factory.
     *
     * @param node    The node which should be transformed.
     * @param factory The factory to create new constraints or variation
     *                points.
     * @param ovModel The new {@link IOvModel} containing the already
     *                transformed constraints or variation points.
     * @return The new variation point or constraint.
     */
    private IOvModelElement nodeToConstraints(
            final Constraint node,
            final IOvModelFactory factory,
            final IOvModel ovModel
    ) throws NotSupportedTransformationException {
        IOvModelElement constraint = null;

        if (
                node instanceof AndConstraint || node instanceof OrConstraint
            //|| node instanceof AtLeast || node instanceof AtMost || node instanceof Choose
        ) {
            final List<IOvModelVariationBase> ovChildren = new ArrayList<>();
            final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(
                    ovModel,
                    DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX + this.constraintToUniqueString(node)
            );
            constraint = ovModelVariationPoint;
            boolean hasConstraint = false;

            for (final Constraint child : node.getConstraintSubParts()) {
                final IOvModelElement childElement = this.nodeToConstraints(child, factory, ovModel);
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

            this.setOvModelVariationBaseProperties(ovModelVariationPoint, node);

            OvModelUtils.setAlternative(ovModelVariationPoint, !(node instanceof AndConstraint));

            if (hasConstraint) {
                OvModelUtils.setOptional(ovModelVariationPoint, true);
            }

            if (node instanceof AndConstraint) {
                OvModelUtils.setMandatoryChildren(ovModelVariationPoint, ovChildren);
            } else if (node instanceof OrConstraint) {
                OvModelUtils.setOptionalChildren(ovModelVariationPoint, ovChildren);
            } else {
                OvModelUtils.setOptionalChildren(ovModelVariationPoint, ovChildren);
            }

            if (node instanceof AndConstraint) {
                OvModelUtils.setMinChoices(ovModelVariationPoint, ovChildren.size());
                OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
            } else if (node instanceof OrConstraint) {
                // 22.06. OvModelUtils.setMinChoices(ovModelVariationPoint, 0);
                OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
                OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
            }

            // TODO
            //            else if (node instanceof AtLeast) {
            //                final AtLeast atLeast = (AtLeast) node;
            //                OvModelUtils.setMinChoices(ovModelVariationPoint, atLeast.min);
            //                OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
            //            } else if (node instanceof AtMost) {
            //                final AtMost atMost = (AtMost) node;
            //                OvModelUtils.setMinChoices(ovModelVariationPoint, 0);
            //                OvModelUtils.setMaxChoices(ovModelVariationPoint, atMost.max);
            //            } else if (node instanceof Choose) {
            //                final Choose choose = (Choose) node;
            //                OvModelUtils.setMinChoices(ovModelVariationPoint, choose.n);
            //                OvModelUtils.setMaxChoices(ovModelVariationPoint, choose.n);
            //            }
        } else if (node instanceof LiteralConstraint) {
            final LiteralConstraint literal = (LiteralConstraint) node;

            final Feature feature = literal.getFeature();
            if (feature == null) {
                throw new NotSupportedTransformationException(node.getClass(), literal.getClass());
            }

            final boolean isPartOfOvModelRoot = this.findFeatureInOvModel(feature, factory, ovModel) != null;
            final IOvModelVariationBase ovModelVariationBase = this.featureToOvModelElement(feature, factory, ovModel);
            constraint = ovModelVariationBase;

            OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, isPartOfOvModelRoot);

        } else if (node instanceof NotConstraint) {
            final String uniqueString = this.constraintToUniqueString(node);
            final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(
                    ovModel,
                    DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX + uniqueString
            );
            constraint = ovModelVariationPoint;
            this.setOvModelVariationBaseProperties(ovModelVariationPoint, node);
            OvModelUtils.setAlternative(ovModelVariationPoint, false);
            OvModelUtils.setMaxChoices(ovModelVariationPoint, 1);
            OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
            OvModelUtils.setOptional(ovModelVariationPoint, true);

            final IOvModelVariant ovModelVariant = factory.createVariant(
                    ovModel,
                    DefaultOvModelTransformationProperties.VARIANT_PREFIX + DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX + uniqueString
            );
            this.setOvModelVariationBaseProperties(ovModelVariant, node);
            ovModelVariationPoint.addMandatoryChild(ovModelVariant);

            final IOvModelConstraint ovModelExcludesConstraint = factory.createExcludesConstraint(ovModel);
            OvModelUtils.setName(ovModelExcludesConstraint, "");
            OvModelUtils.setDescription(ovModelExcludesConstraint, "This excludes constraint arises from the constraint " + node + ".");
            this.setOvModelConstraintProperties(
                    node,
                    ovModelExcludesConstraint,
                    ovModelVariationPoint,
                    (IOvModelVariationBase) this.nodeToConstraints(((NotConstraint) node).getContent(), factory, ovModel));
            ovModel.addConstraint(ovModelExcludesConstraint);

        } else if (node instanceof ImplicationConstraint) {
            final IOvModelConstraint ovModelRequiresConstraint;

            ovModelRequiresConstraint = factory.createRequiresConstraint(ovModel);
            OvModelUtils.setDescription(ovModelRequiresConstraint, "This requires constraint arises from the constraint " + node + ".");
            this.setOvModelConstraintProperties(
                    node,
                    ovModelRequiresConstraint,
                    (IOvModelVariationBase) this.nodeToConstraints(((ImplicationConstraint) node).getLeft(), factory, ovModel),
                    (IOvModelVariationBase) this.nodeToConstraints(((ImplicationConstraint) node).getRight(), factory, ovModel)
            );

            constraint = ovModelRequiresConstraint;
        } else {
            System.err.printf(
                    "Can not transform constraint: %s%n",
                    TraVarTUtils.buildFormulaFromConstraint(node, new FormulaFactory()).cnf()
            );
        }

        return constraint;
    }

    /**
     * This method sets the name, source and the target of a constraint.
     *
     * @param constraint        Constraint from which the name should be generated.
     * @param ovModelConstraint The constraint to which the source and the target
     *                          should be added.
     * @param source            The source.
     * @param target            The target.
     */
    private void setOvModelConstraintProperties(final Constraint constraint,
                                                final IOvModelConstraint ovModelConstraint,
                                                final IOvModelVariationBase source,
                                                final IOvModelVariationBase target
    ) {
        OvModelUtils.setName(
                ovModelConstraint,
                DefaultOvModelTransformationProperties.CONSTRAINT_PREFIX + this.constraintToUniqueString(constraint)
        );
        OvModelUtils.setSource(ovModelConstraint, source);
        OvModelUtils.setTarget(ovModelConstraint, target);
        if (OvModelUtils.getName(source).startsWith(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
            OvModelUtils.setOptional(source, Boolean.TRUE);
        }
        if (OvModelUtils.getName(target).startsWith(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
            OvModelUtils.setOptional(target, Boolean.TRUE);
        }
    }

    /**
     * This method overtakes the properties of an {@link Feature} to an
     * {@link IOvModelVariationBase}. It is overtaken whether the variation base is
     * optional, hidden, abstract or part of the root feature tree. Furthermore, the
     * description and the custom properties are overtaken.
     *
     * @param ovModelVariationBase The target model.
     * @param feature              The feature from which the properties should be
     *                             taken from.
     */
    private void setOvModelVariationBaseProperties(final IOvModelVariationBase ovModelVariationBase,
                                                   final Feature feature) {
        OvModelUtils.setOptional(ovModelVariationBase, !TraVarTUtils.checkGroupType(feature, Group.GroupType.MANDATORY));
        OvModelUtils.setHidden(ovModelVariationBase, TraVarTUtils.isHidden(feature));
        OvModelUtils.setAbstract(ovModelVariationBase, TraVarTUtils.isAbstract(feature));
        OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, Boolean.TRUE);
    }

    /**
     * This method sets the properties of an {@link IOvModelVariationBase} when it
     * was created because of a constraint. It is sets whether the variation base is
     * optional, hidden, abstract or part of the root feature tree. Furthermore, a
     * description is created.
     *
     * @param ovModelVariationBase The target model.
     * @param constraint           The constraint from which the properties should be
     *                             taken from.
     */
    private void setOvModelVariationBaseProperties(final IOvModelVariationBase ovModelVariationBase, final Constraint constraint) {
        OvModelUtils.setOptional(ovModelVariationBase, Boolean.FALSE);
        OvModelUtils.setHidden(ovModelVariationBase, Boolean.FALSE);
        OvModelUtils.setAbstract(ovModelVariationBase, Boolean.FALSE);
        OvModelUtils.setDescription(ovModelVariationBase, "This variant point arises from the constraint " + constraint.toString() + ".");
        OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, Boolean.FALSE);
    }

    private String constraintToUniqueString(final Constraint constraint) {
        this.sequence++;
        return constraint.getClass().getSimpleName().toUpperCase() +
                "_CONSTRAINT_"
                + constraint.getConstraintSubParts().toString().toUpperCase()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "_")
                .replace(".", "")
                .replace(" ", "")
                + "_" + this.sequence;
    }
}
