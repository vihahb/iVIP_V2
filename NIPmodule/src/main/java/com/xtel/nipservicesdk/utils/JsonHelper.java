package com.xtel.nipservicesdk.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Lê Công Long Vũ on 12/27/2016
 */

public class JsonHelper {
    private static Gson gson;

    static {
        gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T getObject(String json, Class<T> clazz) throws NullPointerException {
        if (json == null || json.isEmpty())
            throw new NullPointerException("INPUT IS NULL OR EMPTY");
        else
            return gson.fromJson(json, clazz);
    }

    public static <T> T getObjectNoException(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            Log.e("ex", "null ");
            return null;
        }

        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            Log.e("ex", "e " + e.toString());
            return null;
        }
    }
}