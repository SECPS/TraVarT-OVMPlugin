package at.jku.cps.travart.plugin.ovm.ovm.common;

import at.jku.cps.travart.core.common.IConfigurable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariant;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.DefaultOvModelTransformationProperties;
import de.vill.model.constraint.Constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class provides utility functions to modify an {@link IOvModel}.
 *
 * @author johannstoebich
 */
public class OvModelUtils {

    /**
     * This method returns all variation points of an {@link IOvModel}.
     *
     * @param ovModel the {@link IOvModel} for which the variation points will be
     *                returned.
     * @return the list of variation points.
     */
    public static List<IOvModelVariationPoint> getVariationPoints(final IOvModel ovModel) {
        return ovModel.getVariationPoints();
    }

    /**
     * This method returns all virtual variation points of an {@link IOvModel}.
     *
     * @param ovModel the {@link IOvModel} for which the virtual variation points
     *                will be returned.
     * @return the list of virtual variation points.
     */
    public static List<IOvModelVariationPoint> getVirtualVariationPoints(final IOvModel ovModel) {
        return ovModel.getVariationPoints().stream()
                .filter(
                        vp -> vp.getName().startsWith(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)
                )
                .collect(Collectors.toList());
    }

    /**
     * This method adds a variation point to an {@link IOvModel}.
     *
     * @param ovModel               the {@link IOvModel} to which the variation
     *                              point will be added.
     * @param ovModelVariationPoint the variation point which is added.
     */
    public static void addVariationPoint(final IOvModel ovModel, final IOvModelVariationPoint ovModelVariationPoint) {
        ovModel.addVariationPoint(ovModelVariationPoint);
    }

    /**
     * This method returns all constraints of an {@link IOvModel}.
     *
     * @param ovModel the OvModel for which the constraints will be returned.
     * @return the list of of constraints.
     */
    public static List<IOvModelConstraint> getConstraints(final IOvModel ovModel) {
        return ovModel.getConstraints();
    }

    /**
     * This method adds a constraint to an {@link IOvModel}. A constraint represents
     * a restriction of the model.
     *
     * @param ovModel           the {@link IOvModel} to which the constraints will
     *                          be added.
     * @param ovModelConstraint the constraint which is added.
     */
    public static void addConstraint(final IOvModel ovModel, final IOvModelConstraint ovModelConstraint) {
        ovModel.addConstraint(ovModelConstraint);
    }

    /**
     * This method returns the name of an {@link IIdentifiable}. The name must be
     * unique within an {@link IOvModel}.
     *
     * @param identifiable the {@link IIdentifiable} for which the name will be
     *                     returned.
     * @return the name of the identifiable.
     */
    public static String getName(final IIdentifiable identifiable) {
        return identifiable.getName();
    }

    /**
     * This method sets the name of an {@link IIdentifiable}. The name must be
     * unique within an {@link IOvModel}.
     *
     * @param identifiable the {@link IIdentifiable} for which the name will be set.
     * @param name         the name.
     */
    public static void setName(final IIdentifiable identifiable, final String name) {
        if (name != null && !"".contentEquals(name)) {
            identifiable.setName(name);
        }
    }

    /**
     * This method returns the description of a constraint.
     *
     * @param ovModelConstraint the constraint for which the description will be
     *                          returned.
     * @return The description of the constraint
     */
    public static String getDescription(final IOvModelConstraint ovModelConstraint) {
        return ovModelConstraint.getMetainformation().getDescription();
    }

    /**
     * This method sets the description of a constraint.
     *
     * @param ovModelConstraint the constraint for which the description will be
     *                          set.
     * @param description       the description which will be set.
     */
    public static void setDescription(final IOvModelConstraint ovModelConstraint, final String description) {
        ovModelConstraint.getMetainformation().setDescription(description);
    }

    /**
     * This method returns the description of a variation base (variation point or
     * variant).
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             for which the description will be returned.
     * @return The description of the variaton base.
     */
    public static String getDescription(final IOvModelVariationBase ovModelVariationBase) {
        return ovModelVariationBase.getMetainformation().getDescription();
    }

