package com.aarambha.searchfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
EditText name, email, phone;
Button saveBtn;
DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
saveData();
            }
        });
    }

    private void saveData() {
        String  names = name.getText().toString();
        String  emails = email.getText().toString();
        String  phones = phone.getText().toString();

        User user = new User(names,emails,phones);
        mref = FirebaseDatabase.getInstance().getReference("users");
        String  UserId = mref.push().getKey();
        mref.child(UserId).setValue(user);

        Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show();
    }
    class User{
        public User(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
public User(){

}
        public String name;
        public String email;
        public String phone;
    }
}