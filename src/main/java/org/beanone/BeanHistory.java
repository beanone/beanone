package org.beanone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

/**
 * A document that holds the whole history of a JavaBean as a sequence of patch
 * updates.
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

	public BeanHistory(T bean) {
		this.initialState = SerializationUtils.clone(bean);
		this.latestState = this.initialState;
	}

	public BeanPatch<T> createPatch(BeanUpdater<T> updater) {
		final T newBean = SerializationUtils.clone(latestState);
		updater.update(newBean);
		final BeanPatch<T> returns = BeanPatch.create(latestState, newBean);
		if (returns.hasChanges()) {
			patches.add(returns);
			this.latestState = newBean;
			return returns;
		} else {
			return null;
		}
	}

	public BeanPatch<T> createPatch(final T newBean) {
		final T newLatest = SerializationUtils.clone(newBean);
		final BeanPatch<T> returns = BeanPatch.create(latestState, newLatest);
		if (returns.hasChanges()) {
			patches.add(returns);
			this.latestState = newLatest;
			return returns;
		} else {
			return null;
		}
	}

	public BeanSnapshot<T> getInitialSnapshot() {
		return new BeanSnapshot<>(initialState, this, 0);
	}

	public T getInitialState() {
		return SerializationUtils.clone(initialState);
	}

	public BeanSnapshot<T> getLastestSnapshot() {
		return new BeanSnapshot<>(latestState, this, patches.size());
	}

	public T getLatestState() {
		return SerializationUtils.clone(latestState);
	}

	public List<BeanPatch<T>> getPatches() {
		return patches;
	}
}
