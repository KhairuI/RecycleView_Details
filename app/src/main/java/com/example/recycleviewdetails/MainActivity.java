package com.example.recycleviewdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickInterface{

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> bookList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Book List");

        bookList= new ArrayList<>();

        recyclerView= findViewById(R.id.recycleViewId);

        adapter= new Adapter(bookList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        bookList.add("In Search of Lost Time");
        bookList.add("Ulysses");
        bookList.add("Don Quixote");
        bookList.add("The Great Gatsby");
        bookList.add("One Hundred Years of Solitude");
        bookList.add("Moby Dick");
        bookList.add("War and Peace");
        bookList.add("Lolita");
        bookList.add("Hamlet");
        bookList.add("The Catcher in the Rye");
        bookList.add("The Brothers Karamazov");
        bookList.add("Crime and Punishment");
        bookList.add("Madame Bovary");
        bookList.add("The Divine Comedy");
        bookList.add("Pride and Prejudice");

        swipeRefreshLayout= findViewById(R.id.refreshId);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookList.add("The Iliad");
                bookList.add("Heart of Darkness");
                bookList.add("The Grapes of Wrath");
                bookList.add("Invisible Man");
                bookList.add("To Kill a Mockingbird");
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, bookList.get(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongItemClick(int position) {

        bookList.remove(position);
        adapter.notifyItemRemoved(position);

    }
}
