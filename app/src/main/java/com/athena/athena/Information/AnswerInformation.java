package com.athena.athena.Information;

public class AnswerInformation {
    private String mAnswerText="text";
    private  String mAnswerId="id";
    private String mAnswerstudentId="studentId";
    private String mAnswerquestioId="questionId";

    public String getAnswerText() {
        return mAnswerText;
    }

    public void setAnswerText(String answerText) {
        mAnswerText = answerText;
    }

    public String getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(String answerId) {
        mAnswerId = answerId;
    }

    public String getAnswerstudentId() {
        return mAnswerstudentId;
    }

    public void setAnswerstudentId(String answerstudentId) {
        mAnswerstudentId = answerstudentId;
    }

    public String getAnswerquestioId() {
        return mAnswerquestioId;
    }

    public void setAnswerquestioId(String answerquestioId) {
        mAnswerquestioId = answerquestioId;
    }
}
