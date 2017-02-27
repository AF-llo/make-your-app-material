package com.example.xyzreader.util;

/**
 * Created by lars on 27.02.17.
 */

public class TransitionHelper {

    public static final String EXTRA_IMAGE_TRANSITION_NAME = "extra_image_transition_name";
    private static final String IMAGE_TRANSITION_PREFIX = "article_image_";

    public static String getImageTransitionName(int position) {
        return IMAGE_TRANSITION_PREFIX + String.valueOf(position);
    }
}
