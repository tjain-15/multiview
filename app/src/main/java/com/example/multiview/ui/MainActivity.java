package com.example.multiview.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiview.MyViewModel;
import com.example.multiview.R;
import com.example.multiview.adapters.ParentAdapter;
import com.example.multiview.databinding.ActivityMainBinding;
import com.example.multiview.listeners.ImageClickListener;
import com.example.multiview.models.ApiResponse;

public class MainActivity extends AppCompatActivity implements ImageClickListener {
    private ActivityMainBinding binding;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        observeModelData();

    }

    private void observeModelData() {
        myViewModel.getModelLiveData().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    initParentRecyclerView(apiResponse);
                }
            }
        });
    }

    private void initParentRecyclerView(ApiResponse apiResponse) {
        ParentAdapter parentAdapter = new ParentAdapter(this, apiResponse);

        recyclerView = binding.mainRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parentAdapter);

        if (isFragmentInBackstack(getSupportFragmentManager(), "popUp")) {
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }

        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void imageClicked(String url) {
        setImage(url);
    }

    public void setImage(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", url);

        initFragment(bundle);
    }

    private void initFragment(Bundle bundle) {
        if (!isFragmentInBackstack(getSupportFragmentManager(), "popUp")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, PopUpFragment.class, bundle)
                    .addToBackStack("popUp")
                    .commit();
        }

        recyclerView.setVisibility(View.INVISIBLE);
    }

    public boolean isFragmentInBackstack(final FragmentManager fragmentManager, final String fragmentTagName) {
        int backStackSize = fragmentManager.getBackStackEntryCount();

        for (int entry = 0; entry < backStackSize; entry++) {
            if (fragmentTagName.equalsIgnoreCase(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

}
