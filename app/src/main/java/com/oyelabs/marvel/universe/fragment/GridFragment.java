package com.oyelabs.marvel.universe.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oyelabs.marvel.universe.R;
import com.oyelabs.marvel.universe.adapter.CharacterAdapter;
import com.oyelabs.marvel.universe.constant.Constant;
import com.oyelabs.marvel.universe.databinding.FragmentGridBinding;
import com.oyelabs.marvel.universe.model.MarvelResponse;
import com.oyelabs.marvel.universe.model.Result;
import com.oyelabs.marvel.universe.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GridFragment extends Fragment {

    private static final String TAG = "gridcharcter";
    FragmentGridBinding binding;
    private List<Result> results;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGridBinding.inflate(inflater, container, false);
        getActivity().setTitle("Marvel Universe");
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        binding.recyclercharcter.setLayoutManager(linearLayoutManager);
        searchview();
        changeStatusBarcolor();
        Log.e(TAG, "onCreateView: ");



        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        showdata();

    }
    private void searchview() {

        binding.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    getdata(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void getdata(String s) {


    }
    private void showdata() {
        binding.gridprogress.setVisibility(View.VISIBLE);
        Log.e(TAG, "showdata: ");

        Call<MarvelResponse> call = ApiClient.getApi().getCharacters(Constant.apikey, Constant.hash, Constant.ts);
        call.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {
                if (response.isSuccessful()) {

                    Log.e(TAG, "onResponse: " + response.message());


                    results = response.body().getData().getResults();
                    Log.e(TAG, "onResponse: ID" + results.get(1).getId());
                    Log.e(TAG, "onResponse: HASH" + Constant.hash);
                    Log.e(TAG, "onResponse: ts" + Constant.ts);
                    CharacterAdapter adapter = new CharacterAdapter(results, getContext());
                    binding.recyclercharcter.setAdapter(adapter);
                    binding.gridprogress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                Log.e("responsefail", t.toString());
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarcolor()
    {
        Window window= getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.teal_200));
    }
}
