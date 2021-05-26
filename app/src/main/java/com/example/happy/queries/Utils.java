package com.example.happy.queries;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Result <currentClass> implements Callable<List<currentClass>> {

    private Class classType;
    private Activity currentActivity;
    private String firstStatement;
    private String columns;
    private String table;
    private String secondStatement;
    private String condition;

    // Using a constructor is the only way to pass arguments to this class
    public Result(Class classType, Activity currentActivity, String statement, String columns, String table, String whereStatement, String condition) {
        this.classType = classType;
        this.currentActivity = currentActivity;
        this.firstStatement = statement;
        this.columns = columns;
        this.table = table;
        this.secondStatement = whereStatement;
        this.condition = condition;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<currentClass> call() throws Exception {
        List<currentClass> info = new ArrayList<currentClass>();
        try{
            URL url = null;
            try {
                url = new URL("http://elara.science.ru.nl/MeaningfulMovies3.4/query");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //GsonBuilder gb = new GsonBuilder();
            //gb.disableHtmlEscaping();
            //String jsonWhatever = gb.create().toJson(query, QueryObject.class);
            String jsonInputString = Utils.getQueryString(
                    firstStatement,
                    columns,
                    table,
                    secondStatement,
                    condition
            );

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {

                final StringBuilder response = new StringBuilder();
                String responseLine = null;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                GsonBuilder gb = new GsonBuilder();
                Type listType = TypeToken.getParameterized(ArrayList.class, classType).getType();
                //Type listType = new TypeToken<ArrayList<currentClass>>(){}.getType();
                info = gb.create().fromJson(response.toString(), listType);

            }

        } catch(final Exception e) {
            currentActivity.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(currentActivity, e.toString(), Toast.LENGTH_LONG).show();
                        }});
        }
        // return of the call function
        return info;
    }
}

public class Utils {

    public static String getQueryString(String firstStatement, String columns, String table, String secondStatement, String condition){
        return String.format("{\"firstStatement\":\"%s\", \"columns\":\"%s\", \"table\":\"%s\"," +
                        "\"secondStatement\":\"%s\", \"condition\":\"%s\"}",
                firstStatement,
                columns,
                table,
                secondStatement,
                condition
        );
    }

    public static <currentClass> List<currentClass> executeQuery(Class<currentClass> currentClass,
                                                                 Activity currentActivity,
                                                                 String firstStatement,
                                                                 String columns,
                                                                 String table,
                                                                 String secondStatement,
                                                                 String condition
    ) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();
        // this line runs the call() function of class Result in a separate Thread
        Future<List<currentClass>> futureCall = executor.submit(new Result(
                currentClass,
                currentActivity,
                firstStatement,
                columns,
                table,
                secondStatement,
                condition
        ));
        return futureCall.get(); // Here the thread will be blocked until it gets the result
    }

    public static ArrayList<String> getIconFilenameFromUserRatings(UserRatings userRating){

        String characterStrengths = "creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality";

        String [] cSValues = userRating.getAll();
        String [] cSNames = characterStrengths.split(",");
        ArrayList<String> iconFilenames = new ArrayList<>();

        for (int i=0; i < cSValues.length; i++){
            if (Integer.parseInt(cSValues[i]) > 0){
                iconFilenames.add(cSNames[i] + ".png");
            }
        }
        return iconFilenames;
    }

    public static ArrayList<String> getIconFilenameFromMovieRatings(MovieRatings movieRatings){

        String characterStrengths = "creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality";

        String [] cSValues = movieRatings.getAll();
        String [] cSNames = characterStrengths.split(",");
        ArrayList<String> iconFilenames = new ArrayList<>();

        // this takes the character strength, sorts them by their values but returns the indexes
        // so that we can later get the names
        ArrayIndexComparator comparator = new ArrayIndexComparator(cSValues);
        Integer[] indexes = comparator.createIndexArray();
        Arrays.sort(indexes, comparator);

        for (int idx: indexes){
            iconFilenames.add(cSNames[idx] + ".png");
        }

        return iconFilenames;
    }


    public static class ArrayIndexComparator implements Comparator<Integer>
    {
        private final String[] array;

        public ArrayIndexComparator(String[] array)
        {
            this.array = array;
        }

        public Integer[] createIndexArray()
        {
            Integer[] indexes = new Integer[array.length];
            for (int i = 0; i < array.length; i++)
            {
                indexes[i] = i; // Autoboxing
            }
            return indexes;
        }

        @Override
        public int compare(Integer index1, Integer index2)
        {
            // Autounbox from Integer to int to use as array indexes
            return array[index1].compareTo(array[index2]);
        }
    }

}
