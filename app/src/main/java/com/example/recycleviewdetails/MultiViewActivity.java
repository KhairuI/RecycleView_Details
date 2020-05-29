package com.example.recycleviewdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MultiViewActivity extends AppCompatActivity {
    private RecyclerView multiRecycle;
    private MultiViewAdapter multiViewAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view);

        toolbar= findViewById(R.id.multiViewActivityToolId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Multi View");

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> item = (ArrayList<String>) bundle.getSerializable("booklist");
        multiRecycle= findViewById(R.id.multiRecycleViewId);

        multiViewAdapter= new MultiViewAdapter(item);
        multiRecycle.setLayoutManager(new LinearLayoutManager(MultiViewActivity.this));
        multiRecycle.setAdapter(multiViewAdapter);


    }
}
