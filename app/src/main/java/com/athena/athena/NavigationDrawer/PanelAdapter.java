package com.athena.athena.NavigationDrawer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.athena.athena.R;
import java.util.Collections;
import java.util.List;

public class PanelAdapter extends RecyclerView.Adapter<PanelAdapter.MyViewHolder> {
    //this is a viewholder, this describes an item view and metadata about its place within the RecyclerView
    //image view and textfolder at position
    //onCreateViewHolder(VH=viewHolder)
    //inflate xml for each row, with recyclerviews stategry
    private final LayoutInflater inflator;
    private Context context;
    private ClickListener clickListener;
    //sets the opacity
    private static final int OPACITY=128;


    //ensures that we do not have a null pointer exception for our array
    List<AthenaPanel> data = Collections.emptyList();


    //panelAdapter takes the list of the array
    public PanelAdapter(Context context, List<AthenaPanel> data) {
        //taken the Context from the navigation drawer and stored it in the varaible context
        this.context=context;
        inflator = LayoutInflater.from(context);
        //takes the data we need
        this.data = data;

    }
    //method to be able to delete an item from the recyclerView
    //notifyDataSetChanged(), notifys the user if something has been changed structurally(add+remove) or positional change(updates)
    //User the notiffyItemRemoved, notifyDataSetChanged() is expensive
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //parameters=(parent, viewttype) parent=viewgroup into which the new view will be added after it is bound to an adapter position
        //viewtype= the enw view type of the new view
        //view object represents root of our custom row=custom_row linear layout
        //inflated the viewholder
        //returns a new viewholder that  holds a view of the given view type
        View view = inflator.inflate(R.layout.custom_row, parent, false);
        //passed it into the viewholder
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    //this passes in a RecyclerView.ViewHolder
    @Override
    public void onBindViewHolder(MyViewHolder holder,int position) {
//set the data that should correspond to the current row here
        //good idea to cache refrences to subviews of the view to avoid unecessary findviewbyId calls which are taxing on data
        //called by RecyclerView to display the data at the specified position, should update the contents of  the itemView to reflect the item at the given position
        //Parameters (holder, position), holder=The Viewholder which should be updated to represent the contents of the item at the given position in the data set
        //position, the position of the item within the adapters data set
        //gives us the current position of the items and sets them to the list
        final AthenaPanel current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
        holder.icon.getDrawable().setAlpha(OPACITY);
        if(position==0){
            holder.space.setVisibility(View.VISIBLE);
            System.out.println("Position is 0 Setting Space");
        }else{
            holder.space.setVisibility(View.GONE);

        }
        if(position==4){
            holder.linedivider.setVisibility(View.VISIBLE);
            holder.subHeader.setVisibility(View.VISIBLE);

        }else {
            holder.linedivider.setVisibility(View.GONE);
            holder.subHeader.setVisibility(View.GONE);
        }
        System.out.println("On BindView Holder ");

        //add a listener for our items
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    //returns the total number of items in the data set held by the adapter
    //we want to return the data size
    public int getItemCount() {
        return data.size();
    }

    //a viewholder needs to be created with a new view type that represents the given item
    //this new Viewholder will be used to display items of the adapter using onBindViewHolder (viewHolder,int)
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       public TextView title;
        public ImageView icon;
        private ImageView linedivider;
        private Space space;
        private TextView subHeader;



        public MyViewHolder(View itemView) {
            super(itemView);
            //sets a listener for itemView, we can also set the listener for title or icon
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.dummy_text);
            icon = (ImageView) itemView.findViewById(R.id.list_icon);
            space=(Space)itemView.findViewById(R.id.spaceBeforeFirstItem);
            subHeader=(TextView)itemView.findViewById(R.id.sub_header);
            AssetManager am = context.getApplicationContext().getAssets();
            Typeface roboto = Typeface.createFromAsset(am, "Roboto/Roboto-Medium.ttf");
            title.setTypeface(roboto);
            title.setAlpha(0.87F);
            linedivider=(ImageView)itemView.findViewById(R.id.line_divider);
            subHeader.setTypeface(roboto);
            subHeader.setAlpha(.54F);


        }

        @Override
        public void onClick(View v) {
            //starting the context, will take us to the Navigate to class, we are getting the context, starting the activity, and the intent
            //tells us where we start from in this case context, and where we are going to in this case navigate to
            //This way of doing things are not Ideal UPDATE TO ANOTHER(UPDATE)
            if(clickListener!=null){
                clickListener.itemClicked(v,getLayoutPosition());
            }
        }
    }
    public interface ClickListener{
        void itemClicked(View view, int position);
    }
}
