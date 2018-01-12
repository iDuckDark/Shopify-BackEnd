package com.example.idarkduck.shopify_backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by iDarkDuck on 1/12/18.
 */

public class MenuAdapter extends ArrayAdapter<Menu> {
    private final Context context;
    private ArrayList<Menu> menus;
    //for search
    private ArrayList<Menu> filteredList;


    public MenuAdapter(Context context, ArrayList<Menu> menus) {
        super(context, R.layout.menu_adapter_layout, menus);
        this.context = context;
        this.menus = menus;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //reference
        //final ViewHolder holder;
        // Get the data item for this position
        Menu menu = getItem(position);
        //filteredList.add(product.getProductName());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_adapter_layout, parent, false);
            //reference used
//            holder = new ViewHolder();
//            holder.name = (TextView) convertView.findViewById(R.id.txtName);
//            holder.description = (TextView) convertView.findViewById(R.id.txtDescription);
//            holder.pictureHolder= convertView.findViewById(R.id.imgProduct);
//            convertView.setTag(holder);
        }
//        else {
//            // get view holder back
//            holder = (ViewHolder) convertView.getTag();
//        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.txtName);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        ImageView picture = convertView.findViewById(R.id.imgProduct);
        // Populate the data into the template view using the data object
        tvName.setText(menu.getData());
        if(menu.getChildIDList().size()==0){
            tvDescription.setText("ID: " + menu.getId() + " Children ID: NULL " );
        }else{
            tvDescription.setText("ID: " + menu.getId() + " Children ID: " + menu.getChildIDList().toString());
        }

        picture.setImageResource(R.drawable.chair);
        //Picasso.with(this.context).load(product.getImage()).into(picture);

        //added to search the product names?
        //getFilter();

        // Return the completed view to render on screen
        return convertView;
    }
}
