package ru.expanse.custom.collection.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {
    private CustomArrayList<Integer> list;

    @BeforeEach
    void refreshList() {
        list = new CustomArrayList<>();
    }

    @Test
    void shouldThrowExceptionForWrongCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-1));
    }

    @Test
    void add() {
        Integer[] arr = getDefaultArray();
        fillList(list, arr);
        assertArrayEquals(arr, list.toArray(new Integer[0]));
    }

    @Test
    void addByIdx() {
        Integer[] arr1 = new Integer[]{3, 20, 10};
        fillList(list, arr1);

        list.add(-5, 1);
        Integer[] arr2 = new Integer[]{3, -5, 20, 10};
        assertArrayEquals(arr2, list.toArray(new Integer[0]));

        list.add(2, 3);
        Integer[] arr3 = new Integer[]{3, -5, 20, 2, 10};
        assertArrayEquals(arr3, list.toArray(new Integer[0]));
    }

    @Test
    void throwsExceptionForAddingIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(5, 0));
    }

    @Test
    void remove() {
        Integer[] arr1 = new Integer[]{3, -5, 20, 10, 2};
        fillList(list, arr1);

        assertEquals(arr1[1], list.remove(1));
        Integer[] arr2 = new Integer[]{3, 20, 10, 2};
        assertArrayEquals(arr2, list.toArray(new Integer[0]));

        assertEquals(arr2[3], list.remove(3));
        Integer[] arr3 = new Integer[]{3, 20, 10};
        assertArrayEquals(arr3, list.toArray(new Integer[0]));
    }

    @Test
    void clear() {
        fillList(list, getDefaultArray());
        list.clear();
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void sort() {
        Integer[] arr = getDefaultArray();
        fillList(list, arr);

        Arrays.sort(arr);
        list.sort(Comparator.naturalOrder());
        assertArrayEquals(arr, list.toArray(new Integer[0]));
    }

    @Test
    void toArray() {
        Integer[] arr = getDefaultArray();
        fillList(list, arr);
        assertArrayEquals(arr, list.toArray(new Integer[0]));
    }

    private void fillList(CustomArrayList<Integer> list, Integer[] arr) {
        Arrays.stream(arr).forEach(list::add);
    }

    private Integer[] getDefaultArray() {
        return new Integer[]{3, -5, 20, 10, 2};
    }
}