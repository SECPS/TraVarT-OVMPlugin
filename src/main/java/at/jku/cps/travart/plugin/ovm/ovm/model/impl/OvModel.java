package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.core.common.IConfigurable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelMetainformation;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariant;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelExcludesConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelRequiresConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.DefaultOvModelTransformationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Represents a concrete implementation of an {@link IOvModel}.
 *
 * @author johannstoebich
 * @see IOvModel
 */
public class OvModel extends Identifiable implements IOvModel {
    protected final String factoryId;
    protected final IOvModelMetainformation metainformation;
    protected final List<IOvModelVariationPoint> variationPoints = new ArrayList<>();
    protected final List<IOvModelConstraint> constraints = new ArrayList<>();
    protected String sourceFile;

    public OvModel(final String factoryId) {
        super();
        this.factoryId = factoryId;
        this.metainformation = new OvModelMetainformation();
    }

    public OvModel(final String factoryId, final long id) {
        super(id);
        this.factoryId = factoryId;
        this.metainformation = new OvModelMetainformation();
    }

    private static Map<IOvModelVariationBase, Boolean> getOptionalMap(final IOvModel ovModel) {
        final Map<IOvModelVariationBase, Boolean> optionalMap = new HashMap<>();
        fillListOptional(ovModel.getVariationPoints(), optionalMap);
        for (final IOvModelConstraint constraint : ovModel.getConstraints()) {
            fillListOptional(List.of(constraint.getSource()), optionalMap);
            fillListOptional(List.of(constraint.getTarget()), optionalMap);
        }
        return optionalMap;
    }

    private static void fillListOptional(final List<? extends IOvModelVariationBase> ovModelElements,
                                         final Map<IOvModelVariationBase, Boolean> optionalMap) {
        for (final IOvModelVariationBase ovModelVariationBase : ovModelElements) {
            if (ovModelVariationBase instanceof IOvModelVariationPoint) {
                ((IOvModelVariationPoint) ovModelVariationBase).getMandatoryChildren().forEach(mandatoryChild -> {
                    optionalMap.put(mandatoryChild, false);
                });
                ((IOvModelVariationPoint) ovModelVariationBase).getOptionalChildren().forEach(optionalChild -> {
                    optionalMap.put(optionalChild, true);
                });
                fillListOptional(((IOvModelVariationPoint) ovModelVariationBase).getMandatoryChildren(), optionalMap);
                fillListOptional(((IOvModelVariationPoint) ovModelVariationBase).getOptionalChildren(), optionalMap);
            }
        }
    }

    private static List<IOvModelVariationBase> getIConfigurable(final IOvModel ovModel) {
        final List<IOvModelVariationBase> configurables = new ArrayList<>();
        fillListConfigurable(ovModel.getVariationPoints(), configurables);
        for (final IOvModelConstraint constraint : ovModel.getConstraints()) {
            fillListConfigurable(List.of(constraint.getSource()), configurables);
            fillListConfigurable(List.of(constraint.getTarget()), configurables);
        }

        return configurables;
    }

