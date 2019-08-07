package com.rider.mtsweni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rider.mtsweni.R;
import com.rider.mtsweni.pojo.SportNewsResponseModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SportsNewsAdapter extends RecyclerView.Adapter<SportsNewsAdapter.SportsNewsHolder> {

    private List<SportNewsResponseModel> sportNewsResponseModelList;
    private Context context;
    private SportsNewsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // This is the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(SportsNewsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public SportsNewsAdapter(Context context, List<SportNewsResponseModel> sportNewsResponseModelList) {
        this.sportNewsResponseModelList = sportNewsResponseModelList;
        this.context = context;
    }

    public class SportsNewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewSportName, textViewDescription, textViewDate, textViewLeagueDescription;
        public ImageView imageViewPicture;

        public SportsNewsHolder(View view) {
            super(view);
            textViewSportName = view.findViewById(R.id.textViewSportName);
            textViewDescription = view.findViewById(R.id.textViewDescription);
            textViewDate = view.findViewById(R.id.textViewDate);
            textViewLeagueDescription = view.findViewById(R.id.textViewLeagueDescription);
            imageViewPicture = view.findViewById(R.id.imageViewPicture);

            view.setOnClickListener(v -> {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public SportsNewsAdapter.SportsNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new SportsNewsAdapter.SportsNewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsNewsAdapter.SportsNewsHolder holder, int position) {
        SportNewsResponseModel sportNewsResponseModel = sportNewsResponseModelList.get(position);

        if (sportNewsResponseModel != null) {
            holder.textViewSportName.setText(sportNewsResponseModel.category);
            holder.textViewDescription.setText(sportNewsResponseModel.headline);
            holder.textViewDate.setText(sportNewsResponseModel.urlFriendlyDate);
            holder.textViewLeagueDescription.setText(sportNewsResponseModel.blurb);

            String url = sportNewsResponseModel.imageUrlLocal + sportNewsResponseModel.largeImageName;
            Glide
                    .with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageViewPicture);
        }
    }

    @Override
    public int getItemCount() {
        return sportNewsResponseModelList.size();
    }
}

