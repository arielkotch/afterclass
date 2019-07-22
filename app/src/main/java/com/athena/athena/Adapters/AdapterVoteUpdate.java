package com.athena.athena.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.R;

import java.util.ArrayList;

public class AdapterVoteUpdate extends RecyclerView.Adapter<AdapterVoteUpdate.VoteView> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<QuestionData> data =new ArrayList<>();
    public AdapterVoteUpdate(Context context){
        mLayoutInflater=LayoutInflater.from(context);

    }
    public void setBloglist(ArrayList<QuestionData> data){
        this.data =data;
        notifyItemRangeChanged(0, this.data.size());
    }
    @Override
    public VoteView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.customquestion, null);
        VoteView holder=new VoteView(view);
        return holder;
    }
    //ViewGroup parent and ViewType are not being assigned.
    @Override
    public void onBindViewHolder(VoteView holder, int position) {
        QuestionData currentObj= data.get(position);
        holder.mVotes.setText(currentObj.getVotes());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VoteView extends RecyclerView.ViewHolder{
        private TextView mVotes;

        public VoteView (View itemView){
            super(itemView);
            mVotes=(TextView)itemView.findViewById(R.id.VoteTextView);
        }
    }
}
