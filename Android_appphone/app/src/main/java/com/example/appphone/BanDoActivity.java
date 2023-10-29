package com.example.appphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class BanDoActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;
    TextView kinhdo,vido;
    Location mLastLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_do);
        kinhdo = findViewById(R.id.kinhdo);
        vido = findViewById(R.id.vido);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},999);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;
                                kinhdo.setText(""+mLastLocation.getLatitude());
                                vido.setText(""+mLastLocation.getLongitude());
                                double kd = 20.990589879244247;  //Double.parseDouble(kinhdo.getText().toString());
                                double vd = 105.83853882429808; //Double.parseDouble(vido.getText().toString());
                                Log.d("Vi Tri Cua Ban La",kd+"vi do"+vd);
                                LatLng store = new LatLng(kd, vd);
                                mMap.addMarker(new MarkerOptions()
                                        .position(store)
                                        .title("Vị Trí Của Bạn"));
                                LatLng home = new LatLng(21.049341, 105.752707);
                                mMap.addPolyline(new PolylineOptions().add(home, store).color(R.color.com_facebook_blue));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(store, 20));
                                UiSettings uisetting = mMap.getUiSettings();
                                uisetting.setCompassEnabled(true);
                                uisetting.setZoomControlsEnabled(true);
                                uisetting.setScrollGesturesEnabled(true);
                                uisetting.setTiltGesturesEnabled(true);
                                uisetting.setAllGesturesEnabled(true);
                                uisetting.setIndoorLevelPickerEnabled(true);
                                uisetting.setZoomGesturesEnabled(true);
                                uisetting.setMyLocationButtonEnabled(true);
                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            } else {
                                Toast.makeText(BanDoActivity.this, "Không Có Vị Trí", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},999);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;
                                kinhdo.setText(""+mLastLocation.getLatitude());
                                vido.setText(""+mLastLocation.getLongitude());

                            } else {
                                Toast.makeText(BanDoActivity.this, "Không Có Vị Trí", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 999:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            "tu choi",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}