package org.beanone;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * The snapshot of a bean. With a BeanSnapshot, one can easily navigate in
 * between the different versions of a JavaBean.
 * <p/>
 * A BeanSnapshot is intentionally made not a {@link Serializable}.
 * 
 * @author hongliii
 *
 * @param <T>
 *            the type of JavaBean this snapshot is for.
 */
class BeanSnapshot<T> {
    private final T state;
    private final BeanHistory<T> beanDocument;
    private final int version;

    public int getVersion() {
        return version;
    }

    @SuppressWarnings("unchecked")
    public BeanSnapshot(T bean, BeanHistory<T> beanDocument, int version)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        this.state = (T) BeanUtils.cloneBean(bean);
        this.beanDocument = beanDocument;
        this.version = version;
    }

    @SuppressWarnings("unchecked")
    public T getState()
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return (T) BeanUtils.cloneBean(state);
    }

    public BeanSnapshot<T> previousVersion()
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        if (version == 0) {
            return null;
        } else {
            final BeanPatch<T> patch = getBeanDocument().getPatches().get(version - 1);
            return patch.substractFrom(this);
        }
    }

    public BeanSnapshot<T> nextVersion()
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        if (version == getBeanDocument().getPatches().size() - 1) {
            return null;
        } else {
            final BeanPatch<T> patch = getBeanDocument().getPatches().get(version + 1);
            return patch.addTo(this);
        }
    }

    BeanHistory<T> getBeanDocument() {
        return beanDocument;
    }

    boolean isBaseSnapshot() {
        return version == 0;
    }

    boolean isLatestSnapshot() {
        return version == getBeanDocument().getPatches().size() - 1;
    }
}
