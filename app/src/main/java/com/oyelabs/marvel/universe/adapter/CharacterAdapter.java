package com.oyelabs.marvel.universe.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oyelabs.marvel.universe.MainActivity;
import com.oyelabs.marvel.universe.R;
import com.oyelabs.marvel.universe.constant.Constant;
import com.oyelabs.marvel.universe.databinding.CardBinding;
import com.oyelabs.marvel.universe.fragment.DetailFragment;
import com.oyelabs.marvel.universe.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends  RecyclerView.Adapter<CharacterAdapter.ViewHolder>{
    public List<Result> resultList;
    Context context;

    public CharacterAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CardBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Result result=resultList.get(position);
        String imageurl=result.getThumbnail().getPath()+"."+result.getThumbnail().getExtension();
        Log.e("imageurl",imageurl);
        Picasso.with(context)
                .load(result.getThumbnail().getPath()+"."+result.getThumbnail().getExtension())
                .fit()
                .centerInside()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.noimageavilable)
                .into(holder.binding.charcterpic);

        if (result.getName()!=null)
        {
            holder.binding.charctername.setText(result.getName());
        }
        else{
            holder.binding.charctername.setText("No name ");
        }
        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailFragment detailFragment =new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ID, String.valueOf(result.getId()));
                detailFragment.setArguments(bundle);
                ((MainActivity) context).replaceFragment(R.id.container_FL,detailFragment);

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        CardBinding binding;
        public ViewHolder(@NonNull CardBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
