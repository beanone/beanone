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
 * The only way to update a BeanHistory is through one of the createPatch()
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
	 * Constructs a new instance of this from a JavaBean.
	 *
	 * @param bean
	 *            a JavaBean. Note: it must be a Serializable.
	 */
	public BeanHistory(T bean) {
		this.initialState = cloneBean(bean);
		this.latestState = this.initialState;
	}

	/**
	 * Constructs a new instance of this from a JavaBean.
	 *
	 * @param initialState
	 *            the initial state of a JavaBean.
	 * @param finalState
	 *            the final state of a JavaBean.
	 * @param patches
	 *            the patches in between the initial state and final state of
	 *            the JavaBean
	 */
	public BeanHistory(T initialState, T finalState,
	        List<BeanPatch<T>> patches) {
		if (initialState == null && finalState == null) {
			throw new IllegalArgumentException();
		}

		if (patches != null) {
			this.patches.addAll(patches);
		}

		this.initialState = initialState == null
		        ? calculateInitialState(finalState, this.patches)
		        : cloneBean(initialState);
		this.latestState = finalState == null
		        ? calculateLatestState(initialState, this.patches)
		        : cloneBean(finalState);
	}

	/**
	 * Creates a patch.
	 *
	 * @param updater
	 *            a callback that this calls to get the updated state of the
	 *            JavaBean the patch will be created from.
	 * @return a new patch if the new JavaBean contains any change in the bean
	 *         attributes. If no change is found, returns null.
	 */
	public BeanPatch<T> createPatch(BeanUpdater<T> updater) {
		final T newBean = cloneBean(this.latestState);
		updater.update(newBean);
		return doCreatePatch(newBean);
	}

	/**
	 * Creates a patch from the passed in new state.
	 *
	 * @param newBean
	 *            a JavaBean that represent the new state of the JavaBean.
	 * @return a new patch if the new JavaBean contains any change in the bean
	 *         attributes. If no change is found, returns null.
	 */
	public BeanPatch<T> createPatch(final T newBean) {
		final T newLatest = cloneBean(newBean);
		return doCreatePatch(newLatest);
	}

	/**
	 * @return the initial state of the JavaBean as a BeanSnapshot. A snapshot
	 *         can be used to navigate in between the different snapshots of the
	 *         whole BeanHistory so that one can easily support undo and redo.
	 */
	public BeanSnapshot<T> getInitialSnapshot() {
		return new BeanSnapshot<>(this.initialState, this.getPatches(), 0);
	}

	/**
	 * @return the initial state of the JavaBean. You can safely edit the
	 *         returned JavaBean since it is a a deep clone of what is in this.
	 */
	public T getInitialState() {
		return cloneBean(this.initialState);
	}

	/**
	 * @return the latest state of the JavaBean as a BeanSnapshot. A snapshot
	 *         can be used to navigate in between the different snapshots of the
	 *         whole BeanHistory so that one can easily support undo and redo.
	 */
	public BeanSnapshot<T> getLastestSnapshot() {
		return new BeanSnapshot<>(this.latestState, this.getPatches(),
		        this.patches.size());
	}

	/**
	 * @return the latest state of the JavaBean. You can safely edit the
	 *         returned JavaBean since it is a a deep clone of what is in this.
	 */
	public T getLatestState() {
		return cloneBean(this.latestState);
	}

	/**
	 * @return returns the patches in this as a Unmodifiable List.
	 */
	public List<BeanPatch<T>> getPatches() {
		return Collections.unmodifiableList(this.patches);
	}

	private T calculateInitialState(T finalState, List<BeanPatch<T>> patches) {
		BeanSnapshot<T> snapshot = new BeanSnapshot<>(finalState, patches,
		        patches.size());
		BeanSnapshot<T> previousSnapshot;
		while ((previousSnapshot = snapshot.previousVersion()) != null) {
			snapshot = previousSnapshot;
		}
		return snapshot.getState();
	}

	private T calculateLatestState(T initialState, List<BeanPatch<T>> patches) {
		BeanSnapshot<T> snapshot = new BeanSnapshot<>(initialState, patches, 0);
		BeanSnapshot<T> nextSnapshot;
		while ((nextSnapshot = snapshot.nextVersion()) != null) {
			snapshot = nextSnapshot;
		}
		return snapshot.getState();
	}

	private T cloneBean(final T newBean) {
		return SerializationUtils.clone(newBean);
	}

	private BeanPatch<T> doCreatePatch(final T newBean) {
		final BeanPatch<T> returns = BeanPatch.create(this.latestState,
		        newBean);
		if (returns.hasChanges()) {
			this.patches.add(returns);
			this.latestState = newBean;
			return returns;
		} else {
			return null;
		}
	}
}
