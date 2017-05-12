package edu.pitt.cs.cs1635.studybuddies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Louie on 3/13/17.
 */

public class Group implements Serializable{
    String name = "";
    String professor ="";
    int gid;
    int numMembers = 0;
    int maxMembers = 0;
    boolean isFavorite = false;
    public ArrayList<GroupQuestion> questionList = new ArrayList<>();
    private ArrayList<StudyGroup> studyGroupList = new ArrayList<>();
    int c1 = 0;
    int c2 = 0;


    public Group(){
    }

    public Group(String n){
        name = n;
    }

    @Override
    public String toString(){
        return name;
    }


    public String getName(){
        return name;
    }
    public void setName(String n){
        name = n;
    }
    public int getGID(){
        return gid;
    }

    public int getNumMembers(){
        return numMembers;
    }
    public void setNumMembers(int n){
        numMembers = n;
    }
    public void addMember(){
        System.out.println("In add member.");
        numMembers++;
    }
    public void removeMember(){
        numMembers--;
    }

    public int getMaxMembers(){
        return maxMembers;
    }
    public void setMaxMembers(int n){
        maxMembers = n;
    }

    public void setProfessor(String p){
        professor = p;
    }
    public String getProfessor(){
        return professor;
    }

    public ArrayList<GroupQuestion> getQuestionList() {
        return questionList;
    }

    public void addQuestion(GroupQuestion gq){
        questionList.add(gq);
    }

    public ArrayList<StudyGroup> getStudyGroupList() {
        return studyGroupList;
    }

    public void addStudyGroup(StudyGroup st){
        studyGroupList.add(st);
    }

    public void setFavorite(){isFavorite = true;}

    public void removeFavorite(){isFavorite = false;}

    @Override
    public boolean equals(Object other){
        if(other instanceof Group) {
            return this.toString().equals(other.toString());
        }
        return false;
    }
}
