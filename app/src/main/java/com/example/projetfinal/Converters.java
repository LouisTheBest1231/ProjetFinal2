package com.example.projetfinal;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Converters {

    // Used by AlarmEntity Boolean[] mDaysOfWeek

    @TypeConverter
    public static Boolean[] fromString(String value) {
        Type listType = new TypeToken<Boolean[]>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromBoolean(Boolean[] list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}

/*
public class Converters {
    @TypeConverter
    public static Boolean[] fromBool(Boolean bool) {
        Type listType = new TypeToken<Boolean[]>() {}.getType();
        return new Gson().fromJson(String.valueOf(bool), listType);
    }

    @TypeConverter
    public static Boolean fromArray(Boolean[] list) {
        Gson gson = new Gson();
        Boolean json = Boolean.valueOf(gson.toJson(list));
        return json;
    }
}
*/