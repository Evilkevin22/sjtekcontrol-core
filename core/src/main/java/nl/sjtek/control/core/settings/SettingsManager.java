package nl.sjtek.control.core.settings;

import com.google.common.io.Files;
import com.google.gson.Gson;
import io.habets.javautils.Log;
import nl.sjtek.control.core.events.Bus;
import nl.sjtek.control.core.events.SettingsEvent;
import nl.sjtek.control.core.settings.system.*;
import nl.sjtek.control.settings.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by wouter on 26-3-17.
 */
public class SettingsManager {
    private static final String DEBUG = SettingsManager.class.getSimpleName();
    private static String path = "/var/sjtekcontrol/config.json";
    private static SettingsManager instance;
    private Audio audio;
    private Coffee coffee;
    private Motion motion;
    private Music music;
    private Screen screen;
    private Sonarr sonarr;
    private Temperature temperature;
    private TV tv;
    private Light light;
    private LastFM lastFM;
    private Map<String, User> users;
    private List<String> quotes;

    private SettingsManager() {

    }

    public static synchronized SettingsManager getInstance() {
        if (instance == null) {
            reload();
        }

        return instance;
    }

    public static void reload(String path) {
        SettingsManager.path = path;
        reload();
    }

    public synchronized static void reload() {
        try {
            String json = Files.toString(new File(path), Charset.defaultCharset());
            SettingsManager fromFile = new Gson().fromJson(json, SettingsManager.class);
            if (fromFile != null) {
                if (fromFile.equals(instance)) {
                    Log.i(DEBUG, "Settings reloaded, nothing new detected");
                } else {
                    Log.i(DEBUG, "Settings reloaded");
                    instance = fromFile;
                    Bus.post(new SettingsEvent());
                }
            }
        } catch (IOException e) {
            Log.e(DEBUG, "Could not read settings", e);
        }
    }

    public Audio getAudio() {
        return audio;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public Motion getMotion() {
        return motion;
    }

    public Music getMusic() {
        return music;
    }

    public Screen getScreen() {
        return screen;
    }

    public Sonarr getSonarr() {
        return sonarr;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public TV getTv() {
        return tv;
    }

    public LastFM getLastFM() {
        return lastFM;
    }

    public Light getLight() {
        return light;
    }

    public User getUser(String userName) {
        return users.get(userName);
    }

    public User getDefaultUser() {
        return users.get("default");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingsManager that = (SettingsManager) o;

        if (audio != null ? !audio.equals(that.audio) : that.audio != null) return false;
        if (coffee != null ? !coffee.equals(that.coffee) : that.coffee != null) return false;
        if (motion != null ? !motion.equals(that.motion) : that.motion != null) return false;
        if (music != null ? !music.equals(that.music) : that.music != null) return false;
        if (screen != null ? !screen.equals(that.screen) : that.screen != null) return false;
        if (sonarr != null ? !sonarr.equals(that.sonarr) : that.sonarr != null) return false;
        if (temperature != null ? !temperature.equals(that.temperature) : that.temperature != null) return false;
        if (tv != null ? !tv.equals(that.tv) : that.tv != null) return false;
        if (light != null ? !light.equals(that.light) : that.light != null) return false;
        if (lastFM != null ? !lastFM.equals(that.lastFM) : that.lastFM != null) return false;
        return users != null ? users.equals(that.users) : that.users == null;
    }

    @Override
    public int hashCode() {
        int result = audio != null ? audio.hashCode() : 0;
        result = 31 * result + (coffee != null ? coffee.hashCode() : 0);
        result = 31 * result + (motion != null ? motion.hashCode() : 0);
        result = 31 * result + (music != null ? music.hashCode() : 0);
        result = 31 * result + (screen != null ? screen.hashCode() : 0);
        result = 31 * result + (sonarr != null ? sonarr.hashCode() : 0);
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        result = 31 * result + (tv != null ? tv.hashCode() : 0);
        result = 31 * result + (light != null ? light.hashCode() : 0);
        result = 31 * result + (lastFM != null ? lastFM.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}
