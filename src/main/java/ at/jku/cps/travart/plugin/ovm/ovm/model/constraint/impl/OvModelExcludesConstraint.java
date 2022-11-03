/**
 * Represents a concrete implementation of an {@link IOvModelExcludesConstraint}.
 *
 * @author johannstoebich
 * @see IOvModelExcludesConstraint
 */
public class OvModelExcludesConstraint extends OvModelConstraint implements IOvModelExcludesConstraint {

    /**
     * (non-Javadoc)
     *
     * @see IValidate#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isValid = super.isValid();
        isValid = isValid && (!this.source.isSelected() || !this.target.isSelected());
        return isValid;
    }

}
