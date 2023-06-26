package com.example.licentaapp.Fragmente;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.HomeActivity;
import com.example.licentaapp.IndexAdapter;
import com.example.licentaapp.LoginActivity;
import com.example.licentaapp.R;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {
    DatabaseReference reference;
    FirebaseUser user;
    TextView adresa,nrApartament,totalPlata,nrPersoane;
    Button payHomeBtn,sendIndex;
    TextInputLayout indexInput;
    ListView listView;
    //ArrayAdapter<String>adapter;
    ArrayList<String> indexList,payList;


    PaymentSheet paymentSheet;
    String paymentIntentClientSecret,amount;
    PaymentSheet.CustomerConfiguration customerConfig;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        adresa=view.findViewById(R.id.adresaInput);
        nrApartament=view.findViewById(R.id.nrApartamentInput);
        totalPlata=view.findViewById(R.id.sumaPlataInput);
        payHomeBtn=view.findViewById(R.id.btn_platesteHome);
        nrPersoane=view.findViewById(R.id.nrPersoaneInput);
        sendIndex=view.findViewById(R.id.trimiteIndex);
        indexInput=view.findViewById(R.id.indexInput);
        listView=view.findViewById(R.id.listViewIndex);
        indexList=new ArrayList<>();
        payList=new ArrayList<>();
       // adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,indexList);
        IndexAdapter adapter=new IndexAdapter(getContext(),R.layout.adapter_index,indexList);
        listView.setAdapter(adapter);


        payHomeBtn.setOnClickListener(v ->{
            if (TextUtils.isEmpty(totalPlata.getText()
                    .toString())||totalPlata.getText()=="0"){
                Toast.makeText(getContext(), "Nu aveti plăți de făcut!", Toast.LENGTH_SHORT).show();

            } else {
                amount = totalPlata.getText().toString() + "00";
                getDetails();

            }
        });

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);


        sendIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(currentTime);
                String text = indexInput.getEditText().getText().toString();

                if (!TextUtils.isEmpty(text)) {
                    int newIndex = Integer.parseInt(text);
                    boolean maiMare = true;

                    for (String indexItem : indexList) {
                        int previousIndex = extragereIndex(indexItem);

                        if (newIndex <= previousIndex) {
                            maiMare = false;
                            break;
                        }
                    }

                    if (maiMare) {
                        String text2 = text + "  Transmis la data de: " + formattedDate;
                        indexList.add(text2);
                        reference.child("index").setValue(indexList);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Index adăugat!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Indexul introdus trebuie să fie mai mare decât valorile anterioare!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Introduceti index!", Toast.LENGTH_SHORT).show();
                }
            }

            private int extragereIndex(String item) {
                String[] parts = item.split(" ");
                if (parts.length > 0) {
                    try {
                        return Integer.parseInt(parts[0]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                return -1;
            }
        });




        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String txt_firstname = dataSnapshot.child("adresa").getValue(String.class);
                String txt_lastname = dataSnapshot.child("apartament").getValue(String.class);
                String plata=dataSnapshot.child("sumaDePlata").getValue(String.class);
                String nrPers=dataSnapshot.child("nrPersoane").getValue(String.class);
                adresa.setText(txt_firstname);
                nrApartament.setText(txt_lastname);
                totalPlata.setText(plata);
                nrPersoane.setText(nrPers);
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
//            reference.child("nrPersoane").setValue("0");
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

        nrPersoane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.nrpersoaneapartamentedit, null);
                builder.setView(dialogView);

                EditText et_nrPers = dialogView.findViewById(R.id.editTxtNrPers);

                builder.setPositiveButton("Salvează", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = et_nrPers.getText().toString();

                        reference.child("nrPersoane").setValue(value);
                        nrPersoane.setText(value);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Anulează", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void getDetails() {
        Fuel.INSTANCE.post("https://us-central1-licentaapp-5200c.cloudfunctions.net/stripePayment?amt="+amount,null).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                try {
                    JSONObject result=new JSONObject(s);
                    customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getString("customer"),
                            result.getString("ephemeralKey")
                    );
                    paymentIntentClientSecret = result.getString("paymentIntent");
                    PaymentConfiguration.init(getContext(), result.getString("publishableKey"));
                    showStripePaymentSheet();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void failure(@NonNull FuelError fuelError) {

            }
        });


    }



    private void showStripePaymentSheet(){
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("coDeR")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentIntentClientSecret,
                configuration
        );

    }

   private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult){

        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getContext(), "Tranzacție anulată!", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toasty.error(getContext(), "Tranzacție eșuată!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), ((PaymentSheetResult.Failed) paymentSheetResult).getError().toString() , Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            //Toast.makeText(getContext(), "Tranzacție cu succes!", Toast.LENGTH_SHORT).show();
            Toasty.success(getContext(),"Tranzacție cu succes!",Toast.LENGTH_SHORT).show();
            totalPlata.setText("0");
             Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = df.format(currentTime);
            SimpleDateFormat df2=new SimpleDateFormat("hh:mm",Locale.getDefault());
            String formattedTime= df2.format(currentTime);
//            payList.add("Tranzacția: "+customerConfig.getId().toUpperCase()+" din data: "+formattedDate+" ora: "+formattedTime+" a fost inregistrată!");
//            reference.child("istoricPlăți").setValue(payList);

            reference.child("istoricPlăți").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> payList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String transaction = snapshot.getValue(String.class);
                        payList.add(transaction);
                    }
                    payList.add("Tranzacția: "+customerConfig.getId().toUpperCase()+" din data: "+formattedDate+" ora: "+formattedTime+" a fost inregistrată!");
                    reference.child("istoricPlăți").setValue(payList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle potential errors here
                }
            });
        }

    }


}