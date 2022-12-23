package com.adlemgames.pbk.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.adlemgames.pbk.R;
import com.adlemgames.pbk.models.Teory;

import java.util.List;

public class RecycleAdapterTeory extends RecyclerView.Adapter<RecycleAdapterTeory.ViewHolder>{

    private final LayoutInflater inflater;
    List<Teory> teoryList;
    Activity c;
    Callback callback;

    public interface Callback{
        void paragraph(Teory teory);
        void folder(Teory teory);
        void click(Teory teory);
    }

    public RecycleAdapterTeory(Activity context, List<Teory> teoryList, Callback callback) {
        this.inflater = LayoutInflater.from(context);
        c = context;
        this.teoryList = teoryList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecycleAdapterTeory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.teory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapterTeory.ViewHolder holder, int position) {
        holder.name.setText(teoryList.get(position).getName());
        holder.icon.setImageResource(teoryList.get(position).getDrawable());
        holder.itemView.setOnClickListener(view -> {
            callback.click(teoryList.get(position));
            //if (teoryList.get(position).getPath().contains(".html"))
            //    callback.paragraph(teoryList.get(position));
            //else callback.folder(teoryList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return teoryList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        ViewHolder(View view){
            super(view);
            icon = view.findViewById(R.id.icon_teory);
            name = view.findViewById(R.id.name_teory);
        }
    }
}