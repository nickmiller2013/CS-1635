package edu.pitt.cs.cs1635.studybuddies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jason on 3/15/2017.
 */

public class GroupList implements Serializable{
    private ArrayList<Group> groups = new ArrayList<Group>();

    public GroupList(){

    }
    public void add(Group g){
        groups.add(g);
    }
    public Group get(int n){
        return groups.get(n);
    }
    public ArrayList<Group> getGroupArrayList(){
        return groups;
    }
    public int size(){
        return groups.size();
    }
    public Group getGroupByName(String name){
        Group result = null;
        for(Group g : groups){
            if (g.toString().equals(name)){
                result = g;
                break;
            }
        }
        return result;
    }
}
