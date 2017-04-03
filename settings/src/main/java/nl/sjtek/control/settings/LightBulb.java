package nl.sjtek.control.settings;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wouter on 27-3-17.
 */
public class LightBulb implements Serializable {

    private String name;
    private int id;
    private boolean rgbSupport;
    private List<Integer> groups;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isRgbSupport() {
        return rgbSupport;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LightBulb lightBulb = (LightBulb) o;

        if (id != lightBulb.id) return false;
        if (rgbSupport != lightBulb.rgbSupport) return false;
        if (name != null ? !name.equals(lightBulb.name) : lightBulb.name != null) return false;
        return groups != null ? groups.equals(lightBulb.groups) : lightBulb.groups == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (rgbSupport ? 1 : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        return result;
    }
}
