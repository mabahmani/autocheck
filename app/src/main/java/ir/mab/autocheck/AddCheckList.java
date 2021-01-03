package ir.mab.autocheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.mab.autocheck.databinding.ActivityAddCarBinding;
import ir.mab.autocheck.databinding.ActivityAddCheckListBinding;
import ir.mab.autocheck.db.AutoCheckDB;
import ir.mab.autocheck.db.entity.CarCheckListEntity;
import ir.mab.autocheck.db.entity.CarEntity;
import ir.mab.autocheck.vm.ChecklistViewModel;

public class AddCheckList extends AppCompatActivity {

    ActivityAddCheckListBinding binding;
    ChecklistViewModel checklistViewModel;
    CarCheckListEntity mCarCheckListEntity;
    Long carId = 0L;
    Long checkId = 0L;
    Integer carLastKilometer = 0;
    Boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_check_list);
        checklistViewModel = new ChecklistViewModel(AutoCheckDB.getDbInstance(this));
        carId = getIntent().getLongExtra("carId",0);
        checkId = getIntent().getLongExtra("checkId",0);
        carLastKilometer = getIntent().getIntExtra("carLastKilometer",0);

        if (checkId != 0){
            checklistViewModel.getCheckListById(checkId).observe(this, new Observer<CarCheckListEntity>() {
                @Override
                public void onChanged(CarCheckListEntity carCheckListEntity) {
                    if (carCheckListEntity != null){
                        mCarCheckListEntity = carCheckListEntity;
                        edit = true;
                        binding.checkTitle.setText(carCheckListEntity.getCheckTitle());
                        binding.checkListAction.setText(carCheckListEntity.getCheckAction());
                        binding.checkListPeriod.setText(String.valueOf(carCheckListEntity.getCheckPeriod()));
                        binding.checkListLastCheck.setText(String.valueOf(carCheckListEntity.getLastCheck()));
                        binding.addChecklist.setText("ویرایش");
                    }
                }
            });
        }

        binding.addChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.checkTitle.getText())){
                    binding.checkTitleParent.setErrorEnabled(true);
                    binding.checkTitleParent.setError("عنوان چک لیست را وارد کنید");
                }

                else if (TextUtils.isEmpty(binding.checkListAction.getText())){
                    binding.checkListActionParent.setErrorEnabled(true);
                    binding.checkListActionParent.setError("عملیات چک لیست را وارد کنید");
                }

                else if (TextUtils.isEmpty(binding.checkListPeriod.getText())){
                    binding.checkListPeriodParent.setErrorEnabled(true);
                    binding.checkListPeriodParent.setError("دوره چک را وارد کنید");
                }

                else if (TextUtils.isEmpty(binding.checkListLastCheck.getText())){
                    binding.checkListLastCheckParent.setErrorEnabled(true);
                    binding.checkListLastCheckParent.setError("آخرین کیلومتر چک را وارد کنید");
                }

                else {
                    if (Integer.parseInt(Objects.requireNonNull(binding.checkListLastCheck.getText()).toString()) > carLastKilometer){
                        binding.checkListLastCheckParent.setErrorEnabled(true);
                        binding.checkListLastCheckParent.setError("آخرین کیلومتر چک نمی تواند بزرگتر از کیلومتر فعلی ماشین باشد");
                    }
                    else {
                        if (edit) {
                            mCarCheckListEntity.setCheckTitle(Objects.requireNonNull(binding.checkTitle.getText()).toString());
                            mCarCheckListEntity.setCheckAction(Objects.requireNonNull(binding.checkListAction.getText()).toString());
                            mCarCheckListEntity.setCheckPeriod(Integer.parseInt(Objects.requireNonNull(binding.checkListPeriod.getText()).toString()));
                            mCarCheckListEntity.setLastCheck(Integer.parseInt(Objects.requireNonNull(binding.checkListLastCheck.getText()).toString()));
                            checklistViewModel.updateCheckList(mCarCheckListEntity);
                        } else {
                            CarCheckListEntity carCheckListEntity = new CarCheckListEntity();
                            carCheckListEntity.setCheckTitle(Objects.requireNonNull(binding.checkTitle.getText()).toString());
                            carCheckListEntity.setCheckAction(Objects.requireNonNull(binding.checkListAction.getText()).toString());
                            carCheckListEntity.setCheckPeriod(Integer.parseInt(Objects.requireNonNull(binding.checkListPeriod.getText()).toString()));
                            carCheckListEntity.setLastCheck(Integer.parseInt(Objects.requireNonNull(binding.checkListLastCheck.getText()).toString()));
                            carCheckListEntity.setCarId(carId);
                            checklistViewModel.insertCheckList(carCheckListEntity);
                        }
                        finish();
                    }
                }
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}