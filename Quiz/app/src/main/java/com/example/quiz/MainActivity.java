package com.example.quiz;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.lang3.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String JSON_URL = "https://raw.githubusercontent.com/TomashKarpei/Quiz/main/quiz.json";
    private static final String FILE_NAME = "quiz.json";
    private String selectedTopicName = "";
    private ImageView imageViewMat;
    private ImageView imageViewChem;
    private ImageView imageViewHtml;
    private ImageView imageViewJava;
    private String curTopic;

    private String file_n = "";

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewMat = findViewById(R.id.topic1Image);
        imageViewChem = findViewById(R.id.topic2Image);
        imageViewHtml = findViewById(R.id.topic3Image);
        imageViewJava = findViewById(R.id.topic4Image);
        new DownloadJsonTask().execute(JSON_URL);

        /*String json = loadJsonFromAssets( MainActivity.this, "quiz.json");

        Gson gson = new Gson();
        QuizTopic quiz = gson.fromJson(json, QuizTopic.class);

        for (Topic topic : quiz.getQuiz()) {
            System.out.println(topic.getTemat().getZdjecie());
            System.out.println(topic.getTemat().getNazwa());
            //new DownloadImage(url, imageView).execute();
            if (topic.getTemat().getNazwa().equals("Matematyka")){
                //imageViewMat.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewMat).execute();
            }
            else if(topic.getTemat().getNazwa().equals("Chemia")){
                //imageViewChem.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewChem).execute();
            }
            else if(topic.getTemat().getNazwa().equals("HTML")){
                //imageViewHtml.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewHtml).execute();
            }
            else{
                //imageViewJava.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewJava).execute();
            }

        }*/


        final LinearLayout topic1 = findViewById(R.id.topic1Layout);
        final LinearLayout topic2 = findViewById(R.id.topic2Layout);
        final LinearLayout topic3 = findViewById(R.id.topic3Layout);
        final LinearLayout topic4 = findViewById(R.id.topic4Layout);

        final Button startBtn = findViewById(R.id.startQuizBtn);

        topic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedTopicName = "Matematyka";

                topic1.setBackgroundResource(R.drawable.round_back_white_stroke10);

                topic2.setBackgroundResource(R.drawable.round_back_white10);
                topic3.setBackgroundResource(R.drawable.round_back_white10);
                topic4.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        topic2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedTopicName = "Chemia";

                topic2.setBackgroundResource(R.drawable.round_back_white_stroke10);

                topic1.setBackgroundResource(R.drawable.round_back_white10);
                topic3.setBackgroundResource(R.drawable.round_back_white10);
                topic4.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        topic3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedTopicName = "HTML";

                topic3.setBackgroundResource(R.drawable.round_back_white_stroke10);

                topic1.setBackgroundResource(R.drawable.round_back_white10);
                topic2.setBackgroundResource(R.drawable.round_back_white10);
                topic4.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        topic4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectedTopicName = "Java";

                topic4.setBackgroundResource(R.drawable.round_back_white_stroke10);

                topic1.setBackgroundResource(R.drawable.round_back_white10);
                topic2.setBackgroundResource(R.drawable.round_back_white10);
                topic3.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selectedTopicName.isEmpty()){
                    Toast.makeText(MainActivity.this, "Select the topic", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("selectedTopic", selectedTopicName);
                    startActivity(intent);
                }
            }
        });
    }

    /*protected void OnStart(){
        super.onStart();
        Gson gson = new Gson();
        System.out.println("DZIALA");
        QuizTopic quiz = gson.fromJson(readJsonFromFile(), QuizTopic.class);
        for (Topic topic : quiz.getQuiz()) {
            System.out.println(topic.getTemat().getZdjecie());
            System.out.println(topic.getTemat().getNazwa());
            //new DownloadImage(url, imageView).execute();
            if (topic.getTemat().getNazwa().equals("Matematyka")){
                //new DownloadImage().execute(topic.getTemat().getZdjecie());
                loadImageBitmap(MainActivity.this, "mat.png",imageViewMat);
                //imageViewMat.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewMat).execute();
            }
            else if(topic.getTemat().getNazwa().equals("Chemia")){
                new DownloadImage().execute(topic.getTemat().getZdjecie());
                loadImageBitmap(MainActivity.this, "chem.png", imageViewChem);
                //imageViewChem.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewChem).execute();
            }
            else if(topic.getTemat().getNazwa().equals("HTML")){
                new DownloadImage().execute(topic.getTemat().getZdjecie());
                loadImageBitmap(MainActivity.this, "html.png", imageViewHtml);
                //imageViewHtml.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewHtml).execute();
            }
            else{
                new DownloadImage().execute(topic.getTemat().getZdjecie());
                loadImageBitmap(MainActivity.this, "java.png", imageViewJava);
                //imageViewJava.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewJava).execute();
            }

        }
    }*/
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



    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    /*public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }*/

    private class DownloadJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    json = stringBuilder.toString();

                    // Save the JSON data to a file
                    saveJsonToFile(json);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error downloading JSON: " + e.getMessage());
            }
            return json;
        }

        private void saveJsonToFile(String json) {
            try {
                FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                fos.write(json.getBytes());
                fos.close();
                System.out.println("Udalo sie zapisac");
            } catch (IOException e) {
                Log.e(TAG, "Error saving JSON to file: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            handleJsonData(result);
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG2 = "DownloadImage";
        private String topImg = "";
        private ImageView imageView;
        private Bitmap downloadImageBitmap(String sUrl, String topic) {
            Bitmap bitmap = null;

            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);// Decode Bitmap
                topImg=StringUtils.substringAfterLast(sUrl, '/');
                if (topic.equals("Matematyka")){
                    imageView = imageViewMat;
                }
                else if(topic.equals("Chemia")){
                    imageView = imageViewChem;
                }
                else if (topic.equals("HTML")){
                    imageView = imageViewHtml;
                }
                else {
                    imageView = imageViewJava;
                }
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG2, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0], params[1]);
        }

        protected void onPostExecute(Bitmap result) {
            String img = topImg;
            saveImage(getApplicationContext(), result, img);
            loadImageBitmap(MainActivity.this, img, imageView);
        }

        public void saveImage(Context context, Bitmap b, String imageName) {
            FileOutputStream foStream;
            try {
                foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
                b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                System.out.println("Zapisano: " + imageName);
                foStream.close();
            } catch (Exception e) {
                Log.d("saveImage", "Exception 2, Something went wrong!");
                e.printStackTrace();
            }
        }

    }

    public Bitmap loadImageBitmap(Context context, String imageName, ImageView imageView) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            imageView.setImageBitmap(bitmap);
            System.out.println("Uzyte: " + imageName);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
    private void handleJsonData(String json) {

        Gson gson = new Gson();
        QuizTopic quiz = gson.fromJson(readJsonFromFile(), QuizTopic.class);

        for (Topic topic : quiz.getQuiz()) {
            System.out.println(topic.getTemat().getZdjecie());
            System.out.println(topic.getTemat().getNazwa());
            //new DownloadImage(url, imageView).execute();
            if (topic.getTemat().getNazwa().equals("Matematyka")){
                new DownloadImage().execute(topic.getTemat().getZdjecie(), topic.getTemat().getNazwa());
                loadImageBitmap(MainActivity.this, StringUtils.substringAfterLast(topic.getTemat().getZdjecie(), '/'),imageViewMat);
                //imageViewMat.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewMat).execute();
            }
            else if(topic.getTemat().getNazwa().equals("Chemia")){
                new DownloadImage().execute(topic.getTemat().getZdjecie(), topic.getTemat().getNazwa());
                loadImageBitmap(MainActivity.this,  StringUtils.substringAfterLast(topic.getTemat().getZdjecie(), '/'),imageViewChem);
                //imageViewChem.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewChem).execute();
            }
            else if(topic.getTemat().getNazwa().equals("HTML")){
                new DownloadImage().execute(topic.getTemat().getZdjecie(), topic.getTemat().getNazwa());
                loadImageBitmap(MainActivity.this, StringUtils.substringAfterLast(topic.getTemat().getZdjecie(), '/'), imageViewHtml);
                //imageViewHtml.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewHtml).execute();
            }
            else{
                new DownloadImage().execute(topic.getTemat().getZdjecie(), topic.getTemat().getNazwa());
                loadImageBitmap(MainActivity.this, StringUtils.substringAfterLast(topic.getTemat().getZdjecie(), '/'),imageViewJava);
                //imageViewJava.setImageBitmap(getBitmapFromURL(topic.getTemat().getZdjecie()));
                //new ImageLoadTask(topic.getTemat().getZdjecie(), imageViewJava).execute();
            }

        }
    }

    private String readJsonFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
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
            Log.e(TAG, "Error reading JSON from file: " + e.getMessage());
            return null;
        }
    }
}