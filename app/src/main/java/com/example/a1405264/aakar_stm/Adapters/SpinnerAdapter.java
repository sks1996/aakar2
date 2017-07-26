package com.example.a1405264.aakar_stm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1405264.aakar_stm.Pro_Detail;
import com.example.a1405264.aakar_stm.R;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;

/**
 * Created by 1405214 on 23-07-2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    public ArrayList<String> Names;
    public SpinnerAdapter(Context context, int textViewResourceId,
                           ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub

        Names=objects;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater= Pro_Detail.THIS_.getLayoutInflater();
        View row=inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label=(TextView)row.findViewById(R.id.USER_NAME);
        label.setText(Names.get(position));

        CheckBox icon=(CheckBox) row.findViewById(R.id.Checked);

       icon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               if(isChecked)
               {
                   Pro_Detail.Tasks.put(position,Names.get(position));
               }
               else
               {
                   if(Pro_Detail.Tasks.containsKey(position))
                   {
                       Pro_Detail.Tasks.remove(position);
                   }
               }
           }
       });

        return row;
    }
}