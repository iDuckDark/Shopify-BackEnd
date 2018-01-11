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
    public void setId(String id){
        this.id=id;
    }
    public void setData(String data){
        this.data=data;
    }
    public void setChildID(ArrayList<String> childIDList){
        this.childIDList=childIDList;
    }
    public void addChildID(String childID){
        childIDList.add(childID);
    }
    public void setParentID(ArrayList<String> parentIDList){
        this.parentIDList = parentIDList;
    }
    public void AddParentID(String parentIDList){
        this.parentIDList.add(parentIDList);
    }
    public String getId(){
        return id;
    }
    public String getData(){
        return data;
    }
    public ArrayList<String> getChildIDList(){
        return childIDList;
    }
    public ArrayList<String> getParentIDList() { return parentIDList; }
}
