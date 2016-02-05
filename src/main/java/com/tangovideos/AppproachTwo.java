package com.tangovideos;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class AppproachTwo {

    private static Pattern dancer = Pattern.compile(".*(\\W\\w+)");
    public static Set<String> fetchNames(JSONObject data) {
        String title = JSONHelper.getTitle(data);



        return ImmutableSet.of();
    }

    public static Function<String, Boolean> getMethod(
            Map<String, JSONObject> data,
            ImmutableMap<String, ImmutableSet<String>> expected
    ) {
        return key -> AppproachTwo.fetchNames(data.get(key)).equals(expected.get(key));
    }
}
