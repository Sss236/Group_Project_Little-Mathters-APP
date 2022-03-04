package edu.neu.madcourse.mathters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mathters.R;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://littlemathters-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regName = findViewById(R.id.regname);
        final EditText nickName = findViewById(R.id.nickname);
        final EditText regPassword = findViewById(R.id.regpassword);
        final EditText conPassword = findViewById(R.id.conpassword);

        final Button registerBtn = findViewById(R.id.registerButton);
        final TextView login = (TextView)findViewById(R.id.loginNow);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                RegisterActivity.this.finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nicknameTxt = nickName.getText().toString();
                final String passwordTxt = regPassword.getText().toString();
                final String usernameTxt = regName.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                if(nicknameTxt.isEmpty() || passwordTxt.isEmpty() || usernameTxt.isEmpty() || conPasswordTxt.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if (!passwordTxt.equals(conPasswordTxt)) {
                    Toast.makeText(RegisterActivity.this, "Oops! Passwords are not matching!", Toast.LENGTH_LONG).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernameTxt)) {
                                Toast.makeText(RegisterActivity.this, "Username already exists.", Toast.LENGTH_LONG).show();
                            } else {
                                databaseReference.child("users").child(usernameTxt).child("nickname").setValue(nicknameTxt);
                                databaseReference.child("users").child(usernameTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}