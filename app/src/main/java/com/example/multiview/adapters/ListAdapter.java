package com.example.multiview.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiview.databinding.VerticalListItemBinding;
import com.example.multiview.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Item> items;

    public ListAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(VerticalListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.loadUi(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VerticalListItemBinding verticalListItemBinding;

        public ViewHolder(VerticalListItemBinding verticalListItemBinding) {
            super(verticalListItemBinding.getRoot());
            this.verticalListItemBinding = verticalListItemBinding;
        }

        private void loadUi(int position) {
            Picasso.get().load(items.get(position).getImg()).into(verticalListItemBinding.listImage);
            verticalListItemBinding.listTitle.setText(items.get(position).getTitle());
        }
    }
}
