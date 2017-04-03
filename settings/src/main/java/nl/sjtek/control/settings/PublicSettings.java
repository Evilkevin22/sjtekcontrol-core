package nl.sjtek.control.settings;

import java.util.List;

/**
 * Created by wouter on 27-3-17.
 */
public class PublicSettings {

    private List<LightBulb> lightBulbs;
    private List<User> users;
    private List<String> quotes;

    public List<LightBulb> getLightBulbs() {
        return lightBulbs;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<String> getQuotes() {
        return quotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicSettings that = (PublicSettings) o;

        if (lightBulbs != null ? !lightBulbs.equals(that.lightBulbs) : that.lightBulbs != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        return quotes != null ? quotes.equals(that.quotes) : that.quotes == null;
    }

    @Override
    public int hashCode() {
        int result = lightBulbs != null ? lightBulbs.hashCode() : 0;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (quotes != null ? quotes.hashCode() : 0);
        return result;
    }
}