    /**
     * This method sets the description of a variation base (variation point or
     * variant).
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             for which the description will be set.
     * @param description          the description which will be set.
     */
    public static void setDescription(final IOvModelVariationBase ovModelVariationBase, final String description) {
        ovModelVariationBase.getMetainformation().setDescription(description);
    }

    /**
     * This method returns the referenced constraints. If a variation point came
     * from a feature model {@link Constraint} and its child have been transformed
     * to an {@link IOvModel} constraint, this constraint is referenced here. This
     * is required for reconstructing the feature model from the corresponding
     * OvModel correctly.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             for which the constraints will be returned.
     * @return the constraints which are referenced.
     * @see OvModelUtils#isPartOfOvModelRoot(IOvModelVariationBase) to determine if
     * a variation point came from a feature model {@link Constraint}.
     */
    public static List<IOvModelConstraint> getReferencedConstraints(final IOvModelVariationBase ovModelVariationBase) {
        if (ovModelVariationBase.getMetainformation().getReferencedConstraints() == null) {
            ovModelVariationBase.getMetainformation().setReferencedConstraints(new ArrayList<IOvModelConstraint>());
        }
        return ovModelVariationBase.getMetainformation().getReferencedConstraints();
    }

    /**
     * This method adds a referenced constraint to a variation base (variation point
     * or variant). If a variation point came from a feature model
     * {@link Constraint} and its child have been transformed to an
     * {@link IOvModel} constraint, this constraint is referenced here. This is
     * required for reconstructing the feature model from the corresponding OvModel
     * correctly.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the constraint will be added.
     * @param ovModelConstraint    the constraint which will be added.
     * @see OvModelUtils#isPartOfOvModelRoot(IOvModelVariationBase) to determine if
     * a variation point came from a feature model {@link Constraint}.
     */
    public static void addReferencedConstraint(final IOvModelVariationBase ovModelVariationBase,
                                               final IOvModelConstraint ovModelConstraint) {
        if (ovModelVariationBase.getMetainformation().getReferencedConstraints() == null) {
            ovModelVariationBase.getMetainformation().setReferencedConstraints(new ArrayList<>());
        }
        ovModelVariationBase.getMetainformation().getReferencedConstraints().add(ovModelConstraint);
    }

    /**
     * This method returns the property partOfModelRoot. This property determines if
     * a variation base (variation point or variant) came from a feature or a
     * feature model constraint.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where it will be returned.
     * @return the property partOfModelRoot.
     */
    public static boolean isPartOfOvModelRoot(final IOvModelVariationBase ovModelVariationBase) {
        return ovModelVariationBase.getMetainformation().isPartOfOvModelRoot();
    }

    /**
     * This method sets the property partOfModel of an
     * {@link IOvModelVariationBase}. This property determines if a variation base
     * (variation point or variant) came from a feature or a feature model
     * constraint.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will be set.
     * @param partOfOvModelRoot    the value of the parameter.
     */
    public static void setPartOfOvModelRoot(final IOvModelVariationBase ovModelVariationBase,
                                            final boolean partOfOvModelRoot) {
        ovModelVariationBase.getMetainformation().setPartOfOvModelRoot(partOfOvModelRoot);
    }

    /**
     * This method returns the property abstract.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will returned.
     * @return the property abstract.
     */
    public static boolean isAbstract(final IOvModelVariationBase ovModelVariationBase) {
        return ovModelVariationBase.getMetainformation().isAbstract();
    }

    /**
     * This method sets the property abstract of an {@link IOvModelVariationBase}.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will be set.
     * @param isAbstract           the value which will be set.
     */
    public static void setAbstract(final IOvModelVariationBase ovModelVariationBase, final boolean isAbstract) {
        ovModelVariationBase.getMetainformation().setAbstract(isAbstract);
    }

    /**
     * This method returns the property optional.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will returned.
     * @return the property optional.
     */
    public static boolean isOptional(final IOvModelVariationBase ovModelVariationBase) {
        return ovModelVariationBase.isOptional();
    }

