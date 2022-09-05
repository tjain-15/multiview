package com.example.multiview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.multiview.databinding.CarouselItemBinding;
import com.example.multiview.listeners.ImageClickListener;
import com.example.multiview.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Item> items;
    private final LayoutInflater mLayoutInflater;
    private CarouselItemBinding carouselItemBinding;
    private ImageClickListener imageClickListener;

    public ViewPagerAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        this.imageClickListener = (ImageClickListener) context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    private void loadCarouselImage(ImageView carouselImage, String ImagePath) {
        Picasso.get().load(ImagePath).into(carouselImage);
    }

    private void setImageClickListener(ImageView carouselImage, String ImagePath) {
        carouselImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageClickListener.imageClicked(ImagePath);
            }
        });
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        carouselItemBinding = CarouselItemBinding.inflate(LayoutInflater.from(container.getContext()));
        View itemView = carouselItemBinding.getRoot();

        loadCarouselImage(carouselItemBinding.carouselImage, items.get(position).getImg());

        setImageClickListener(carouselItemBinding.carouselImage, items.get(position).getImg());

        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
