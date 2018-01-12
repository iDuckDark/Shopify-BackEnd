package com.example.idarkduck.shopify_backend;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements Runnable {
    private static int state = 1;

    OkHttpClient client;
    ResponseJson responseJson;

    String address = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=";

    ListView listView;
    TextView loadingTextView;
    Button viewAnswers;
    Switch aSwitch;

    ArrayList<String> id;
    ArrayList<Menu> menus;
    MenuAdapter menuAdapter;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client =  new OkHttpClient();
        responseJson = responseJson.getInstance();
        responseJson.clear();

        viewAnswers = findViewById(R.id.view_answers);

        loadingTextView = (TextView) findViewById(R.id.loadingTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        aSwitch = findViewById(R.id.challenge);

        if (state == 2) {
            aSwitch.setChecked(true);
            address = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=" + state + "&page=";
        }

        final DownloadFilesTask download = new DownloadFilesTask();
        download.execute(address);

        id = new ArrayList<>();

        viewAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent results = new Intent(getApplicationContext(), AnswersActivity.class);
                startActivity(results);
                finish();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // /*
                if(aSwitch.isChecked())
                    state = 2;
                else
                    state = 1;

                address = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=" + state +"&page=";

                responseJson.clear();
                menus = new ArrayList<>();


                Runnable r = new DownloadFilesTask();
                Thread t = new Thread(r);
                t.start();

                try {
                    t.join();
                } catch (Exception e) {
                    System.out.println(e);
                }

                updateMenuAdapter();
              //  */
            }
        });
    }

    // Validates graph on a thread.
    public void run() {

    }

    public void updateMenuAdapter(){

        runOnUiThread(new Runnable(){
            public void run(){

                menuAdapter.notifyDataSetChanged();
                menuAdapter = new MenuAdapter(getApplicationContext(), menus);

                listView = (ListView) findViewById(R.id.lstItems);
                listView.setAdapter(menuAdapter);
            }
        });
    }

    private void setMenuAdapter() {
        //init adapters
        menuAdapter = new MenuAdapter(this, menus);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.lstItems);
        listView.setAdapter(menuAdapter);
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
                int id = o.getInt("id");
                String data = o.getString("data");
                int parentID = -1;

                if (o.length() == 4)
                    parentID = o.getInt("parent_id");

                ArrayList<Integer> childList = new ArrayList<>();

                // Get ids.
                JSONArray arrChildJson = o.getJSONArray("child_ids");

                // Parse child id data.
                if (arrChildJson != null) {
                    String[] arrID = parseJsonArray(arrChildJson);

                    for (int j = 0; j < arrID.length; j++) {
                        if (!arrID[j].isEmpty())
                            childList.add(Integer.parseInt(arrID[j]));
                    }
                }

                // Update menu.
                Menu newMenu = new Menu(id, data, parentID, childList);
                menus.add(newMenu);
            }
        }
        catch(JSONException e){
            System.out.println(e);
        }
    }

    private String[] parseJsonArray(JSONArray jsonArray) {
        String arrBefore = jsonArray.toString();
        return arrBefore.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
    }

    private void validateGraphs() {
        ArrayList<Integer> roots = new ArrayList<>();

        // Find all roots.
        for (int i = 0; i < menus.size(); i++) {
            // Check if root.
            if (menus.get(i).getParentID() == -1)
                roots.add(i);
        }

        Thread[] threads = new Thread[roots.size()];

        // Validate each graph of the root.
        for (int i = 0; i < roots.size(); i++) {
            // https://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread
            Runnable r = new ValidateGraphTask(roots.get(i));
            threads[i] = new Thread(r);
            threads[i].start();
        }

        // Wait for workers to finish.
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        // Results ready.
        viewAnswers.setVisibility(View.VISIBLE);
    }

    // Executes worker thread to validate a graph.
    private class ValidateGraphTask implements Runnable {
        ResponseJson responseJson;
        private int key = 0;

        public ValidateGraphTask(int key) {
            this.key = key;
            responseJson = responseJson.getInstance();
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            ArrayList<Menu> menu = new ArrayList<>();
            ArrayList<Integer> childList = menus.get(key).getChildIDList();
            Stack<Integer> itemsStack = new Stack<>();
            int[] visited = new int[menus.size()];
            boolean invalid = false;

            if (childList != null) {
                int currentID = key;
                itemsStack.push(currentID);

                // Check children
                do {
                    currentID = itemsStack.pop();
                    menu.add(menus.get(currentID));

                    // Check if child was previously visited.
                    if (visited[currentID] > 0) {
                        // Invalid.
                        invalid = true;
                    }
                    else {
                        // Push all children.
                        childList = menus.get(currentID).childIDList;
                        for (int i = 0; i < childList.size(); i++) {
                            itemsStack.push(childList.get(i) - 1);
                        }
                    }

                    visited[currentID]++;
                } while (!itemsStack.empty());
                // Add menu to json.
                if (invalid)
                    responseJson.addInvalidMenu(menu);
                else
                    responseJson.addValidMenu(menu);
            }
        }
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> implements Runnable {

        ArrayList<String> bodyResponse = new ArrayList<>();

        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            doInBackground();
            menus = new ArrayList<Menu>();

            for (int i = 0; i < bodyResponse.size(); i++) {
                readJson(bodyResponse.get(i));
            }

            validateGraphs();
        }

        protected String doInBackground(String... urls) {
            ArrayList<Downloader> downloaders = new ArrayList<>();

            // Download pages.
            for (int i = 1; i < 6; i++) {
                downloaders.add(new Downloader(address + i));
            }

            //added for progress bar
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
            }

            // Get response.
            for (int i = 0; i < downloaders.size(); i++) {
                bodyResponse.add(downloaders.get(i).getBodyRespose());
            }

            return bodyResponse.get(0);
        }

        //Maybe need to be implemented to avoid crashes
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressBar.setProgress(progress[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //showDialog("Downloaded " + result + " bytes");
            if (result != null) {
                menus = new ArrayList<Menu>();

                for (int i = 0; i < bodyResponse.size(); i++) {
                    readJson(bodyResponse.get(i));
                }

                validateGraphs();

                progressBar.setVisibility(View.GONE);
                loadingTextView.setVisibility(View.GONE);
                //allItems.setMovementMethod(new ScrollingMovementMethod());
                setMenuAdapter();
            }
        }

        //not needed actually but who knows
        protected void onPreExcecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }
    }
}


