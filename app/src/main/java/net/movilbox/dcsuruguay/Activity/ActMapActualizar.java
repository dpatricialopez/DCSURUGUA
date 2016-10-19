package net.movilbox.dcsuruguay.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Model.EntLisSincronizar;
import net.movilbox.dcsuruguay.Model.ResponseMarcarPedido;
import static net.movilbox.dcsuruguay.Model.EntLoginR.setIndicador_refres;
import net.movilbox.dcsuruguay.R;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;



public class ActMapActualizar extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMarkerDragListener, View.OnClickListener {

    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double latitud = 0;
    private double longitud = 0;
    private double latitudActual = 0;
    private double longitudActual = 0;
    private String nombrepos;
    public ProgressDialog progressDialog;
    //private DBHelper mydb;
    private EntLisSincronizar entLisSincronizar;
    private RequestQueue rq;
    public static final String TAG = "MyTag";
    private int idpo;
    private  ControllerLogin controllerLogin;
    //private SpotsDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_actualizar);

        //alertDialog = new SpotsDialog(this, R.style.Custom);
       // mydb = new DBHelper(this);
        controllerLogin = new ControllerLogin(this);
        TextView txtIdPunto = (TextView) findViewById(R.id.txtIdPunto);
        TextView txtNombrePunto = (TextView) findViewById(R.id.txtNombrePunto);
        TextView txtDireccion = (TextView) findViewById(R.id.txtDireccion);

        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        Button btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            entLisSincronizar = (EntLisSincronizar) bundle.getSerializable("value");
            latitud = entLisSincronizar.getLatitud();
            longitud = entLisSincronizar.getLongitud();

            nombrepos = entLisSincronizar.getNombre_punto();
            idpo = entLisSincronizar.getIdpos();

            txtIdPunto.setText(String.format("ID PDV: %s", idpo));
            txtNombrePunto.setText(String.format("Nombre: %s", nombrepos.toUpperCase()));

            String direccion;

            if (entLisSincronizar.getTexto_direccion() != null) {

                if (entLisSincronizar.getTexto_direccion().trim().isEmpty())
                    direccion = "N/A";
                else
                    direccion = entLisSincronizar.getTexto_direccion();

            } else {
                direccion = "N/A";
            }
            txtDireccion.setText(String.format("Dirección: %s", direccion));

        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No Tiene Permisos de ubicacion", Toast.LENGTH_LONG).show();
            return;
        } else {
            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.setOnMarkerDragListener(this);

            if (latitud == 0 && longitud == 0) {
                Toast.makeText(this, "El punto no tiene geolocalización asignada", Toast.LENGTH_LONG).show();
                LatLng pdvlocation = new LatLng(0, 0);
                mMap.addMarker(new MarkerOptions()
                        .position(pdvlocation)
                        .title(nombrepos)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location3)));
            } else {
                LatLng pdvlocation = new LatLng(latitud, longitud);
                mMap.addMarker(new MarkerOptions()
                        .position(pdvlocation)
                        .title(nombrepos)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location3)));
            }

            mGoogleApiClient.connect();

        }

        // Add a marker in Sydney and move the camera
        /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }


    @Override
    public void onLocationChanged(Location location) {

        LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myCoordinates)      // Sets the center of the map to LatLng (refer to previous snippet)
                .zoom(11)                   // Sets the zoom
                //.bearing(50)              // Sets the orientation of the camera to east
                .tilt(45)                 // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {

                LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());

                latitudActual = location.getLatitude();
                longitudActual = location.getLongitude();

                mMap.addMarker(new MarkerOptions()
                        .position(myCoordinates)
                        .title(nombrepos)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location4)));

                 onLocationChanged(location);

            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Log.i("Mapas", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        LatLng dragPosition = marker.getPosition();
        latitudActual = dragPosition.latitude;
        longitudActual = dragPosition.longitude;
        Toast.makeText(getApplicationContext(), "Nueva posición para el punto.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancelar:
                finish();
                break;
            case R.id.btnActualizar:
                if(latitud != latitudActual || longitud != longitudActual)
                {
                    servicioGuardarLocation();
                }
                else
                {
                    Toast.makeText(this, "La posicíon selecionada no se a modificado", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void servicioGuardarLocation() {

        progressDialog = new ProgressDialog(ActMapActualizar.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere un momento");
        progressDialog.setMessage("Enviando información al servidor");
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "actualiza_location_pdv");
        rq = Volley.newRequestQueue(this);
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        respuestaLocation(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActMapActualizar.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActMapActualizar.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActMapActualizar.this, "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActMapActualizar.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActMapActualizar.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idpos", String.valueOf(idpo));
                params.put("latitud", String.valueOf(latitudActual));
                params.put("longitud", String.valueOf(longitudActual));
                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis",String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("db", controllerLogin.getUserLogin().getBd());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));

                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void respuestaLocation(String response) {
        Gson gson = new Gson();
        if (!response.equals("[]")) {
            try {
                Charset.forName("UTF-8").encode(response);
                byte ptext[] = response.getBytes(Charset.forName("ISO-8859-1"));
                String value = new String(ptext, Charset.forName("UTF-8"));

                ResponseMarcarPedido responseMarcarPedido = gson.fromJson(value, ResponseMarcarPedido.class);
                if (responseMarcarPedido.getEstado() == -1) {
                    //Error
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, responseMarcarPedido.getMsg(), Toast.LENGTH_LONG).show();
                    setIndicador_refres(1);
                    Intent intent = new Intent(this, ActMenu.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rq != null) {
            rq.cancelAll(TAG);
        }
    }


}
