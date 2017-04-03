package nl.sjtek.control.core.settings.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wouter on 26-3-17.
 */
public class MotionSensor implements Serializable {

    private final int sensorId;
    private final List<Integer> lights;
    private final long timeout;
    private final int startHour;
    private final int stopHour;
    private final boolean onlyAtSundown;
    private final boolean honourNightMode;

    public MotionSensor(int sensorId, List<Integer> lights, long timeout, int startHour, int stopHour, boolean onlyAtSundown, boolean honourNightMode) {
        this.sensorId = sensorId;
        this.lights = lights;
        this.timeout = timeout;
        this.startHour = startHour;
        this.stopHour = stopHour;
        this.onlyAtSundown = onlyAtSundown;
        this.honourNightMode = honourNightMode;
    }

    public int getSensorId() {
        return sensorId;
    }

    public List<Integer> getLights() {
        return lights;
    }

    public long getTimeout() {
        return timeout;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStopHour() {
        return stopHour;
    }

    public boolean isOnlyAtSundown() {
        return onlyAtSundown;
    }

    public boolean isHonourNightMode() {
        return honourNightMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotionSensor that = (MotionSensor) o;

        if (sensorId != that.sensorId) return false;
        if (timeout != that.timeout) return false;
        if (startHour != that.startHour) return false;
        if (stopHour != that.stopHour) return false;
        if (onlyAtSundown != that.onlyAtSundown) return false;
        if (honourNightMode != that.honourNightMode) return false;
        return lights != null ? lights.equals(that.lights) : that.lights == null;
    }

    @Override
    public int hashCode() {
        int result = sensorId;
        result = 31 * result + (lights != null ? lights.hashCode() : 0);
        result = 31 * result + (int) (timeout ^ (timeout >>> 32));
        result = 31 * result + startHour;
        result = 31 * result + stopHour;
        result = 31 * result + (onlyAtSundown ? 1 : 0);
        result = 31 * result + (honourNightMode ? 1 : 0);
        return result;
    }
}
