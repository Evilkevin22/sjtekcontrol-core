package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.Setting;

/**
 * Created by wouter on 26-3-17.
 */
public class Coffee extends Setting {
    private int switchId;
    private long timeout;

    public long getTimeout() {
        return timeout;
    }

    public int getSwitchId() {
        return switchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coffee coffee = (Coffee) o;

        if (switchId != coffee.switchId) return false;
        return timeout == coffee.timeout;
    }

    @Override
    public int hashCode() {
        int result = switchId;
        result = 31 * result + (int) (timeout ^ (timeout >>> 32));
        return result;
    }
}
