package ru.expanse.custom.collection.impl;

import ru.expanse.custom.collection.CustomList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Custom realization of a linked (node-based) list.
 * Supports adding, retrieving and removing elements by index, clearing and sorting the collection.
 * Not thread-safe.
 *
 * @param <E> - the type of stored elements.
 */
public class CustomLinkedList<E> implements CustomList<E>, Iterable<E> {
    /**
     * Pointer indicating the first element of this collection (or null if empty).
     */
    private Node<E> first;
    /**
     * Pointer indicating the last element of this collection (or null if empty).
     */
    private Node<E> last;
    /**
     * Counter which contains the size of this collection.
     */
    private int size;

    /**
     * Adds new element to the end of this list.
     *
     * @param elem - added element.
     */
    @Override
    public void add(E elem) {
        Node<E> node = new Node<>(elem);
        size++;

        if (first == null) {
            first = node;
            last = node;
            return;
        }
        last.next = node;
        node.prev = last;
        last = node;
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
        Node<E> target = this.findByIdx(idx);
        Node<E> node = new Node<>(elem);

        node.next = target;
        node.prev = target.prev;
        target.prev = node;

        if (node.prev != null) {
            node.prev.next = node;

        } else {
            first = node;
        }
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
    public E get(int idx) {
        return this.findByIdx(idx).val;
    }

    /**
     * Removes the element from the target index.
     * Moves all elements located to the right of the removed element by 1 position to the left.
     *
     * @param idx - target index.
     * @throws IndexOutOfBoundsException - if specified index is out of bounds of this collection.
     */
    @Override
    public E remove(int idx) {
        Node<E> target = this.findByIdx(idx);

        if (target.prev != null) {
            target.prev.next = target.next;
        } else {
            first = target.next;
        }

        if (target.next != null) {
            target.next.prev = target.prev;
        } else {
            last = target.prev;
        }
        size--;

        return target.val;
    }

    /**
     * Clears this collection by replacing pointers with null and setting its size to zero.
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Sorts this collection by using the supplied comparator.
     * Sorting is performed by creating an array containing elements of this collection, sorting it,
     * then consequently replacing values of nodes with array's elements.
     *
     * @param comparator - provided comparator.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<E> comparator) {
        if (size < 2) {
            return;
        }
        E[] arr = this.toArray((E[]) new Object[0]);
        Arrays.sort(arr, comparator);

        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            node.val = arr[i];
            node = node.next;
        }
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
        Object[] arr = new Object[size];
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            arr[i] = node.val;
            node = node.next;
        }

        return (T[]) Arrays.copyOf(arr, size, target.getClass());
    }

    /**
     * Returns the size of this collection.
     */
    @Override
    public int size() {
        return size;
    }

    private Node<E> findByIdx(int idx) {
        Objects.checkIndex(idx, size);
        Node<E> node;
        int cur;

        if (size / 2 > idx) {
            cur = 0;
            node = first;

            while (cur != idx) {
                node = node.next;
                cur++;
            }

        } else {
            cur = size - 1;
            node = last;

            while (cur != idx) {
                node = node.prev;
                cur--;
            }
        }

        return node;
    }

    /**
     * Iterator implementation.
     * Allows this collection to be the target of the enhanced for statement.
     * @see Iterable
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> node = first;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<E> res = node;
                node = node.next;
                return res.val;
            }
        };
    }

    private static class Node<E> {
        private E val;
        private Node<E> prev;
        private Node<E> next;

        public Node(E val) {
            this.val = val;
        }
    }
}
