package nl.sjtek.control.core.settings.system;

import nl.sjtek.control.core.settings.Setting;

/**
 * Created by wouter on 26-3-17.
 */
public class Screen extends Setting {
    private String header1;
    private String header2;
    private String title;
    private String playlist;

    public String getHeader1() {
        return header1;
    }

    public String getHeader2() {
        return header2;
    }

    public String getTitle() {
        return title;
    }

    public String getPlaylist() {
        return playlist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screen screen = (Screen) o;

        if (header1 != null ? !header1.equals(screen.header1) : screen.header1 != null) return false;
        if (header2 != null ? !header2.equals(screen.header2) : screen.header2 != null) return false;
        if (title != null ? !title.equals(screen.title) : screen.title != null) return false;
        return playlist != null ? playlist.equals(screen.playlist) : screen.playlist == null;
    }

    @Override
    public int hashCode() {
        int result = header1 != null ? header1.hashCode() : 0;
        result = 31 * result + (header2 != null ? header2.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (playlist != null ? playlist.hashCode() : 0);
        return result;
    }
}
