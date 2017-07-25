package com.example.sara.samplequiz;

/**
 * Created by sara on 01-Jul-17.
 */

class TrueFalse {

   int Question;
   boolean trueFalse;
   boolean answered;

    public TrueFalse(int Question, boolean trueFalse)
    {
        this.Question = Question;
        this.trueFalse = trueFalse;
        answered = false;
    }


    public int getQuestion() {
        return Question;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setAnswered(boolean true_false){ answered = true_false;}

    public boolean isAnswered() {
        return answered;
    }
}
