package com.sp.mvc.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {

    @NotEmpty
    private int id;

    //@NotEmpty
    //private LocalDate timeDay;

    @NotEmpty
    private LocalDateTime time;

    @NotEmpty
    private int personId;

    private String personName;

    @NotEmpty
    @Size(min=0, max=160, message = "something is going not right!!!")
    private String text;

    public Message() {
        LocalDateTime localDateTime = LocalDateTime.now();
                //.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        //LocalDateTime localTime = LocalDateTime.from(LocalTime.now());

        setTime(localDateTime);
        setId(0);
        setPersonId(0);
        setText("");
        //this.setText("empty message");
    }

    public Message(int id, LocalDateTime time, int personId, String text) {
        setId(id);
        setTime(time);
        setPersonId(personId);
        setText(text);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
