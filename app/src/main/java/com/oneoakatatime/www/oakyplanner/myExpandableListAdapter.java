package com.oneoakatatime.www.oakyplanner;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 6/14/2017.
 */

public class myExpandableListAdapter implements ExpandableListAdapter  {
    Context context;
    List weekDays;
    Map <String,List<weekly_view.transferables>> week_events;

    public myExpandableListAdapter(Context context, List<String> weekDays, Map<String, List<weekly_view.transferables>> week_events) {
        this.context = context;
        this.weekDays = weekDays;
        this.week_events = week_events;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return weekDays.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return week_events.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return weekDays.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return week_events.get(weekDays.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String day = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.daily_listview_header,null);
        }
        //TODO NOT SOLD ON THE LAYOUT, CHECK THAT THIS DOES NOT SCREW WITH ANYTHING
        TextView txtParent = (TextView) convertView.findViewById(R.id.daily_listView_header);
        txtParent.setText(day);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        weekly_view.transferables day_event = (weekly_view.transferables) getChild(groupPosition,childPosition);
        if(convertView == null){
            //TODO NOT SOLD ON THE LAYOUT, CHECK THAT THIS DOES NOT SCREW WITH ANYTHING
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.daily_list_view_row,null);
        }
        TextView hours = (TextView) convertView.findViewById(R.id.daily_hours);
        TextView minutes = (TextView) convertView.findViewById(R.id.daily_minutes);
        TextView place = (TextView) convertView.findViewById(R.id.daily_place);
        TextView description = (TextView) convertView.findViewById(R.id.daily_description);
        hours.setText(day_event.Hour);
        minutes.setText(day_event.Minute);
        place.setText(day_event.Place);
        description.setText(day_event.Description);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
