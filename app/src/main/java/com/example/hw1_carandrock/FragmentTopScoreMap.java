package com.example.hw1_carandrock;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.hw1_carandrock.MainActivity.KEY_SCORES;

public class FragmentTopScoreMap extends Fragment implements OnMapReadyCallback {
    MySharedPreferences msp;
    Location location;
    View view;
    public FragmentTopScoreMap(Context cntx) {
        super();
        msp = new MySharedPreferences(cntx);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_topmap, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapTopLocation);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        MapsInitializer.initialize(getContext());

        ArrayList<Player> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(msp.getString(KEY_SCORES, ""), new TypeToken<List<Player>>() {
        }.getType());

        int i = 1;
        LatLng pos = null;
        for (Player score : list) {
            pos = new LatLng(score.getLocation().getLatitude(), score.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions().position(pos).title((i++) + ") " + score.getName()));
        }
        if (pos != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, googleMap.getMaxZoomLevel() / 2));
        }
    }



}
