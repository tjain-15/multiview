package com.example.multiview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiview.databinding.LayoutBannerBinding;
import com.example.multiview.databinding.LayoutCarouselBinding;
import com.example.multiview.databinding.LayoutVerticalListBinding;
import com.example.multiview.models.ApiResponse;
import com.squareup.picasso.Picasso;

public class ParentAdapter extends RecyclerView.Adapter {
    private final ApiResponse apiResponse;
    private final Context ctx;
    private final int BANNER_VIEW = 1;
    private final int CAROUSEL_VIEW = 2;
    private final int LIST_VIEW = 3;

    public ParentAdapter(Context ctx, ApiResponse apiResponse) {
        this.ctx = ctx;
        this.apiResponse = apiResponse;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER_VIEW) {
            return new BannerViewHolder(LayoutBannerBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false));
        } else if (viewType == CAROUSEL_VIEW) {
            return new CarouselViewHolder(LayoutCarouselBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false));
        } else {
            return new ListViewHolder(LayoutVerticalListBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getClass() == BannerViewHolder.class) {
            ((BannerViewHolder) holder).loadUI(position);

        } else if (holder.getClass() == CarouselViewHolder.class) {
            ((CarouselViewHolder) holder).loadUI(position);
        } else {
            ((ListViewHolder)holder).loadUI(position);

        }
    }

    @Override
    public int getItemCount() {
        return apiResponse.getResults().size();
    }

    @Override
    public int getItemViewType(int position) {
        String viewType = apiResponse.getResults().get(position).getViewType();
        if (viewType.equalsIgnoreCase("bannerImage")) {
            return BANNER_VIEW;
        } else if (viewType.equals("carousel")) {
            return CAROUSEL_VIEW;
        } else {
            return LIST_VIEW;
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private final LayoutBannerBinding layoutBannerBinding;

        public BannerViewHolder(LayoutBannerBinding layoutBannerBinding) {
            super(layoutBannerBinding.getRoot());
            this.layoutBannerBinding = layoutBannerBinding;
        }

        private void loadUI(int position) {
            Picasso.get().load(apiResponse.getResults().get(position).getImg()).into(layoutBannerBinding.bannerImage);
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private LayoutVerticalListBinding layoutVerticalListBinding;

        public ListViewHolder(LayoutVerticalListBinding layoutVerticalListBinding) {
            super(layoutVerticalListBinding.getRoot());
            this.layoutVerticalListBinding = layoutVerticalListBinding;
        }

        private void loadUI(int position) {
            ListAdapter listAdapter = new ListAdapter(apiResponse.getResults().get(position).getItems());

            layoutVerticalListBinding.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            layoutVerticalListBinding.nestedRecyclerView.setAdapter(listAdapter);
        }
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        private LayoutCarouselBinding layoutCarouselBinding;

        public CarouselViewHolder(LayoutCarouselBinding layoutCarouselBinding) {
            super(layoutCarouselBinding.getRoot());
            this.layoutCarouselBinding = layoutCarouselBinding;
        }

        private void loadUI(int position) {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(ctx, apiResponse.getResults().get(position).getItems());
            layoutCarouselBinding.viewPager.setAdapter(viewPagerAdapter);
        }
    }
}
