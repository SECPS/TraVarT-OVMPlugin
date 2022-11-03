import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFactory;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
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

import java.util.ArrayList;
import java.util.List;

import static DefaultModelTransformerProperties.CONSTRAINT_PREFIX;
import static DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX;
import static DefaultModelTransformerProperties.VARIANT_PREFIX;

/**
 * This transformer transforms an {@link IFeatureModel} to an {@link IOvModel}.
 *
 * @author johannstoebich
 */
public class DefaultModelTransformerFeatureModelToOvModel implements IModelTransformer<IFeatureModel, IOvModel> {

    private Integer sequence = 0;

    /**
     * This method transforms an {@link IFeatureModel} to an {@link IOvModel}. The method
     * {@link #featureToOvModelElement(IFeature, IFeatureModel, IOvModelFactory, IOvModel)} is used to transform all variation points and the method
     * {@link #nodeToConstraints(Node, IFeatureModel, IOvModelFactory, IOvModel)} is used to transform the constraints.
     *
     * @param featureModel the feature model which should be transformed.
     * @param factoryTo    the factory which is used to create new elements.
     * @returns a new ovModel
     */
    @Override
    public IOvModel transform(IFeatureModel featureModel, IFactory<IOvModel> factoryTo) throws NotSupportedTransformationException {
        IOvModelFactory factory = (IOvModelFactory) factoryTo;
        IOvModel ovModel = factory.create();
        OvModelUtils.setCustomPropertiesEntries(ovModel, featureModel.getProperty().getProperties());

        if (FeatureUtils.getRoot(featureModel).getName().contentEquals(DefaultModelTransformerProperties.ROOT_FEATURE_NAME)) {
            for (IFeatureStructure structure : FeatureUtils.getRoot(featureModel).getStructure().getChildren()) {
                IOvModelVariationPoint modelVariantPoint = (IOvModelVariationPoint) this.featureToOvModelElement(structure.getFeature(), factory, ovModel);
                OvModelUtils.addVariationPoint(ovModel, modelVariantPoint);
            }

        } else {
            IOvModelVariationPoint modelVariantPoint =
                    (IOvModelVariationPoint) this.featureToOvModelElement(FeatureUtils.getRoot(featureModel), factory, ovModel);
            OvModelUtils.addVariationPoint(ovModel, modelVariantPoint);
        }

        for (IConstraint constraint : FeatureUtils.getConstraints(featureModel)) {
            IOvModelElement ovModelElement = this.nodeToConstraints(FeatureUtils.getNode(constraint), featureModel, factory, ovModel);

            if (ovModelElement instanceof IOvModelConstraint) {
                IOvModelConstraint ovModelConstraint = (IOvModelConstraint) ovModelElement;
                OvModelUtils.setDescription(ovModelConstraint, constraint.getDescription());
                OvModelUtils.setName(ovModelConstraint, constraint.getName());

                OvModelUtils.addConstraint(ovModel, ovModelConstraint);
                OvModelUtils.setCustomPropertiesEntries(ovModelConstraint, constraint.getCustomProperties().getProperties());

            } else if (ovModelElement instanceof IOvModelVariationPoint) {
                IOvModelVariationPoint ovModelVariationBase = (IOvModelVariationPoint) ovModelElement;

                OvModelUtils.setDescription(ovModelVariationBase, constraint.getDescription());
                OvModelUtils.setName(ovModelVariationBase, constraint.getName());

                OvModelUtils.addVariationPoint(ovModel, ovModelVariationBase);
                OvModelUtils.setCustomPropertiesEntries(ovModelVariationBase, constraint.getCustomProperties().getProperties());
            }
        }

        return ovModel;
    }

    /**
     * This method transforms a feature to a variation point. It uses the method recursively to transform the children as well. The new elements will be created
     * by the factory.
     *
     * @param feature The feature which should be transformed.
     * @param factory The factory to create new variation points.
     * @param ovModel The new {@link IOvModel} containing the already transformed variation points and variants.
     * @return The new variation point or variant.
     */
    private IOvModelVariationBase featureToOvModelElement(IFeature feature, IOvModelFactory factory, IOvModel ovModel) {

        IOvModelVariationBase ovModelVariationBase = this.findFeatureInOvModel(feature, factory, ovModel);
        if (ovModelVariationBase != null) {
            return ovModelVariationBase;
        }

        if (!FeatureUtils.hasChildren(feature)) {
            IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(ovModel, FeatureUtils.getName(feature));
            ovModelVariationBase = ovModelVariationPoint;

            IOvModelVariant ovModelVariant = factory.createVariant(ovModel, VARIANT_PREFIX + FeatureUtils.getName(feature));
            OvModelUtils.setPartOfOvModelRoot(ovModelVariant, true);
            OvModelUtils.setMaxChoices(ovModelVariationPoint, -1);
            OvModelUtils.setMinChoices(ovModelVariationPoint, -1);
            OvModelUtils.addMandatoryChild(ovModelVariationPoint, ovModelVariant);

        } else if (FeatureUtils.hasChildren(feature)) {
            IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(ovModel, FeatureUtils.getName(feature));
            ovModelVariationBase = ovModelVariationPoint;

            for (IFeature child : FeatureUtils.getChildren(feature)) {
                IOvModelVariationBase ovModelChild = this.featureToOvModelElement(child, factory, ovModel);

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
                OvModelUtils.setAlternative(ovModelVariationPoint, true);
                OvModelUtils.setMinChoices(ovModelVariationPoint, 1);
                OvModelUtils.setMaxChoices(ovModelVariationPoint, OvModelUtils.getOptionalChildrenCount(ovModelVariationPoint));
            }
        }

        this.setOvModelVariationBaseProperties(ovModelVariationBase, feature);

        return ovModelVariationBase;
    }

