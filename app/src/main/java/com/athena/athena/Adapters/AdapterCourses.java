package com.athena.athena.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.athena.athena.Information.Search;
import com.athena.athena.MainActivitys.ImageLoading;
import com.athena.athena.MainActivitys.Check;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;

import java.util.ArrayList;

public class AdapterCourses extends  RecyclerView.Adapter<AdapterCourses.ViewTags>{
    private LayoutInflater mLayoutInflater;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    public static ArrayList<Search> ListSearch=new ArrayList<>();
    public AdapterCourses(Context context){
        mLayoutInflater=LayoutInflater.from(context);
    }
    public void setTagsList (ArrayList<Search> ListSearch){
        this.ListSearch=ListSearch;
        notifyItemRangeChanged(0,ListSearch.size());
    }

    @Override
    public ViewTags onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.fragment_tags, parent, false);
        ViewTags viewholder=new ViewTags(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewTags holder, int position) {
        final Search currentTags=ListSearch.get(position);
        holder.mName.setText(currentTags.getName());
        holder.mCode.setText(currentTags.getCode());
        holder.mInstructor.setText(currentTags.getInstructor());
        holder.mDepartment.setText(currentTags.getDepartment());
        holder.mCheckBox.setOnCheckedChangeListener(null);
        ImageLoading imageLoading=new ImageLoading(currentTags.getImage(),holder.imageView,"course");
        imageLoading.setImage();
        holder.mCheckBox.setChecked(currentTags.isSelected());

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentTags.setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListSearch.size();
    }

     class ViewTags extends RecyclerView.ViewHolder implements View.OnClickListener {
         private TextView mName;
         private TextView mCode;
         private TextView mInstructor;
         private TextView mDepartment;
         private ImageView imageView;

         private CheckBox mCheckBox;



        public ViewTags(View itemView){
            super(itemView);
            mName =(TextView)itemView.findViewById(R.id.myName);
            mCode =(TextView)itemView.findViewById(R.id.myCode);
            mInstructor=(TextView)itemView.findViewById(R.id.myInstructor);
            mDepartment=(TextView)itemView.findViewById(R.id.myDepartment);
            imageView=(ImageView)itemView.findViewById(R.id.courseImage);

            mCheckBox=(CheckBox)itemView.findViewById(R.id.checkBoxDelete);
            mCheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Check check=new Check(AdapterCourses.this,getAdapterPosition(),ListSearch);
            check.unchecked(view);


        }
    }
}
