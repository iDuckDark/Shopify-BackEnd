package com.example.idarkduck.shopify_backend;

import java.util.ArrayList;

/**
 * Created by iDarkDuck on 1/8/18.
 */

public class Menu {

    int id;
    int parentID;
    String data;
    ArrayList<Integer> childIDList;
    ArrayList<String> childIDListString;

    Menu(int id, String data){
        this.id = id;
        this.data = data;
        parentID = -1;
        childIDList =  new ArrayList<>();
    }
    Menu(int id, String data, int parentID, ArrayList<Integer> childIDList){
        this.id = id;
        this.data = data;
        this.parentID = parentID;
        this.childIDList = childIDList;
        makeString();
    }
    private void makeString(){
        childIDListString = new ArrayList<>();
        for(int i=0; i<childIDList.size() ; i++){
            childIDListString.add(Integer.toString(childIDList.get(i)));
        }
    }

    public String toString(){ String s="";
        s += " ID : " + id +'\n';
        s += " data: " + data +'\n';

        if (parentID != -1)
            s += " parent_id: " + parentID;

        s += " child_id: " + childIDList.toString();
        return  s;
    }

    protected void setId(int id){
        this.id = id;
    }
    protected void setData(String data){
        this.data=data;
    }
    protected void setChildID(ArrayList<Integer> childIDList){
        this.childIDList = childIDList;
    }
    protected void addChildID(int childID){
        childIDList.add(childID);
    }
    protected void setParentID(int parentID){
        this.parentID = parentID;
    }
    protected int getId(){
        return id;
    }
    protected String getData(){
        return data;
    }
    protected ArrayList<Integer> getChildIDList(){
        return childIDList;
    }
    protected ArrayList<String> getChildIDListString(){
        return childIDListString;
    }
    protected int getParentID() { return parentID; }
}
