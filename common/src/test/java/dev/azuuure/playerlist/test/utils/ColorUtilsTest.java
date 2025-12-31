package dev.azuuure.playerlist.test.utils;

import dev.azuuure.playerlist.utils.ColorUtils;
import dev.azuuure.playerlist.utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorUtilsTest {

    @Test
    public void testRanges() {
        assertEquals(Constants.COLOR_EXCELLENT, ColorUtils.latencyToColor(Integer.MIN_VALUE));
        assertEquals(Constants.COLOR_EXCELLENT, ColorUtils.latencyToColor(-100));
        assertEquals(Constants.COLOR_EXCELLENT, ColorUtils.latencyToColor(-1));
        assertEquals(Constants.COLOR_EXCELLENT, ColorUtils.latencyToColor(0));
        assertEquals(Constants.COLOR_EXCELLENT, ColorUtils.latencyToColor(99));

        assertEquals(Constants.COLOR_GOOD, ColorUtils.latencyToColor(100));
        assertEquals(Constants.COLOR_GOOD, ColorUtils.latencyToColor(120));
        assertEquals(Constants.COLOR_GOOD, ColorUtils.latencyToColor(149));

        assertEquals(Constants.COLOR_DECENT, ColorUtils.latencyToColor(150));
        assertEquals(Constants.COLOR_DECENT, ColorUtils.latencyToColor(200));
        assertEquals(Constants.COLOR_DECENT, ColorUtils.latencyToColor(250));
        assertEquals(Constants.COLOR_DECENT, ColorUtils.latencyToColor(299));

        assertEquals(Constants.COLOR_BAD, ColorUtils.latencyToColor(300));
        assertEquals(Constants.COLOR_BAD, ColorUtils.latencyToColor(350));
        assertEquals(Constants.COLOR_BAD, ColorUtils.latencyToColor(450));
        assertEquals(Constants.COLOR_BAD, ColorUtils.latencyToColor(500));
        assertEquals(Constants.COLOR_BAD, ColorUtils.latencyToColor(599));

        assertEquals(Constants.COLOR_VERY_BAD, ColorUtils.latencyToColor(600));
        assertEquals(Constants.COLOR_VERY_BAD, ColorUtils.latencyToColor(700));
        assertEquals(Constants.COLOR_VERY_BAD, ColorUtils.latencyToColor(800));
        assertEquals(Constants.COLOR_VERY_BAD, ColorUtils.latencyToColor(999));

        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(1000));
        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(1234));
        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(5000));
        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(9999));
        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(10_000));
        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(100_000));
        assertEquals(Constants.COLOR_HORRIBLE, ColorUtils.latencyToColor(Integer.MAX_VALUE));
    }
}