    /**
     * This method sets the property optional of an {@link IOvModelVariationBase}.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will be set.
     * @param optional             the value which will be set.
     */
    public static void setOptional(final IOvModelVariationBase ovModelVariationBase, final boolean optional) {
        ovModelVariationBase.setOptional(optional);
    }

    /**
     * This method returns the property hidden.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will returned.
     * @return the property hidden.
     */
    public static boolean isHidden(final IOvModelVariationBase ovModelVariationBase) {
        return ovModelVariationBase.getMetainformation().isHidden();
    }

    /**
     * This method sets the property hidden of an {@link IOvModelVariationBase}.
     *
     * @param ovModelVariationBase the variation base (variation point or variant)
     *                             where the property will be set.
     * @param hidden               the value which will be set.
     */
    public static void setHidden(final IOvModelVariationBase ovModelVariationBase, final boolean hidden) {
        ovModelVariationBase.getMetainformation().setHidden(hidden);
    }

    /**
     * This method returns <code>true</code>, if there are mandatory children
     * defined for this variation point. It is defined by <code>lenght > 0</code>.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return <code>true</code> if there are mandatory children.
     */
    public static boolean hasMandatoryChildren(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.hasMandatoryChildren();
    }

    /**
     * This method returns the amount of mandatory children defined for this
     * variation point.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the amount of mandatory children.
     */
    public static int getMandatoryChildrenCount(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.getMandatoryChildrenCount();
    }

