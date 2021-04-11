package com.example.sit305task31c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextUserName;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextUserName = (EditText) findViewById(R.id.et_username);
        mStartButton = (Button) findViewById(R.id.btn_start);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextUserName.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("name", mEditTextUserName.getText().toString());
                    intent.putExtra("quizNum", 0);
                    intent.putExtra("score", 0);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
}