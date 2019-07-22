package com.athena.athena.MainActivitys.Answer;

import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.TimeAgo;
import com.athena.athena.MainActivitys.Voting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AnswerParsing {

    private ArrayList<QuestionData> mListblogs = new ArrayList<>();

    public ArrayList<QuestionData> parseJSONResponseQuestion(JSONArray response) {
        if (!response.equals("")) {
            ArrayList<QuestionData> questionDataArrayList = new ArrayList<>();
            try {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentQuestions = response.getJSONObject(i);
                    String text = currentQuestions.getString("text");
                    String questionId = currentQuestions.getString("questionId");
                    String studentId = currentQuestions.getString("studentId");
                    String votes = currentQuestions.getString("votes");
                    System.out.println("Votes are " + votes);
                    JSONArray voters = currentQuestions.getJSONArray("voters");
                    String DateCreated = currentQuestions.getString("created");
                    System.out.println("Dated Created in answers" +DateCreated);
                    System.out.println(votes + " VOTES");
                    int voteInt = Integer.parseInt(votes);
                    String courseId = currentQuestions.optString("courseId");
                    //BSCI105_1 is the code, and we want to trim it to BSCI105
                    //First Converted CourseId to StringBuilder
                    System.out.println(voteInt);
                    String Answerid = currentQuestions.getString("id");
                    String isSelected = currentQuestions.getString("selected");

                    System.out.println(response.length() + "length");
                    JSONObject student = currentQuestions.getJSONObject("student");
                    String username = student.getString("username");
                    String image = student.getString("image");





                    long time = Long.parseLong(DateCreated.trim());

                    data.append(text + Answerid + "\n");

                    System.out.println(data);

                    QuestionData questionData = new QuestionData();
                    questionData.setStudentImage(image);
                    questionData.setMtext(text);
                    questionData.setVotes(votes);
                    questionData.setUsername(username);
                    questionData.setMstudentId(studentId);
                    questionData.setVoters(Voting.checkIfVoted(voters));
                    questionData.setId(Answerid);
                    questionData.setMquestioId(questionId);
                    questionData.setSelected(isSelected);
                    questionData.setDateCreated(TimeAgo.calculateTime(time));
                    mListblogs.add(questionData);
                }
                System.out.println(data.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return mListblogs;
    }

}
