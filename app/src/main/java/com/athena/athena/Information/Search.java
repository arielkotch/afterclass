package com.athena.athena.Information;

public class Search {

    /*
     "id": "PSY101_1",
    "code": "PSY101",
    "name": "Introduction to Physics",
    "department": "Physics",
    "description": "Explores the laws of motion.",
    "instructor": "Professor Stern",
    "credits": 3,
    "schoolId": 1
     */
    private String mCode = "code";
    private String mName = "name";
    private String mDepartment = "department";
    private String mInstructor = "instructor";
    private String image="image";
    private String active="active";


    private String mDescription="description";
    //// TODO: 4/20/16 Update Tags, and take out mCourseId
    private String mCourseId="courseId";

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String courseId) {
        mCourseId = courseId;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public String getInstructor() {
        return mInstructor;
    }

    public void setInstructor(String instructor) {
        mInstructor = instructor;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    private boolean mSelected;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