    /**
     * This method returns all mandatory children defined for a variation point.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the list of mandatory children.
     */
    public static List<IOvModelVariationBase> getMandatoryChildren(final IOvModelVariationPoint ovModelVariationPoint) {
        if (ovModelVariationPoint.getMandatoryChildren() == null) {
            ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>());
        }
        return ovModelVariationPoint.getMandatoryChildren();
    }

    /**
     * This method overwrites all mandatory children of a variation point. This
     * method sets a clone of the list.
     *
     * @param ovModelVariationPoint the variation point for which the mandatory
     *                              children will be set.
     * @param ovChildren            the new list of mandatory children.
     */
    public static void setMandatoryChildren(final IOvModelVariationPoint ovModelVariationPoint,
                                            final List<IOvModelVariationBase> ovChildren) {
        ovModelVariationPoint.setMandatoryChildren(new ArrayList<>(ovChildren));
    }

    /**
     * This method adds a mandatory child to the list of mandatory children.
     *
     * @param ovModelVariationPoint the variation point to which the child will be
     *                              added.
     * @param ovChild               the child which should be added.
     */
    public static void addMandatoryChild(final IOvModelVariationPoint ovModelVariationPoint,
                                         final IOvModelVariationBase ovChild) {
        if (ovModelVariationPoint.getMandatoryChildren() == null) {
            ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>());
        }
        ovModelVariationPoint.addMandatoryChild(ovChild);
    }

    /**
     * This method returns <code>true</code>, if there are optional children defined
     * for this variation point. It is defined by <code>lenght > 0</code>.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return <code>true</code> if there are optional children.
     */
    public static boolean hasOptionalChildren(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.hasOptionalChildren();
    }

    /**
     * This method returns the amount of optional children defined for a variation
     * point.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the amount of optional children.
     */
    public static int getOptionalChildrenCount(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.getOptionalChildrenCount();
    }

    /**
     * This method returns all optional children defined for a variation point.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the list of optional children.
     */
    public static List<IOvModelVariationBase> getOptionalChildren(final IOvModelVariationPoint ovModelVariationPoint) {
        if (ovModelVariationPoint.getOptionalChildren() == null) {
            ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>());
        }
        return ovModelVariationPoint.getOptionalChildren();
    }

    /**
     * This method overwrites all optional children of a variation point. This
     * method sets a clone of the list.
     *
     * @param ovModelVariationPoint the variation point for which the optional
     *                              children will be set.
     * @param ovChildren            the new list of optional children.
     */
    public static void setOptionalChildren(final IOvModelVariationPoint ovModelVariationPoint,
                                           final List<IOvModelVariationBase> ovChildren) {
        ovModelVariationPoint.setOptionalChildren(new ArrayList<>(ovChildren));
    }

    /**
     * This method adds an optional child to the list of optional children.
     *
     * @param ovModelVariationPoint the variation point to which the child will be
     *                              added.
     * @param ovChild               the child which should be added.
     */
    public static void addOptionalChild(final IOvModelVariationPoint ovModelVariationPoint,
                                        final IOvModelVariationBase ovChild) {
        if (ovModelVariationPoint.getOptionalChildren() == null) {
            ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>());
        }
        ovModelVariationPoint.addOptionalChild(ovChild);
    }

    /**
     * This method returns the property alternative.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the property alternative.
     */
    public static boolean isAlternative(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.isAlternative();
    }

    /**
     * This method sets the property alternative of an
     * {@link IOvModelVariationPoint}.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              set.
     * @param alternative           the value which will be set.
     */
    public static void setAlternative(final IOvModelVariationPoint ovModelVariationPoint, final boolean alternative) {
        ovModelVariationPoint.setAlternative(alternative);
    }

    /**
     * This method returns <code>true</code>, if there is a minimum amount of
     * choices defined for a variation point. It is defined if
     * <code>minChoices != -1
     * </code>.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return <code>true</code> if the property min choices is defined.
     */
    public static boolean hasMinChoices(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.hasMinChoices();
    }

    /**
     * This method returns the minimum amount of children which have to be chosen at
     * a variation point.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the minimum amount of choices.
     */
    public static int getMinChoices(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.getMinChoices();
    }

    /**
     * This method sets the property min choices of an
     * {@link IOvModelVariationPoint}. It determines the minimum children which have
     * to be chosen.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              set.
     * @param min                   the value which will be set.
     */
    public static void setMinChoices(final IOvModelVariationPoint ovModelVariationPoint, final int min) {
        ovModelVariationPoint.setMinChoices(min);
    }

    /**
     * This method returns <code>true</code>, if there is a maximal amount of
     * choices defined for a variation point. It is defined if
     * <code>maxChoices != -1
     * </code>.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return <code>true</code> if the property max choices is defined.
     */
    public static boolean hasMaxChoices(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.hasMaxChoices();
    }

    /**
     * This method returns the maximum amount of children which can be chosen at a
     * variation point.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              returned.
     * @return the maximum amount of choices.
     */
    public static int getMaxChoices(final IOvModelVariationPoint ovModelVariationPoint) {
        return ovModelVariationPoint.getMaxChoices();
    }

    /**
     * This method sets the property max choices of an
     * {@link IOvModelVariationPoint}. It determines the maximum amount of children
     * which can be chosen.
     *
     * @param ovModelVariationPoint the variation point where the property will be
     *                              set.
     * @param max                   the value which will be set.
     */
    public static void setMaxChoices(final IOvModelVariationPoint ovModelVariationPoint, final int max) {
        ovModelVariationPoint.setMaxChoices(max);
    }

    /**
     * This method returns the well defined source of a constraint as a variation
     * base (variation point or variant).
     *
     * @param ovModelConstraint the constraint where the property will be returned.
     * @return the source of the variation base (variation point or variant).
     */
    public static IOvModelVariationBase getSource(final IOvModelConstraint ovModelConstraint) {
        return ovModelConstraint.getSource();
    }

    /**
     * This method sets the source of an OvModel constraint. An OvModel constrain
     * has always a well defined source.
     *
     * @param ovModelConstraint the constraint where the property will be set.
     * @param source            the source as variation base (variation point or
     *                          variant) of the constraint.
     */
    public static void setSource(final IOvModelConstraint ovModelConstraint, final IOvModelVariationBase source) {
        ovModelConstraint.setSource(source);
    }

    /**
     * This method returns the well defined target of a constraint as variation base
     * (variation point or variant).
     *
     * @param ovModelConstraint the constraint where the property will be returned.
     * @return the target of the constraint as variation base (variation point or
     * variant).
     */
    public static IOvModelVariationBase getTarget(final IOvModelConstraint ovModelConstraint) {
        return ovModelConstraint.getTarget();
    }

    /**
     * This method sets the target of an OvModel constraint. An OvModel constrain
     * has always a well defined target.
     *
     * @param ovModelConstraint the constraint where the property will be set.
     * @param target            the target variation base (variation point or
     *                          variant) of the constraint.
     */
    public static void setTarget(final IOvModelConstraint ovModelConstraint, final IOvModelVariationBase target) {
        ovModelConstraint.setTarget(target);
    }

    public static Map<IConfigurable, Boolean> getIConfigurable(final IOvModel ovModel) {
        final Map<IConfigurable, Boolean> configurables = new HashMap<>();
        fillListConfigurable(ovModel.getVariationPoints(), configurables);
        for (final IOvModelConstraint constraint : ovModel.getConstraints()) {
            fillListConfigurable(Arrays.asList(constraint.getSource()), configurables);
            fillListConfigurable(Arrays.asList(constraint.getTarget()), configurables);
        }
        return configurables;
    }

    private static void fillListConfigurable(final List<? extends IOvModelVariationBase> ovModelElements,
                                             final Map<IConfigurable, Boolean> configurables) {
        for (final IOvModelVariationBase ovModelVariationBase : ovModelElements) {
            if (!configurables.containsKey(ovModelVariationBase)) {
                configurables.put(ovModelVariationBase, ovModelVariationBase.isSelected());
            }

            if (ovModelVariationBase instanceof IOvModelVariationPoint) {
                fillListConfigurable(((IOvModelVariationPoint) ovModelVariationBase).getMandatoryChildren(),
                        configurables);
                fillListConfigurable(((IOvModelVariationPoint) ovModelVariationBase).getOptionalChildren(),
                        configurables);
            } else if (ovModelVariationBase instanceof IOvModelVariant) {
                return;
            } else {
                throw new RuntimeException();
            }
        }
    }

    /**
     * This method prints statistic information from the given OvModel to the
     * System.output stream.
     *
     * @param logger  the logger to write to.
     * @param ovModel the model to print the statistics from.
     */
    public static void logModelStatistics(final Logger logger, final IOvModel ovModel) {
        logger.log(Level.INFO, String.format("#Variation Points: %s", countVariationPoints(ovModel)));
        logger.log(Level.INFO, String.format("#Variants: %s", countVariants(ovModel)));
        logger.log(Level.INFO, String.format("#Constraints: %s", ovModel.getConstraintCount()));
    }

    public static long getNumberOfVariationPoints(final IOvModel ovModel) {
        return countVariationPoints(ovModel);
    }

    public static long getNumberOfVariants(final IOvModel ovModel) {
        return countVariants(ovModel);
    }

    public static long getNumberOfConstraints(final IOvModel ovModel) {
        return ovModel.getConstraintCount();
    }

    private static long countVariants(final IOvModel ovModel) {
        return countVariantsRec(ovModel.getVariationPoints());
    }

    private static long countVariantsRec(final List<? extends IOvModelVariationBase> ovModelElements) {
        long counter = 0;
        for (final IOvModelVariationBase ovModelVariationBase : ovModelElements) {
            if (ovModelVariationBase instanceof IOvModelVariationPoint) {
                final IOvModelVariationPoint vp = (IOvModelVariationPoint) ovModelVariationBase;
                counter += countVariantsRec(vp.getMandatoryChildren());
                counter += countVariantsRec(vp.getOptionalChildren());
            }
            if (ovModelVariationBase instanceof IOvModelVariant) {
                counter++;
            }
        }
        return counter;
    }

    private static long countVariationPoints(final IOvModel ovModel) {
        return countVariationPointsRec(ovModel.getVariationPoints());
    }

    private static long countVariationPointsRec(final List<? extends IOvModelVariationBase> ovModelElements) {
        long counter = 0;
        for (final IOvModelVariationBase ovModelVariationBase : ovModelElements) {
            if (ovModelVariationBase instanceof IOvModelVariationPoint) {
                counter++;
                final IOvModelVariationPoint vp = (IOvModelVariationPoint) ovModelVariationBase;
                counter += countVariationPointsRec(vp.getMandatoryChildren());
                counter += countVariationPointsRec(vp.getOptionalChildren());
            }
        }
        return counter;
    }
}
