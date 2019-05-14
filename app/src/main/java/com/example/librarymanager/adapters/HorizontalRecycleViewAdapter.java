package com.example.librarymanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanager.R;

import java.util.ArrayList;

public class HorizontalRecycleViewAdapter extends RecyclerView.Adapter<HorizontalRecycleViewAdapter.MyViewHolder> {
    private int layoutId;
    private ArrayList dataSource;

    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public HorizontalRecycleViewAdapter(int layoutId, ArrayList dataSource) {
        this.layoutId = layoutId;
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        CardView cardView = (CardView) inflater.inflate(i, viewGroup, false);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        });
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //TODO Get name and image from firestore
        myViewHolder.imageView.setImageDrawable(myViewHolder.imageView.getResources().getDrawable(R.drawable.common_google_signin_btn_text_light));
        myViewHolder.textView.setText("abc");
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgBookImage);
            textView = itemView.findViewById(R.id.txtBookName);
        }
    }


    public void clear()
    {
        dataSource.clear();
        super.notifyDataSetChanged();
    }

    public ArrayList getDataSource() {
        return dataSource;
    }

    public void setDataSource(ArrayList dataSource) {
        this.dataSource = dataSource;
    }
}
