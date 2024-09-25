package com.practice.warmup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class FindNonUniqueElementsTest {

    private FindNonUniqueElementsInList nonUniqueElementsSearcher;

    @Before
    public void setUp() {
        nonUniqueElementsSearcher = new FindNonUniqueElementsInList();
    }

    @Test
    public void testFiningNonUniqueElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        Set<Integer> duplicates = nonUniqueElementsSearcher.findNonUniqueElements(list);
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertTrue(duplicates.contains(3));
        Assert.assertTrue(duplicates.contains(4));
        Assert.assertFalse(duplicates.contains(1));
    }

    @Test
    public void testFiningNonUniqueElementsInUniqueList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Set<Integer> duplicates = nonUniqueElementsSearcher.findNonUniqueElements(list);
        Assert.assertEquals(duplicates.size(), 0);
    }

    @Test
    public void testFiningNonUniqueInHomogeneousList() {
        List<Integer> list = Arrays.asList(7,7,7,7,7,7);
        Set<Integer> duplicates = nonUniqueElementsSearcher.findNonUniqueElements(list);
        Assert.assertEquals(1, duplicates.size());
    }

    @Test
    public void testThatMethodWorksWithDifferentTypes() {
        List<String> list = Arrays.asList("1", "2", "2", "3", "4", "5", "5");
        Set<String> duplicates = nonUniqueElementsSearcher.findNonUniqueElements(list);
        Assert.assertEquals(2, duplicates.size());
        Assert.assertTrue(duplicates.contains("2"));
        Assert.assertTrue(duplicates.contains("5"));
    }
}
