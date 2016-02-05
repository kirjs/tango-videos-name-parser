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
public class AppproachOne {


    public static Set<String> fetchNames(JSONObject jsonObject) {

        return ImmutableSet.of("Nito Garcia", "Elba Garcia");
    }

    public static Function<String, Boolean> getMethod(
            Map<String, JSONObject> data,
            ImmutableMap<String, ImmutableSet<String>> expected
    ) {
        return key -> compare(AppproachOne.fetchNames(data.get(key)), (expected.get(key)));
    }

    private static Boolean compare(Set<String> strings, ImmutableSet<String> strings1) {
        return strings.equals(strings1);
    }
}
