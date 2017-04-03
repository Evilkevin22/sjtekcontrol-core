package nl.sjtek.control.core.settings.system;

/**
 * Created by wouter on 27-3-17.
 */
public class LastFM {
    private String apiKey;
    private String albumPath;
    private String artistPath;

    public String getApiKey() {
        return apiKey;
    }

    public String getAlbumPath() {
        return albumPath;
    }

    public String getArtistPath() {
        return artistPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastFM lastFM = (LastFM) o;

        if (apiKey != null ? !apiKey.equals(lastFM.apiKey) : lastFM.apiKey != null) return false;
        if (albumPath != null ? !albumPath.equals(lastFM.albumPath) : lastFM.albumPath != null) return false;
        return artistPath != null ? artistPath.equals(lastFM.artistPath) : lastFM.artistPath == null;
    }

    @Override
    public int hashCode() {
        int result = apiKey != null ? apiKey.hashCode() : 0;
        result = 31 * result + (albumPath != null ? albumPath.hashCode() : 0);
        result = 31 * result + (artistPath != null ? artistPath.hashCode() : 0);
        return result;
    }
}
