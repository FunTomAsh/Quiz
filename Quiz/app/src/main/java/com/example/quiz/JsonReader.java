package com.example.quiz;

import com.google.gson.Gson;
public class JsonReader {

    public static QuizTopic parseJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, QuizTopic.class);
    }
}
