package com.practice.warmup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindNonUniqueElementsInList {

    /**
     * Method that takes a list of integers and returns a set of integers that appear
     * more than once (non-unique elements).
     *
     * @param inputList list of integers, which may contain both unique and duplicate values.
     * @return set of non-unique elements.
     */
    public <T> Set<T> findNonUniqueElements(List<T> inputList) {
        Set<T> elements = new HashSet<>();
        Set<T> duplicates = new HashSet<>();

        for (T element : inputList) {
            if (!elements.add(element)) {
                duplicates.add(element);
            }
        }

        return duplicates;
    }

    public <T> Set<T> findNonUniqueElementsUsingStream(List<T> inputList) {
        Set<T> elements = new HashSet<>();
        return inputList.stream().filter(n -> !elements.add(n)).collect(Collectors.toSet());
    }
}
