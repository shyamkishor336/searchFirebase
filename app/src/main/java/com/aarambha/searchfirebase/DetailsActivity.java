package com.aarambha.searchfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.VolumeAutomation;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {
     ViewPager collectionViewPager;
     TabLayout collectionTabLayout;
     String syllabus;
    TextView txtTitle;
     String notes;
     String questionBank;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        collectionViewPager = findViewById(R.id.collectionViewPager);
        collectionTabLayout = findViewById(R.id.collection_details_tablayout);
txtTitle = findViewById(R.id.titleName);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        ref = FirebaseDatabase.getInstance().getReference("users");
        Query query = ref.orderByKey().equalTo(key);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        txtTitle.setText(ds.child("name").getValue(String.class));
                        syllabus = ds.child("name").getValue(String.class);
                       // Log.e("shyam",""+ syllabus);
                        notes= ds.child("email").getValue(String.class);
                       questionBank= ds.child("phone").getValue(String.class);




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(eventListener);

        collectionViewPager.setAdapter(new CollectionDetailAdapter(getSupportFragmentManager(),
                collectionTabLayout.getTabCount(), syllabus,notes,questionBank));

        collectionViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(collectionTabLayout));
        collectionTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                collectionViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}