package com.example.chatapp;

public class Message {

    private String message;
    private String time;
    private String name;

    public Message(){}
    public Message(String message ,String date , String name){

        this.message = message;
        this.time = date;
        this.name = name;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    /*
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }*/

    /*
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }*/
}
