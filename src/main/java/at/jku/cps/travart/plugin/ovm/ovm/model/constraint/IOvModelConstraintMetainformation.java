package at.jku.cps.travart.plugin.ovm.ovm.model.constraint;

/**
 * Manages all additional properties of an {@link IOvModelConstraint} and
 * therefore for an {@link IOvModelExcludesConstraint} and
 * {@link IOvModelRequiresConstraint}. The meta information stores all
 * information which is defined for a constraint in FeatureIDE however cannot be
 * represented by an {@link IOvModelConstraint}.
 */
public interface IOvModelConstraintMetainformation {

    /**
     * This method returns the description of the constraint.
     *
     * @return The description of the constraint
     */
    String getDescription();

    /**
     * This method sets the description of the constraint.
     *
     * @param description the description which will be set.
     */
    void setDescription(String description);

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);
}
