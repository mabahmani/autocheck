package ir.mab.autocheck.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ir.mab.autocheck.db.entity.CarCheckListEntity;

@Dao
public interface CheckListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> insertCheckList(CarCheckListEntity carCheckListEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    Single<Integer> updateCheckList(CarCheckListEntity carCheckListEntity);

    @Delete
    Single<Integer> deleteCheckList(CarCheckListEntity carCheckListEntity);

    @Query("SELECT * FROM carchecklistentity")
    LiveData<List<CarCheckListEntity>> findCheckLists();

    @Query("SELECT * FROM carchecklistentity WHERE id = :id")
    LiveData<CarCheckListEntity> findCheckListById(Long id);

}
