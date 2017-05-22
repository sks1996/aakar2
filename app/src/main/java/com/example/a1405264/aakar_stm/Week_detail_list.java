package com.example.a1405264.aakar_stm;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 1405264 on 5/16/2017.
 */

public class Week_detail_list extends ArrayAdapter<Week_detail>
{

    private Activity context;

    private List<Week_detail> week_details;

    public Week_detail_list(Activity context, List<Week_detail> week_details)
    {
        super(context,R.layout.activity_weekly_update,week_details);
        this.context=context;
        this.week_details=week_details;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listviewitems=inflater.inflate(R.layout.list_layout ,null,true);

        TextView tv=(TextView)listviewitems.findViewById(R.id.tvl1);
        TextView tv1=(TextView)listviewitems.findViewById(R.id.tvl2);

        Week_detail week_detail= week_details.get(position);


        tv.setText(week_detail.getWork());

        tv1.setText(week_detail.getDate());
        return  listviewitems;


    }
}
