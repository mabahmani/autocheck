package ir.mab.autocheck.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ir.mab.autocheck.db.entity.CarEntity;
import ir.mab.autocheck.db.entity.CarWithCheckLists;

@Dao
public interface CarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> insertCar(CarEntity carEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    Single<Integer> updateCar(CarEntity carEntity);

    @Delete
    Single<Integer> deleteCar(CarEntity carEntity);

    @Query("SELECT * FROM carentity ORDER BY lastKilometer DESC")
    LiveData<List<CarEntity>> findCars();

    @Query("SELECT * FROM carentity WHERE id = :carId")
    @Transaction
    LiveData<CarWithCheckLists> findCarWithCheckListsById(Long carId);

    @Query("SELECT * FROM carentity WHERE id = :carId")
    LiveData<CarEntity> findCarById(Long carId);

}

