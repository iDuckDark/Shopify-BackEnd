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

            if(position==0){
                picture.setImageResource(R.drawable.house);
            }
            if(position==1){
                picture.setImageResource(R.drawable.company);
            }
            if(position==2){
                picture.setImageResource(R.drawable.livingroom);
            }
            if(position==3){
                picture.setImageResource(R.drawable.meetingroom);
            }
            if(position==4){
                picture.setImageResource(R.drawable.kitchen);
            }
            if(position==5){
                picture.setImageResource(R.drawable.oven);
            }
            if(position==6){
                picture.setImageResource(R.drawable.table);
            }
            if(position==7){
                picture.setImageResource(R.drawable.hr);
            }
            if(position==8){
                picture.setImageResource(R.drawable.computer);
            }
            if(position==9){
                picture.setImageResource(R.drawable.cpu);
            }
            if(position==10){
                picture.setImageResource(R.drawable.motherboard);
            }
            if(position==11){
                picture.setImageResource(R.drawable.peripherals);
            }
            if(position==12){
                picture.setImageResource(R.drawable.mouse);
            }
            if(position==13){
                picture.setImageResource(R.drawable.keyboard);
            }
            if(position==14){
                picture.setImageResource(R.drawable.chair);
            }





        //Picasso.with(this.context).load(product.getImage()).into(picture);

        //added to search the product names?
        //getFilter();

        // Return the completed view to render on screen
        return convertView;
    }
}
