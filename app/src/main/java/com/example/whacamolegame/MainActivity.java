package com.example.whacamolegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startButton;
    private Spinner spinner;
    private CheckBox VibrationCheckBox;
    private static TextView RecordScoreTextView;
    private static int recordScore;

    private List<String> difficultiesList = new ArrayList<>();
    public static int difficulty;

    public static Boolean vibration = false;
    private static SharedPreferences myPreferences;
    private static SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        difficultiesList.add("Easy");
        difficultiesList.add("Medium");
        difficultiesList.add("Hard");

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultiesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                switch (item) {
                    case "Easy":
                        difficulty = 100;
                        break;
                    case "Medium":
                        difficulty = 200;
                        break;
                    case "Hard":
                        difficulty = 300;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        VibrationCheckBox = findViewById(R.id.VibrationCheckBox);
        VibrationCheckBox.setOnClickListener(this);

        RecordScoreTextView = findViewById(R.id.RecordScoreTextView);

        recordScore = getRecordScoreEdit();
        RecordScoreTextView.setText("Record score: " + recordScore);

        startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(this);
    }
    public static void setRecordScoreEdit(int score) {
        myEditor.putInt("score", score);
        myEditor.commit();
    }
    public static int getRecordScoreEdit() {
        return myPreferences.getInt("score",0);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.StartButton:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.VibrationCheckBox:
                Thread mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!vibration) {
                            vibration = true;
                        } else if (vibration){
                            vibration = false;
                        }

                    }
                }); mThread.start();
                break;
        }
    }
    public static int getRecordScore() {
        return recordScore;
    }
    public static void setRecordScore(int recordScore) {
        if (recordScore > MainActivity.recordScore) {
            MainActivity.recordScore = recordScore;
            RecordScoreTextView.setText(recordScore);
            setRecordScoreEdit(recordScore);
        }
    }
}