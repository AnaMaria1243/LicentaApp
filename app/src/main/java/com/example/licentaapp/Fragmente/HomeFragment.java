package com.example.licentaapp.Fragmente;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.IndexAdapter;
import com.example.licentaapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class HomeFragment extends Fragment {
    DatabaseReference reference;
    FirebaseUser user;
    TextView adresa,nrApartament,totalPlata,istoricIndex;
    Button payHomeBtn,sendIndex;
    TextInputLayout indexInput;
    ListView listView;
    //ArrayAdapter<String>adapter;
    ArrayList<String> indexList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        adresa=view.findViewById(R.id.adresaInput);
        nrApartament=view.findViewById(R.id.nrApartamentInput);
        totalPlata=view.findViewById(R.id.sumaPlataInput);
        payHomeBtn=view.findViewById(R.id.btn_platesteHome);
       // istoricIndex=view.findViewById(R.id.istoricIndexTxtVw);
        sendIndex=view.findViewById(R.id.trimiteIndex);
        indexInput=view.findViewById(R.id.indexInput);
        listView=view.findViewById(R.id.listViewIndex);
        indexList=new ArrayList<>();
       // adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,indexList);
        IndexAdapter adapter=new IndexAdapter(getContext(),R.layout.adapter_index,indexList);
        listView.setAdapter(adapter);





//        sendIndex.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String index=indexInput.getEditText().getText().toString();
//                listaIndex.add(index);
//                listViewIndex.setAdapter(arrayAdapter);
//                arrayAdapter.notifyDataSetChanged();
//            }
//        });


        sendIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(currentTime);
                String text=indexInput.getEditText().getText().toString();
                if(!TextUtils.isEmpty(text)){
                    String text2=text+"  Transmis la data de: "+formattedDate;
                    indexList.add(text2);
                   // listView.setAdapter(adapter);
                    reference.child("index").setValue(indexList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Index adăugat!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Introduceti index!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        payHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, new PayFragment()).commit();
            }
        });

//        istoricIndex.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), IndexActivity.class);
//                startActivity(intent);
//                ((Activity) getActivity()).overridePendingTransition(0, 0);
//            }
//        });
//        try {
//            user= FirebaseAuth.getInstance().getCurrentUser();
//            reference= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
//            reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //Client client=new Client(String id, String codClient, String adresa, String email, String sumaPlata, String nrAp);
//               // String adresa1=snapshot.child("adresa").getValue().toString();
//                //adresa.setText(adresa1);
////                HashMap<String,Object>hashMap=new HashMap<>();
////                hashMap.put("adresa",adresa.getText().toString());
//
//               // Client client=snapshot.getValue(Client.class);
////                //client.writeNewPost("shoX2Q6uljdbgAmeo95mbEzCsH23","aaa","aaa","aaa");
////
////
//                for (DataSnapshot data: snapshot.getChildren()){
//                    String adresa1=""+data.child("adresa").getValue();
//                    String nrappp=""+data.child("apartament").getValue();
//                    String plata=""+data.child("sumaDePlata").getValue();
//
//                    adresa.setText(adresa1);
//                    nrApartament.setText(nrappp);
//                    totalPlata.setText(plata);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String txt_firstname = dataSnapshot.child("adresa").getValue(String.class);
                String txt_lastname = dataSnapshot.child("apartament").getValue(String.class);
                String plata=dataSnapshot.child("sumaDePlata").getValue(String.class);
                adresa.setText(txt_firstname);
                nrApartament.setText(txt_lastname);
                totalPlata.setText(plata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        try {
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
            reference.addListenerForSingleValueEvent(valueEventListener);
            //reference.child("sumaDePlata").setValue("300");
            reference.child("index").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String value=""+snapshot.getValue();
                    if (!indexList.contains(value)) {
                        indexList.add(value);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

}