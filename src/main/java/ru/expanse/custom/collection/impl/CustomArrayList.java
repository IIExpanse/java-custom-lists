package ru.expanse.custom.collection.impl;

import ru.expanse.custom.collection.CustomList;

import java.util.*;

/**
 * Custom realization of a dynamic array-based list.
 * Supports adding, retrieving and removing elements by index, clearing and sorting the collection.
 * Not thread-safe.
 *
 * @param <E> - the type of stored elements.
 */
public class CustomArrayList<E> implements CustomList<E>, Iterable<E> {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Internal array-based storage for the generic type.
     */
    private Object[] elementData;
    /**
     * Counter which contains the size of this collection.
     */
    private int size;

    /**
     * Default constructor.
     * Initializes internal storage with default initial capacity.
     */
    public CustomArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Optional constructor which allows to set the desired initial capacity of this collection.
     *
     * @param capacity - required capacity, must be greater than zero.
     * @throws IllegalArgumentException - if the provided capacity is not greater than zero.
     */
    public CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.elementData = new Object[capacity];
    }

    /**
     * Adds new element to the end of this list, increasing collection's capacity if necessary.
     *
     * @param elem - added element.
     */
    @Override
    public void add(E elem) {
        if (size == elementData.length) {
            this.increaseCapacity();
        }
        elementData[size++] = elem;
    }

    /**
     * Puts new element at the specified index, increasing collection's capacity if necessary.
     * Moves the previous element at the target index and all elements to the right by 1 position.
     *
     * @param elem - added element.
     * @param idx - target index.
     * @throws IndexOutOfBoundsException - if specified index is out of bounds of this collection.
     */
    @Override
    public void add(E elem, int idx) {
        Objects.checkIndex(idx, size);
        if (size == elementData.length) {
            this.increaseCapacity();
        }
        System.arraycopy(elementData, idx, elementData, idx + 1, size - idx);
        elementData[idx] = elem;
        size++;
    }

    /**
     * Returns the element located at the specified index.
     *
     * @param idx - target index.
     * @return E - the requested element of this collection.
     * @throws IndexOutOfBoundsException - if specified index is out of bounds of this collection.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int idx) {
        Objects.checkIndex(idx, size);
        return (E) elementData[idx];
    }

    /**
     * Removes the element from the target index.
     * Moves all elements located to the right of the removed element by 1 position to the left.
     *
     * @param idx - target index.
     * @throws IndexOutOfBoundsException - if specified index is out of bounds of this collection.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int idx) {
        Objects.checkIndex(idx, size);
        E val = (E) elementData[idx];

        System.arraycopy(elementData, idx + 1, elementData, idx, elementData.length - idx - 1);
        size--;
        return val;
    }

    /**
     * Clears this collection by replacing its storage array and setting size counter to zero.
     */
    @Override
    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Sorts this collection by using the supplied comparator.
     *
     * @param comparator - provided comparator.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<E> comparator) {
        Arrays.sort((E[]) elementData, 0, size, comparator);
    }

    /**
     * Creates and returns an array of the specified type containing elements of this collection.
     *
     * @param target - provided (empty) array of the required type.
     * @return T[] - array of type T containing elements of this collection.
     * @throws ClassCastException - if the required type is not compatible with the type of stored elements.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] target) {
        return (T[]) Arrays.copyOf(elementData, size, target.getClass());
    }

    /**
     * Returns the size of this collection.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Iterator implementation.
     * Allows this collection to be the target of the enhanced for statement.
     * @see Iterable
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int cur;

            @Override
            public boolean hasNext() {
                return cur < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elementData[cur++];
            }
        };
    }

    private void increaseCapacity() {
        Object[] arr = new Object[size * 2];
        System.arraycopy(elementData, 0, arr, 0, size);
        elementData = arr;
    }
}
