package com.example.idarkduck.shopify_backend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswersActivity extends AppCompatActivity {

    ResponseJson responseJson;
    TextView answer;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        responseJson = responseJson.getInstance();
        back = findViewById(R.id.back);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, " Project completed by" +'\n' +
                        " Nevin WS Ganesan & Nicholas Broadbent" +'\n'
                       // "nevinganesan@hotmail.com & nbroa025@uottawa.ca"
                        , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        answer = (TextView) findViewById(R.id.answer);
        System.out.println("response: " + responseJson.getResponse());
        answer.setText(responseJson.getResponse());
    }
}
