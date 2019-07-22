package com.athena.athena.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.athena.athena.Information.AnswerInformation;
import com.athena.athena.R;

import java.util.ArrayList;


public class myAnswerAdapter extends RecyclerView.Adapter<myAnswerAdapter.ViewStudentAnswer> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<AnswerInformation> AnswerList=new ArrayList<>();
    public myAnswerAdapter(Context context){

        mLayoutInflater=LayoutInflater.from(context);

    }
    public void setAnswerList(ArrayList<AnswerInformation> AnswerList){
        this.AnswerList=AnswerList;
        notifyItemRangeChanged(0,AnswerList.size());
        System.out.println(AnswerList);

    }

    @Override
    public ViewStudentAnswer onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.custom_student_answer, null);
        ViewStudentAnswer holder=new ViewStudentAnswer(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewStudentAnswer holder, int position) {
        AnswerInformation currentSearch=AnswerList.get(position);
        holder.mStudentAnswerText.setText(currentSearch.getAnswerText());
        holder.mStudentId.setText(currentSearch.getAnswerstudentId());
        holder.mStudentAnswerId.setText(currentSearch.getAnswerId());
    }

    @Override
    public int getItemCount() {
        return AnswerList.size();
    }
    static class ViewStudentAnswer extends RecyclerView.ViewHolder{
        private TextView mStudentAnswerText;
        private TextView mStudentId;
        private TextView mStudentAnswerId;



        public ViewStudentAnswer (View view){
            super(view);
            mStudentAnswerText=(TextView)itemView.findViewById(R.id.StudentAnswerText);
            mStudentId=(TextView)itemView.findViewById(R.id.StudentId);
            mStudentAnswerId=(TextView)itemView.findViewById(R.id.StudentAnswerId);

        }
    }
}



