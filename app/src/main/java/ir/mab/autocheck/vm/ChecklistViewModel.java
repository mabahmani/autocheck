package ir.mab.autocheck.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.mab.autocheck.db.AutoCheckDB;
import ir.mab.autocheck.db.dao.CheckListDao;
import ir.mab.autocheck.db.entity.CarCheckListEntity;

public class ChecklistViewModel extends ViewModel {
    CheckListDao checkListDao;
    public ChecklistViewModel(AutoCheckDB autoCheckDB) {
        checkListDao = autoCheckDB.checkListDao();
    }

    public void insertCheckList(CarCheckListEntity carCheckListEntity){
        checkListDao.insertCheckList(carCheckListEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void updateCheckList(CarCheckListEntity carCheckListEntity){
        checkListDao.updateCheckList(carCheckListEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer aLong) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteCheckList(CarCheckListEntity carCheckListEntity){
        checkListDao.deleteCheckList(carCheckListEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer aLong) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public LiveData<CarCheckListEntity> getCheckListById(Long id){
        return checkListDao.findCheckListById(id);
    }
}
