package com.example.xyzreader.util;

/**
 * Created by lars on 27.02.17.
 */

public class TransitionHelper {

    public static final String EXTRA_IMAGE_TRANSITION_NAME = "extra_image_transition_name";
    private static final String IMAGE_TRANSITION_PREFIX = "article_image_";

    public static final int UNDEFINED_POSITION = -1;

    private static long requrestedArticleId = UNDEFINED_POSITION;

    public static String getImageTransitionName(int position) {
        return IMAGE_TRANSITION_PREFIX + String.valueOf(position);
    }

    public static void setRequrestedArticleId(long requrestedArticleId) {
        TransitionHelper.requrestedArticleId = requrestedArticleId;
    }

    public static long getRequrestedArticleId() {
        return requrestedArticleId;
    }

    public static void resetRequrestedArticleId() {
        requrestedArticleId = UNDEFINED_POSITION;
    }
}
