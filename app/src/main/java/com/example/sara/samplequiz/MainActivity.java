package com.example.sara.samplequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button Btrue;
    private Button Bfalse;
    private ImageView Bnext;
    private ImageView Bprev;
    private TextView question;
    private Button Bcheat;
    private boolean isCheater;
    private int index = 0;
    private int score = 0;


    private TrueFalse[] questions = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };


    private void CheckAnswer(boolean trueFalse)
    {
        boolean answer = questions[index].isTrueFalse();
        int messageResId;

        if(!questions[index].isAnswered())
        {
            if(isCheater)
            {
                Toast.makeText(this,R.string.user_cheated,Toast.LENGTH_SHORT).show();
                score -= 10;
            }

            else
            {
                if(trueFalse == answer)
                {
                    messageResId = R.string.correct_Toast;
                    score += 10;
                }

                else
                {
                    messageResId = R.string.incorrect_Toast;
                    score -= 5;
                }


                Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
            }
            isCheater = false;
        }
        else
            Toast.makeText(this,R.string.answered,Toast.LENGTH_SHORT).show();
    }
    private void updateScore()
    {
        TextView myScore = (TextView)findViewById(R.id.textView4);
        myScore.setText(String.valueOf(score));
    }
    private void UpdateQuestion()
    {
        int questionTurn = questions[index].getQuestion();
        question.setText(questionTurn);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentScore",score);
        outState.putInt("currentIndex",index);

        boolean array [] = new boolean[questions.length];

        for(int i=0;i<questions.length;i++)
            array[i] = questions[i].isAnswered();


        outState.putBooleanArray("answered_orNot",array);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        index = savedInstanceState.getInt("currentIndex");
        score = savedInstanceState.getInt("currentScore");
        UpdateQuestion();
        updateScore();

        boolean get_back[] = savedInstanceState.getBooleanArray("answered_orNot");
        for(int i =0;i< questions.length;i++ )
            questions[i].setAnswered(get_back[i]);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        isCheater = data.getBooleanExtra("shown_orNot",false);

    }

    // TREBA DA CESTITA NA KRAJO OD PRASANJATA!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = (TextView)findViewById(R.id.textView);
        int txt = questions[index].getQuestion();
        question.setText(txt);

        updateScore();

        // TextView
        question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(index+1 < questions.length)
                    {
                        index ++;
                        UpdateQuestion();

                    }
                    else
                        Toast.makeText(MainActivity.this,R.string.no_more,Toast.LENGTH_SHORT).show();

                }
            }

        );

        // TRUE
        Btrue  = (Button)findViewById(R.id.true_button);
        Btrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(true);
                questions[index].setAnswered(true);
                updateScore();
                UpdateQuestion();
            }
        });

        // FALSE
        Bfalse = (Button)findViewById(R.id.false_button);
        Bfalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(false);
                questions[index].setAnswered(true);
                updateScore();
                UpdateQuestion();

            }
        });

        // NEXT
        Bnext  = (ImageView)findViewById(R.id.next_button);
        Bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index+1 < questions.length)
                {
                    index++;
                    UpdateQuestion();
                }
                else
                    Toast.makeText(MainActivity.this,R.string.no_more,Toast.LENGTH_SHORT).show();


            }
        });

        // PRev
        Bprev = (ImageView)findViewById(R.id.prev_button);
        Bprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index > 0)
                {
                    index = index - 1;
                    UpdateQuestion();
                }

            }
        });

        // CHEAT
        Bcheat = (Button)findViewById(R.id.button_cheat);
        Bcheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this,CheatActivity.class);
               boolean answer = questions[index].isTrueFalse();
                //pass the correct answer
               intent.putExtra("answer",answer);

               startActivityForResult(intent,0);

            }
        });


    }


}
