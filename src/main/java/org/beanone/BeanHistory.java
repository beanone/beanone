package org.beanone;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

	private final T						initialState;
	private T							latestState;
	private final List<BeanPatch<T>>	patches	= new ArrayList<BeanPatch<T>>();

	public BeanHistory(T bean)
	        throws IllegalAccessException, InstantiationException,
	        InvocationTargetException, NoSuchMethodException {
		this.initialState = SerializationUtils.clone(bean);
		this.latestState = this.initialState;
	}

	public BeanPatch<T> createPatch(BeanUpdater<T> updater)
	        throws IllegalAccessException, InstantiationException,
	        InvocationTargetException, NoSuchMethodException {
		final T newBean = SerializationUtils.clone(latestState);
		updater.update(newBean);
		final BeanPatch<T> returns = BeanPatch.create(latestState, newBean);
		patches.add(returns);
		this.latestState = newBean;
		return returns;
	}

	public BeanPatch<T> createPatch(T newBean) throws IllegalAccessException,
	        InvocationTargetException, NoSuchMethodException {
		final BeanPatch<T> returns = BeanPatch.create(latestState, newBean);
		patches.add(returns);
		this.latestState = newBean;
		return returns;
	}

	public BeanSnapshot<T> getInitialSnapshot()
	        throws IllegalAccessException, InstantiationException,
	        InvocationTargetException, NoSuchMethodException {
		return new BeanSnapshot<T>(initialState, this, 0);
	}

	public T getInitialState() {
		return SerializationUtils.clone(initialState);
	}

	public BeanSnapshot<T> getLastestSnapshot()
	        throws IllegalAccessException, InstantiationException,
	        InvocationTargetException, NoSuchMethodException {
		return new BeanSnapshot<T>(latestState, this, patches.size());
	}

	public T getLatestState() {
		return SerializationUtils.clone(latestState);
	}

	public List<BeanPatch<T>> getPatches() {
		return patches;
	}
}
