package com.example.recycleviewdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MultiViewAdapter extends RecyclerView.Adapter {

    private List<String> bookList;

    public MultiViewAdapter(List<String> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getItemViewType(int position) {
        if(bookList.get(position).toLowerCase().contains("ulysses")){
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view;

        if(viewType==0){
            view= layoutInflater.inflate(R.layout.single_item,parent,false);
            return new ViewHolderOne(view);
        }
        view= layoutInflater.inflate(R.layout.another_item,parent,false);
        return new ViewHolderTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(bookList.get(position).toLowerCase().contains("ulysses")){

            ViewHolderOne viewHolderOne= (ViewHolderOne) holder;
            viewHolderOne.bookName.setText(bookList.get(position));
            viewHolderOne.itemNo.setText(String.valueOf(position));

        }
        else {

            ViewHolderTwo viewHolderTwo= (ViewHolderTwo) holder;
            viewHolderTwo.multiBookName.setText(bookList.get(position));
            viewHolderTwo.multiItemNo.setText(String.valueOf(position));

        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder{
        private AppCompatTextView bookName,itemNo;
        private AppCompatImageView imageView;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageId);
            bookName= itemView.findViewById(R.id.bookNameId);
            itemNo= itemView.findViewById(R.id.itemId);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder{
        private AppCompatTextView multiBookName,multiItemNo;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            multiBookName= itemView.findViewById(R.id.multiBookNameId);
            multiItemNo= itemView.findViewById(R.id.multiItemId);

        }
    }

}
