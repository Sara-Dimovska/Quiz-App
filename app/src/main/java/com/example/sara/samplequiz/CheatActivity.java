package com.example.sara.samplequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {


    private Button show_answer;
    private TextView text;
    private boolean answer;

    private void CheckIfUserCheated(boolean cheated)
    {
        Intent i = new Intent();
        i.putExtra("shown_orNot",cheated);
        setResult(RESULT_CANCELED,i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        CheckIfUserCheated(false);


        text = (TextView)findViewById(R.id.text);

        show_answer = (Button)findViewById(R.id.show_answer_button);

        show_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("The answer is: ");

                answer = getIntent().getBooleanExtra("answer",false);

                if(answer == true)

                    text.setText("The answer is: True");
                else
                    text.setText("The answer is: False");

                CheckIfUserCheated(true);
            }
        });


    }
}
