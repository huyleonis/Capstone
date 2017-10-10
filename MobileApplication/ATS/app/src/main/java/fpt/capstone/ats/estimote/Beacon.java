package fpt.capstone.ats.estimote;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;

import java.util.UUID;

/**
 * Created by hp on 9/24/2017.
 */

public class Beacon {
    public static final int BEACON_PAYMENT = 1;
    public static final int BEACON_CHECK_RESULT = 2;

    private UUID proximityUUID;
    private int major;
    private int minor;
    private int type;

    public Beacon(UUID proximityUUID, int major, int minor, int type) {
        this.proximityUUID = proximityUUID;
        this.major = major;
        this.minor = minor;
        this.type = type;
    }

    public Beacon(String proximityUUID, int major, int minor, int type) {
        this(UUID.fromString(proximityUUID), major, minor, type);
    }

    public UUID getProximityUUID() {
        return proximityUUID;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getType() {
        return this.type;
    }

    public BeaconRegion toBeaconRegion() {
        return new BeaconRegion(toString(), getProximityUUID(), getMajor(), getMinor());
    }

    @Override
    public String toString() {
        return getProximityUUID().toString() + ";" + getMajor() + ";" + getMinor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beacon beacon = (Beacon) o;

        if (major != beacon.major) return false;
        if (minor != beacon.minor) return false;
        return proximityUUID != null ? proximityUUID.equals(beacon.proximityUUID) : beacon.proximityUUID == null;

    }

    @Override
    public int hashCode() {
        int result = proximityUUID != null ? proximityUUID.hashCode() : 0;
        result = 31 * result + major;
        result = 31 * result + minor;
        return result;
    }
}
