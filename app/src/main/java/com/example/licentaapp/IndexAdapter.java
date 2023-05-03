package com.example.licentaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class IndexAdapter extends ArrayAdapter<String>{
    private Context mContext;
    private ArrayList<String> mItems;

    public IndexAdapter(Context context,int resource, ArrayList<String> lista) {
        super(context,resource, lista);
        mContext = context;
        mItems = lista;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.adapter_index, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.row_index);
        textView.setText(mItems.get(position));

        return convertView;
    }

//    private Context context;
//    private int resource;
//    private List<String> lista;
//    private LayoutInflater inflater;
//
//    public IndexAdapter(@NonNull Context context,int resource, List<String>lista) {
//        super(context,resource, lista);
//        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_index, parent, false);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.text.setText(getItem(position));
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        private final TextView text;
//        public ViewHolder(View view) {
//            text = (TextView) view.findViewById(R.id.row_index);
//
//        }
//    }
}
