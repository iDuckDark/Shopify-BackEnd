package com.example.idarkduck.shopify_backend;

import java.util.ArrayList;

/**
 * Created by iDarkDuck on 1/8/18.
 */

public class Menu {


    String id;
    String data;
    ArrayList<String> childIDList;
    ArrayList<String> parentIDList;

    Menu(String id, String data){
        this.id = id;
        this.data = data;
        childIDList =  new ArrayList<String>();
        parentIDList = new ArrayList<String>();
    }
    Menu(String id, String data, ArrayList<String> childIDList, ArrayList<String> parentIDList){
        this.id = id;
        this.data = data;
        this.childIDList = childIDList;
        this.parentIDList = parentIDList;
    }

    public String toString(){ String s="";
        s += " ID : " + id +'\n';
        s += " data: " + data +'\n';
        s += " parent_id: " + parentIDList.toString();
        s += " child_id: " + childIDList.toString();
        return  s;
    }

    protected void setId(String id){
        this.id=id;
    }
    protected void setData(String data){
        this.data=data;
    }
    protected void setChildID(ArrayList<String> childIDList){
        this.childIDList=childIDList;
    }
    protected void addChildID(String childID){
        childIDList.add(childID);
    }
    protected void setParentID(ArrayList<String> parentIDList){
        this.parentIDList = parentIDList;
    }
    protected void AddParentID(String parentIDList){
        this.parentIDList.add(parentIDList);
    }
    protected String getId(){
        return id;
    }
    protected String getData(){
        return data;
    }
    protected ArrayList<String> getChildIDList(){
        return childIDList;
    }
    protected ArrayList<String> getParentIDList() { return parentIDList; }
}
