package com.tangovideos;

import com.google.common.collect.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TestNameParsing {
    private ImmutableMap<String, ImmutableSet<String>> expected;

    @Before
    public void prepareData() throws IOException, JSONException {
        final JSONArray videosAndDancersJson = new JSONArray(new String(Files.readAllBytes(Paths.get("videos.json"))));
        ArrayList<VideoAndDancer> videosAndDancers = Lists.newArrayList();
        for (int i = 0; i < videosAndDancersJson.length(); i++) {
            final VideoAndDancer videoAndDancer = new VideoAndDancer();
            final JSONObject videoAndDancerJson = videosAndDancersJson.getJSONObject(i);
            videoAndDancer.setVideoId(videoAndDancerJson.getString("videoId"));
            videoAndDancer.setTitle(videoAndDancerJson.getString("title"));
            videoAndDancer.setDescription(videoAndDancerJson.getString("description"));
            final JSONArray dancersJson = videoAndDancerJson.getJSONArray("dancers");
            Set<String> dancers = Sets.newHashSet();
            for (int j = 0; j < dancersJson.length(); j++) {
                dancers.add(dancersJson.getString(j));
            }
            videoAndDancer.setDancers(dancers);
            videosAndDancers.add(videoAndDancer);
        }


        final Set<String> names = videosAndDancers.stream().map(VideoAndDancer::getDancers).flatMap(Set::stream).collect(Collectors.toSet());
        List<NameExtractor> extractors = ImmutableList.of(
                new ApproachOne(names),
                new BasicNameParser(names)
        );

        System.out.println(extractors.stream()
                .map(NameExtractor::getLabel)
                .map(this::padApproach).collect(Collectors.joining(" | ")));

        final String output = videosAndDancers.stream().map(videoAndDancer -> extractors
                .stream()
                .map(e -> e.extractNames(videoAndDancer))
                .map(result -> result.equals(videoAndDancer.getDancers()))
                .map(Object::toString)
                .map(this::padApproach)
                .collect(Collectors.joining(" | "))
                + pad(videoAndDancer.getTitle(), 100)
        ).collect(Collectors.joining("\n"));

        final String result = extractors.stream().map(extractor -> videosAndDancers
                .stream()
                .map(videos -> extractor.extractNames(videos).equals(videos.getDancers()))
                .filter(e -> e)
                .count()
        ).map(Object::toString)
                .map(e -> {
                    final int percent = (int) (Integer.parseInt(e) / (double)videosAndDancers.size() * 100);
                    return String.valueOf(percent) + "% (" + e + "/" + videosAndDancers.size() + ")";
                })
                .map(this::padApproach).collect(Collectors.joining(" | "));

        System.out.println(result);
        System.out.println(output);
    }

    public String padApproach(String s) {
        return pad(s, 15);
    }

    public String pad(String s, int n) {
        return String.format("%" + (n) + "s", s);
    }

    @Test
    public void testApp() throws IOException, JSONException {


        final Multiset<String> score = HashMultiset.create();
    }
}
