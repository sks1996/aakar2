package com.example.a1405264.aakar_stm;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public  class Recycler_view_task_adapter extends RecyclerView.Adapter<Recycler_to_do_holder> {

    private List<Task> task;
    protected Context context;

    public Recycler_view_task_adapter(Context context, List<Task> task) {
        this.task = task;
        this.context = context;
    }
    @Override
    public Recycler_to_do_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Recycler_to_do_holder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list, parent, false);
        viewHolder = new Recycler_to_do_holder(layoutView, task);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(Recycler_to_do_holder holder, int position) {
        holder.categoryTitle.setText(task.get(position).getTask());
    }


    @Override
    public int getItemCount() {
        return this.task.size();
    }
}
