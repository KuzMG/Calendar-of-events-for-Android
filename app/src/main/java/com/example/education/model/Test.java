package com.example.education.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Test implements Parcelable {
    private final String question, rightAnswer;
    private final List<String> answers;
    private final int numberRightAnswer;

    public Test(String question, String rightAnswer, List<String> answers, int numberRightAnswer) {
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answers = answers;
        this.numberRightAnswer = numberRightAnswer;
    }

    protected Test(Parcel in) {
        question = in.readString();
        rightAnswer = in.readString();
        answers = in.createStringArrayList();
        numberRightAnswer = in.readInt();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getNumberRightAnswer() {
        return numberRightAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(rightAnswer);
        parcel.writeStringList(answers);
        parcel.writeInt(numberRightAnswer);
    }
}
