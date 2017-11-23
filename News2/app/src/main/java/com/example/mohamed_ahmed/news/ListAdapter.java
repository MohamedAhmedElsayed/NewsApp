package com.example.mohamed_ahmed.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<ListItem> NewsList;
    private Context context;
    private int resource;

    public ListAdapter(List<ListItem> newsList, Context context, int resource) {
        NewsList = newsList;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return NewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return NewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, parent, false);
        TextView type = (TextView) v.findViewById(R.id.Type);
        TextView WebPublicationDate = (TextView) v.findViewById(R.id.Date);
        TextView WebTitle = (TextView) v.findViewById(R.id.WebTitle);
        TextView SectionName = (TextView) v.findViewById(R.id.SectionName);
        type.setText(NewsList.get(position).getType());
        WebPublicationDate.setText(NewsList.get(position).getWebPublicationDate());
        WebTitle.setText(NewsList.get(position).getWebTitle());
        SectionName.setText(NewsList.get(position).getSectionName());
        return v;
    }
}
