package com.example.sit305task31c;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private TextView mWelcome, mQuizNum, mQuizTitle, mQuizDetail;
    private Button mAnswer1Button, mAnswer2Button, mAnswer3Button, mSubmitButton, mNextButton;
    private ProgressBar mPbQuizNum;
    private Integer quizNum;
    private Integer score;
    private Integer selectedAnswer = null;
    private Integer correctAnswer = 0;
    private Integer maxNum = 5;
    private Button[] mAnswerButtons;
    String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mWelcome = (TextView) findViewById(R.id.tv_welcome);
        mQuizNum = (TextView) findViewById(R.id.tv_quiznum);
        mQuizTitle = (TextView) findViewById(R.id.tv_quiztitle);
        mQuizDetail = (TextView) findViewById(R.id.tv_quizdetail);
        mAnswer1Button = (Button) findViewById(R.id.btn_answer1);
        mAnswer2Button = (Button) findViewById(R.id.btn_answer2);
        mAnswer3Button = (Button) findViewById(R.id.btn_answer3);
        mSubmitButton = (Button) findViewById(R.id.btn_submit);
        mNextButton = (Button) findViewById(R.id.btn_next);
        mPbQuizNum = (ProgressBar) findViewById(R.id.pb_quiznum);
        mAnswerButtons = new Button[]{mAnswer1Button, mAnswer2Button, mAnswer3Button};

        Intent intent = getIntent();
        quizNum = intent.getIntExtra("quizNum", 0);
        score = intent.getIntExtra("score", 0);
        name = intent.getStringExtra("name");
        mWelcome.setText("Welcome " + name + "!");

        displayQuizInfo();

        //answer1
        mAnswer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer1Button.setBackgroundColor(getResources().getColor(R.color.teal_200));
                mAnswer2Button.setBackgroundColor(Color.WHITE);
                mAnswer3Button.setBackgroundColor(Color.WHITE);
                selectedAnswer = 0;
            }
        });

        //answer2
        mAnswer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer2Button.setBackgroundColor(getResources().getColor(R.color.teal_200));
                mAnswer1Button.setBackgroundColor(Color.WHITE);
                mAnswer3Button.setBackgroundColor(Color.WHITE);
                selectedAnswer = 1;
            }
        });

        //answer3
        mAnswer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer3Button.setBackgroundColor(getResources().getColor(R.color.teal_200));
                mAnswer1Button.setBackgroundColor(Color.WHITE);
                mAnswer2Button.setBackgroundColor(Color.WHITE);
                selectedAnswer = 2;
            }
        });

        //submit
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAnswer != null) {
                    switch (quizNum) {
                        case 0:
                        case 3:
                            correctAnswer = 0;
                            break;
                        case 1:
                        case 4:
                            correctAnswer = 1;
                            break;
                        case 2:
                            correctAnswer = 2;
                            break;
                    }
                    mAnswer1Button.setClickable(false);
                    mAnswer2Button.setClickable(false);
                    mAnswer3Button.setClickable(false);
                    if (selectedAnswer.equals(correctAnswer)) {
                        mAnswerButtons[selectedAnswer].setBackgroundColor(Color.GREEN);
                        score = score + 1;
                    } else {
                        mAnswerButtons[selectedAnswer].setBackgroundColor(Color.RED);
                        mAnswerButtons[correctAnswer].setBackgroundColor(Color.GREEN);
                    }
                    mSubmitButton.setVisibility(View.GONE);
                    mNextButton.setVisibility(View.VISIBLE);
                    quizNum = 1 + quizNum;
                    selectedAnswer = null;
                } else {
                    Toast.makeText(QuizActivity.this, "Please choose your answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizNum < maxNum) {
                    displayQuizInfo();
                } else {
                    Intent intent1 = new Intent(QuizActivity.this, FinishActivity.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("score", score);
                    startActivityForResult(intent1, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent2 = new Intent();
        setResult(RESULT_OK, intent2);
        finish();
    }

    public void displayQuizInfo() {
        TypedArray mTypedArray = getResources().obtainTypedArray(R.array.arrayQuiz);
        int quizID = mTypedArray.getResourceId(quizNum, 0);
        String[] quizContent = getResources().getStringArray(quizID);

        mQuizTitle.setText(quizContent[0]);
        mQuizDetail.setText(quizContent[1]);

        mAnswer1Button.setBackgroundColor(Color.WHITE);
        mAnswer1Button.setText(quizContent[2]);
        mAnswer1Button.setClickable(true);

        mAnswer2Button.setBackgroundColor(Color.WHITE);
        mAnswer2Button.setText(quizContent[3]);
        mAnswer2Button.setClickable(true);

        mAnswer3Button.setBackgroundColor(Color.WHITE);
        mAnswer3Button.setText(quizContent[4]);
        mAnswer3Button.setClickable(true);

        mPbQuizNum.setProgress((quizNum + 1));
        mQuizNum.setText((quizNum + 1) + "/5");

        mSubmitButton.setVisibility(View.VISIBLE);
        mNextButton.setVisibility(View.GONE);
    }
}