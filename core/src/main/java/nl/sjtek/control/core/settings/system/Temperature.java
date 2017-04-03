package nl.sjtek.control.core.settings.system;

/**
 * Created by wouter on 26-3-17.
 */
public class Temperature {
    private long updateInterval;
    private String urlOutside;

    public long getUpdateInterval() {
        return updateInterval;
    }

    public String getUrlOutside() {
        return urlOutside;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        if (updateInterval != that.updateInterval) return false;
        return urlOutside != null ? urlOutside.equals(that.urlOutside) : that.urlOutside == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (updateInterval ^ (updateInterval >>> 32));
        result = 31 * result + (urlOutside != null ? urlOutside.hashCode() : 0);
        return result;
    }
}
