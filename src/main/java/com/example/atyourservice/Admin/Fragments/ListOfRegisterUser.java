package com.example.atyourservice.Admin.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfRegisterUser extends Fragment {

    View view;
    MyAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<SenderModel> list;
    private ProgressDialog mProgress;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ListOfRegisterUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_of_register_user, container, false);
        requireActivity().setTitle("List Of Register User");
        recyclerView = view.findViewById(R.id.view2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Customers");
        list = new ArrayList<>();

        mProgress = new ProgressDialog(getContext());
        String titleId = "Loading...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        adapter = new MyAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot db : snapshot.getChildren()) {
                    mProgress.show();
                    SenderModel senderModel = db.getValue(SenderModel.class);
                    list.add(senderModel);
                }
                adapter.notifyDataSetChanged();
                mProgress.dismiss();
                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}