package ir.mab.autocheck;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

import ir.mab.autocheck.databinding.ItemCarBinding;
import ir.mab.autocheck.db.entity.CarEntity;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    private static final String TAG = "CarsAdapter";
    Activity context;
    List<CarEntity> carEntities;
    CarActionInterface carActionInterface;

    public CarsAdapter(Activity context, List<CarEntity> carEntities, CarActionInterface carActionInterface) {
        this.context = context;
        this.carEntities = carEntities;
        this.carActionInterface = carActionInterface;
    }

    public Activity getContext() {
        return context;
    }

    public void setCarEntities(List<CarEntity> carEntities) {
        this.carEntities = carEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCarBinding itemCarBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_car,parent,false);
        return new ViewHolder(itemCarBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemCarBinding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CheckListActivity.class);
                intent.putExtra("carId",carEntities.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.bind(carEntities.get(position), NumberFormat.getInstance().format(carEntities.get(position).getLastKilometer()));

    }

    @Override
    public int getItemCount() {
        return carEntities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarBinding itemCarBinding;
        public ViewHolder(@NonNull ItemCarBinding itemView) {
            super(itemView.getRoot());
            itemCarBinding = itemView;
            itemCarBinding.executePendingBindings();
        }

        public void bind(CarEntity carEntity, String formattedKilometer){
            itemCarBinding.setCar(carEntity);
            itemCarBinding.setFormattedKilometer(formattedKilometer);
        }
    }

    public void deleteItem(int position){
        carActionInterface.onDelete(carEntities.get(position),position);
    }

    public void editItem(int position){
        carActionInterface.onEdit(carEntities.get(position),position);
    }
}
