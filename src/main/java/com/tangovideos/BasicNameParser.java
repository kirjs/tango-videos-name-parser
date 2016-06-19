package com.tangovideos;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BasicNameParser implements NameExtractor{


    private static Pattern dancer = Pattern.compile("([A-Z][a-z]+ )?([A-Za-z]+? |[\"“][A-Za-z ]+[\"”] )?([A-Za-z]+? |[\"“][A-Za-z ]+[\"”] )?([A-Z][a-z]+) (and|y|&|e|et|Y) ([A-Z][a-z]+)( [\"]?[A-Z][a-z]+[\"]?)?( [A-Z][a-z]+)?");

    public BasicNameParser(Set<Set<String>> names) {

    }


    public static Set<String> parseString(String string) {
        final Matcher matcher = dancer.matcher(string);
        if (matcher.find()) {

            final String group = matcher.group(0);
            String dancer1Name = matcher.group(1);
            String dancerNickname = matcher.group(3);
            String dancer1LastName = matcher.group(4);
            String dancer2Name = matcher.group(6);
            String dancer2LastName = matcher.group(7);
            String dancer2RealLastName = matcher.group(8);

            if (dancer1LastName == null) {
                dancer1LastName = dancer2LastName;
            }

            if (dancer2RealLastName != null) {
                dancer2LastName = dancer2RealLastName;
            }
            if (dancer2LastName == null) {
                dancer2LastName = dancer1LastName;
            }

            if (dancer1Name == null) {
                dancer1Name = dancer1LastName;
                dancer1LastName = dancer2LastName;
            }
            if (dancer2Name == null) {
                dancer2Name = "";
            }
            if (dancer1LastName == null) {
                dancer1LastName = "";
            }
            if (dancer2LastName == null) {
                dancer2LastName = "";
            }


            final String dancer1 = dancer1Name.trim() + " " + dancer1LastName.trim();
            final String dancer2 = dancer2Name.trim() + " " + dancer2LastName.trim();
            final ImmutableSet<String> matches = ImmutableSet.of(dancer1.trim(), dancer2.trim());
            final Set<String> others = parseString(string.replace(group, ""));
            return ImmutableSet.<String>builder().addAll(matches).addAll(others).build();
        } else {
            return ImmutableSet.of();
        }
    }



    public static Set<String> extractNames(String title, String description) {
        title = StringUtils.stripAccents(title);
        description = StringUtils.stripAccents(description);
        final Set<String> titleData = parseString(title);
        if (titleData.size() > 0) {
            return titleData;
        }
        return parseString(description);
    }



    public static boolean compare(Set<String> a, Set<String> b, String key) {
        if (!a.equals(b)) {
            System.out.println();
            System.out.println("[");
            System.out.println(a);
            System.out.println(b);
            System.out.println(a.equals(b));
            System.out.println("]");
        }
        return a.equals(b);
    }

    @Override
    public Set<String> extractNames(VideoAndDancer videoAndDancer) {
         return extractNames(videoAndDancer.getTitle(), videoAndDancer.getDescription());
    }

    @Override
    public String getLabel() {
        return "Regex parser";
    }
}
