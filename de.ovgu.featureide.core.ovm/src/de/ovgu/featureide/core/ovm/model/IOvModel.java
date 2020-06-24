package de.ovgu.featureide.core.ovm.model;

import java.util.List;

import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModelProperty;

public interface IOvModel extends IIdentifyable, Cloneable {

	String getFactoryId();

	String getSourceFile();

	void setSourceFile(String sourceFile);

	/**
	 * Returns the number of features stored in this feature model. This call must be constistent with {@link IOvModel#getFeatureTable()} size.
	 *
	 * @see #addFeature(IFeature)
	 * @see #deleteFeature(IFeature)
	 * @see #getFeature(CharSequence)
	 * @see #getFeatures()
	 * @see #reset()
	 *
	 * @since 3.0
	 *
	 * @return number of feature stored in this model
	 */
	int getNumberOfVariationPoints();

	/**
	 * Add a new feature <code>feature</code> to this feature model. If the feature model not contains a feature with the name {@link IFeature#getName()} of
	 * <code>feature</code>, the <code>feature</code> will be added and the method returns <b>true</b>. Otherwise, the feature is not added and the method
	 * returns <b>false</b>. Classes implementing <code>IFeatureModel</code> must provide consistency with the underlying <i>feature table</i> which is
	 * accessible by {@link #getFeatureTable()}.
	 *
	 * @param feature the feature to be added. <code>feature</code> is assumed to be <i>non-null</i>
	 * @return <b>true</b> if the feature was added, otherwise <b>false</b>.
	 *
	 * @see #deleteFeature(IFeature)
	 * @see #getFeature(CharSequence)
	 * @see #getFeatures()
	 * @see #getNumberOfFeatures()
	 * @see #reset()
	 *
	 * @since 3.0
	 */
	List<IOvModelVariationPoint> getVariationPoints();

	/**
	 * Add a new feature <code>feature</code> to this feature model. If the feature model not contains a feature with the name {@link IFeature#getName()} of
	 * <code>feature</code>, the <code>feature</code> will be added and the method returns <b>true</b>. Otherwise, the feature is not added and the method
	 * returns <b>false</b>. Classes implementing <code>IFeatureModel</code> must provide consistency with the underlying <i>feature table</i> which is
	 * accessible by {@link #getFeatureTable()}.
	 *
	 * @param feature the feature to be added. <code>feature</code> is assumed to be <i>non-null</i>
	 * @return <b>true</b> if the feature was added, otherwise <b>false</b>.
	 *
	 * @see #deleteFeature(IFeature)
	 * @see #getFeature(CharSequence)
	 * @see #getFeatures()
	 * @see #getNumberOfFeatures()
	 * @see #reset()
	 *
	 * @since 3.0
	 */
	boolean addVariationPoint(IOvModelVariationPoint variationPoint);

	/**
	 * Removes <code>feature</code> from this model. <code>feature</code> can not be removed, if it is the feature models <i>root</i> feature or if it is not
	 * contained in this model. In both cases, the method returns <b>false</b>. Otherwise the method returns <b>true</b>. <br> <br> Implementations of this
	 * method must ensure, that after removing <code>feature</code>, the feature's <i>parent feature</i> is changed to an <i>and</i> ( <i>or</i>,
	 * <i>alternative</i>) group if <code>feature</code> was an <i>and</i> (<i>or</i>, <i>alternative</i>) group. Additionally, removing <code>feature</code>
	 * has to add the children of <code>feature</code> as children to the <i>parent feature</i>. <br> <br> Removing a feature also removes this feature from the
	 * <i>feature table</i> and the <i>feature order list</i>. Both must be consistent with {@link #getFeatureOrderList()} and {@link #getFeatureOrderList()}
	 * <br> <br> <b>Note</b>If the structure should not be changed, use {@link #deleteFeatureFromTable(IFeature)}
	 *
	 * @param feature the feature that should be removed. It is assumed to be <i>non-null</i>
	 * @return <b>false</b> if <code>feature</code> is the models <i>root</i> feature, or if <code>feature</code> is not contained in this model. Otherwise
	 *         <b>true</b>.
	 *
	 * @see #addFeature(IFeature)
	 * @see #getFeature(CharSequence)
	 * @see #getFeatures()
	 * @see #getNumberOfFeatures()
	 * @see #reset()
	 *
	 * @since 3.0
	 */
	boolean removeVariationPoint(IOvModelVariationPoint variationPoint);

	/**
	 * @return Returns the number of constraints contained in this feature model.
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 */
	int getConstraintCount();

