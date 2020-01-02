package com.example.hw1_carandrock;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

    public class LocationSensor {
        private LocationManager locationManager;
        private LocationListener locationListener;
        private MySharedPreferences msp;

        public LocationSensor(Activity activity) {            //Location Settings
            msp = new MySharedPreferences(activity);
            resetLatLong();
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    msp.putInt("playerLongitude", (int) longitude);
                    msp.putInt("playerLatitude", (int) latitude);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }


        }

        /**
         * Clear the LatLong from previous player
         */
        private void resetLatLong(){
            msp.putInt("playerLongitude",  0);
            msp.putInt("playerLatitude",  0);
        }
    }




