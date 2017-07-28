package com.oneoakatatime.www.oakyplanner;

import android.database.Cursor;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by AIGARS on 16/07/2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.myViewHolder> {
    ArrayList<row_info> arrayList = new ArrayList<>();
    Fragment1.currentlySelectedDayData currentData;
    DataBaseHelper myDb;
    MyRecyclerAdapter(ArrayList<row_info> arrayList, Fragment1.currentlySelectedDayData currentData)
    {
    this.arrayList = arrayList;
        this.currentData = currentData;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tpd_row,parent,false);
        myViewHolder recyclerViewHolder = new myViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
row_info rowinfo = arrayList.get(position);
        holder.hours.setText(rowinfo.getFrom());
        holder.description.setText(rowinfo.getDescription());
        holder.minutes.setText(rowinfo.getUntil());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
TextView hours,description,minutes;
        public myViewHolder(final View itemView) {
            super(itemView);
            hours = (TextView) itemView.findViewById(R.id.tpd_from);
            minutes = (TextView)itemView.findViewById(R.id.tpd_until);
            description = (TextView)itemView.findViewById(R.id.tpd_description);
            final ImageView imageView = (ImageView) itemView.findViewById(R.id.more_row_info);
            myDb = new DataBaseHelper(itemView.getContext());
           imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(v.getId() == imageView.getId() ){


                   PopupMenu  popupMenu = new PopupMenu(v.getContext(),v);
                   popupMenu.getMenuInflater().inflate(R.menu.row_menu,popupMenu.getMenu());
                       popupMenu.show();
                   popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {
                           switch (item.getItemId()){
                               case R.id.more__row_details: /** stuff**/ getAt(getAdapterPosition()); break;
                               case R.id.delete_event_row: /** stuff**/

                               removeAt(getAdapterPosition());
                                   break;

                           }
                           return false;
                       }
                   });
               }}
           });

        }
    }
    public static class ChangeFragmentToTwoEvent{
        public  int change_to;
        public int id;
        public int selectedYear;
        public int selectedMonth;
        public int selectedDay;

        public ChangeFragmentToTwoEvent(int change_to,int id,int selectedYear,int selectedMonth,int selectedDay) {
            this.change_to = change_to;
            this.id = id;
            this.selectedYear = selectedYear;
            this.selectedMonth = selectedMonth;
            this.selectedDay = selectedDay;
        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        row_info rowinfo = arrayList.get(position);
        int id= rowinfo.getId();
        myDb.deleteEvent(id);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }
    public void getAt(int position){
        row_info rowinfo = arrayList.get(position);
        int id= rowinfo.getId();

        EventBus.getDefault().post(new ChangeFragmentToTwoEvent(1,id,currentData.year,currentData.month,currentData.day));


    }

}
