package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.Setting;

/**
 * Created by wouter on 26-3-17.
 */
public class Sonarr extends Setting {
    private String urlCalendar;
    private String urlDiskspace;
    private String apiKey;
    private long updateInterval;

    public String getUrlCalendar() {
        return urlCalendar;
    }

    public String getUrlDiskspace() {
        return urlDiskspace;
    }

    public String getApiKey() {
        return apiKey;
    }

    public long getUpdateInterval() {
        return updateInterval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sonarr sonarr = (Sonarr) o;

        if (updateInterval != sonarr.updateInterval) return false;
        if (urlCalendar != null ? !urlCalendar.equals(sonarr.urlCalendar) : sonarr.urlCalendar != null) return false;
        if (urlDiskspace != null ? !urlDiskspace.equals(sonarr.urlDiskspace) : sonarr.urlDiskspace != null)
            return false;
        return apiKey != null ? apiKey.equals(sonarr.apiKey) : sonarr.apiKey == null;
    }

    @Override
    public int hashCode() {
        int result = urlCalendar != null ? urlCalendar.hashCode() : 0;
        result = 31 * result + (urlDiskspace != null ? urlDiskspace.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (int) (updateInterval ^ (updateInterval >>> 32));
        return result;
    }
}