    private static void fillListConfigurable(final List<? extends IOvModelVariationBase> ovModelElements,
                                             final List<IOvModelVariationBase> configurables) {
        for (final IOvModelVariationBase ovModelVariationBase : ovModelElements) {

            if (ovModelVariationBase instanceof IOvModelVariationPoint) {
                fillListConfigurable(((IOvModelVariationPoint) ovModelVariationBase).getMandatoryChildren(),
                        configurables);
                fillListConfigurable(((IOvModelVariationPoint) ovModelVariationBase).getOptionalChildren(),
                        configurables);
            }

            if (!configurables.contains(ovModelVariationBase)) {
                configurables.add(ovModelVariationBase);
            }
        }
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public boolean addConstraint(final IOvModelConstraint constraint) {
        return this.constraints.add(constraint);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void addConstraint(final IOvModelConstraint constraint, final int index) {
        this.constraints.add(index, constraint);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public boolean addVariationPoint(final IOvModelVariationPoint variationPoint) {
        return this.variationPoints.add(variationPoint);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void afterSelection() {
        final List<IOvModelVariationBase> configurables = getIConfigurable(this);
        final Map<IOvModelVariationBase, Boolean> optionalMap = getOptionalMap(this);
        final Map<IOvModelVariationBase, CanAssume> store = new HashMap<>();
        for (final IOvModelVariationBase vb : configurables) {
            if (!this.isVirtual(vb)) {
                store.put(vb, vb.isSelected() ? CanAssume.ASSUME_SELECTED_TRUE : CanAssume.ASSUME_SELECTED_FALSE);
                continue;
            }
            if (optionalMap.containsKey(vb) && optionalMap.get(vb) || !optionalMap.containsKey(vb) && vb.isOptional()) {
                store.put(vb, CanAssume.CANT_ASSUME);
                continue;
            }
            if (vb instanceof IOvModelVariant) {
                store.put(vb, optionalMap.get(vb) ? CanAssume.ASSUME_SELECTED_TRUE : CanAssume.CANT_ASSUME);
            }
            if (vb instanceof IOvModelVariationPoint) {
                final IOvModelVariationPoint vp = (IOvModelVariationPoint) vb;
                boolean canAssumeTrue = true;
                for (final IOvModelVariationBase mandatoryChild : vp.getMandatoryChildren()) {
                    final CanAssume stored = store.get(mandatoryChild);
                    // must be already part of the store as bottom-up approach
                    assert stored != null;
                    canAssumeTrue = canAssumeTrue && stored == CanAssume.ASSUME_SELECTED_TRUE;
                }
                store.put(vp, canAssumeTrue ? CanAssume.ASSUME_SELECTED_TRUE : CanAssume.CANT_ASSUME);
            }
        }
        for (final IOvModelConstraint constraint : this.getConstraints()) {
            if (constraint instanceof IOvModelExcludesConstraint) {
//					s: FALSE -> don't care
//					s: True -> false
                if (!this.isVirtual(constraint.getSource()) && this.isVirtual(constraint.getTarget())) {
                    final CanAssume canAssume = store.get(constraint.getTarget());
                    final CanAssume newAssume = constraint.getSource().isSelected() ? CanAssume.ASSUME_SELECTED_FALSE
                            : CanAssume.CANT_ASSUME;

                    if (canAssume == CanAssume.ASSUME_SELECTED_TRUE && newAssume == CanAssume.ASSUME_SELECTED_FALSE) {
                        return;
                    }
                    store.put(constraint.getTarget(), newAssume);
                } else if (this.isVirtual(constraint.getSource()) && !this.isVirtual(constraint.getTarget())) {
                    final CanAssume canAssume = store.get(constraint.getSource());
                    final CanAssume newAssume = constraint.getTarget().isSelected() ? CanAssume.ASSUME_SELECTED_FALSE
                            : CanAssume.CANT_ASSUME;

                    if (canAssume == CanAssume.ASSUME_SELECTED_TRUE && newAssume == CanAssume.ASSUME_SELECTED_FALSE) {
                        return;
                    }
                    store.put(constraint.getSource(), newAssume);
                }
            } else if (constraint instanceof IOvModelRequiresConstraint) {
//					s: TRUE -> True
//					s: FALSE -> don't care
                if (!this.isVirtual(constraint.getSource()) && this.isVirtual(constraint.getTarget())) {
                    final CanAssume canAssume = store.get(constraint.getTarget());
                    final CanAssume newAssume = constraint.getSource().isSelected() ? CanAssume.ASSUME_SELECTED_TRUE
                            : CanAssume.CANT_ASSUME;

                    if (canAssume == CanAssume.ASSUME_SELECTED_FALSE && newAssume == CanAssume.ASSUME_SELECTED_TRUE) {
                        return;
                    }
                    store.put(constraint.getTarget(), newAssume);
                } else if (this.isVirtual(constraint.getSource()) && !this.isVirtual(constraint.getTarget())) {
                    final CanAssume canAssume = store.get(constraint.getSource());
                    final CanAssume newAssume = constraint.getTarget().isSelected() ? CanAssume.ASSUME_SELECTED_TRUE
                            : CanAssume.CANT_ASSUME;

                    if (canAssume == CanAssume.ASSUME_SELECTED_FALSE && newAssume == CanAssume.ASSUME_SELECTED_TRUE) {
                        return;
                    }
                    store.put(constraint.getSource(), newAssume);
                }
            }
        }
        for (final IOvModelVariationPoint vp : this.getVariationPoints()) {
            this.isPossibleToSelect(vp, store);
        }
        for (final Entry<IOvModelVariationBase, CanAssume> entry : store.entrySet()) {
            if (entry.getValue() == CanAssume.ASSUME_SELECTED_TRUE) {
                entry.getKey().setSelected(true);
            }
            if (entry.getValue() == CanAssume.ASSUME_SELECTED_FALSE) {
                entry.getKey().setSelected(false);
            }
        }
//		// constraint setting depending on conditions and set vps
//		for (IOvModelConstraint constraint : getConstraints()) {
//			// if condition holds set the overall condition vp as selected
//			boolean isSelected = constraint.getSource().isValid(!constraint.getSource().isOptional());
//			constraint.getSource().setSelected(isSelected);
//		}
    }

    private void isPossibleToSelect(final IOvModelVariationBase vb, final Map<IOvModelVariationBase, CanAssume> store) {
        if (vb instanceof IOvModelVariationPoint) {
            final IOvModelVariationPoint vp = (IOvModelVariationPoint) vb;
            vp.getMandatoryChildren().forEach(child -> this.isPossibleToSelect(child, store));
            vp.getOptionalChildren().forEach(child -> this.isPossibleToSelect(child, store));
        }
        if (vb instanceof IOvModelVariationPoint && store.get(vb) == CanAssume.CANT_ASSUME) {
            final IOvModelVariationPoint vp = (IOvModelVariationPoint) vb;
            final List<IOvModelVariationBase> cantDecideMandatoryVbs = this.getVariationBaseElements(vp.getMandatoryChildren(),
                    store, CanAssume.CANT_ASSUME);
            final List<IOvModelVariationBase> selectedMandatoryVbs = this.getVariationBaseElements(vp.getMandatoryChildren(),
                    store, CanAssume.ASSUME_SELECTED_TRUE);
            final List<IOvModelVariationBase> selectedOptionalVbs = this.getVariationBaseElements(vp.getOptionalChildren(), store,
                    CanAssume.ASSUME_SELECTED_TRUE);
            final List<IOvModelVariationBase> cantDecideOptionalVbs = this.getVariationBaseElements(vp.getOptionalChildren(),
                    store, CanAssume.CANT_ASSUME);
            final long mandatoryCount = selectedMandatoryVbs.size() + cantDecideMandatoryVbs.size();
            if (vp.isAlternative()) {
                if (mandatoryCount + selectedOptionalVbs.size() > vp.getMaxChoices()) {
                    store.put(vp, CanAssume.ASSUME_SELECTED_FALSE);
                    return;
                }
                if (mandatoryCount + selectedOptionalVbs.size() + cantDecideOptionalVbs.size() < vp.getMinChoices()) {
                    store.put(vp, CanAssume.ASSUME_SELECTED_FALSE);
                    return;
                }
                if (mandatoryCount + selectedOptionalVbs.size() < vp.getMinChoices()) {
                    int decided = 0;
                    while (mandatoryCount + selectedOptionalVbs.size() + decided < vp.getMinChoices()) {
                        store.put(cantDecideOptionalVbs.get(decided), CanAssume.ASSUME_SELECTED_TRUE);
                        decided++;
                    }
                }
            } else { // potential optional check for vp?
                if (mandatoryCount + selectedOptionalVbs.size() + cantDecideOptionalVbs.size() == 0) {
                    store.put(vp, CanAssume.ASSUME_SELECTED_FALSE);
                    return;
                }
                if (mandatoryCount + selectedOptionalVbs.size() == 0) {
                    store.put(cantDecideOptionalVbs.get(0), CanAssume.ASSUME_SELECTED_TRUE);
                }
            }
        } else if (vb instanceof IOvModelVariationPoint && store.get(vb) == CanAssume.ASSUME_SELECTED_TRUE) {
            final IOvModelVariationPoint vp = (IOvModelVariationPoint) vb;
            final List<IOvModelVariationBase> cantDecideMandatoryVbs = this.getVariationBaseElements(vp.getMandatoryChildren(),
                    store, CanAssume.CANT_ASSUME);
            final List<IOvModelVariationBase> selectedMandatoryVbs = this.getVariationBaseElements(vp.getMandatoryChildren(),
                    store, CanAssume.ASSUME_SELECTED_TRUE);
            final List<IOvModelVariationBase> selectedOptionalVbs = this.getVariationBaseElements(vp.getOptionalChildren(), store,
                    CanAssume.ASSUME_SELECTED_TRUE);
            final List<IOvModelVariationBase> cantDecideOptionalVbs = this.getVariationBaseElements(vp.getOptionalChildren(),
                    store, CanAssume.CANT_ASSUME);
            final long mandatoryCount = selectedMandatoryVbs.size() + cantDecideMandatoryVbs.size();
            if (vp.isAlternative()) {
                if (mandatoryCount + selectedOptionalVbs.size() > vp.getMaxChoices()) {
                    return;
                }
                if (mandatoryCount + selectedOptionalVbs.size() + cantDecideOptionalVbs.size() < vp.getMinChoices()) {
                    return;
                }
                if (mandatoryCount + selectedOptionalVbs.size() < vp.getMinChoices()) {
                    int decided = 0;
                    while (mandatoryCount + selectedOptionalVbs.size() + decided < vp.getMinChoices()) {
                        store.put(cantDecideOptionalVbs.get(decided), CanAssume.ASSUME_SELECTED_TRUE);
                        decided++;
                    }
                }
            } else { // potential optional check for vp?
                if (mandatoryCount + selectedOptionalVbs.size() + cantDecideOptionalVbs.size() == 0) {
                    return;
                }
                if (mandatoryCount + selectedOptionalVbs.size() == 0) {
                    store.put(cantDecideOptionalVbs.get(0), CanAssume.ASSUME_SELECTED_TRUE);
                }
            }
        } else if (vb instanceof IOvModelVariationPoint && store.get(vb) == CanAssume.ASSUME_SELECTED_FALSE) {
            return;
        }
    }

    private List<IOvModelVariationBase> getVariationBaseElements(final List<? extends IOvModelVariationBase> vb,
                                                                 final Map<IOvModelVariationBase, CanAssume> store, final CanAssume enumValue) {
        return vb.stream().filter(child -> store.get(child) == enumValue).collect(Collectors.toList());
    }

    private boolean isVirtual(final IConfigurable configurable) {
        return configurable.getName()
                .contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX);
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final OvModel other = (OvModel) obj;
        if (this.constraints == null) {
            if (other.constraints != null) {
                return false;
            }
        } else if (!this.constraints.equals(other.constraints)) {
            return false;
        }
        if (this.metainformation == null) {
            if (other.metainformation != null) {
                return false;
            }
        } else if (!this.metainformation.equals(other.metainformation)) {
            return false;
        }
        if (this.variationPoints == null) {
            if (other.variationPoints != null) {
                return false;
            }
        } else if (!this.variationPoints.equals(other.variationPoints)) {
            return false;
        }
        return true;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public int getConstraintCount() {
        return this.constraints.size();
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public int getConstraintIndex(final IOvModelConstraint constraint) {
        return this.constraints.indexOf(constraint);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public List<IOvModelConstraint> getConstraints() {
        return Collections.unmodifiableList(this.constraints);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setConstraints(final Iterable<IOvModelConstraint> constraints) {
        this.constraints.clear();
        this.constraints.addAll(
                StreamSupport.stream(
                                constraints.spliterator(),
                                false
                        )
                        .collect(Collectors.toList())
        );
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public IOvModelElement getElement(final IIdentifiable identifiable) {
        IOvModelElement element;
        for (final IOvModelVariationPoint variationPoint : this.variationPoints) {
            element = variationPoint.getElement(identifiable);
            if (element != null) {
                return element;
            }
        }
        for (final IOvModelConstraint constraint : this.constraints) {
            element = constraint.getElement(identifiable);
            if (element != null) {
                return element;
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public String getFactoryId() {
        return this.factoryId;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public IOvModelMetainformation getMetainformation() {
        return this.metainformation;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public int getNumberOfVariationPoints() {
        return this.variationPoints.size();
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public String getSourceFile() {
        return this.sourceFile;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setSourceFile(final String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public List<IOvModelVariationPoint> getVariationPoints() {
        return Collections.unmodifiableList(this.variationPoints);
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.constraints == null ? 0 : this.constraints.hashCode());
        result = prime * result + (this.metainformation == null ? 0 : this.metainformation.hashCode());
        result = prime * result + (this.variationPoints == null ? 0 : this.variationPoints.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public boolean isValid() {
        boolean isValid = true;
        for (final IOvModelVariationPoint variationPoint : this.variationPoints) {
            isValid = isValid && variationPoint.isValid(!variationPoint.isOptional());
            if (!isValid) {
                return false;
            }
        }
        for (final IOvModelConstraint constraint : this.constraints) {
            isValid = isValid && constraint.isValid();
            if (!isValid) {
                return false;
            }
        }
        return isValid;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void removeConstraint(final int index) {
        this.constraints.remove(index);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public boolean removeConstraint(final IOvModelConstraint constraint) {
        return this.constraints.remove(constraint);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public boolean removeVariationPoint(final IOvModelVariationPoint variationPoint) {
        return this.variationPoints.remove(variationPoint);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void replaceConstraint(final IOvModelConstraint constraint, final int index) {
        if (constraint == null) {
            throw new NullPointerException();
        }
        this.constraints.remove(this.constraints.get(index));
        this.constraints.set(index, constraint);
    }

    private enum CanAssume {
        ASSUME_SELECTED_TRUE, ASSUME_SELECTED_FALSE, CANT_ASSUME;
    }
}
