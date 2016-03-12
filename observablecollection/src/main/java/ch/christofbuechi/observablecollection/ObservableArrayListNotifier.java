package ch.christofbuechi.observablecollection;

/**
 * Created by bui on 25.08.2015.
 */
public interface ObservableArrayListNotifier<T> {
    void listChanged(int index, T object);
}
