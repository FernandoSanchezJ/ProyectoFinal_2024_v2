package com.example.pyoyectofinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    public MapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MapFragment_);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Tu ubicación"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                }
            }
        });
        addPointsToMap();
    }

    private void addPointsToMap() {
        // Lista de coordenadas correspondientes a universidades en Teziutlán, Puebla.
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(19.822222, -97.355833)); // Instituto Tecnológico Superior de Teziutlán
        points.add(new LatLng(19.812345, -97.342456)); // Universidad Autónoma de Puebla (BUAP) Campus Teziutlán
        points.add(new LatLng(19.818432, -97.350123)); // Universidad Angelópolis de Puebla Campus Teziutlán
        points.add(new LatLng(19.825678, -97.348900)); // Universidad de Teziutlán

        // Agregar marcadores para cada punto en el mapa.
        for (LatLng point : points) {
            mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title("Universidad en Teziutlán"));
        }

        // Mover la cámara al primer punto y establecer un nivel de zoom adecuado.
        LatLng firstPoint = points.get(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPoint, 14));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                onMapReady(mMap);
            }
        }
    }
}