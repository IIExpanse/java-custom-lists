package ru.expanse.custom.collection;

import java.util.Comparator;

public interface CustomList<E> {
    /**
     * Add new element to the list.
     *
     * @param elem - added element.
     */
    void add(E elem);

    /**
     * Add new element at the specified index.
     *
     * @param elem - added element.
     * @param idx  - target index.
     */
    void add(E elem, int idx);

    /**
     * Return element located at the specified index.
     *
     * @param idx - target index.
     * @return E - returned element.
     */
    E get(int idx);

    /**
     * Return and remove element located at the specified index.
     *
     * @param idx - target index.
     * @return E - removed element.
     */
    E remove(int idx);

    /**
     * Remove all elements from the list.
     */
    void clear();

    /**
     * Sort the list according to the supplied comparator's logic.
     */
    void sort(Comparator<E> comparator);

    /**
     * Return an array of the requested type containing elements of this list.
     *
     * @param target - supplied (empty) array of the requested type.
     * @return T[] - resulting array of the requested type.
     */
    <T> T[] toArray(T[] target);

    /**
     * Return the size of the list.
     */
    int size();
}
