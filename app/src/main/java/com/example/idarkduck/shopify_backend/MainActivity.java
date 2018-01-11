package com.example.idarkduck.shopify_backend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    String bodyResponse1;
    String bodyResponse2;
    String bodyResponse3;

    String address1 = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=1";
    String address2 = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=2";
    String address3 = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=3";


    OkHttpClient client;

    TextView test;

    ArrayList<String> id;
    ArrayList<Menu> menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bodyResponse1="";
        client =  new OkHttpClient();
        test = (TextView) findViewById(R.id.textView);

        DownloadFilesTask download = new DownloadFilesTask();
        download.execute(address1);

        id = new ArrayList<>();
    }

    private void find(){

    }


    //https://stackoverflow.com/questions/7646392/convert-string-to-int-array-in-java
    private void readJson(String bodyResponse) {
        try {
            JSONObject jsonObj = new JSONObject(bodyResponse);

            JSONArray arr = jsonObj.getJSONArray("menus");


            for (int i = 0; i < arr.length(); i++) {
                //create entire object of JSON file at index i
                JSONObject o = arr.getJSONObject(i);
                //find the keys associated
                String ID = o.getString("id");
                String data = o.getString("data");

                // Get ids.
                JSONArray arrParentJson = o.getJSONArray("child_ids");
                JSONArray arrChildJson = o.getJSONArray("child_ids");

                // Parse parent id data.
                String[] arrID = parseJsonArray(arrParentJson);
                ArrayList<String> parentList = new ArrayList<>();

                System.out.println("hi");
                System.out.println(arrID.length);


                for(int j = 0; j < arrID.length; j++){
                    parentList.add(arrID[j]);
                }

                System.out.println(arrID.toString());


                // Parse child id data.
                arrID = parseJsonArray(arrChildJson);


                ArrayList<String> childList = new ArrayList<>();
                for(int j = 0; j < arrID.length; j++){
                    childList.add(arrID[j]);
                }

                System.out.println(parentList.toString());
                System.out.println(childList.toString());

                // Update menu.
                Menu newMenu = new Menu(ID , data, childList, parentList);
                menus.add(newMenu);
            }
        }
        catch(JSONException e){
            test.setText("Failed: "+ e);
        }
    }

    private String[] parseJsonArray(JSONArray jsonArray) {
        String arrBefore = jsonArray.toString();
        return arrBefore.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            Downloader d1 = new Downloader(address1);
            Downloader d2 = new Downloader(address2);
            Downloader d3 = new Downloader(address3);

            //added for progress bar
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
            }
            //
            bodyResponse1 = d1.getBodyRespose();
            bodyResponse2 = d2.getBodyRespose();
            bodyResponse3 = d3.getBodyRespose();

            return d1.getBodyRespose();
        }

        //Maybe need to be implemented to avoid crashes
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            //test.setText(" Loading ... ");
            //progressBar.setProgress(progress[0]);
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //showDialog("Downloaded " + result + " bytes");
            if (result != null) {
                menus = new ArrayList<Menu>();
                readJson(bodyResponse1);
                readJson(bodyResponse2);
                readJson(bodyResponse3);
                test.setText(menus.toString());


            }
        }

        //not needed actually but who knows
        protected void onPreExcecute() {
            super.onPreExecute();
            //progressBar.setMax(100);
        }
    }
}


