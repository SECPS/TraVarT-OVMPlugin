package de.ovgu.featureide.core.configuration;

/**
 * This represents a configurable item of an variablilty model. An item implementing this interface can be selected.
 *
 * @author johannstoebich
 */
public interface IConfigurable {

	/**
	 * Returns whether the feature is selected or not.
	 *
	 * @return
	 */
	boolean isSelected();

	/**
	 * Selects the feature.
	 *
	 * @param selected
	 */
	void setSelected(boolean selected);

	String getName();
}
