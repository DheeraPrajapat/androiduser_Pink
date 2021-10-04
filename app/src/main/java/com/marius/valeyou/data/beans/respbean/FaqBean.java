package com.marius.valeyou.data.beans.respbean;

public class FaqBean {

    /**
     * id : 3
     * question : Where can I download earlier versions?
     * answer : You have access to all previous free versions of all extensions on this website.
     */

    private int id;
    private String question;
    private String answer;
    private boolean check;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
