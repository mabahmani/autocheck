package ir.mab.autocheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mab.autocheck.databinding.ActivityCheckListBinding;
import ir.mab.autocheck.db.AutoCheckDB;
import ir.mab.autocheck.db.entity.CarCheckListEntity;
import ir.mab.autocheck.db.entity.CarEntity;
import ir.mab.autocheck.db.entity.CarWithCheckLists;
import ir.mab.autocheck.vm.CarViewModel;
import ir.mab.autocheck.vm.ChecklistViewModel;

public class CheckListActivity extends AppCompatActivity implements CheckListActionInterface {
    ActivityCheckListBinding binding;
    CheckListAdapter checkListAdapter;
    ChecklistViewModel checklistViewModel;
    CarViewModel carViewModel;
    CarWithCheckLists carWithCheckLists;
    Integer selectedPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_check_list);
        checklistViewModel = new ChecklistViewModel(AutoCheckDB.getDbInstance(this));
        carViewModel = new CarViewModel(AutoCheckDB.getDbInstance(this));
        checkListAdapter = new CheckListAdapter(this,new ArrayList<>(),0,this);
        binding.checkListRv.setAdapter(checkListAdapter);
        binding.checkListRv.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new CheckListSwipeCallBack(checkListAdapter));
        itemTouchHelper.attachToRecyclerView(binding.checkListRv);


        carViewModel.getCarWithCheckList(getIntent().getLongExtra("carId",0)).observe(this, carWithCheckLists -> {
            if (carWithCheckLists != null){
                this.carWithCheckLists = carWithCheckLists;
                binding.title.setText(String.format("چک لیست %s",carWithCheckLists.getCarEntity().getName()));
                binding.carLastKilometer.setText(String.valueOf(carWithCheckLists.getCarEntity().getLastKilometer()));
                if (carWithCheckLists.getCarCheckListEntities().isEmpty()){
                    binding.noChecklist.setVisibility(View.VISIBLE);
                    binding.listTitlesParent.setVisibility(View.GONE);
                }
                else{
                    binding.noChecklist.setVisibility(View.GONE);
                    binding.listTitlesParent.setVisibility(View.VISIBLE);
                }

                SortByDeadline sortByDeadline = new SortByDeadline(carWithCheckLists.getCarEntity().getLastKilometer());

                Collections.sort(carWithCheckLists.getCarCheckListEntities(),sortByDeadline);

                checkListAdapter.setCarLastKilometer(carWithCheckLists.getCarEntity().getLastKilometer());
                checkListAdapter.setList(carWithCheckLists.getCarCheckListEntities());
                checkListAdapter.notifyDataSetChanged();


                binding.addChecklist.setVisibility(View.VISIBLE);

                Intent intent = new Intent(CheckListActivity.this,AddCheckList.class);
                intent.putExtra("carId",getIntent().getLongExtra("carId",0));
                intent.putExtra("carLastKilometer",carWithCheckLists.getCarEntity().getLastKilometer());

                binding.addChecklist.setOnClickListener(v -> startActivity(intent));
            }
        });

        binding.updateCarKilometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.carLastKilometer.getText())){
                    binding.carLastKilometerParent.setErrorEnabled(true);
                    binding.carLastKilometerParent.setError("کارکرد ماشین را وارد کنید");
                }

                else {
                    if (carWithCheckLists != null){
                        if (Integer.parseInt(Objects.requireNonNull(binding.carLastKilometer.getText()).toString()) >=  carWithCheckLists.getCarEntity().getLastKilometer()){
                            carWithCheckLists.getCarEntity().setLastKilometer(Integer.parseInt(Objects.requireNonNull(binding.carLastKilometer.getText()).toString()));
                            carViewModel.updateCar(carWithCheckLists.getCarEntity());
                            binding.carLastKilometerParent.setErrorEnabled(false);
                        }

                        else {
                            binding.carLastKilometerParent.setErrorEnabled(true);
                            binding.carLastKilometerParent.setError("کارکرد جدید ماشین نمی تواند کمتر از کارکرد فعلی باشد");
                        }
                    }
                }
            }
        });




    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onDelete(CarCheckListEntity carCheckListEntity, int position) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), String.format("حذف %s تا %s ثانیه دیگر",carCheckListEntity.getCheckTitle(),"5") , 5000)
                .setTextColor(Color.WHITE)
                .setActionTextColor(Color.YELLOW);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                snackbar.setText(String.format("حذف %s تا %s ثانیه دیگر",carCheckListEntity.getCheckTitle(),(millisUntilFinished / 1000) + 1));
            }

            public void onFinish() {
                checkListAdapter.notifyItemRemoved(position);
                checklistViewModel.deleteCheckList(carCheckListEntity);
            }

        }.start();



        snackbar.setAction("انصراف", v -> {
            countDownTimer.cancel();
            checkListAdapter.notifyItemChanged(position);
        });

        snackbar.show();


        selectedPosition = position;
    }

    @Override
    public void onEdit(CarCheckListEntity carCheckListEntity, int position) {
        selectedPosition = position;
        Intent intent = new Intent(CheckListActivity.this,AddCheckList.class);
        intent.putExtra("checkId",carCheckListEntity.getId());
        intent.putExtra("carLastKilometer",carWithCheckLists.getCarEntity().getLastKilometer());
        CheckListActivity.this.startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkListAdapter.notifyItemChanged(selectedPosition);
    }

    static class SortByDeadline implements Comparator<CarCheckListEntity>
    {
        Integer carLastKilometer = 0;

        public SortByDeadline(Integer carLastKilometer) {
            this.carLastKilometer = carLastKilometer;
        }

        @Override
        public int compare(CarCheckListEntity o1, CarCheckListEntity o2) {
            int c1 =  o1.getLastCheck() + o1.getCheckPeriod() - carLastKilometer;
            int c2 =  o2.getLastCheck() + o2.getCheckPeriod() - carLastKilometer;
            return c1 - c2;
        }
    }
}

