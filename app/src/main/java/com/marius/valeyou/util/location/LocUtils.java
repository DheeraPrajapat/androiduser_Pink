package com.marius.valeyou.util.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

public class LocUtils {

    public static String getAddressString(@NonNull Context context, Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.get(0).getMaxAddressLineIndex() != -1)
                return addresses.get(0).getAddressLine(0);
            else
                return addresses.get(0).getSubLocality() + "," + addresses.get(0).getLocality();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Address getAddressObject(@NonNull Context context, Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            return addresses.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
