package ir.mab.autocheck.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ir.mab.autocheck.db.dao.CarDao;
import ir.mab.autocheck.db.dao.CheckListDao;
import ir.mab.autocheck.db.entity.CarCheckListEntity;
import ir.mab.autocheck.db.entity.CarEntity;


@Database(entities = {CarEntity.class, CarCheckListEntity.class}, version = 1, exportSchema = false)
public abstract class AutoCheckDB extends RoomDatabase {
    public abstract CarDao carDao();
    public abstract CheckListDao checkListDao();

    private static AutoCheckDB autoCheckDbInstance = null;

    public static AutoCheckDB getDbInstance(Context context){
        if (autoCheckDbInstance == null){
            autoCheckDbInstance = Room.databaseBuilder(context,AutoCheckDB.class,"AutoCheckDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return autoCheckDbInstance;
    }
}
