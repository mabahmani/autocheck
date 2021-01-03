package ir.mab.autocheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.text.NumberFormat;
import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mab.autocheck.databinding.ActivityAddCarBinding;
import ir.mab.autocheck.db.AutoCheckDB;
import ir.mab.autocheck.db.entity.CarEntity;
import ir.mab.autocheck.vm.CarViewModel;

public class AddCarActivity extends AppCompatActivity {

    ActivityAddCarBinding binding;
    CarViewModel carViewModel;
    Boolean edit = false;
    CarEntity car = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_car);

        carViewModel = new CarViewModel(AutoCheckDB.getDbInstance(this));

        if (getIntent().getLongExtra("carId",-1) != -1){
            edit = true;
            carViewModel.getCarById(getIntent().getLongExtra("carId",-1)).observe(this, new Observer<CarEntity>() {
                @Override
                public void onChanged(CarEntity carEntity) {
                    car = carEntity;
                    binding.carName.setText(carEntity.getName());
                    binding.carLastKilometer.setText(String.valueOf(carEntity.getLastKilometer()));
                }
            });

            binding.saveCar.setText("ویرایش");
        }

        binding.saveCar.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.carName.getText())){
                binding.carNameParent.setErrorEnabled(true);
                binding.carNameParent.setError("نام ماشین را وارد کنید");
            }

            else if (TextUtils.isEmpty(binding.carLastKilometer.getText())){
                binding.carLastKilometerParent.setErrorEnabled(true);
                binding.carLastKilometerParent.setError("کارکرد ماشین را وارد کنید");
            }

            else {

                if (edit){
                    if (car != null) {
                        car.setName(Objects.requireNonNull(binding.carName.getText()).toString());
                        car.setLastKilometer(Integer.parseInt(Objects.requireNonNull(binding.carLastKilometer.getText()).toString()));
                        carViewModel.updateCar(car);
                    }
                }

                else {
                    CarEntity carEntity = new CarEntity();
                    carEntity.setName(Objects.requireNonNull(binding.carName.getText()).toString());
                    carEntity.setLastKilometer(Integer.parseInt(Objects.requireNonNull(binding.carLastKilometer.getText()).toString()));
                    carViewModel.insertCar(carEntity);
                }
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}