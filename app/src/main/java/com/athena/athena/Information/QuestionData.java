package com.athena.athena.Information;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class QuestionData  {

    private String mtext="text";
    private String mPoints="points";
    private String replies="replies";
    private  String mId="id";
    private String studentImage="image";
    private String mstudentId="studentId";
    private String mquestioId="questionId";
    private String mDateCreated="created";
    private String mCourseId="courseId";
    private String mVoters="voters";
    private String username="username";


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isCheckIfSelected() {
        return checkIfSelected;
    }

    public void setCheckIfSelected(boolean checkIfSelected) {
        this.checkIfSelected = checkIfSelected;
    }

    private boolean checkIfSelected=false;


    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    private String courseImage="image";



    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    private String mUrl="http://52.86.96.132:3001/api/containers/profile/download/student3.png";

    private boolean isLiked;



    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String courseId) {
        mCourseId = courseId;
    }

    private String mVotes="votes";
    private String mSelected="selected";
    private String mQuestin="question";
    private String mTitle="title";


    public void setTitle(String title){
        mTitle=title;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getQuestin() {
        return mQuestin;
    }

    public void setQuestin(String questin) {
        mQuestin = questin;
    }


    public String getVotes() {
        return mVotes;
    }

    public void setVotes(String votes) {
        mVotes = votes;
    }

    public String getVoters() {
        return mVoters;
    }

    public void setVoters(String voters) {
        mVoters = voters;
    }

    public String getSelected() {
        return mSelected;
    }

    public void setSelected(String selected) {
        mSelected = selected;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public String getMquestioId() {
        return mquestioId;
    }

    public void setMquestioId(String mquestioId) {
        this.mquestioId = mquestioId;
    }

    public String getMstudentId() {
        return mstudentId;
    }

    public void setMstudentId(String mstudentId) {
        this.mstudentId = mstudentId;
    }

    public String getMtext() {
        return mtext;
    }

    public void setMtext(String mtext) {
        this.mtext = mtext;
    }

    public String getPoints() {
        return mPoints;
    }

    public void setPoints(String points) {
        mPoints = points;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }
}

