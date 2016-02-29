package com.tangovideos;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NameAwareNameParserTest {

    @Test
    public void testSmth(){
        assertEquals(NameAwareNameParser.filterDuplicateNames(ImmutableSet.of("One", "Two Two", "One Two")),ImmutableSet.of("One"));
        assertEquals(NameAwareNameParser.filterDuplicateNames(
                ImmutableSet.of("Velitas Velitas", "Homer Ladas", "Santos Velitas", "Cristina Ladas"))
                ,ImmutableSet.of("Homer Ladas", "Cristina Ladas"));
    }

}
