package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.models.LightBulb;

import java.util.List;

/**
 * Created by wouter on 27-3-17.
 */
public class Light {

    private List<LightBulb> lightBulbs;

    public List<LightBulb> getLightBulbs() {
        return lightBulbs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Light light = (Light) o;

        return lightBulbs != null ? lightBulbs.equals(light.lightBulbs) : light.lightBulbs == null;
    }

    @Override
    public int hashCode() {
        return lightBulbs != null ? lightBulbs.hashCode() : 0;
    }
}
