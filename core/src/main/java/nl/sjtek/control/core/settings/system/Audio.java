package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.Setting;

public class Audio extends Setting {
    private long timeout;

    public long getTimeout() {
        return timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audio audio = (Audio) o;

        return timeout == audio.timeout;
    }

    @Override
    public int hashCode() {
        return (int) (timeout ^ (timeout >>> 32));
    }
}
