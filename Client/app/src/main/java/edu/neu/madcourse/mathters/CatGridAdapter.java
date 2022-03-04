package edu.neu.madcourse.mathters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import mathters.R;

public class CatGridAdapter extends BaseAdapter {

    private List<CategoryModel> catList;

    public CatGridAdapter(List<CategoryModel> catList) {
        this.catList = catList;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        View view;

        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);
        }
        else
        {
            view = convertView;
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SplashActivity.selected_cat_index = position;

//                switch (position) {
//                    case 0:
//                        UserDetails.category = "3~6 years old";
//                        break;
//                    case 1:
//                        UserDetails.category = "6~9 years old";
//                        break;
//                    case 2:
//                        UserDetails.category = "9~12 years old";
//                        break;
//
//                }
                UserDetails.category = String.valueOf(position+1);
                Intent intent = new Intent(parent.getContext(),SetsActivity.class);
                parent.getContext().startActivity(intent);
            }
        });


        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(position).getName());


        int[] rgb = new int[3];
        for(int i = 0; i < 3; i++) {
            Random rnd = new Random();
            if(rnd.nextInt() > 200) {
                rgb[i] = rnd.nextInt();
            } else {
                rgb[i] = 204;
            }
        }
        int color = Color.argb(255, rgb[0],rgb[1],rgb[2]);
        view.setBackgroundColor(color);
        int paddingDp = 25;
        float density = view.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        view.setPadding(100,1,100,3);

        return view;
    }
}