    /**
     * This method searches for a variation point or variant in the given {@link IOvModel} by using the feature's name.
     *
     * @param feature The feature which should be found by the given name.
     * @param factory The factory used to create an identifiable for searching in the {@link IOvModel}.
     * @param ovModel The model which should be searched threw.
     * @return
     */
    private IOvModelVariationBase findFeatureInOvModel(IFeature feature, IOvModelFactory factory, IOvModel ovModel) {
        return (IOvModelVariationBase) ovModel.getElement(factory.createIdentifiable(0, feature.getName()));
    }

    /**
     * This method transforms a node to a constraint or variation point. It uses the method recursively to transform the children as well. The new elements will
     * be created by the factory.
     *
     * @param node         The node which should be transformed.
     * @param featureModel The feature model used to retrieve a specific feature.
     * @param factory      The factory to create new constraints or variation points.
     * @param ovModel      The new {@link IOvModel} containing the already transformed constraints or variation points.
     * @return The new variation point or constraint.
     */
    private IOvModelElement nodeToConstraints(Node node, IFeatureModel featureModel, IOvModelFactory factory, IOvModel ovModel)
            throws NotSupportedTransformationException {
        IOvModelElement constraint = null;

        if ((node instanceof And) || (node instanceof Or) || (node instanceof AtLeast) || (node instanceof AtMost) || (node instanceof Choose)) {
            List<IOvModelVariationBase> ovChildren = new ArrayList<IOvModelVariationBase>();

            IOvModelVariationPoint ovModelVariationPoint =
                    factory.createVariationPoint(ovModel, CONSTRAINT_VARIATION_POINT_PREFIX + this.nodeToUniqueString(node));
            constraint = ovModelVariationPoint;

            boolean hasConstraint = false;

            for (Node child : node.getChildren()) {
                IOvModelElement childElement = this.nodeToConstraints(child, featureModel, factory, ovModel);
                if (childElement instanceof IOvModelVariationBase) {
                    ovChildren.add((IOvModelVariationBase) childElement);
                } else if (childElement instanceof IOvModelConstraint) {
                    IOvModelConstraint ovModelConstraint = (IOvModelConstraint) childElement;
                    OvModelUtils.addConstraint(ovModel, ovModelConstraint);
                    OvModelUtils.addReferencedConstraint(ovModelVariationPoint, ovModelConstraint);
                    hasConstraint = true;
                    ovChildren.add(ovModelConstraint.getSource());
                } else {
                    throw new NotSupportedTransformationException(child.getClass(), childElement.getClass());
                }
            }

            this.setOvModelVariationBaseProperties(ovModelVariationPoint, node);

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
                AtLeast atLeast = (AtLeast) node;
                OvModelUtils.setMinChoices(ovModelVariationPoint, atLeast.min);
                OvModelUtils.setMaxChoices(ovModelVariationPoint, ovChildren.size());
            } else if (node instanceof AtMost) {
                AtMost atMost = (AtMost) node;
                OvModelUtils.setMinChoices(ovModelVariationPoint, 0);
                OvModelUtils.setMaxChoices(ovModelVariationPoint, atMost.max);
            } else if (node instanceof Choose) {
                Choose choose = (Choose) node;
                OvModelUtils.setMinChoices(ovModelVariationPoint, choose.n);
                OvModelUtils.setMaxChoices(ovModelVariationPoint, choose.n);
            }
        } else if (node instanceof Literal) {
            Literal literal = (Literal) node;

            IFeature feature;

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

            boolean isPartOfOvModelRoot = this.findFeatureInOvModel(feature, factory, ovModel) != null;
            IOvModelVariationBase ovModelVariationBase = this.featureToOvModelElement(feature, factory, ovModel);
            constraint = ovModelVariationBase;

            OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, isPartOfOvModelRoot);

        } else if (node instanceof Not) {
            IOvModelVariationPoint ovModelVariationPoint =
                    factory.createVariationPoint(ovModel, CONSTRAINT_VARIATION_POINT_PREFIX + this.nodeToUniqueString(node));
            constraint = ovModelVariationPoint;
            this.setOvModelVariationBaseProperties(ovModelVariationPoint, node);
            OvModelUtils.setAlternative(ovModelVariationPoint, false);
            OvModelUtils.setMaxChoices(ovModelVariationPoint, 1);
            OvModelUtils.setMinChoices(ovModelVariationPoint, 1);

            IOvModelVariant ovModelVariant =
                    factory.createVariant(ovModel, CONSTRAINT_VARIATION_POINT_PREFIX + VARIANT_PREFIX + this.nodeToUniqueString(node));
            this.setOvModelVariationBaseProperties(ovModelVariant, node);
            ovModelVariationPoint.addMandatoryChild(ovModelVariant);

            IOvModelConstraint ovModelExcludesConstraint = factory.createExcludesConstraint(ovModel);
            OvModelUtils.setName(ovModelExcludesConstraint, "");
            OvModelUtils.setDescription(ovModelExcludesConstraint, "This excludes constraint arises from the constraint " + node.toString() + ".");
            this.setOvModelConstraintProperties(node, ovModelExcludesConstraint, ovModelVariationPoint,
                    (IOvModelVariationBase) this.nodeToConstraints(node.getChildren()[0], featureModel, factory, ovModel));
            ovModel.addConstraint(ovModelExcludesConstraint);

        } else if (node instanceof Implies) {
            IOvModelConstraint ovModelRequiresConstraint;

            ovModelRequiresConstraint = factory.createRequiresConstraint(ovModel);
            OvModelUtils.setDescription(ovModelRequiresConstraint, "This requires constraint arises from the constraint " + node.toString() + ".");
            this.setOvModelConstraintProperties(node, ovModelRequiresConstraint,
                    (IOvModelVariationBase) this.nodeToConstraints(node.getChildren()[0], featureModel, factory, ovModel),
                    (IOvModelVariationBase) this.nodeToConstraints(node.getChildren()[1], featureModel, factory, ovModel));

            constraint = ovModelRequiresConstraint;

        } else {
            throw new NotSupportedTransformationException(node.getClass(), IOvModelElement.class);
        }
        return constraint;
    }

    /**
     * This method sets the name, source and the target of a constraint.
     *
     * @param node              Node from which the name should be generated.
     * @param ovModelConstraint The constraint to which the source and the target should be added.
     * @param source            The source.
     * @param target            The target.
     */
    private void setOvModelConstraintProperties(Node node, IOvModelConstraint ovModelConstraint, IOvModelVariationBase source,
                                                IOvModelVariationBase target) {
        OvModelUtils.setName(ovModelConstraint, CONSTRAINT_PREFIX + this.nodeToUniqueString(node));
        OvModelUtils.setSource(ovModelConstraint, source);
        OvModelUtils.setTarget(ovModelConstraint, target);
    }

    /**
     * This method overtakes the properties of an {@link IFeature} to an {@link IOvModelVariationBase}. It is overtaken whether the variation base is optional,
     * hidden, abstract or part of the root feature tree. Furthermore the description and the custom properties are overtaken.
     *
     * @param ovModelVariationBase The target model.
     * @param feature              The feature from which the properties should be taken from.
     */
    private void setOvModelVariationBaseProperties(IOvModelVariationBase ovModelVariationBase, IFeature feature) {
        OvModelUtils.setOptional(ovModelVariationBase, !FeatureUtils.isMandatory(feature));
        OvModelUtils.setHidden(ovModelVariationBase, FeatureUtils.isHidden(feature));

        OvModelUtils.setDescription(ovModelVariationBase, FeatureUtils.getDescription(feature));
        OvModelUtils.setAbstract(ovModelVariationBase, FeatureUtils.isAbstract(feature));
        OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, true);
        OvModelUtils.setCustomPropertiesEntries(ovModelVariationBase, feature.getCustomProperties().getProperties());
    }

    /**
     * This method sets the properties of an {@link IOvModelVariationBase} when it was created because of a constraint. It is sets whether the variation base is
     * optional, hidden, abstract or part of the root feature tree. Furthermore a description is created.
     *
     * @param ovModelVariationBase The target model.
     * @param node                 The node from which the properties should be taken from.
     */
    private void setOvModelVariationBaseProperties(IOvModelVariationBase ovModelVariationBase, Node node) {
        OvModelUtils.setOptional(ovModelVariationBase, false);
        OvModelUtils.setHidden(ovModelVariationBase, false);
        OvModelUtils.setAbstract(ovModelVariationBase, false);
        OvModelUtils.setDescription(ovModelVariationBase, "This variant point arises from the constraint " + node.toString() + ".");
        OvModelUtils.setPartOfOvModelRoot(ovModelVariationBase, false);
    }

    private String nodeToUniqueString(Node node) {
        this.sequence++;
        return node.getClass().getSimpleName().toUpperCase() + "_CONSTRAINT_"
                + node.getLiterals().toString().toUpperCase().replace("[", "").replace("]", "").replace(",", "_").replace(".", "").replace(" ", "") + "_"
                + this.sequence;
    }
}
