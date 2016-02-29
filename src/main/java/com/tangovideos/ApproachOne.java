package com.tangovideos;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class ApproachOne implements  NameExtractor {
    public ApproachOne(Set<Set<String>> dancers){

    }


    @Override
    public Set<String> extractNames(VideoAndDancer videoAndDancers) {
        return ImmutableSet.of();
    }

    @Override
    public String getLabel() {
        return "Approach One";
    }
}
