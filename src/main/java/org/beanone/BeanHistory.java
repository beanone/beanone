package org.beanone;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * A document that holds the whole history of a JavaBean as a sequence of patch
 * updates.
 * 
 * @author hongliii
 *
 * @param <T>
 *            the type of JavaBean whose history this holds on to.
 */
public class BeanHistory<T> implements Serializable {
    private static final long serialVersionUID = 7372416049246900193L;

    private final T initialState;
    private T latestState;
    private final List<BeanPatch<T>> patches = new ArrayList<BeanPatch<T>>();

    @SuppressWarnings("unchecked")
    public BeanHistory(T bean)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        this.initialState = (T) BeanUtils.cloneBean(bean);
        this.latestState = this.initialState;
    }

    public T getInitialState() {
        return initialState;
    }

    public T getLatestState() {
        return latestState;
    }

    public List<BeanPatch<T>> getPatches() {
        return patches;
    }

    public BeanPatch<T> createPatch(BeanUpdater<T> updater)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        @SuppressWarnings("unchecked")
        final T newBean = (T) BeanUtils.cloneBean(latestState);
        updater.update(newBean);
        final BeanPatch<T> returns = BeanPatch.create(latestState, newBean);
        patches.add(returns);
        this.latestState = newBean;
        return returns;
    }

    public BeanSnapshot<T> getInitialSnapshot()
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return new BeanSnapshot<T>(initialState, this, 0);
    }

    public BeanSnapshot<T> getLastestSnapshot()
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return new BeanSnapshot<T>(latestState, this, patches.size() - 1);
    }
}
