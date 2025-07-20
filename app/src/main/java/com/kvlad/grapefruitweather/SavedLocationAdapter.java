package com.kvlad.grapefruitweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.ViewHolder> {
    private List<SavedLocation> savedLocations;
    private DatabaseHelper db;

    public SavedLocationAdapter(Context context) {
        db = new DatabaseHelper(context);
        this.savedLocations = db.getAllLocations();
    }

    @NonNull
    @Override
    public SavedLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedLocationAdapter.ViewHolder holder, int position) {
        holder.getTxtLocationName().setText(savedLocations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return savedLocations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtCurrentTemperature;
        private final TextView txtLocationName;
        private final ImageView imgWeatherStatus;

        public ViewHolder(android.view.View view) {
            super(view);
            this.txtCurrentTemperature = view.findViewById(R.id.txtCurrentTemperature);
            this.imgWeatherStatus = view.findViewById(R.id.imgWeatherStatus);
            this.txtLocationName = view.findViewById(R.id.txtLocationName);

        }

        public TextView getTxtCurrentTemperature() {
            return txtCurrentTemperature;
        }

        public TextView getTxtLocationName() {
            return txtLocationName;
        }

        public ImageView getImgWeatherStatus() {
            return imgWeatherStatus;
        }
    }
}
