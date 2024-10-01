package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    private TextView questions;
    private TextView question;
    private AppCompatButton answer1, answer2, answer3, answer4;
    private AppCompatButton nextBtn;
    private Timer quizTimer;
    private int totalTimeInMin = 3;
    private int seconds = 0;

    private List<QuestionsList> questionsList;

    private int currentQuestionPosition = 0;

    private String selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView timer = findViewById(R.id.timer);
        final TextView selectedTopicName = findViewById(R.id.topicName);

        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        nextBtn = findViewById(R.id.nextBtn);

        final String getSelectedTopicName = getIntent().getStringExtra("selectedTopic");

        selectedTopicName.setText(getSelectedTopicName);

        questionsList = QuestionsBank.getQuestions(getApplicationContext(), getSelectedTopicName);

        startTimer(timer);

        questions.setText((currentQuestionPosition+1)+"/"+questionsList.size());
        question.setText(questionsList.get(0).getQuestion());
        answer1.setText(questionsList.get(0).getAnswer1());
        answer2.setText(questionsList.get(0).getAnswer2());
        answer3.setText(questionsList.get(0).getAnswer3());
        answer4.setText(questionsList.get(0).getAnswer4());

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = answer1.getText().toString();

                    answer1.setBackgroundResource(R.drawable.round_back_red10);
                    answer1.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = answer2.getText().toString();

                    answer2.setBackgroundResource(R.drawable.round_back_red10);
                    answer2.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = answer3.getText().toString();

                    answer3.setBackgroundResource(R.drawable.round_back_red10);
                    answer3.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = answer4.getText().toString();

                    answer4.setBackgroundResource(R.drawable.round_back_red10);
                    answer4.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selectedOptionByUser.isEmpty()){
                    Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else{
                    changeNextQuestion();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void changeNextQuestion(){
        currentQuestionPosition++;

        if((currentQuestionPosition+1) == questionsList.size()){
            nextBtn.setText("Submit Quiz");
        }

        if(currentQuestionPosition < questionsList.size()){
            selectedOptionByUser = "";

            answer1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            answer1.setTextColor(Color.parseColor("#1F6BB8"));

            answer2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            answer2.setTextColor(Color.parseColor("#1F6BB8"));

            answer3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            answer3.setTextColor(Color.parseColor("#1F6BB8"));

            answer4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            answer4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition+1)+"/"+questionsList.size());
            question.setText(questionsList.get(currentQuestionPosition).getQuestion());
            answer1.setText(questionsList.get(currentQuestionPosition).getAnswer1());
            answer2.setText(questionsList.get(currentQuestionPosition).getAnswer2());
            answer3.setText(questionsList.get(currentQuestionPosition).getAnswer3());
            answer4.setText(questionsList.get(currentQuestionPosition).getAnswer4());

        }
        else{
            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getIncorrectAnswers());
            startActivity(intent);

            finish();
        }


    }
    private void startTimer (TextView timerTextView){
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                if (seconds == 0){
                    totalTimeInMin--;
                    seconds = 59;
                }
                else if (seconds == 0 && totalTimeInMin == 0){
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(QuizActivity.this, "Time over", Toast.LENGTH_SHORT). show();

                    Intent intent = new Intent(QuizActivity.this, QuizResults.class);

                    intent.putExtra("correct", getCorrectAnswers());
                    intent.putExtra("incorrect", getIncorrectAnswers());
                    startActivity(intent);

                    finish();
                }
                else {
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        String finalMinutes = String.valueOf(totalTimeInMin);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1){
                            finalMinutes = "0"+finalMinutes;
                        }

                        if(finalSeconds.length() == 1){
                            finalSeconds = "0"+finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });

            }
        }, 1000, 1000);
    }

    private int getCorrectAnswers(){

        int correctAnswers = 0;

        for(int i = 0; i < questionsList.size();i++){
            final String getUserSelectedAnswer = questionsList.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsList.get(i).getAnswer();

            if (getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private int getIncorrectAnswers(){

        int correctAnswers = 0;

        for(int i = 0; i < questionsList.size();i++){
            final String getUserSelectedAnswer = questionsList.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsList.get(i).getAnswer();

            if (!getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed(){
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish();
    }

    private void revealAnswer(){
        final String getAnswer = questionsList.get(currentQuestionPosition).getAnswer();

        if (answer1.getText().toString().equals(getAnswer)){
            answer1.setBackgroundResource(R.drawable.round_back_green10);
            answer1.setTextColor(Color.WHITE);
        }
        else if(answer2.getText().toString().equals(getAnswer)){
            answer2.setBackgroundResource(R.drawable.round_back_green10);
            answer2.setTextColor(Color.WHITE);
        }
        else if(answer3.getText().toString().equals(getAnswer)){
            answer3.setBackgroundResource(R.drawable.round_back_green10);
            answer3.setTextColor(Color.WHITE);
        }
        else if(answer4.getText().toString().equals(getAnswer)){
            answer4.setBackgroundResource(R.drawable.round_back_green10);
            answer4.setTextColor(Color.WHITE);
        }
    }
}








