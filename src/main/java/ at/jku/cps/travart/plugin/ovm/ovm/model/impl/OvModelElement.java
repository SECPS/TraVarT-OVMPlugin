import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;

import java.util.Objects;

/**
 * Represents a concrete implementation of an {@link IOvModelElement}.
 *
 * @author johannstoebich
 * @see IOvModelElement
 */
public abstract class OvModelElement extends Identifiable implements IOvModelElement {

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelElement#getElement(de.ovgu.featureide.core.ovm.model.IIdentifiable)
     */
    @Override
    public IOvModelElement getElement(IIdentifiable identifiable) {
        if (identifiable == null) {
            return null;
        } else if (identifiable == this) {
            return this;
        } else if (Objects.equals(identifiable.getName(), this.getName())) {
            return this;
        }
        return null;
    }

}