	/**
	 * A constraint is an additional restriction on features in the feature model.
	 *
	 * This methods adds the constraint <code>constraint</code> to the <i>end</i> of the existing collection. Please note that <ul> <li>the specification do not
	 * require a check if <code>constraint</code> is <i>null</i>. However, for regular use, <code>constraint</code> is assumed to be <i>non-null</i></li>
	 * <li>the specification do not require a check of duplicates. In FeatureIDE's default implementation, the collection is managed using a <code>List</code>.
	 * For regular use case, this collection is assumed to be duplicate-free. Therefore, duplicates should not be added.</li> </ul>
	 *
	 * To add a constraint at a specific position, use {@link #addConstraint(IConstraint, int)}
	 *
	 * @param constraint The constraint to be added at the end of the existing collection
	 *
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 */
	boolean addConstraint(IOvModelConstraint constraint);

	/**
	 * A constraint is an additional restriction on features in the feature model.
	 *
	 * This methods adds the constraint <code>constraint</code> at the given <i>index</i> of the existing collection. Please note that <ul> <li>the
	 * specification do not require a check if <code>constraint</code> is <i>null</i>. However, for regular use, <code>constraint</code> is assumed to be
	 * <i>non-null</i></li> <li>the specification do not require a check of duplicates. In FeatureIDE's default implementation, the collection is managed using
	 * a <code>List</code>. For regular use case, this collection is assumed to be duplicate-free. Therefore, duplicates should not be added.</li> </ul>
	 *
	 * To add a constraint at a specific position, use {@link #addConstraint(IConstraint, int)}
	 *
	 * @param constraint The constraint to be added at position <i>index</i> of the existing collection
	 * @param index The position. It is assumed, that the index is valid. Otherwise a exception have to be thrown by the implementation.
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 */
	void addConstraint(IOvModelConstraint constraint, int index);

	/**
	 * Returns the index of the first occurrence of <code>constraint</code> in the collection of constraints, or <b>-1</b> if <code>constraint</code> is not
	 * contained. <br> <br> <b>Note</b>:
	 *
	 * @param constraint the element to be removed. It is assumed that this parameter is <i>non-null</i>
	 * @throws NullPointerException - if <code>constraint</code> is null (optional)
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 *
	 * @return the index of the first occurrence of <code>constraint</code> in the collection of constraints, or <b>-1</b> otherwise.
	 */
	int getConstraintIndex(IOvModelConstraint constraint);

	/**
	 * Returns the list of constraints stored in this feature model. <br> <br> <b>Note</b>: The returned list should be <b>unmodifiable</b> to avoid external
	 * access to internal data
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 *
	 * @return All constraints stored in this feature model.
	 */
	List<IOvModelConstraint> getConstraints();

	/**
	 * Returns the model properties attached to this feature model. These properties contain at least <ul> <li>Annotations</li> <li>Comments</li> <li>The
	 * feature order specification</li> </ul> The properties returned by this model is implementation specific and might contain additional properties (see
	 * {@link IFeatureModelProperty}).
	 *
	 * @since 3.0
	 *
	 * @return feature model properties
	 */
	IOvModelMetainformation getMetainformation();

	/**
	 * Removes the first occurrence of <code>constraint</code> from the collection of constraints in this model, if it is present. Otherwise there is no effect
	 * to this model.
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 *
	 * @param constraint The constraint to be removed
	 */
	boolean removeConstraint(IOvModelConstraint constraint);

	/**
	 * Removes the constraint at the specified position <code>index</code> in this collection of constraints in this model. When a constraint was removed, the
	 * remaining constraints to the right are shifted one position to the left.
	 *
	 * @param index position of the constraint to be removed
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @throws IndexOutOfBoundsException If the index is out of range
	 * @since 3.0
	 */
	void removeConstraint(int index);

	/**
	 * Replaces the constraint <code>constraint</code> at the specified position <code>index</code> in the collection of constraints of this feature model.
	 *
	 * @param constraint constraint which should be stored at <code>index</code>
	 * @param index position for replacement
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #setConstraints(Iterable)
	 *
	 * @throws NullPointerException if <code>constraint</code> is <b>null</b>
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *
	 * @since 3.0
	 *
	 */
	void replaceConstraint(IOvModelConstraint constraint, int index);

	/**
	 * Sets the collections of constraints to the ones yielded by <code>constraints</code>. Existing constraint in the collection will be removed before this
	 * operation.
	 *
	 * @param constraints Source of constraints which should be copied into this feature model
	 *
	 * @see #addConstraint(IConstraint)
	 * @see #addConstraint(IConstraint, int)
	 * @see #getConstraintCount()
	 * @see #getConstraintIndex(IConstraint)
	 * @see #getConstraints()
	 * @see #removeConstraint(IConstraint)
	 * @see #removeConstraint(int)
	 * @see #setConstraint(int, IConstraint)
	 * @see #replaceConstraint(IConstraint, int)
	 *
	 * @since 3.0
	 */
	void setConstraints(final Iterable<IOvModelConstraint> constraints);

	/**
	 * @param iIdentifyable
	 * @return
	 */
	IOvModelElement getElement(IIdentifyable identifyable);
}
