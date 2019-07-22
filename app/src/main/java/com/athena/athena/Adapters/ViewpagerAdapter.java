package com.athena.athena.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
//import com.athena.athena.MainActivitys.QuestionActivity;
import com.athena.athena.MainActivitys.QuestionActivity;
import com.athena.athena.MainActivitys.Replies;
import com.athena.athena.MainActivitys.Voting;
import com.athena.athena.MainActivitys.ImageLoading;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class ViewpagerAdapter extends RecyclerView.Adapter<ViewpagerAdapter.ViewDashboard> {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private VolleySingleton mVolleySingleton;

    public static ArrayList<QuestionData> data =new ArrayList<>();

    public ViewpagerAdapter(Context context){
        mLayoutInflater=LayoutInflater.from(context);
        this.context=context;
    }
    public void setBloglist(ArrayList<QuestionData> listBlogs){
        this.data=listBlogs;
        //Notify any registered observers that the itemCount items starting at position positionStart have changed.
        notifyItemRangeChanged(0,listBlogs.size());
        //TODO Fix, not clear as to why NotifyDataSetChanged should be used
        //Notifies the attached observers that the underlying data has been changed
        //any View reflecting the data set should refresh itself.
        notifyDataSetChanged();

    }
    @Override
    public ViewDashboard onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mLayoutInflater.inflate(R.layout.customizejson, parent, false);
        ViewDashboard viewholder=new ViewDashboard(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final ViewDashboard holder, int position) {
        final QuestionData questionHolder= data.get(position);

        holder.questionText.setText(questionHolder.getMtext());
        holder.points.setText(questionHolder.getPoints());
        holder.questionId.setText(questionHolder.getId());
        holder.studentId.setText(questionHolder.getMstudentId());
        holder.mDateCreated.setText(questionHolder.getDateCreated());
        holder.mTitle.setText(questionHolder.getTitle());
        holder.mCourseId.setText(questionHolder.getCourseId());
        holder.mStudentVoted.setText(questionHolder.getVoters());
        holder.mVotes.setText(questionHolder.getVotes());
        holder.mLikeButton.setLiked(Boolean.valueOf(questionHolder.getVoters()));
        holder.imageName.setText(questionHolder.getCourseImage());
        holder.replies.setText(questionHolder.getReplies());
        ImageLoading imageLoading=new ImageLoading(questionHolder.getCourseImage(),holder.imageView,ModelType.COURSE);
        imageLoading.setImage();
        holder.mLikeButton.setTag(holder);
        holder.mVotes.setTag(questionHolder.getVotes());
        //place the questionId into the replies constructor





    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewDashboard extends RecyclerView.ViewHolder {
        private TextView questionText;
        private TextView points;
        private TextView questionId;
        private TextView studentId;
        private TextView mDateCreated;
        private TextView mTitle;
        private TextView mCourseId;
        private LikeButton mLikeButton;
        private TextView mStudentVoted;
        private TextView mVotes;
        private Typeface mTypeface;
        private ImageView imageView;
        private TextView mReplies;
        private TextView imageName;
        private CardView cardView;
         private TextView replies;


        public ViewDashboard(View itemView) {
            super(itemView);
            AssetManager am = context.getApplicationContext().getAssets();
            Typeface roboto = Typeface.createFromAsset(am, "Roboto/Roboto-Regular.ttf");
            mTitle = (TextView) itemView.findViewById(R.id.classCode);
            questionText = (TextView) itemView.findViewById(R.id.questionText);
            points = (TextView) itemView.findViewById(R.id.points);
            questionId = (TextView) itemView.findViewById(R.id.questionId);
            studentId = (TextView) itemView.findViewById(R.id.StudentId);
            mDateCreated = (TextView) itemView.findViewById(R.id.TimeCreated);
            mCourseId = (TextView) itemView.findViewById(R.id.courseId);
            mStudentVoted = (TextView) itemView.findViewById(R.id.studentVoted);
            imageName = (TextView) itemView.findViewById(R.id.imageName);
              replies=(TextView)itemView.findViewById(R.id.repliesViewpager);

            cardView = (CardView) itemView.findViewById(R.id.cardviewAnswer);

            mLikeButton = (LikeButton) itemView.findViewById(R.id.like_button_viewpager);
            //mReplies=(TextView)itemView.findViewById(R.id.replies);
            mTitle.setTypeface(roboto);
            questionText.setTypeface(roboto);
            points.setTypeface(roboto);
            mDateCreated.setTypeface(roboto);
            //mReplies.setTypeface(roboto);
            mVotes = (TextView) itemView.findViewById(R.id.votes);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println("CARDVIEW CLICKED AT POSITION "+getAdapterPosition());
                    Intent intent=new Intent(context,QuestionActivity.class);

                    intent.putExtra("adapterPosition",getAdapterPosition());
                    context.startActivity(intent);


                }
            });
            imageView = (ImageView) itemView.findViewById(R.id.studentImage);

            mLikeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    Voting voting = new Voting(ModelType.QUESTION, data, getAdapterPosition(), questionId.getText().toString(), context, mVotes, mLikeButton);
                    voting.onUpVote();

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Voting voting = new Voting(ModelType.QUESTION, data, getAdapterPosition(), questionId.getText().toString(), context, mVotes, mLikeButton);
                    voting.onDownVote();
                }
            });
        }

    }

}