package edu.neu.madcourse.mathters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import mathters.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<OneRecord> recordList;

    DatabaseReference dbHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordList = new ArrayList<OneRecord>();
        adapter = new HistoryAdapter(this, recordList);
        recyclerView.setAdapter(adapter);


        dbHistory = FirebaseDatabase.getInstance().getReference().child("users").child(UserDetails.username).child("score");

        Query query = dbHistory.limitToLast(10);
        query.addListenerForSingleValueEvent(valueEventListener);

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            recordList.clear();
//            if (dataSnapshot.child(UserDetails.username).hasChild("score")) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                OneRecord record = snapshot.getValue(OneRecord.class);
                recordList.add(record);
            }
            adapter.notifyDataSetChanged();
//            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}