package com.jacksplwxy.start;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jacksplwxy.start.entity.User;

import java.util.ArrayList;

/**
 * Created by shen on 2015/12/17.
 */
public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> list;
    public UserAdapter(Context context, ArrayList<User> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_user, null);
            viewHolder=new ViewHolder();
            viewHolder.tvUserName=(TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvPassword=(TextView) convertView.findViewById(R.id.tv_password);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tvUserName.setText(list.get(position).username);
        viewHolder.tvPassword.setText(list.get(position).password);
        return convertView;
    }

    static class ViewHolder{
        TextView tvUserName;
        TextView tvPassword;
    }
}
