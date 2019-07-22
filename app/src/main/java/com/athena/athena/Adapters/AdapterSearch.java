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
import com.athena.athena.Information.Search;
import com.athena.athena.MainActivitys.ImageLoading;
import com.athena.athena.R;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewSearch>{
    private LayoutInflater mLayoutInflater;
    private  ArrayList<Search> search =new ArrayList<>();
    private View view;

    public AdapterSearch(Context context){
        mLayoutInflater=LayoutInflater.from(context);

    }
    public void setSearch(ArrayList<Search> searchList){
        search=searchList;
        search=addActiveClasses(search);
        notifyItemRangeChanged(0,search.size());
    }
//Precondition: Insert an ArrayList of Data
/*Postcondition: returns an ArrayList of Data that contains
 *the activated courses.
 */
    private ArrayList<Search> addActiveClasses(ArrayList<Search> data) {
        ArrayList<Search> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            boolean isActive = Boolean.parseBoolean(data.get(i).getActive());
            if (isActive){
                list.add(data.get(i));
            }
        }
        return list;
    }

    @Override
    public ViewSearch onCreateViewHolder(ViewGroup parent, int viewType) {
         view= mLayoutInflater.inflate(R.layout.custom_search, null);
        ViewSearch holder=new ViewSearch(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewSearch holder, int position) {
        final Search currentSearch= search.get(position);

        holder.mName.setText(currentSearch.getName());
        holder.mCode.setText(currentSearch.getCode());
        holder.mInstructor.setText(currentSearch.getInstructor());
        holder.mDepartment.setText(currentSearch.getDepartment());
        ImageLoading imageLoading=new ImageLoading(currentSearch.getImage(),holder.mImageView,"course");
        imageLoading.setImage();
        holder.addSelect.setOnCheckedChangeListener(null);
        holder.addSelect.setChecked(currentSearch.isSelected());
        holder.addSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                currentSearch.setSelected(isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return search.size();
    }
    public void setFilter(ArrayList<Search> Search) {
        search = new ArrayList<>();
        search.addAll(Search);
        notifyDataSetChanged();
    }

    static class ViewSearch extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mCode;
        private TextView mInstructor;
        private ImageView mImageView;

        private TextView mDepartment;


        public CheckBox addSelect;




        public ViewSearch (View view){
            super(view);
            mName =(TextView)itemView.findViewById(R.id.autoCourse);
            mCode =(TextView)itemView.findViewById(R.id.code);
            mInstructor=(TextView)itemView.findViewById(R.id.instructor);
            addSelect=(CheckBox)itemView.findViewById(R.id.checkBox);
            mDepartment=(TextView)itemView.findViewById(R.id.department);
            mImageView=(ImageView)itemView.findViewById(R.id.searchBarImage);
        }
    }
}

