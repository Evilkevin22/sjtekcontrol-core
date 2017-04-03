package nl.sjtek.control.core.settings.system;

/**
 * Created by wouter on 26-3-17.
 */
public class TV {
    private String host;
    private int port;
    private String key;
    private String protocol;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getKey() {
        return key;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TV tv = (TV) o;

        if (port != tv.port) return false;
        if (host != null ? !host.equals(tv.host) : tv.host != null) return false;
        if (key != null ? !key.equals(tv.key) : tv.key != null) return false;
        return protocol != null ? protocol.equals(tv.protocol) : tv.protocol == null;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        return result;
    }
}
