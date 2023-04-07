package com.example.voghdev_pdfviewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageActivity extends AppCompatActivity {

    RecyclerView rv;
    MessageAdapter adapter;
    FirebaseRecyclerOptions<MessageModel> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rv = findViewById(R.id.message_rv);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference rf = db.getReference("message");

        options = new FirebaseRecyclerOptions.Builder<MessageModel>()
                .setQuery(rf,MessageModel.class)
                .build();

        Toast.makeText(this, options.toString(), Toast.LENGTH_SHORT).show();

        if (options != null){
            rv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MessageAdapter(options);
            adapter.startListening();
            rv.setAdapter(adapter);
        }



    }
}