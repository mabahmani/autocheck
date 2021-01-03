package ir.mab.autocheck.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CarCheckListEntity {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long carId;
    String checkTitle;
    String checkAction;
    Integer checkPeriod;
    Integer lastCheck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCheckTitle() {
        return checkTitle;
    }

    public void setCheckTitle(String checkTitle) {
        this.checkTitle = checkTitle;
    }

    public String getCheckAction() {
        return checkAction;
    }

    public void setCheckAction(String checkAction) {
        this.checkAction = checkAction;
    }

    public Integer getCheckPeriod() {
        return checkPeriod;
    }

    public void setCheckPeriod(Integer checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    public Integer getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(Integer lastCheck) {
        this.lastCheck = lastCheck;
    }

    @Override
    public String toString() {
        return "CarCheckListEntity{" +
                "id=" + id +
                ", carId=" + carId +
                ", checkTitle='" + checkTitle + '\'' +
                ", checkAction='" + checkAction + '\'' +
                ", checkPeriod=" + checkPeriod +
                ", lastCheck=" + lastCheck +
                '}';
    }
}
