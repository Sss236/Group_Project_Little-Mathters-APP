package edu.neu.madcourse.mathters;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mathters.R;

public class ScoreActivity extends AppCompatActivity {

    private TextView score;
    private Button done;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://littlemathters-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.sa_done);

        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);
        UserDetails.userscore = score_str;
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<String, String>();
                String time = new SimpleDateFormat().format(new Date());
                map.put("time", time);
                map.put("category", UserDetails.category);
                map.put("level", UserDetails.level);
                map.put("score", UserDetails.userscore);

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(UserDetails.username)) {
                            databaseReference.child("users").child(UserDetails.username).child("score").push().setValue(map);

                        } else {
                            Toast.makeText(ScoreActivity.this, "Scores not saved", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(ScoreActivity.this, SetsActivity.class);

                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScoreActivity.this, SetsActivity.class);
        startActivity(intent);
    }
}
