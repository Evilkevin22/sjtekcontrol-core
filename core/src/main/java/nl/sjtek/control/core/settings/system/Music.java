package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.Setting;

/**
 * Created by wouter on 26-3-17.
 */
public class Music extends Setting {

    private String host;
    private int portMpd;
    private String mopidyUri;
    private int volumeStepUp;
    private int volumeStepDown;
    private int defaultVolume;

    public String getHost() {
        return host;
    }

    public int getPortMpd() {
        return portMpd;
    }

    public String getMopidyUri() {
        return mopidyUri;
    }

    public int getVolumeStepUp() {
        return volumeStepUp;
    }

    public int getVolumeStepDown() {
        return volumeStepDown;
    }

    public int getDefaultVolume() {
        return defaultVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Music music = (Music) o;

        if (portMpd != music.portMpd) return false;
        if (volumeStepUp != music.volumeStepUp) return false;
        if (volumeStepDown != music.volumeStepDown) return false;
        if (defaultVolume != music.defaultVolume) return false;
        if (host != null ? !host.equals(music.host) : music.host != null) return false;
        return mopidyUri != null ? mopidyUri.equals(music.mopidyUri) : music.mopidyUri == null;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + portMpd;
        result = 31 * result + (mopidyUri != null ? mopidyUri.hashCode() : 0);
        result = 31 * result + volumeStepUp;
        result = 31 * result + volumeStepDown;
        result = 31 * result + defaultVolume;
        return result;
    }
}
