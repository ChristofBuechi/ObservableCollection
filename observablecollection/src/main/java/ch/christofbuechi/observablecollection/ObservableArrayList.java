package ch.christofbuechi.observablecollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bui on 25.08.2015.
 */
public class ObservableArrayList<T> extends ArrayList<T> implements List<T> {
    private ObservableArrayListNotifier<T> notifier;
    private int index;

    @Override
    public boolean add(T object) {
        boolean b = super.add(object);
        notifier.listChanged(this.size(), object);
        return b;
    }

    @Override
    public boolean remove(Object object) {
        index = indexOf(object);
        boolean b = super.remove(object);
        if (b) {
            notifier.listChanged(index, (T) object);
        }
        return b;
    }

    @Override
    public void clear() {
        super.clear();
        notifier.listChanged(-1, null);
    }

    @Override
    public T remove(int position) {
        T t = super.remove(position);
        notifier.listChanged(position, t);
        return t;
    }

    public void removeBack() {
        if (size() == 0) {
            return;
        }
        remove(size() - 1);
    }

    public void setObserver(ObservableArrayListNotifier notifier) {
        this.notifier = notifier;
    }

    public void removeObserver () {
        this.notifier = new ObservableArrayListNotifier<T>() {
            @Override
            public void listChanged(int index, T object) {
                //no operation
            }
        };
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean success = super.removeAll(collection);
        if (success) {
            notifier.listChanged(-1, null);
        }
        return success;
    }

    @Override
    public T set(int index, T object) {
        T element = super.set(index, object);
        notifier.listChanged(index, element);
        return element;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            notifier.listChanged(fromIndex, get(fromIndex));
        } else {
            notifier.listChanged(toIndex, get(toIndex));
        }
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        int size = this.size();
        boolean success = super.addAll(index, collection);
        if (size + collection.size() == this.size()) {
            notifier.listChanged(index, get(index));
        }
        return success;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        index = this.size();
        boolean success = super.addAll(collection);
        if (index + collection.size() == this.size()) {
            notifier.listChanged(index, get(index));
        }
        return success;
    }

    @Override
    public void add(int index, T object) {
        index = this.size();
        super.add(index, object);
        if (index + 1 == this.size()) {
            notifier.listChanged(index, get(index));
        }
    }
}
