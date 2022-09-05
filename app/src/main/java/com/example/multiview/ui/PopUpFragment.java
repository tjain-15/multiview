package com.example.multiview.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multiview.databinding.FragmentPopUpBinding;
import com.squareup.picasso.Picasso;

public class PopUpFragment extends Fragment {
    private FragmentPopUpBinding fragmentPopUpBinding;
    private ImageView carouselImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentPopUpBinding = FragmentPopUpBinding.inflate(inflater, container, false);
        View view = fragmentPopUpBinding.getRoot();

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carouselImageView = fragmentPopUpBinding.fragmentImage;
        setCarouselImageView(carouselImageView);


    }

    private void setCarouselImageView(ImageView imageView) {
        if (getArguments() != null) {
            String url = getArguments().getString("imageUrl");
            if (url != null)
                Picasso.get().load(url).into(imageView);
        }
    }


}
