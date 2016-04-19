package org.beanone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

/**
 * A document that holds the whole history of a JavaBean as a sequence of patch
 * updates.
 *
 * <p>
 * The only way to update a BeanHistory is throw one of the createPatch()
 * methods.
 * </p>
 *
 * @author Hongyan Li
 *
 * @param <T>
 *            the type of JavaBean whose history this holds on to.
 */
public class BeanHistory<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 7372416049246900193L;
	private final T initialState;
	private T latestState;
	private final List<BeanPatch<T>> patches = new ArrayList<>();

	/**
	 * Construct a new instance of this from a JavaBean.
	 *
	 * @param bean
	 *            a JavaBean. Note: it must be Serializable.
	 */
	public BeanHistory(T bean) {
		this.initialState = SerializationUtils.clone(bean);
		this.latestState = this.initialState;
	}

	/**
	 * Creates a patch.
	 *
	 * @param updater
	 *            a callback that this calls to get the updated state of the
	 *            JavaBean the patch will be created from.
	 * @return a new patch if the new JavaBean contains any change in the bean
	 *         attributes. If no change is found, return null.
	 */
	public BeanPatch<T> createPatch(BeanUpdater<T> updater) {
		final T newBean = SerializationUtils.clone(latestState);
		updater.update(newBean);
		return doCreatePatch(newBean);
	}

	/**
	 * Creates a patch from the passed in new state.
	 *
	 * @param newBean
	 *            a JavaBean that represent the new state of the JavaBean.
	 * @return a new patch if the new JavaBean contains any change in the bean
	 *         attributes. If no change is found, return null.
	 */
	public BeanPatch<T> createPatch(final T newBean) {
		final T newLatest = SerializationUtils.clone(newBean);
		return doCreatePatch(newLatest);
	}

	private BeanPatch<T> doCreatePatch(final T newBean) {
		final BeanPatch<T> returns = BeanPatch.create(latestState, newBean);
		if (returns.hasChanges()) {
			patches.add(returns);
			this.latestState = newBean;
			return returns;
		} else {
			return null;
		}
	}

	/**
	 * @return the initial state of the JavaBean as a BeanSnapshot. A snapshot
	 *         can be used to navigate in between the different snapshots of the
	 *         whole BeanHistory so that one can easily support undo and redo.
	 */
	public BeanSnapshot<T> getInitialSnapshot() {
		return new BeanSnapshot<>(initialState, this, 0);
	}

	/**
	 * @return the initial state of the JavaBean.
	 */
	public T getInitialState() {
		return SerializationUtils.clone(initialState);
	}

	/**
	 * @return the latest state of the JavaBean as a BeanSnapshot. A snapshot
	 *         can be used to navigate in between the different snapshots of the
	 *         whole BeanHistory so that one can easily support undo and redo.
	 */
	public BeanSnapshot<T> getLastestSnapshot() {
		return new BeanSnapshot<>(latestState, this, patches.size());
	}

	/**
	 * @return the latest state of the JavaBean.
	 */
	public T getLatestState() {
		return SerializationUtils.clone(latestState);
	}

	/**
	 * @return returns the patches in this as a Unmodifiable List.
	 */
	public List<BeanPatch<T>> getPatches() {
		return Collections.unmodifiableList(patches);
	}
}
