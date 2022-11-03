/**
 * Represents a concrete implementation of an {@link IOvModelRequiresConstraint}.
 *
 * @author johannstoebich
 * @see IOvModelRequiresConstraint
 */
public class OvModelRequiresConstraint extends OvModelConstraint implements IOvModelRequiresConstraint {

    /**
     * (non-Javadoc)
     *
     * @see IValidate#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isValid = super.isValid();
        isValid = isValid && (!this.source.isSelected() || this.target.isSelected());
        return isValid;
    }
}
