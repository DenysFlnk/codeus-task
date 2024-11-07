package com.practice.warmup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindNonUniqueElementsInList {

    /**
     * Implement generic method that takes a list of any object and returns a set of them that appear
     * more than once (non-unique elements).
     *
     * Validate your impplementation using existing unit tests.
     */
    public Set findNonUniqueElements(List inputList) {
        Set uniqueElements = new HashSet();
        Set duplicates = new HashSet();

        inputList.stream()
            .forEach(e -> {
                if (uniqueElements.contains(e)) {
                    duplicates.add(e);
                } else {
                    uniqueElements.add(e);
                }
            });

        return duplicates;
    }
}
