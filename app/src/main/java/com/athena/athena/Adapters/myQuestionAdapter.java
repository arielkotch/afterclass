package com.athena.athena.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.athena.athena.Information.myQuestions;
import com.athena.athena.R;

import java.util.ArrayList;

public class myQuestionAdapter extends RecyclerView.Adapter<myQuestionAdapter.ViewStudentQuestion> {
    private LayoutInflater mLayoutInflater;

    public ArrayList<myQuestions> ListQuestionArray=new ArrayList<>();
    public myQuestionAdapter(Context context){

        mLayoutInflater=LayoutInflater.from(context);

    }
    public void setListQuestionArray(ArrayList<myQuestions> ListQuestionArray){
        this.ListQuestionArray=ListQuestionArray;
        notifyItemRangeChanged(0,ListQuestionArray.size());
    }

    @Override
    public ViewStudentQuestion onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.custom_student_question, null);
        ViewStudentQuestion holder=new ViewStudentQuestion(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewStudentQuestion holder, int position) {
        myQuestions currentSearch=ListQuestionArray.get(position);
        holder.mStudentQuestionText.setText(currentSearch.getText());
        holder.mStudentQuestionPoints.setText(currentSearch.getPoints());
        holder.mStudentQuestionId.setText(currentSearch.getId());
        holder.mStudentVotes.setText(currentSearch.getVotes());
        holder.mStudentIsNewInstance.setText(currentSearch.getIsNewInstance());
    }

    @Override
    public int getItemCount() {
        return ListQuestionArray.size();
    }
    public void setFilter(ArrayList<myQuestions> StudentQuestionArray) {
        ListQuestionArray = new ArrayList<>();
        ListQuestionArray.addAll(StudentQuestionArray);
        notifyDataSetChanged();
    }

    static class ViewStudentQuestion extends RecyclerView.ViewHolder{
        private TextView mStudentQuestionText;
        private TextView mStudentQuestionPoints;
        private TextView mStudentQuestionId;
        private TextView mStudentVotes;
        private TextView mStudentIsNewInstance;



        public ViewStudentQuestion (View view){
            super(view);
            mStudentQuestionText=(TextView)itemView.findViewById(R.id.StudentQuestionText);
            mStudentQuestionPoints=(TextView)itemView.findViewById(R.id.StudentQuestionPoints);
            mStudentQuestionId=(TextView)itemView.findViewById(R.id.StudentQuestionId);
            mStudentVotes=(TextView)itemView.findViewById(R.id.myVotes);
            mStudentIsNewInstance=(TextView)itemView.findViewById(R.id.isNewInstance);
        }
    }
}


