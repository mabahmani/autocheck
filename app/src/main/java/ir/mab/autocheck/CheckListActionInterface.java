package ir.mab.autocheck;

import ir.mab.autocheck.db.entity.CarCheckListEntity;
import ir.mab.autocheck.db.entity.CarEntity;

public interface CheckListActionInterface {
    void onDelete(CarCheckListEntity carCheckListEntity, int position);
    void onEdit(CarCheckListEntity carCheckListEntity, int position);
}
