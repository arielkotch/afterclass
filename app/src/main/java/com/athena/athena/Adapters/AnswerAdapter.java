package com.athena.athena.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.ImageLoading;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
import com.athena.athena.MainActivitys.QuestionActivity;
import com.athena.athena.MainActivitys.Select;
import com.athena.athena.MainActivitys.Voting;
import com.athena.athena.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
//Adapter(Only Responsible for Managing an Array)
//Creates a View out of the data and gives the data to an AdapterView
//AdapterView is Responsible for how data is displayed.

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewQuestion>{
    private LayoutInflater mLayoutInflater;
    //this is an arrayList of questionData objects
    private ArrayList<QuestionData> data =new ArrayList<>();
    //Created the layoutInflator
    private Context mContext;
    public AnswerAdapter(Context context){
        //get from context
        this.mContext=context;
        mLayoutInflater=LayoutInflater.from(context);

    }
    public void setBloglist(ArrayList<QuestionData> data){
        this.data =data;
        notifyItemRangeChanged(0, this.data.size());
    }
    @Override
    public ViewQuestion onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflates the customQuestion view or converts it to java code
        View view= mLayoutInflater.inflate(R.layout.customquestion, null);
        //We now want to convert the View into a ViewQuestion, view question takes
        //a view so we pass the view into view question and then return it.
        ViewQuestion holder=new ViewQuestion(view);
        return holder;
    }
//ViewGroup parent and ViewType are not being assigned.
    @Override
    public void onBindViewHolder(ViewQuestion holder, int position) {
        //here we need to bind the data to our view, there is currently no Data!
        //We need to get the data from our JSON
        //Parameters is a ViewHolder and a Position
        //This gives us the current information object from the whole arraylist
        //data.get(position) data is the arraylist and we are getting the current position or index;
        //That current obj is of Type QuestionData
        QuestionData currentObj = data.get(position);
        //we are accessing the Inflated view, or saved view with holder
        //holder.answerText is the textView in holder. We are then taking that current object
        //We are getting the text of the current object and setting it to the AnswerText view
        holder.answerText.setText(currentObj.getMtext());
        holder.answerId.setText(currentObj.getId());
        holder.mVotes.setText(currentObj.getVotes());
        holder.mDateCreated.setText(currentObj.getDateCreated());
        holder.mLikeButton.setLiked(Boolean.valueOf(currentObj.getVoters()));
        holder.mStudentVoted.setText(currentObj.getVoters());
        holder.studentId.setText(currentObj.getMstudentId());
        ImageLoading studentImage=new ImageLoading(currentObj.getStudentImage(),holder.studentImage,"profile");
        studentImage.setImage();
        holder.username.setText(currentObj.getUsername());
        holder.select.setLiked(Boolean.parseBoolean(currentObj.getSelected()));
        holder.mLikeButton.setTag(holder);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewQuestion extends RecyclerView.ViewHolder{
        //once we create it once the reclycer view will automatically recycle it
        private TextView answerText;
        private TextView answerId;
        private TextView mVotes;
        private TextView mDateCreated;
        private TextView mInitial;
        private LikeButton mLikeButton;
        private Button markButton;
        private Button unMark;
        private CardView cardView;
        private RelativeLayout layout;
        private TextView studentId;
        private TextView username;
        private LikeButton select;
        private ImageView studentImage;

        private TextView mStudentVoted;



        public ViewQuestion (View itemView){
            super(itemView);
            answerText=(TextView)itemView.findViewById(R.id.answerText);
            answerId=(TextView)itemView.findViewById(R.id.answerId);
            mVotes=(TextView)itemView.findViewById(R.id.VoteTextView);
            mDateCreated=(TextView)itemView.findViewById(R.id.TimeCreated);
            mLikeButton=(LikeButton)itemView.findViewById(R.id.thumb_viewpager);
            mStudentVoted=(TextView)itemView.findViewById(R.id.studentVotedAnswer);
            cardView=(CardView)itemView.findViewById(R.id.cardviewAnswer);
            layout=(RelativeLayout)itemView.findViewById(R.id.answerlayout);
            studentId=(TextView)itemView.findViewById(R.id.studentId);
            username=(TextView)itemView.findViewById(R.id.username);
            select=(LikeButton)itemView.findViewById(R.id.check);
            studentImage=(ImageView)itemView.findViewById(R.id.studentImage);
            answerId.setVisibility(View.INVISIBLE);

            studentId.setVisibility(View.INVISIBLE);

            if(QuestionActivity.studentQuestionId.equals(Login.getUserId())) {
                    //studentQuestionId stores the Id of the user that asked the question
                    //Login.getUserId() gets the Id of the user that logged on
                    //Therefore, if the studentQuestionId equals the Login.getUserId()
                    //its the current users question and he is allowed to select the best answer
                select.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        Select select = new Select(ModelType.SELECTED, data.get(getAdapterPosition()).getId(), mContext, layout,getAdapterPosition(),data,AnswerAdapter.this,data.get(getAdapterPosition()).getMquestioId());
                        select.modelSelect();
                        System.out.println("UpSelected Answer");
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        Select select = new Select(ModelType.UNSELECTED, data.get(getAdapterPosition()).getId(), mContext, layout,getAdapterPosition(),data,AnswerAdapter.this,data.get(getAdapterPosition()).getMquestioId());
                        select.modelSelect();

                        System.out.println("DownSelected Answer");
                    }
                });
                }else{
                    //otherwise, the user is restricted from selecting
                    //by making both marking up and down buttons invisible
                    select.setVisibility(View.INVISIBLE);
                }

            mLikeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    System.out.println("The TextView ID for the Answer is " + answerId.getText().toString());
                    System.out.println("LikedUp");
                    //"http://52.86.96.132:3001/api/"+type+"/" + stringId + "/upvote?access_token="+Login.getStudentAuth();
                    //http://52.86.96.132:3001/api/ Answers / answerId /upvote?access_token="+Login.getStudentAuth()
                    Voting voting=new Voting(ModelType.ANSWER,data,getAdapterPosition(), answerId.getText().toString(),mContext,mVotes,mLikeButton);
                    voting.onUpVote();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    //TODO, Fix Constructor(Shorten)
                    Voting voting=new Voting(ModelType.ANSWER,data,getAdapterPosition(), answerId.getText().toString(),mContext,mVotes,mLikeButton);
                    voting.onDownVote();
                }
            });

        }

    }
}
