package com.example.idarkduck.shopify_backend;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Nick-JR on 1/11/2018.
 */

class ResponseJson {
    String jsonString;
    JSONObject valid;

    public ResponseJson() {
        try {
            valid = new JSONObject();
            jsonString = new JSONObject()
                        .put("valid_menus", new JSONArray()).toString();
        } catch (JSONException e) {

        }
    }

    // Sets the menu of an item key valid.
    protected void addValidMenu(ArrayList<Menu> menu) {
        try {
            valid.put("root_id", menu.get(0).getId());
            JSONArray children = new JSONArray();

            for (int i = 1; i < menu.size(); i++) {
                children.put(i - 1, menu.get(i).getId());
            }
            valid.put("children", children);

            jsonString = new JSONObject().put("valid_menus", valid).toString();


            System.out.println(jsonString);
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    // Sets the menu of an item key invalid.
    protected void addInvalidMenu(ArrayList<Menu> menu) {

    }
}
