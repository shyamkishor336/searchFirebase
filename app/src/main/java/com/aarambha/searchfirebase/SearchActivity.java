package com.aarambha.searchfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    DatabaseReference ref;
    AutoCompleteTextView txtSearch;
    RecyclerView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ref = FirebaseDatabase.getInstance().getReference("users");
        txtSearch = (AutoCompleteTextView) findViewById(R.id.search);
        listView = findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(SearchActivity.this, MainActivity.class));
    }
});
        populateSearch();

    }

    private void populateSearch() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<String> names = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String n = ds.child("name").getValue(String.class);
                        names.add(n);

                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                    txtSearch.setAdapter(adapter);
                    txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                            String selection = (String) parent.getItemAtPosition(position);
                            getUsers(selection);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }

    private void getUsers(String selection) {
        Query query = ref.orderByChild("name").equalTo(selection);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<UserInfo> userInfos = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        UserInfo userInfo = new UserInfo(ds.child("name").getValue(String.class), ds.child("email").getValue(String.class), ds.getKey());
                        userInfos.add(userInfo);
                    }
                    CustomAdapter adapter = new CustomAdapter(userInfos, SearchActivity.this);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }

    class UserInfo {
        public String name;
        public String email;
        public String key;

        public UserInfo(String name, String email, String key) {
            this.name = name;
            this.email = email;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getKey() {
            return key;
        }
    }

    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<UserInfo> localDataSet;
        private Context context;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView email;

            public ViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.txtName);
                email = view.findViewById(R.id.txtEmail);
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         *                by RecyclerView.
         */
        public CustomAdapter(ArrayList<UserInfo> dataSet, Context context) {
            this.localDataSet = dataSet;
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.row_style, parent, false);
            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            UserInfo thisuser = localDataSet.get(position);
            viewHolder.name.setText(thisuser.getName());
            viewHolder.email.setText(thisuser.getEmail());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context,DetailsActivity.class);
                    intent.putExtra("key",thisuser.getKey());
                    context.startActivity(intent);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

}