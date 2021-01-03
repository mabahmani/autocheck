package ir.mab.autocheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mab.autocheck.databinding.ActivityMainBinding;
import ir.mab.autocheck.db.AutoCheckDB;
import ir.mab.autocheck.db.entity.CarEntity;
import ir.mab.autocheck.vm.CarViewModel;

public class MainActivity extends AppCompatActivity implements CarActionInterface {

    ActivityMainBinding binding;
    CarViewModel carViewModel;
    CarsAdapter carsAdapter;
    Integer selectedPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        carViewModel = new CarViewModel(AutoCheckDB.getDbInstance(this));
        carsAdapter = new CarsAdapter(this,new ArrayList<>(),this);
        binding.carsList.setAdapter(carsAdapter);
        binding.carsList.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new CarSwipeCallBack(carsAdapter));
        itemTouchHelper.attachToRecyclerView(binding.carsList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            binding.carsList.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        carViewModel.getAllCars().observe(this, carEntities -> {
            if (carEntities.isEmpty()){
                binding.noCars.setVisibility(View.VISIBLE);
            }

            else {
                binding.noCars.setVisibility(View.GONE);
                carsAdapter.setCarEntities(carEntities);
            }
        });

        binding.addCar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,AddCarActivity.class)));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void onDelete(CarEntity carEntity, int position) {

        Snackbar snackbar = Snackbar.make(binding.getRoot(), String.format("حذف %s تا %s ثانیه دیگر",carEntity.getName(),"5") , 5000)
                .setTextColor(Color.WHITE)
                .setActionTextColor(Color.YELLOW);

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                snackbar.setText(String.format("حذف %s تا %s ثانیه دیگر",carEntity.getName(),(millisUntilFinished / 1000) + 1));
            }

            public void onFinish() {
                carsAdapter.notifyItemRemoved(position);
                carViewModel.deleteCar(carEntity);
            }

        }.start();



        snackbar.setAction("انصراف", v -> {
            countDownTimer.cancel();
            carsAdapter.notifyItemChanged(position);
        });

        snackbar.show();


        selectedPosition = position;
    }

    @Override
    public void onEdit(CarEntity carEntity, int position) {
        selectedPosition = position;
        Intent intent = new Intent(MainActivity.this,AddCarActivity.class);
        intent.putExtra("carId",carEntity.getId());
        MainActivity.this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carsAdapter.notifyItemChanged(selectedPosition);
    }
}