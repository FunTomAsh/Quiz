package com.example.quiz;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class QuestionsBank {

    private static final String FILE_NAME = "quiz.json";


    public static List<QuestionsList> getQuestions(Context context, String selectedTopicName) {
        List<QuestionsList> questionsLists = new ArrayList<>();

        //String json = loadJsonFromAssets( context, "quiz.json");

        Gson gson = new Gson();
        QuizTopic quiz = gson.fromJson(loadJsonFromFile2(context), QuizTopic.class);

        for (Topic topic : quiz.getQuiz()) {
            /*System.out.println(topic);
            System.out.println(selectedTopicName);
            System.out.println(topic.getTemat().getNazwa());
            System.out.println(topic.getTemat().getPytania());*/
            if ((topic.getTemat().getNazwa()).equals(selectedTopicName)) {
                //System.out.println("Po ifie");
                for (Pytania pytania : topic.getTemat().getPytania()) {
                    //System.out.println(pytania.getPytanie().getTresc());
                    List<Odpowiedz> odpowiedzi = pytania.getPytanie().getOdpowiedzi();
                    //System.out.println("Po ifie 2");
                    String[] answersArray = new String[odpowiedzi.size()];
                    for (int i = 0; i < odpowiedzi.size(); i++) {
                        answersArray[i] = odpowiedzi.get(i).getOdp();
                    }

                    QuestionsList questionsList = new QuestionsList(
                            pytania.getPytanie().getTresc(),
                            answersArray[0],
                            answersArray[1],
                            answersArray[2],
                            answersArray[3],
                            pytania.getPytanie().getPoprawnaOdp(),
                            ""
                    );
                    questionsLists.add(questionsList);
                }
                break;
            }
        }

        return questionsLists;
    }

    private static String loadJsonFromAssets(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String loadJsonFromFile2(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fis.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            Log.e("QuestionsBank", "Error reading JSON from file: " + e.getMessage());
            return null;
        }
    }

    /*
    private static List<QuestionsList> topic1Questions(){
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("2+2", "4", "22", "0", "2", "4", "");
        final QuestionsList question2 = new QuestionsList("3*3", "33", "6", "9", "303", "9", "");
        final QuestionsList question3 = new QuestionsList("2+2*2", "8", "6", "24", "10", "6", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }

    private static List<QuestionsList> topic2Questions(){
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("2+2", "4", "22", "0", "2", "4", "");
        final QuestionsList question2 = new QuestionsList("3*3", "33", "6", "9", "303", "9", "");
        final QuestionsList question3 = new QuestionsList("2+2*2", "8", "6", "24", "10", "6", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }

    private static List<QuestionsList> topic3Questions(){
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("2+2", "4", "22", "0", "2", "4", "");
        final QuestionsList question2 = new QuestionsList("3*3", "33", "6", "9", "303", "9", "");
        final QuestionsList question3 = new QuestionsList("2+2*2", "8", "6", "24", "10", "6", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }

    private static List<QuestionsList> topic4Questions(){
        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 = new QuestionsList("2+2", "4", "22", "0", "2", "4", "");
        final QuestionsList question2 = new QuestionsList("3*3", "33", "6", "9", "303", "9", "");
        final QuestionsList question3 = new QuestionsList("2+2*2", "8", "6", "24", "10", "6", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);

        return questionsLists;
    }

    public static List<QuestionsList> getQuestions(String selectedTopicName){
        switch (selectedTopicName){
            case "topic2":
                return topic2Questions();
            case "topic3":
                return topic3Questions();
            case "topic4":
                return topic4Questions();
            default:
                return topic1Questions();
        }
    }*/
}
