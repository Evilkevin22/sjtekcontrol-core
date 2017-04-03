package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.Setting;
import nl.sjtek.control.core.settings.models.MotionSensor;

import java.util.List;

/**
 * Created by wouter on 26-3-17.
 */
public class Motion extends Setting {
    private List<MotionSensor> sensors;

    public List<MotionSensor> getSensors() {
        return sensors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Motion motion = (Motion) o;

        return sensors != null ? sensors.equals(motion.sensors) : motion.sensors == null;
    }

    @Override
    public int hashCode() {
        return sensors != null ? sensors.hashCode() : 0;
    }
}
