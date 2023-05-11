package com.example.licentaapp.Fragmente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.licentaapp.IndexAdapter;
import com.example.licentaapp.R;
import com.example.licentaapp.TransactionsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PayFragment extends Fragment {
    DatabaseReference reference;
    FirebaseUser user;
    TextView textViewTitle;
    ListView listViewTransaction;
    Spinner spinnerYear;
    ArrayList<String> transactionsList=new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pay, container, false);


        textViewTitle=view.findViewById(R.id.txtViewTitle);
        listViewTransaction=view.findViewById(R.id.listViewTransaction);
       // adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,transactionsList);
        TransactionsAdapter adapter=new TransactionsAdapter(getContext(),R.layout.adapter_transactions,transactionsList);
        listViewTransaction.setAdapter(adapter);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        reference.child("istoricPlăți").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String value=""+snapshot.getValue();
                if (!transactionsList.contains(value)) {
                    transactionsList.add(value);
                    adapter.notifyDataSetChanged();
                }
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


        return view;
    }
}