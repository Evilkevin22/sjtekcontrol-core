package nl.sjtek.control.settings;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wouter on 26-3-17.
 */
public class User implements Serializable {
    private String displayName;
    private String firstName;
    private String lastName;
    private String userName;
    private List<Integer> groups;
    private Map<String, String> playlists;
    private String defaultPlaylist;

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public Map<String, String> getPlaylists() {
        return playlists;
    }

    public String getDefaultPlaylist() {
        return playlists.get(defaultPlaylist);
    }

    public boolean isInGroup(List<Integer> checkGroup) {
        for (int group : groups) {
            for (int possibleGroup : checkGroup) {
                if (possibleGroup == group) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (displayName != null ? !displayName.equals(user.displayName) : user.displayName != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (groups != null ? !groups.equals(user.groups) : user.groups != null) return false;
        if (playlists != null ? !playlists.equals(user.playlists) : user.playlists != null) return false;
        return defaultPlaylist != null ? defaultPlaylist.equals(user.defaultPlaylist) : user.defaultPlaylist == null;
    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (playlists != null ? playlists.hashCode() : 0);
        result = 31 * result + (defaultPlaylist != null ? defaultPlaylist.hashCode() : 0);
        return result;
    }
}
