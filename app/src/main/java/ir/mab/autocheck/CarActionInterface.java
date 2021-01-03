package ir.mab.autocheck;

import ir.mab.autocheck.db.entity.CarEntity;

public interface CarActionInterface {
    void onDelete(CarEntity carEntity, int position);
    void onEdit(CarEntity carEntity, int position);
}
