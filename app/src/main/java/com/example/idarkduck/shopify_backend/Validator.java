package com.example.idarkduck.shopify_backend;

import java.util.ArrayList;

/**
 * Created by iDarkDuck on 1/11/18.
 */

public class Validator {

    ArrayList<Menu> menus;
    TreeNode<String> treeNode;

    Validator(ArrayList<Menu> menus){
        this.menus=menus;

    }

    private void buildTree(){

        for(int i=0; i<menus.size(); i++){
            if(treeNode==null){
                treeNode = new TreeNode<>(menus.get(0).getId());
            }
            else{
                treeNode.addChild(menus.get(i).getId());
            }
        }
    }

    Comparable<String> searchCriteria = new Comparable<String>() {
        @Override
        public int compareTo(String treeData) {
            if (treeData == null)
                return 1;
            boolean nodeOk = treeData.contains("210");
            return nodeOk ? 0 : 1;
        }
    };



    }


