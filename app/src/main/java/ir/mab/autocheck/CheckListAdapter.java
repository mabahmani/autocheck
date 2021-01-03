package ir.mab.autocheck;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

import ir.mab.autocheck.databinding.ItemChecklistBinding;
import ir.mab.autocheck.db.entity.CarCheckListEntity;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {
    Activity context;
    List<CarCheckListEntity> list;
    Integer carLastKilometer;
    CheckListActionInterface checkListActionInterface;

    public CheckListAdapter(Activity context, List<CarCheckListEntity> list, Integer carLastKilometer, CheckListActionInterface actionInterface) {
        this.context = context;
        this.list = list;
        this.carLastKilometer = carLastKilometer;
        this.checkListActionInterface = actionInterface;
    }

    public Activity getContext() {
        return context;
    }

    public void setList(List<CarCheckListEntity> list) {
        this.list = list;
    }

    public void setCarLastKilometer(Integer carLastKilometer) {
        this.carLastKilometer = carLastKilometer;
    }

    public void deleteItem(int position){
        checkListActionInterface.onDelete(list.get(position),position);
    }
    public void editItem(int position){
        checkListActionInterface.onEdit(list.get(position),position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChecklistBinding itemChecklistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_checklist,parent,false);
        return new ViewHolder(itemChecklistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), NumberFormat.getInstance().format(list.get(position).getCheckPeriod() + list.get(position).getLastCheck()));
        double p = (((list.get(position).getCheckPeriod() + list.get(position).getLastCheck()) - carLastKilometer) * 1.0) / (list.get(position).getCheckPeriod()*1.0);

        double d = 1 - p;
        Log.d("deiffrence " , String.valueOf(d));
        if (d < 0.1){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color1);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.2){
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.3){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color3);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.4){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color4);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.5){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color5);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.6){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color6);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color10));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.7){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color7);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.8){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color8);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }

        else if (d < 0.9){
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color9);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color10));
        }
        else {
            holder.itemChecklistBinding.overlay.setBackgroundResource(R.color.color10);
            holder.itemChecklistBinding.checkListTitle.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListAction.setTextColor(context.getResources().getColor(R.color.color2));
            holder.itemChecklistBinding.checkListDeadLine.setTextColor(context.getResources().getColor(R.color.color2));
        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f,(float) p)
                .setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                holder.itemChecklistBinding.guide.setGuidelinePercent((float) animation.getAnimatedValue());
            }
        });

        valueAnimator.start();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemChecklistBinding itemChecklistBinding;
        public ViewHolder(@NonNull ItemChecklistBinding itemView) {
            super(itemView.getRoot());
            this.itemChecklistBinding = itemView;
            this.itemChecklistBinding.executePendingBindings();
        }

        public void bind(CarCheckListEntity carCheckListEntity,String formattedDeadLine){
            itemChecklistBinding.setCheckList(carCheckListEntity);
            itemChecklistBinding.setFormattedDeadLine(formattedDeadLine);
        }
    }
}
