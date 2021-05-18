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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Result <currentClass> implements Callable<List<currentClass>> {

    private Class classType;
    private Activity currentActivity;
    private String columns;
    private String table;
    private String whereStatement;
    private String condition;

    public Result(Class classType, Activity currentActivity, String columns, String table, String whereStatement, String condition) {
        this.classType = classType;
        this.currentActivity = currentActivity;
        this.columns = columns;
        this.table = table;
        this.whereStatement = whereStatement;
        this.condition = condition;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<currentClass> call() throws Exception {
        List<currentClass> info = new ArrayList<currentClass>();
        try{
            URL url = null;
            try {
                url = new URL("http://elara.science.ru.nl/MeaningfulMovies1.64/query");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GsonBuilder gb = new GsonBuilder();
            //gb.disableHtmlEscaping();
            //String jsonWhatever = gb.create().toJson(query, QueryObject.class);

            String jsonInputString = Utils.getQueryString(
                    columns,
                    table,
                    whereStatement,
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
                //System.out.println(response.toString());

                Type listType = TypeToken.getParameterized(ArrayList.class, classType).getType();
                //Type listType = new TypeToken<ArrayList<currentClass>>(){}.getType();
                info = gb.create().fromJson(response.toString(), listType);

            }

        }catch(final Exception e){
            currentActivity.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(currentActivity, e.toString(), Toast.LENGTH_LONG).show();
                        }});

        }
        return info;
    }
}

public class Utils {

    public static String getQueryString(String columns, String table, String whereStatement, String condition){
        return String.format("{\"columns\":\"%s\", \"table\":\"%s\"," +
                        "\"whereStatement\":\"%s\", \"condition\":\"%s\"}",
                columns,
                table,
                whereStatement,
                condition
        );
    }

    public static <currentClass> List<currentClass> executeQuery(Class<currentClass> currentClass,
                                                                 Activity currentActivity,
                                                                 String columns,
                                                                 String table,
                                                                 String whereStatement,
                                                                 String condition) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<currentClass>> futureCall = executor.submit(new Result(
                currentClass,
                currentActivity,
                columns,
                table,
                whereStatement,
                condition
        ));
        return futureCall.get(); // Here the thread will be blocked
        // until the result came back.
    }



}
