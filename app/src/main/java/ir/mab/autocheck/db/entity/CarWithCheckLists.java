package ir.mab.autocheck.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CarWithCheckLists {
    @Embedded
    CarEntity carEntity;

    @Relation(
            parentColumn = "id",
            entityColumn ="carId")
    List<CarCheckListEntity> carCheckListEntities;

    public List<CarCheckListEntity> getCarCheckListEntities() {
        return carCheckListEntities;
    }

    public void setCarCheckListEntities(List<CarCheckListEntity> carCheckListEntities) {
        this.carCheckListEntities = carCheckListEntities;
    }

    public CarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(CarEntity carEntity) {
        this.carEntity = carEntity;
    }
}
