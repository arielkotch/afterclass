package com.athena.athena.MainActivitys;

import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.TimeAgo;
import com.athena.athena.MainActivitys.Voting;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParsingMethods {
    private ArrayList<QuestionData> mQuestionDataArrayList = new ArrayList<QuestionData>();
//The Home View Requires a special parsing method, as it is parsing nested JSON data
    public ArrayList<QuestionData> parseResponseHome(JSONObject response) {
        if (!response.equals("")) {
            try {
                StringBuilder data = new StringBuilder();
                for (int x = 0; x < response.length(); x++) {
                    JSONArray currentQuestions = response.getJSONArray("questions");
                    for (int y = 0; y < currentQuestions.length(); y++) {
                        JSONObject objectSubcategory = currentQuestions.getJSONObject(y);
                        JSONArray answers = objectSubcategory.getJSONArray("answers");
                        String title = objectSubcategory.getString("title");
                        String text = objectSubcategory.getString("text");
                        String points = objectSubcategory.getString("points");
                        String ID = objectSubcategory.getString("id");
                        String studentId = objectSubcategory.getString("studentId");
                        String DateCreated = objectSubcategory.getString("created");
                        String courseId = objectSubcategory.getString("courseId");
                        String votes = objectSubcategory.getString("votes");
                        JSONObject course=objectSubcategory.getJSONObject("course");
                        //BSCI105_1 is the code, and we want to trim it to BSCI105
                        //First Converted CourseId to StringBuilder
                        StringBuilder stringBuilderCode = new StringBuilder(courseId);
                        //set the new length of the String sequence to the original length-2
                        stringBuilderCode.setLength(stringBuilderCode.length() - 2);
                        //assigned the trimmed StringBuilder to a new String
                        String codeTrimmed = stringBuilderCode.toString();
                        System.out.println("CourseId" + courseId);
                        long time = Long.parseLong(DateCreated.trim());
                        data.append(text + "\n" + points + "\n");
                        /*JSONArray cycles through the array of voters, when a user votes
                        their ID is added to the array.When they downvote, it is removed
                         */
                        String image="Propose.png";
                        JSONArray voters = objectSubcategory.optJSONArray("voters");
                        if(course.has("image")){
                             image=course.getString("image");
                        }

                        final QuestionData questionData = new QuestionData();
                        questionData.setId(ID);
                        //set the item to the codetrimmed
                        questionData.setCourseId(codeTrimmed);
                        questionData.setMstudentId(studentId);
                        questionData.setMtext(text);
                        questionData.setReplies(""+answers.length());
                        questionData.setPoints(points + "pts");
                        questionData.setTitle(title);
                        questionData.setVotes(votes);
                        questionData.setCourseImage(image);
                        //checkIfVoted, determines if the students Id is in the array(if they voted)
                        questionData.setVoters(Voting.checkIfVoted(voters));
                        //The dateCreated was off by 1 hour so 3600000 ms where added=1hour, (UPDATE)
                        questionData.setDateCreated(TimeAgo.calculateTime(time));
                        mQuestionDataArrayList.add(questionData);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mQuestionDataArrayList;
    }
    //The parsing methods for both the Points and Recent Views are the Same
    public ArrayList<QuestionData> parseQuestions(JSONArray response) {
        if (!response.equals("")) {
            try {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentQuestions = response.getJSONObject(i);
                    JSONArray answers=currentQuestions.getJSONArray("answers");
                    //the length of the answers array is the number of responses
                    String text = currentQuestions.getString("text");
                    String points = currentQuestions.getString("points");
                    String ID=currentQuestions.getString("id");
                    String studentId = currentQuestions.getString("studentId");
                    String DateCreated=currentQuestions.getString("created");
                    String title=currentQuestions.getString("title");
                    String courseId=currentQuestions.getString("courseId");
                    String votes=currentQuestions.getString("votes");
                    JSONArray voters= currentQuestions.getJSONArray("voters");
                    JSONObject course = currentQuestions.getJSONObject("course");
                    //The Default image is Propose if nothing shows up
                    //TODO Fix Default Image
                    String image="Propose.png";
                    if(course.has("image")){
                        System.out.println("yes");
                        image=course.getString("image");
                        System.out.println("Image is "+image);
                    }
                    StringBuilder stringBuilderCode=new StringBuilder(courseId);
                    //set the new length of the String sequence to the original length-2
                    stringBuilderCode.setLength(stringBuilderCode.length() - 2);
                    //assigned the trimmed StringBuilder to a new String
                    String codeTrimmed=stringBuilderCode.toString();

                    long time=Long.parseLong(DateCreated.trim());
                    data.append(text + "\n" + points + "\n");
                    System.out.println(data);
                    final QuestionData questionData = new QuestionData();

                    questionData.setVoters(Voting.checkIfVoted(voters));
                    questionData.setCourseId(codeTrimmed);
                    questionData.setId(ID);
                    questionData.setMstudentId(studentId);
                    questionData.setMtext(text);
                    questionData.setReplies(""+answers.length());
                    questionData.setVotes(votes);
                    questionData.setPoints(points+ "pts");
                    questionData.setTitle(title);
                    questionData.setCourseImage(image);
                    questionData.setDateCreated(TimeAgo.calculateTime(time));
                    mQuestionDataArrayList.add(questionData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mQuestionDataArrayList;
    }

    public ArrayList<QuestionData> parseAnswers(JSONArray response) {
        if (!response.equals("")) {
            try {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentQuestions = response.getJSONObject(i);
                    JSONObject questionsToAnswer=currentQuestions.getJSONObject("question");
                    String text = questionsToAnswer.getString("text");
                    String points = questionsToAnswer.getString("points");
                    String ID=questionsToAnswer.getString("id");
                    String studentId = questionsToAnswer.getString("studentId");
                    String DateCreated=questionsToAnswer.getString("created");
                    String title=questionsToAnswer.getString("title");
                    String courseId=questionsToAnswer.getString("courseId");
                    String votes=questionsToAnswer.getString("votes");
                    JSONArray voters= questionsToAnswer.getJSONArray("voters");
                    //The Default image is Propose if nothing shows up
                    StringBuilder stringBuilderCode=new StringBuilder(courseId);
                    //set the new length of the String sequence to the original length-2
                    stringBuilderCode.setLength(stringBuilderCode.length() - 2);
                    //assigned the trimmed StringBuilder to a new String
                    String codeTrimmed=stringBuilderCode.toString();
                    JSONObject course = questionsToAnswer.getJSONObject("course");

                    String image="Propose.png";
                    if(course.has("image")){
                        System.out.println("yes");
                        image=course.getString("image");
                        System.out.println("Image is "+image);
                    }
                    long time=Long.parseLong(DateCreated.trim());
                    data.append(text + "\n" + points + "\n");
                    System.out.println(data);
                    QuestionData questionData = new QuestionData();
                    questionData.setVoters(Voting.checkIfVoted(voters));
                    questionData.setCourseId(codeTrimmed);
                    questionData.setId(ID);
                    questionData.setMstudentId(studentId);
                    questionData.setMtext(text);
                    questionData.setVotes(votes);
                    questionData.setPoints(points+ "pts");
                    questionData.setCourseImage(image);
                    questionData.setTitle(title);
                    questionData.setDateCreated(TimeAgo.calculateTime(time));
                    mQuestionDataArrayList.add(questionData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return  mQuestionDataArrayList;

    }
}
