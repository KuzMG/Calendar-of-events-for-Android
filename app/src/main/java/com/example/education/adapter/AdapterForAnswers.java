package com.example.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;

import java.util.List;

public class AdapterForAnswers extends RecyclerView.Adapter<AdapterForAnswers.ViewHolder>{
    public boolean isClickable = true;
    public interface Click{
        void click(int position);
    }
    private final LayoutInflater inflater;
    private final List<String> questions;
    private final Click click;
    public AdapterForAnswers(Context context, List<String> questions, Click click) {
        this.inflater = LayoutInflater.from(context);
        this.questions = questions;
        this.click = click;
    }
    @NonNull
    @Override
    public AdapterForAnswers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.answer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAnswers.ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position+1)+".");
        holder.answer.setText(questions.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.click(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView number, answer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number_answer);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}
