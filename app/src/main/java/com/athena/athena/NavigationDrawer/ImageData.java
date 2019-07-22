package com.athena.athena.NavigationDrawer;


public class ImageData {
   private String subject;
   private int color;


    public ImageData(String subject, int color){
        this.subject=subject;
        this.color=color;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
