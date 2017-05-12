package edu.pitt.cs.cs1635.studybuddies;

import java.io.Serializable;

public class StudyGroup implements Serializable {
    private String name;
    private String time;
    private String floor;
    private String duration;

    public StudyGroup() {
    }

    public StudyGroup(String n) {
        name = n;
        time = "2";
        floor = "Hillman Library, Floor 3";
        duration = "3";
    }
    public StudyGroup(String n, String f, String t, String d) {
        name = n;
        time = t;
        floor = f;
        duration = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String t)
    {
        this.time = t;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String f)
    {
        this.floor = f;
    }

    public void setDuration(String d){this.duration = d;}

    public String getDuration(){return duration;}
}
