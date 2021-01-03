package ir.mab.autocheck.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CarEntity {
    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    Integer lastKilometer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLastKilometer() {
        return lastKilometer;
    }

    public void setLastKilometer(Integer lastKilometer) {
        this.lastKilometer = lastKilometer;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastKilometer=" + lastKilometer +
                '}';
    }
}
