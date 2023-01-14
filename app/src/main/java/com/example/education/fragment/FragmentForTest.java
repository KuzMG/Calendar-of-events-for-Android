package com.example.education.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.education.R;
import com.example.education.adapter.AdapterForAnswers;
import com.example.education.model.Test;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentForTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentForTest extends Fragment {
    public interface Click extends Parcelable {
        void click(int page);
        void addNumberPage(boolean trueOrFalse);
        @Override
        int describeContents();
        @Override
        void writeToParcel(Parcel parcel, int i);
    }
    private Test test;
    public Click nextPage;
    public int countPages;
    public FragmentForTest() {

    }

    public static FragmentForTest newInstance(Test tests, Click clicks, int countPages) {
        FragmentForTest fragment = new FragmentForTest();
        Bundle args=new Bundle();
        args.putInt("countPages",countPages);
        args.putParcelable(Test.class.getSimpleName(),tests);
        args.putParcelable(Click.class.getSimpleName(), clicks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        assert getArguments() != null;
        nextPage = getArguments().getParcelable(Click.class.getSimpleName());
        test = getArguments().getParcelable(Test.class.getSimpleName());
        countPages= getArguments().getInt("countPages");
        RecyclerView recyclerView = view.findViewById(R.id.answers);
        TextView rightAnswer = view.findViewById(R.id.right_answer);
        AdapterForAnswers.Click click = new AdapterForAnswers.Click() {
            @Override
            public void click(int position) {
                AdapterForAnswers adapter = (AdapterForAnswers) recyclerView.getAdapter();
                if(!adapter.isClickable)
                    return;
                boolean trueOrFalse;
                recyclerView.getChildAt(position).setBackgroundResource(R.color.winter_today);
                recyclerView.getChildAt(test.getNumberRightAnswer()-1).setBackgroundResource(R.color.winter_celected);

                if(position+1!=test.getNumberRightAnswer()) {
                    trueOrFalse = false;
                    nextPage.addNumberPage(trueOrFalse);
                    rightAnswer.setText("Правильный ответ:" + test.getRightAnswer());
                }
                else {
                    trueOrFalse=true;
                    nextPage.addNumberPage(trueOrFalse);
                    nextPage.click(position);
                }
                adapter.isClickable=false;
            }
        };
        AdapterForAnswers adapterForAnswers = new AdapterForAnswers(getContext(),test.getAnswers(), click);
        recyclerView.setAdapter(adapterForAnswers);
        TextView question = view.findViewById(R.id.question);
        question.setText(test.getQuestion());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }
}