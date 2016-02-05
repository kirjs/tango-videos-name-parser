package com.tangovideos;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Hello world!
 */
public class AppproachTwo {
    public static Set<String> fetchNames(JSONObject data) {

        final String title = JSONHelper.getTitle(data);

        return ImmutableSet.of("Nito Garcia", "Elba Garcia");
    }

    public static Function<String, Boolean> getMethod(
            Map<String, JSONObject> data,
            ImmutableMap<String, ImmutableSet<String>> expected
    ) {
        return key -> AppproachOne.fetchNames(data.get(key)).equals(expected.get(key));
    }
}
