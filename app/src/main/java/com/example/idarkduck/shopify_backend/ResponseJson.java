package com.example.idarkduck.shopify_backend;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Nick-JR on 1/11/2018.
 */

public final class ResponseJson {
    private static volatile ResponseJson instance = null;

    String jsonString;
    JSONArray valid;
    JSONArray invalid;

    private ResponseJson() {
        try {
            valid = new JSONArray();
            invalid = new JSONArray();
            jsonString = new JSONObject()
                        .put("valid_menus", new JSONArray()).toString();
        } catch (JSONException e) {

        }
    }

    // Sets the menu of an item key valid.
    protected void addValidMenu(ArrayList<Menu> menu) {
        try {
            JSONArray jsonArray = new JSONArray();
            JSONArray children = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("root_id", menu.get(0).getId());

            System.out.println("menu size: " + menu.size());
            for (int i = 1; i < menu.size(); i++) {
                children.put(menu.get(i).getId());
                System.out.println("children" + children.toString());
            }
            jo.put("children", children);
            valid.put(jo);

            jsonString = new JSONObject().put("valid_menus", valid).toString();


            System.out.println(jsonString);
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    // Sets the menu of an item key invalid.
    protected void addInvalidMenu(ArrayList<Menu> menu) {
        try {
            JSONObject jo = new JSONObject();
            jo.put("root_id", menu.get(0).getId());
            JSONArray children = new JSONArray();

            System.out.println("menu size: " + menu.size());
            for (int i = 1; i < menu.size(); i++) {
                children.put(menu.get(i).getId());
                System.out.println("children" + children.toString());
            }
            jo.put("children", children);
            invalid.put(jo);

            jsonString = new JSONObject().put("invalid_menus", invalid).toString();

            System.out.println(jsonString);
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    // Returns singleton instance.
    protected static ResponseJson getInstance() {
        // Initalize instance.
        if (instance == null){
            synchronized (ResponseJson.class){
                if (instance == null){
                    instance = new ResponseJson();
                }
            }
        }
        return instance;
    }
}
