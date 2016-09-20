package com.example.sharvani.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharvani.myapplication.models.News;
import com.example.sharvani.myapplication.R;

import java.util.List;

/**
 * Created by sharvani on 2/13/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    public static class ViewHolder {
        TextView tvTitle, tvDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_row, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.attrib_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.attrib_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(news.getmTitle());
        viewHolder.tvDate.setText(news.getmDate());
        return convertView;
    }
}