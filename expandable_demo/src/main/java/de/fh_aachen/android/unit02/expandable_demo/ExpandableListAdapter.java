package de.fh_aachen.android.unit02.expandable_demo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context context;
    private List<String> dataHeader;
    private HashMap<String, List<String>> dataContent;
 
    public ExpandableListAdapter(Context context,
                                 List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.dataHeader = listDataHeader;
        this.dataContent = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.dataContent.get(this.dataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
 
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
 
        String text = (String)getChild(groupPosition, childPosition);
        TextView tv = convertView.findViewById(R.id.lblListItem);
        tv.setText(text);
        
        CheckBox cb = convertView.findViewById(R.id.checkBoxLast);
        cb.setChecked(isLastChild);
        
        Button btn = convertView.findViewById(R.id.button1);
        btn.setTag(""+groupPosition+","+childPosition);

        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.dataContent.get(this.dataHeader.get(groupPosition)).size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this.dataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this.dataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
 
        String text = (String)getGroup(groupPosition);
        TextView tv = convertView.findViewById(R.id.lblListHeader);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(text);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
