package edu.neu.madcourse.mathters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import mathters.R;

public class MainActivity extends AppCompatActivity {
    private TextView title, totalCountView, consecutiveCountView;
    private Button start;
    private Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.main_title);
        start = findViewById(R.id.ma_startB);
        history = findViewById(R.id.historyButton);
        totalCountView = findViewById(R.id.totalCount);
        consecutiveCountView = findViewById(R.id.consecutiveCount);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        title.setTypeface(typeface);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizHistory.class);
                startActivity(intent);
            }
        });
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        Date now = new Date();
        String todayDate = df.format(now);

        calendar.add(Calendar.DATE, -1);
        String yesterdayDate = df.format(calendar.getTime());


        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        String today = preferences.getString("todayDate", todayDate);
        String yesterday = preferences.getString("yesterdayDate", yesterdayDate);
        String lastLoginDate = preferences.getString("lastLoginDate", "1980-01-01");
        SharedPreferences.Editor editor = preferences.edit();
        int totalCount = preferences.getInt("totalCount", 0);
        int consecutiveCount = preferences.getInt("consecutiveCount", 0);



        if (!today.equals(lastLoginDate) && !yesterday.equals(lastLoginDate)) {
            Toast.makeText(this, "Daily reward!", Toast.LENGTH_SHORT).show();
            editor.putString("lastLoginDate", today);
            editor.putInt("consecutiveCount", 1);
            editor.putInt("totalCount", totalCount + 1);
            editor.apply();
            totalCountView.setText("Total hardwork days: " + preferences.getInt("totalCount", 0));
            consecutiveCountView.setText("Consecutive study days: " + preferences.getInt("consecutiveCount", 0));

        } else if (!today.equals(lastLoginDate) && yesterday.equals(lastLoginDate)) {
            Toast.makeText(this, "Daily reward!", Toast.LENGTH_SHORT).show();
            editor.putString("lastLoginDate", today);
            editor.putInt("consecutiveCount", consecutiveCount + 1);
            editor.putInt("totalCount", totalCount + 1);
            editor.apply();
            totalCountView.setText("Total hardwork days: " + preferences.getInt("totalCount", 0));
            consecutiveCountView.setText("Consecutive study days: " + preferences.getInt("consecutiveCount", 0));

        } else {
            totalCountView.setText("Total hardwork days: " + preferences.getInt("totalCount", 0));
            consecutiveCountView.setText("Consecutive study days: " + preferences.getInt("consecutiveCount", 0));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, StartMenu.class);
        MainActivity.this.finish();
        startActivity(intent);
    }
}
