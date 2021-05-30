package com.example.happy.queries;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.happy.R;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
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
                url = new URL("http://elara.science.ru.nl/MeaningfulMovies4.0/query");
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

                String responseToConvert = response.toString().replace("\"\"", "\"");

                GsonBuilder gb = new GsonBuilder();
                Type listType = TypeToken.getParameterized(ArrayList.class, classType).getType();
                info = gb.create().fromJson(responseToConvert, listType);

            }

        } catch(final Exception e) {
            currentActivity.runOnUiThread(
                    () -> Toast.makeText(currentActivity, e.toString(), Toast.LENGTH_LONG).show());
        }
        // return of the call function
        return info;
    }
}

public class Utils {

    public static boolean connectedInternet(Context context){
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            Log.e("isInternetAvailable:",e.toString());
            return false;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

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

    public static ArrayList<String> getCSFromUserRatings(UserRatings userRating){

        String characterStrengths = "creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality";

        String [] cSValues = userRating.getAll();
        String [] cSNames = characterStrengths.split(",");
        ArrayList<String> cs = new ArrayList<>();

        for (int i=0; i < cSValues.length; i++){
            if (Integer.parseInt(cSValues[i]) > 0){
                cs.add(cSNames[i]);
            }
        }
        return cs;
    }

    public static ArrayList<String> getOrderedCSFromMovieRatings(MovieRatings movieRatings){

        String characterStrengths = "creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality";

        String [] cSValues = movieRatings.getAll();
        String [] cSNames = characterStrengths.split(",");
        ArrayList<String> orderedCS = new ArrayList<>();

        // this takes the character strength, sorts them by their values but returns the indexes
        // so that we can later get the names
        ArrayIndexComparator comparator = new ArrayIndexComparator(cSValues);
        Integer[] indexes = comparator.createIndexArray();
        Arrays.sort(indexes, comparator);

        for (int idx: indexes){
            if(Integer.parseInt(cSValues[idx]) > 0) {
                orderedCS.add(cSNames[idx]);
            }
        }

        return orderedCS;
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
            return array[index2].compareTo(array[index1]);
        }
    }

    public static int getCSResourceId(String cs) {
        String characterStrengths = "creativity," +
                "curiosity, judgement, love_of_learning, perspective, bravery, honesty, zest," +
                "perseverance, love, kindness, social_intelligence, teamwork, fairness, leadership," +
                "forgiveness, humility, prudence, self_regulation, appreciation_beauty_excellence," +
                "gratitude, hope, humor, spirituality";

        String[] chStrs = characterStrengths.split(",");
        int resourceId = 0;

        if (cs.equals(chStrs[0])){
            resourceId = R.drawable.creativity;
        }else if (cs.equals(chStrs[1])) {
            resourceId = R.drawable.curiosity;
        }else if (cs.equals(chStrs[2])) {
            resourceId = R.drawable.judgement;
        }else if (cs.equals(chStrs[3])) {
            resourceId = R.drawable.love_of_learning;
        }else if (cs.equals(chStrs[4])) {
            resourceId = R.drawable.perspective;
        }else if (cs.equals(chStrs[5])) {
            resourceId = R.drawable.bravery;
        }else if (cs.equals(chStrs[6])) {
            resourceId = R.drawable.honesty;
        }else if (cs.equals(chStrs[7])) {
            resourceId = R.drawable.zest;
        }else if (cs.equals(chStrs[8])) {
            resourceId = R.drawable.perseverance;
        }else if (cs.equals(chStrs[9])) {
            resourceId = R.drawable.love;
        }else if (cs.equals(chStrs[10])) {
            resourceId = R.drawable.kindness;
        }else if (cs.equals(chStrs[11])) {
            resourceId = R.drawable.social_intelligence;
        }else if (cs.equals(chStrs[12])) {
            resourceId = R.drawable.teamwork;
        }else if (cs.equals(chStrs[13])) {
            resourceId = R.drawable.fairness;
        }else if (cs.equals(chStrs[14])) {
            resourceId = R.drawable.leadership;
        }else if (cs.equals(chStrs[15])) {
            resourceId = R.drawable.forgiveness;
        }else if (cs.equals(chStrs[16])) {
            resourceId = R.drawable.humility;
        }else if (cs.equals(chStrs[17])) {
            resourceId = R.drawable.prudence;
        }else if (cs.equals(chStrs[18])) {
            resourceId = R.drawable.self_regulation;
        }else if (cs.equals(chStrs[19])) {
            resourceId = R.drawable.appreciation_of_beauty_and_excellence_;
        }else if (cs.equals(chStrs[20])) {
            resourceId = R.drawable.gratitude;
        }else if (cs.equals(chStrs[21])) {
            resourceId = R.drawable.hope;
        }else if (cs.equals(chStrs[22])) {
            resourceId = R.drawable.humor;
        }else if (cs.equals(chStrs[23])) {
            resourceId = R.drawable.spirituality;
        }
        return resourceId;
    }
}
