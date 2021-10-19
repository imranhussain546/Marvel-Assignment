package com.oyelabs.marvel.universe.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.oyelabs.marvel.universe.R;
import com.oyelabs.marvel.universe.constant.Constant;
import com.oyelabs.marvel.universe.databinding.FragmentDetailBinding;
import com.oyelabs.marvel.universe.model.MarvelResponse;
import com.oyelabs.marvel.universe.model.Result;
import com.oyelabs.marvel.universe.network.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    private static final String TAG = "charcterdetails";
    FragmentDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentDetailBinding.inflate(inflater, container, false);
        getActivity().setTitle("Detail");
     //   Log.e(TAG, "onCreateView: "+getArguments().getString(Constant.ID) );

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        charaterDetail();
    }

    private void charaterDetail() {
        binding.detailprogress.setVisibility(View.VISIBLE);
        Call<MarvelResponse> call= ApiClient.getApi().characterdetail(getArguments().getString(Constant.ID),
                Constant.apikey,Constant.hash,Constant.ts);
        call.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {
                if (response.isSuccessful())
                {
                    Log.e(TAG, "onResponse: "+response.message() );
                   List<Result> results= response.body().getData().getResults();
                    Picasso.with(getContext())
                            .load(results.get(0).getThumbnail().getPath()+"."+results.get(0).getThumbnail().getExtension())
                            .fit()
                            .centerInside()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.noimageavilable)
                            .into(binding.detailIV);

                    if (results.get(0).getName().isEmpty())
                    {
                        binding.detailname.setText("No Name");

                    }
                    else
                    {
                        binding.detailname.setText(results.get(0).getName());
                    }
                    if (results.get(0).getId()!=null)
                    {
                        binding.detailID.setText(String.valueOf(results.get(0).getId()));
                    }
                    else
                    {
                        binding.detailID.setText("No id");
                    }
                    if (results.get(0).getDescription().isEmpty())
                    {
                        binding.detailDES.setText("No Description");

                    }
                    else
                    {
                        binding.detailDES.setText(results.get(0).getDescription());
                    }
                    binding.detailprogress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
}