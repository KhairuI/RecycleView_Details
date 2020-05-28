package com.example.recycleviewdetails;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    public static final String TAG= "Adapter";
    private List<String> bookList;
    private ClickInterface clickInterface;

    public Adapter(List<String> bookList, ClickInterface clickInterface) {
        this.bookList = bookList;
        this.clickInterface= clickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookName.setText(bookList.get(position));
        holder.item.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    //implement with interface class.....
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;
        private AppCompatTextView bookName,item;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);

             imageView= itemView.findViewById(R.id.imageId);
             bookName= itemView.findViewById(R.id.bookNameId);
             item= itemView.findViewById(R.id.itemId);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     clickInterface.onItemClick(getAdapterPosition());
                 }
             });

             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     /*bookList.remove(getAdapterPosition());
                     notifyItemRemoved(getAdapterPosition());*/
                     clickInterface.onLongItemClick(getAdapterPosition());
                     return true;
                 }
             });


         }

    }

    /*//implement without interface class.....
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatImageView imageView;
        private AppCompatTextView bookName,item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView= itemView.findViewById(R.id.imageId);
            bookName= itemView.findViewById(R.id.bookNameId);
            item= itemView.findViewById(R.id.itemId);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bookList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), bookList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();

        }
    }*/

}
