package com.j_kemp.chris.myactivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Christopher Kemp on 31/10/17.
 */

public class TaskGMapFragment extends SupportMapFragment
                                implements OnMapReadyCallback,
                                GoogleApiClient.ConnectionCallbacks,
                                GoogleApiClient.OnConnectionFailedListener,
                                LocationListener{

    private static final String TAG = "TaskGMapFragment";
    private static final String ARG_TASK_OBJECT = "task_object";

    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrentLocMarker;
    private Task mTask;
    private Geocoder mGeocoder;

    // Receives a Task object passed through the Bundle. Changes are then pushed back on return.
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mTask = (Task) getArguments().getSerializable(ARG_TASK_OBJECT);
    }

    @Override
    public void onResume(){
        super.onResume();
        initMap();
    }

    /**
     * Get a Google Map object if we don't already have one.
     */
    private void initMap() {
        if (mGoogleMap == null){
            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Connect to Google Play Services
        // - If newer than Marshmallow (6.0), check permission first.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            mLocationRequest.setInterval(100);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setNumUpdates(3);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    // Required methods from Interface we are not using
    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location){
        Log.d(TAG, "Location Updated: " + location.getLatitude() + ", " + location.getLongitude() + " Stored Values: " + mTask.getPlaceLat() + ", " + mTask.getPlaceLon());
        mLastLocation = location;
        mGeocoder = new Geocoder(this.getContext(), Locale.getDefault());

        if (mCurrentLocMarker != null){
            mCurrentLocMarker.remove();
        }

        // If Task Object already has a Location, overwrite LastLocation with those values.
        if (mTask.getPlaceLat() != 0.0 && mTask.getPlaceLon() != 0.0) {
            mLastLocation.setLatitude(mTask.getPlaceLat());
            mLastLocation.setLongitude(mTask.getPlaceLon());
        } else { // Otherwise, save location to Task Object, and try get a meaningful tag as well
            mTask.setPlaceLat(mLastLocation.getLatitude());
            mTask.setPlaceLon(mLastLocation.getLongitude());
            Log.d(TAG, "Task updated.");
            List<Address> addressList;
            try {
                addressList = mGeocoder.getFromLocation(mLastLocation.getLatitude(),
                        mLastLocation.getLongitude(), 1);
                mTask.setPlaceFriendly(addressList.get(0).getPremises());
                Log.d(TAG, "Place set: " + mTask.getPlaceFriendly());
            } catch (IOException ioe) {
                Log.e(TAG, "Geocoder error: " + ioe);
            }
            TaskLog.get(getActivity()).updateTask(mTask);
        }

        // Generate a marker for the current location
        LatLng pointerCurrent = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(mTask.getTitle());//getString(R.string.map_marker_current));
        markerOptions.position(pointerCurrent);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrentLocMarker = mGoogleMap.addMarker(markerOptions);

        // Zoom in to current point, including marker
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointerCurrent,9));
    }

    @Override
    public void onPause() {
        super.onPause();
        // Disconnect Service API while app is not active in foreground.
        if (mGoogleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
