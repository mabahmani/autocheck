package ir.mab.autocheck.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.mab.autocheck.db.AutoCheckDB;
import ir.mab.autocheck.db.dao.CarDao;
import ir.mab.autocheck.db.entity.CarEntity;
import ir.mab.autocheck.db.entity.CarWithCheckLists;

public class CarViewModel extends ViewModel {
    CarDao carDao;
    public CarViewModel(AutoCheckDB autoCheckDB) {
        carDao = autoCheckDB.carDao();
    }

    public void insertCar(CarEntity carEntity){
        carDao.insertCar(carEntity).subscribeOn(Schedulers.io())
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

    public void updateCar(CarEntity carEntity){
        carDao.updateCar(carEntity).subscribeOn(Schedulers.io())
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

    public void deleteCar(CarEntity carEntity){
        carDao.deleteCar(carEntity).subscribeOn(Schedulers.io())
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

    public LiveData<List<CarEntity>> getAllCars(){
        return carDao.findCars();
    }

    public LiveData<CarEntity> getCarById(Long carId){
        return carDao.findCarById(carId);
    }

    public LiveData<CarWithCheckLists> getCarWithCheckList(Long carId){
        return carDao.findCarWithCheckListsById(carId);
    }
}
