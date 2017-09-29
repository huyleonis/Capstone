package com.example.hp.atsapplication.estimote;

import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;

import java.util.UUID;

/**
 * Created by hp on 9/24/2017.
 */

public class Beacon {
    private UUID proximityUUID;
    private int major;
    private int minor;

    public Beacon(UUID proximityUUID, int major, int minor) {
        this.proximityUUID = proximityUUID;
        this.major = major;
        this.minor = minor;
    }

    public Beacon(String proximityUUID, int major, int minor) {
        this(UUID.fromString(proximityUUID), major, minor);
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
