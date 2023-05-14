package com.example.licentaapp.Fragmente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.MesajeAdapter;
import com.example.licentaapp.PDFAdapter;
import com.example.licentaapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ForumFragment extends Fragment {

    ListView listViewMesaje;
    DatabaseReference reference;
    FirebaseUser user;
    ArrayList<String> mesajeList=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ImageButton sendBtn;
    TextInputLayout mesajInput;
    TextView tvUtilizator;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_forum, container, false);
        listViewMesaje=view.findViewById(R.id.listViewMesaje);
        sendBtn=view.findViewById(R.id.sendMsj_btn);
        mesajInput=view.findViewById(R.id.til_inputmesaj);
        //adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,mesajeList);
        MesajeAdapter adapter=new MesajeAdapter(getContext(),R.layout.adapter_mesaj,mesajeList);
        listViewMesaje.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("Mesaje");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(currentTime);
                String text=mesajInput.getEditText().getText().toString();
                    mesajeList.add(text);
                    //reference = FirebaseDatabase.getInstance().getReference().child("Mesaje");
                     listViewMesaje.setAdapter(adapter);
                    reference.setValue(mesajeList);
                    adapter.notifyDataSetChanged();

            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=""+snapshot.getValue();
                if (!mesajeList.contains(value)) {
                    mesajeList.add(value);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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