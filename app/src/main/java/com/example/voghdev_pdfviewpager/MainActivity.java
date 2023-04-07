package com.example.voghdev_pdfviewpager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    PdfListAdapter pdfListAdapter;
    ProgressBar progressBar;
    EditText t1,t2,t3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.main_node);
        t2 = findViewById(R.id.first_child);
        t3 = findViewById(R.id.second_child);

        Button show_btn = findViewById(R.id.show);
        Button message_btn = findViewById(R.id.message);

        show_btn.setOnClickListener(v -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(t1.getText().toString()).child(t2.getText().toString()).child(t3.getText().toString());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String value = snapshot.getValue(String.class);
                    Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        ArrayList<PdfListModel> list = new ArrayList<>();

        rv = findViewById(R.id.rv);

        pdfListAdapter = new PdfListAdapter(list,this);



        FirebaseDatabase.getInstance().getReference().child("list")
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                   PdfListModel pdfListModel = snapshot1.getValue(PdfListModel.class);
                   list.add(pdfListModel);
                }
                pdfListAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        rv.setAdapter(pdfListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);


        message_btn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this,MessageActivity.class)));


    }
}