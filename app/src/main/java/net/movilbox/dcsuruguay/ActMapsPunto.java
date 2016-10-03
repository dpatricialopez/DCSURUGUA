package net.movilbox.dcsuruguay;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.movilbox.dcsuruguay.Activity.ActMarcarVisita;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;

public class ActMapsPunto extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EntLisSincronizar rutero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_maps_punto);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            rutero = (EntLisSincronizar)bundle.getSerializable("values");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No Tiene Permisos de ubicacion", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            LatLng location = new LatLng(rutero.getLatitud(), rutero.getLongitud());
            mMap.setMyLocationEnabled(true);
            UiSettings mUiSettings = mMap.getUiSettings();

            // Keep the UI Settings state in sync with the checkboxes.
            mUiSettings.setZoomControlsEnabled(true);
            mUiSettings.setCompassEnabled(true);
            mUiSettings.setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);
            mUiSettings.setScrollGesturesEnabled(true);
            mUiSettings.setZoomGesturesEnabled(true);
            mUiSettings.setTiltGesturesEnabled(true);
            mUiSettings.setRotateGesturesEnabled(true);
            mMap.isTrafficEnabled();

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
            mMap.addMarker(new MarkerOptions()
                    .position(location));
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // Getting view from the layout file info_window_layout
                    View v = getLayoutInflater().inflate(R.layout.dialog_markermap, null);

                    TextView txt_id_numero = (TextView) v.findViewById(R.id.txt_id_numero);
                    TextView nombre_punto = (TextView) v.findViewById(R.id.nombre_punto);
                    TextView txt_direccion = (TextView) v.findViewById(R.id.txt_direccion);
                    TextView txt_circuito = (TextView) v.findViewById(R.id.txt_circuito);
                    TextView txt_ruta = (TextView) v.findViewById(R.id.txt_ruta);

                    String direccion;

                    if (rutero.getTexto_direccion() != null) {

                        if (rutero.getTexto_direccion().trim().isEmpty())
                            direccion = "N/A";
                        else
                            direccion = rutero.getTexto_direccion();

                    } else {
                        direccion = "N/A";
                    }

                    txt_id_numero.setText(String.valueOf(rutero.getIdpos()));
                    nombre_punto.setText(String.valueOf(rutero.getNombre_punto()));
                    txt_direccion.setText(String.valueOf(direccion));
                    txt_circuito.setText(String.valueOf(rutero.getNombreTerritorio()));
                    txt_ruta.setText(String.valueOf(rutero.getNombreZona()));

                    String texto = "Visitar";

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActMapsPunto.this);
                    builder.setCancelable(false);
                    builder.setTitle("Datos del Punto");
                    builder.setView(v).setPositiveButton(texto, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Activity Detalle
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(ActMapsPunto.this, ActMarcarVisita.class);
                            bundle.putSerializable("value", rutero);
                            bundle.putString("page", "marcar_rutero");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                    // Returning the view containing InfoWindow contents
                    return null;
                }
            });
        }
    }
}
