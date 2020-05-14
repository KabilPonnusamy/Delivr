package com.delivr.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RewardsGson {
    private static Gson gsonClient;

    public RewardsGson() {
    }

    public static Gson getInstance() {
        if (gsonClient == null) {
            gsonClient = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setLenient()
                    .create();
        }

        return gsonClient;
    }
}
