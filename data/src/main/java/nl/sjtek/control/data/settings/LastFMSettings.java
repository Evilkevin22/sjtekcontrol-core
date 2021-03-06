package nl.sjtek.control.data.settings;

/**
 * Created by wouter on 21-12-15.
 */
public class LastFMSettings extends Setting {

    private final String apiKey;
    private final String cachePathArtists;
    private final String cachePathAlbum;

    public LastFMSettings(String apiKey, String cachePathArtists, String cachePathAlbum) {
        this.apiKey = apiKey;
        this.cachePathArtists = cachePathArtists;
        this.cachePathAlbum = cachePathAlbum;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getCachePathArtists() {
        return cachePathArtists;
    }

    public String getCachePathAlbum() {
        return cachePathAlbum;
    }
}
