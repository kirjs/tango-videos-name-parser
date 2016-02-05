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
                .put("izHGohLz4jw", ImmutableSet.of("Pablo Inza", "Sofia Saborido"))
                .put("ZKnlIpDe15M", ImmutableSet.of("Pablo Inza", "Sofia Saborido"))
                .put("PS91okv4uHE", ImmutableSet.of("Maria Filali", "Gianpiero Galdi"))
                .put("Ztt5B5yxGpQ", ImmutableSet.of("Maria Filali", "Gianpiero Galdi"))
                .put("MoDjiGsqBJU", ImmutableSet.of("Maria Filali", "Gianpiero Galdi"))
                .put("0wFi7aUz9Qk", ImmutableSet.of("Maria Filali", "Gianpiero Galdi"))
                .put("Mr3ChIzv5bI", ImmutableSet.of("Maria Filali", "Gianpiero Galdi"))
                .put("X9CJMkNlEUc", ImmutableSet.of("Maria Filali", "Gianpiero Galdi"))
                .put("vvzLbJrOnmc", ImmutableSet.of("Sebastian Jimenez", "Maria Ines Bogado"))
                .put("wi9Mp1RYjyI", ImmutableSet.of("Sebastian Jimenez", "Maria Ines Bogado"))
                .put("_kJD7txuWrA", ImmutableSet.of("Sebastian Jimenez", "Maria Ines Bogado"))
                .put("4ovYgzpqi14", ImmutableSet.of("Sebastian Jimenez", "Maria Ines Bogado", "Javier Rodriguez", "Noelia Barsi"))
                .put("rgnBgLXK2xg", ImmutableSet.of("Maria del Carmen Romero", "Jorge Dispari"))
                // Names in the description
                .put("ACdCdvamIO4", ImmutableSet.of("Kalganova Eleonora", "Michael Nadtochi"))
                .put("2f4hR4TJ-Wg", ImmutableSet.of("Kalganova Eleonora", "Michael Nadtochi"))
                .put("_D_JghR445Y", ImmutableSet.of("Stephanie Fesneau", "Fausto Carpino"))
                .put("v-vnXfQB9og", ImmutableSet.of("Stephanie Fesneau", "Fausto Carpino"))
                .put("tRNJUtAhsQU", ImmutableSet.of("Pablo Rodriguez", "Noelia Hurtado"))
                .put("4HYwJWt3kC8", ImmutableSet.of("Pablo Rodriguez", "Noelia Hurtado"))
                .put("sbuqfkU2mTI", ImmutableSet.of("Pablo Rodriguez", "Noelia Hurtado"))
                .put("nh01sq6-eoo", ImmutableSet.of("Pablo Rodriguez", "Corina Herrera"))
                .put("JwcXVRzSU5Q", ImmutableSet.of("Pablo Rodriguez", "Corina Herrera"))
                .put("EnhyK1b1zMw", ImmutableSet.of("Pablo Rodriguez", "Corina Herrera"))
                .put("IqXR9F1exEI", ImmutableSet.of("Pablo Rodriguez", "Mariana Dragone"))
                .put("IyMxGI2prvk", ImmutableSet.of("Carlitos Espinoza", "Noelia Hurtado"))
                .put("9gcgHx9FJ2U", ImmutableSet.of("Carlitos Espinoza", "Noelia Hurtado"))
                .put("6XX9CBVzorA", ImmutableSet.of("Carlitos Espinoza", "Noelia Hurtado"))
                .put("IkZQGa9i31Q", ImmutableSet.of("Pablo Giorgini", "Noelia Coletti"))
                .put("8c6BYL1rroA", ImmutableSet.of("Pablo Giorgini", "Noelia Coletti"))
                .put("LyMuMuGTwIk", ImmutableSet.of("Mariano Frumboli", "Juana Sepulveda"))
                .put("NBj_PV_87SQ", ImmutableSet.of("Mariano Frumboli", "Juana Sepulveda"))
                .put("K4R-uoldB3g", ImmutableSet.of("Mariano Frumboli", "Juana Sepulveda"))
                .put("A3IngDjc8nw", ImmutableSet.of("Mariano Frumboli", "Juana Sepulveda"))
                .put("DKtQyoahE98", ImmutableSet.of("Sebastian Achaval", "Roxana Suarez"))
                .put("FTlwZ_3cAQs", ImmutableSet.of("Diego Riemer", "Natalia Cristobal Rive"))
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
