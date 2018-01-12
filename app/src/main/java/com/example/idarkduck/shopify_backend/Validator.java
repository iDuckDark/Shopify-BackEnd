package com.example.idarkduck.shopify_backend;

import java.util.ArrayList;

/**
 * Created by iDarkDuck on 1/11/18.
 */

public class Validator {

    ArrayList<Menu> menus;
    TreeNode<String> treeNode;
    ArrayList<String> validMenus_ID;
    ArrayList<String> invalidMenus_ID;


    Validator(ArrayList<Menu> menus){
        this.menus=menus;
        validMenus_ID = new ArrayList<>();
        invalidMenus_ID = new ArrayList<>();
        buildTree();

    }
    public ArrayList<String> validMenus_ID(){
        return validMenus_ID;
    }
    public ArrayList<String> getInvalidMenus_ID(){
        return invalidMenus_ID;
    }

    private void buildTree(){

        for(int i=0; i<menus.size(); i++){
            if(treeNode==null || i==0){
                treeNode = new TreeNode<>(menus.get(0).getId());
                validMenus_ID.add(menus.get(0).getId());
            }
            else{
                //if contains
                if(treeNode.searchTreeNode(menus.get(i).getId())){
                    invalidMenus_ID.add(menus.get(i).getId());
                }
                else{
                    treeNode.addChild(menus.get(i).getId());
                    validMenus_ID.add(menus.get(i).getId());
                    //add the child from id i
                    ArrayList<String> childList =menus.get(i).getChildIDListString();
                    for(int j=0; j<childList.size() ; j++){
                        if(!treeNode.searchTreeNode(childList.get(j))){
                            treeNode.addChild(childList.get(j));
                            validMenus_ID.add(menus.get(0).getId());
                        }
                        else{
                            invalidMenus_ID.add(menus.get(j).getId());
                        }
                    }
                }


            }
        }
    }





    }


