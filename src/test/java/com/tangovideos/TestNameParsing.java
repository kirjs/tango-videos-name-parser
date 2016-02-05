package com.tangovideos;

import com.google.common.collect.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class TestNameParsing {


    private ImmutableMap<String, ImmutableSet<String>> expected;
    private Map<String, JSONObject> data;

    @Before
    public void prepareData() {
        expected = ImmutableMap.<String, ImmutableSet<String>>builder()
                .put("6XCia7vEk3I", ImmutableSet.of("Elba Garcia", "Nito Garcia"))
                .put("282A-FHZyf8", ImmutableSet.of("Elba Garcia", "Nito Garcia"))
                .put("v620ie9j4nQ", ImmutableSet.of("Elba Garcia", "Nito Garcia"))
                .put("RfOM8pxafzg", ImmutableSet.of("Sebastian Arce", "Mariana Montes"))
                .put("1a-o0M8JJfc", ImmutableSet.of("Claudio Ruberti", "Silvia Tonelli"))
                .build();

        data = expected.keySet()
                .stream()
                .collect(Collectors.toMap(i -> i, YoutubeFetcher::getVideoInfo));
    }

    private static Pattern methodNameFormat = Pattern.compile("com\\.tangovideos\\.(\\w+)\\$\\$");

    public String formatMethodName(String name) {
        final Matcher matcher = methodNameFormat.matcher(name);
        matcher.find();
        return matcher.group(1);

    }

    public String padApproach(String s) {
        return pad(s, 15);
    }

    public String pad(String s, int n) {
        return String.format("%" + (n) + "s", s);
    }



    @Test
    public void testApp() throws IOException, JSONException {


        int max = expected.size();

        final Map<String, Function<String, Boolean>> methods = ImmutableList.of(
                AppproachOne.getMethod(data, expected),
                AppproachTwo.getMethod(data, expected)
        ).stream().collect(Collectors.toMap(Object::toString, i -> i));


        final BinaryOperator<String> join = (a, b) -> a + ",  " + b;


        final Multiset<String> score = HashMultiset.create();

        final String header = methods.keySet()
                .stream()
                .map(this::formatMethodName)
                .map(this::padApproach)
                .reduce(join).get();


        System.out.println(header);

        data.keySet().stream().map(
                key -> String.format("%s: %s",
                        methods.values()
                                .stream()
                                .map(method -> method.apply(key) && score.add(method.toString()))
                                .map(y -> y ? "pass" : "fail")
                                .map(this::padApproach)
                                .reduce(join).get(),
                        JSONHelper.getTitle(data.get(key)))
        ).forEach(System.out::println);


        final String footer = methods.keySet()
                .stream()
                .map(score::count)
                .map(Object::toString)
                .map(s -> s + "/" + max)
                .map(this::padApproach)
                .reduce(join).get();

        System.out.println(footer);
    }
}
