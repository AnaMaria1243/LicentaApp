package com.example.licentaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PDFAdminAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> mItems;

    public PDFAdminAdapter(Context context,int resource, ArrayList<String> lista) {
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
            convertView = inflater.inflate(R.layout.adapter_pdf_admin, parent, false);
        }
        

        return convertView;
    }
}
