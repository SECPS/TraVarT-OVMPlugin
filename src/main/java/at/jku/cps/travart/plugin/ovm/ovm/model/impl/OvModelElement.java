package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement;

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
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement#getElement(at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable)
     */
    @Override
    public IOvModelElement getElement(final IIdentifiable identifiable) {
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
