package com.example.licentaapp.Clase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Client {
    public String id,codClient,adresa,email,sumaPlata,nrAp;
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("Users");


    public Client() {
    }

    public Client(String id, String codClient, String adresa, String email, String sumaPlata, String nrAp) {
        this.id = id;
        this.codClient = codClient;
        this.adresa = adresa;
        this.email = email;
        this.sumaPlata = sumaPlata;
        this.nrAp = nrAp;
    }

//    public Client(String codClient, String adresa, String sumaPlata, String nrAp) {
//        this.codClient = codClient;
//        this.adresa = adresa;
//        this.sumaPlata = sumaPlata;
//        this.nrAp = nrAp;
//    }

    public Client(String adresa, String sumaPlata, String nrAp) {
        this.adresa = adresa;
        this.sumaPlata = sumaPlata;
        this.nrAp = nrAp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodClient() {
        return codClient;
    }

    public void setCodClient(String codClient) {
        this.codClient = codClient;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSumaPlata() {
        return sumaPlata;
    }

    public void setSumaPlata(String sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getNrAp() {
        return nrAp;
    }

    public void setNrAp(String nrAp) {
        this.nrAp = nrAp;
    }

//    DatabaseReference usersRef = ref.child("Users");
//
//    Map<String, Client> clientMap = new HashMap<>();
//    usersRef.child("Users").setValueAsync(new Client("June 23, 1912", "Alan Turing"));
//    usersRef.child("gracehop").setValueAsync(new User("December 9, 1906", "Grace Hopper"));

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("adresa", adresa);
//        result.put("apartament", nrAp);
//        result.put("sumaDePlata", sumaPlata);
//
//        return result;
//    }

//    public void writeNewPost(String userId, String adresa, String nrAp, String sumaPlata) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        String key = ref.child("Users/"+userId+"/").push().getKey();
//        Client client = new Client(adresa,nrAp,sumaPlata);
//        Map<String, Object> postValues = client.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
////        childUpdates.put(" /adresa", "aaa");
////        childUpdates.put("/Users/" + userId + "/" + "adresa",postValues);
//       // ref.child("Users").setValueAsync(new Client("aaa", "Alan Turing","aaa"));
////    usersRef.child("gracehop").setValueAsync(new User("December 9, 1906", "Grace Hopper"));
//        //childUpdates.put("/user-adresa/" + userId + "/" + adresa, postValues);
//        //Users/shoX2Q6uljdbgAmeo95mbEzCsH23/adresa
//        ref.updateChildren(childUpdates);
//    }

}
