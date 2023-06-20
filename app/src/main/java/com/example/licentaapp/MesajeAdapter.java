package com.example.licentaapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MesajeAdapter extends ArrayAdapter<String>  {
    private Context mContext;
    private ArrayList<String> mItems;
    FirebaseUser user;
    DatabaseReference reference;

    public MesajeAdapter(Context context,int resource, ArrayList<String> lista) {
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

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            convertView = inflater.inflate(R.layout.adapter_mesaj, parent, false);
//        }
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(currentTime);
//        SimpleDateFormat df2=new SimpleDateFormat("hh:mm",Locale.getDefault());
//        String formattedTime= df2.format(currentTime);
//
//        TextView textView1 = convertView.findViewById(R.id.tvUtilizator);
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String txt_ap = dataSnapshot.child("apartament").getValue(String.class);
//                textView1.setText(user.getEmail()+" Număr apartament: "+txt_ap);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d(TAG, databaseError.getMessage());
//            }
//        };
//
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
//        reference.addListenerForSingleValueEvent(valueEventListener);
//
//
//       // TextView textView1 = convertView.findViewById(R.id.tvUtilizator);
//        TextView textView2=convertView.findViewById(R.id.tvMesaj);
//        TextView textView3=convertView.findViewById(R.id.tvOraMesaj);
//        textView2.setText(mItems.get(position));
//        textView3.setText("ora:"+formattedTime+"  data:"+formattedDate);
//        //textView1.setText(user.getEmail());
//
//        return convertView;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.adapter_mesaj, parent, false);
        }

        TextView textView1 = convertView.findViewById(R.id.tvUtilizator);
        TextView textView2 = convertView.findViewById(R.id.tvMesaj);
        TextView textView3 = convertView.findViewById(R.id.tvOraMesaj);

        String message = mItems.get(position);
        String[] messageParts = message.split(" \\| ");

        if (messageParts.length == 3) {
            String email = messageParts[0];
            String date = messageParts[1];
            String content = messageParts[2];

            textView1.setText(email);
            textView2.setText(content);
            textView3.setText(date);
        }

        return convertView;
    }

}


