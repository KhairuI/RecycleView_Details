package com.example.recycleviewdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements ClickInterface{

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> bookList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String deleteBook="";
    private List<String> archiveBookList= new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= findViewById(R.id.mainActivityToolId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book List");

        bookList= new ArrayList<>();

        recyclerView= findViewById(R.id.recycleViewId);

        adapter= new Adapter(bookList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        bookList.add("In Search of Lost Time");
        bookList.add("Ulysses:1");
        bookList.add("Don Quixote");
        bookList.add("The Great Gatsby");
        bookList.add("One Hundred Years of Solitude");
        bookList.add("Moby Dick");
        bookList.add("War and Peace");
        bookList.add("Lolita");
        bookList.add("Ulysses:2");
        bookList.add("Hamlet");
        bookList.add("The Catcher in the Rye");
        bookList.add("The Brothers Karamazov");
        bookList.add("Crime and Punishment");
        bookList.add("Madame Bovary");
        bookList.add("Ulysses:3");
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

        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START |
            ItemTouchHelper.END,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition= viewHolder.getAdapterPosition();
            int toPosition= target.getAdapterPosition();

            Collections.swap(bookList,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

           final int position= viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    deleteBook= bookList.get(position);
                    bookList.remove(position);
                    adapter.notifyItemRemoved(position);

                    Snackbar.make(recyclerView,deleteBook,Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            bookList.add(position,deleteBook);
                            adapter.notifyItemInserted(position);
                        }
                    }).show();

                    break;
                case ItemTouchHelper.RIGHT:
                    final String archiveBook= bookList.get(position);
                    archiveBookList.add(archiveBook);
                    bookList.remove(position);
                    adapter.notifyItemRemoved(position);

                    Snackbar.make(recyclerView,archiveBook+" Archived.",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            archiveBookList.remove(archiveBookList.lastIndexOf(archiveBook));
                            bookList.add(position,archiveBook);
                            adapter.notifyItemInserted(position);

                        }
                    }).show();

                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                                float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary))
                    .addSwipeRightActionIcon(R.drawable.ic_archive)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, bookList.get(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongItemClick(int position) {

        bookList.remove(position);
        adapter.notifyItemRemoved(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.multiViewId:
                Bundle bundle=new Bundle();
                bundle.putSerializable("booklist", (Serializable) bookList);

                Intent intent= new Intent(MainActivity.this,MultiViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.sectionViewId:
               Intent intent1= new Intent(MainActivity.this,CollapsActivity.class);
               startActivity(intent1);
                break;


        }
        return super.onOptionsItemSelected(item);

    }
}
