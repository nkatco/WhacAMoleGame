package com.example.whacamolegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView EndTextView, EndScoreTextView;
    private Button RestartButton, MenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        EndTextView = findViewById(R.id.EndTextView);
        EndScoreTextView = findViewById(R.id.EndScoreTextView);
        RestartButton = findViewById(R.id.RestartButton);
        MenuButton = findViewById(R.id.MenuButton);

        EndTextView.setText("Record Score: " + MainActivity.getRecordScore());
        EndScoreTextView.setText("Score: " + GameActivity.getScore());
        RestartButton.setOnClickListener(this);
        MenuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RestartButton:
                Intent gameIntent = new Intent(this, GameActivity.class);
                startActivity(gameIntent);
                GameActivity.resetScore();
                break;
            case R.id.MenuButton:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}