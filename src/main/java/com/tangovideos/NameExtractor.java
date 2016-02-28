package com.tangovideos;

import java.util.Set;

public interface NameExtractor {
    Set<String> extractNames(VideoAndDancer videoAndDancers);
    String getLabel();


}
