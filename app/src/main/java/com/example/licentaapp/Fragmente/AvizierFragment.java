package com.example.licentaapp.Fragmente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.licentaapp.PDFAdapter;
import com.example.licentaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AvizierFragment extends Fragment {
    ListView listViewPdf;
    DatabaseReference reference;
    FirebaseUser user;
    ArrayList<String> documentsList=new ArrayList<>();
    ArrayAdapter<String> adapter;
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_avizier, container, false);
        listViewPdf=view.findViewById(R.id.listViewPdf);
       // adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,documentsList);
        PDFAdapter adapter=new PDFAdapter(getContext(),R.layout.adapter_pdf,documentsList);
        listViewPdf.setAdapter(adapter);

        try{
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        reference.child("documente").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                    String value=""+snapshot.getValue();
//                if (!transactionsList.contains(value)) {
//                    transactionsList.add(value);
//                    adapter.notifyDataSetChanged();
//                }
//                message = snapshot.getValue(String.class);
//                documentsList.add(message);
//                adapter.notifyDataSetChanged();

                message = snapshot.child("url").getValue(String.class);
                String numePdf = snapshot.child("numePdf").getValue(String.class);
                if(!documentsList.contains(numePdf)){
                documentsList.add(numePdf);
                adapter.notifyDataSetChanged();}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }

       listViewPdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
//               Toasty.success(getContext(),"Document descarcat cu succes!", Toast.LENGTH_SHORT).show();
               startActivity(intent);


           }
       });
        return view;
    }
}