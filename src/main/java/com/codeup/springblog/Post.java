package com.codeup.springblog;

public class Post {
    //  PROPERTIES
    private long id;
    private String title;
    private String body;

    //  CONSTRUCTORS
    public Post(){}

    public Post(String title, String body){
        this.title = title;
        this.body = body;
    }

    public Post(long id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
    }

    //  GETTERS/SETTERS
    public long getId(){return id;}
    public void setId(long id){this.id = id;}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}