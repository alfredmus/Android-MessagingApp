package com.finale.phiser.myapplication;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    GoogleMap mMap;
    private static final int GPS_ERRORDIALOG_REQUEST = 9001;
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9002;
    private static final String LOGTAG = "maps";

    // LocationClient mLocationClient;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Marker marker;

   /* private static final double
            SEATTLE_LAT = 47.60621,
            SEATTLE_LNG = -122.33207,
            SYDNEY_LAT = -33.867487,
            SYDNEY_LNG = 151.20699,
            NEWYORK_LAT = 40.714353,
            NEWYORK_LNG = -74.005973;*/
    private static final float DEFAULTZOOM = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (servicesOK()) {
            Toast.makeText(this, "Ready to Map", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_maps);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            //mapFragment.getMapAsync(this);
            if (initMap()) {
                Toast.makeText(this, "Ready to Map", Toast.LENGTH_SHORT).show();
                //mLocationClient = new LocationClient (this, this, this);
                // mLocationClient.connect();
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
                mGoogleApiClient.connect();

                // mMap.setMyLocationEnabled(true);
                // gotoLocation(SEATTLE_LAT,SEATTLE_LNG, DEFAULTZOOM);
            } else {
                Toast.makeText(this, "Map not Available", Toast.LENGTH_SHORT).show();
            }
        } else {
            setContentView(R.layout.activity_maps);
        }


    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);

    }


    public boolean servicesOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to Google Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();

            if (mMap != null) {
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.info_window, null);

                        TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                        TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                        TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                        TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);

                        LatLng ll = marker.getPosition();

                        tvLocality.setText(marker.getTitle());
                        tvLat.setText("Latitude: " + ll.latitude);
                        tvLat.setText("Longitude: " + ll.longitude);
                        tvSnippet.setText(marker.getSnippet());

                        return v;
                    }
                });

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        Geocoder gc = new Geocoder(MapsActivity.this);
                        List<Address> list = null;
                        try {
                            list = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        Address add = list.get(0);
                        MapsActivity.this.setMarker(add.getLocality(), add.getCountryName(), latLng.latitude, latLng.longitude);
                    }
                });

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        String msg = marker.getTitle() + "(" + marker.getPosition().latitude + "," + marker.getPosition().longitude + ")";
                        Toast.makeText(MapsActivity.this, msg, Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {

                        Geocoder gc = new Geocoder(MapsActivity.this);
                        List<Address> list = null;
                        LatLng latLng = marker.getPosition();
                        try {
                            list = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        Address add = list.get(0);
                        marker.setTitle(add.getLocality());
                        marker.setSnippet(add.getCountryName());
                        marker.showInfoWindow();
                    }
                });
            }
        }
        return (mMap != null);
    }

    private void gotoLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);
    }

    public void geoLocate(View v) throws IOException {

        EditText et = (EditText) findViewById(R.id.editText1);
        String location = et.getText().toString();

        if (location.length() == 0) {
            Toast.makeText(this, "please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }

        hideSoftKeyboard(v);

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();
        gotoLocation(lat, lng, DEFAULTZOOM);

        if (marker != null) {
            marker.remove();
        }

        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat, lng));
        marker = mMap.addMarker(options);

    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;

            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

   /* private void gotoCurrentLocation() {
        Location currentLocation = LocationClient.getLastLocation();
        if (currentLocation == null) {
            Toast.makeText(this, "current location is not available", Toast.LENGTH_SHORT).show();
        } else {
            LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOM);
            mMap.animateCamera(update);
        }
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        MapStateManager mgr = new MapStateManager(this);
        mgr.saveMapState(mMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MapStateManager mgr = new MapStateManager(this);
        CameraPosition position = mgr.getSavedCameraPosition();
        if (position != null) {
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.moveCamera(update);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "connected to location services", Toast.LENGTH_SHORT).show();

        /*LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(6000);
        request.setFastestInterval(1000);
        mLocationRequest.requestLocationUpdates(request, this);*/
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
           private void setMarker(String locality, String country, double lat, double lng){
               if (marker != null){
                   marker.remove();
               }

               MarkerOptions options = new MarkerOptions()
                       .title(locality)
                       .position(new LatLng(lat, lng))
                       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                       .draggable(true);

               if (country.length()>0){
                   options.snippet(country);
               }

               marker = mMap.addMarker(options);
           }

           @Override
           public void onLocationChanged(Location location) {
               String msg = "Location: " + location.getLatitude() + "," + location.getLongitude();
               Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onStatusChanged(String provider, int status, Bundle extras) {

           }

           @Override
           public void onProviderEnabled(String provider) {

           }

           @Override
           public void onProviderDisabled(String provider) {

           }
       }
