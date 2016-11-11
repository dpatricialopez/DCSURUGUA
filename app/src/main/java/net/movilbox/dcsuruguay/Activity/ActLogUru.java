package net.movilbox.dcsuruguay.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.BuildConfig;
import net.movilbox.dcsuruguay.Controller.ControllerCategoria;
import net.movilbox.dcsuruguay.Controller.ControllerDepartamento;
import net.movilbox.dcsuruguay.Controller.ControllerDireccion;
import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerMunicipio;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;
import net.movilbox.dcsuruguay.Model.EntLoginR;
import net.movilbox.dcsuruguay.Model.TimeService;
import static net.movilbox.dcsuruguay.Model.EntLoginR.setIndicador_refres;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActLogUru extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<Status>  {

    @BindView(R.id.editUsuario)
    EditText editUsuario;
    @BindView(R.id.editPassword)
    EditText editPassword;

    @BindView(R.id.btnIngresar)
    Button btnIngresar;

    private CoordinatorLayout coordinatorLayout;
    private int progressStatus = 0;
    private String mensaje = "";
    private Handler handler = new Handler();
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_CHECK_SETTINGS = 2;
    private GoogleApiClient mGoogleApiClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationRequest mLocationRequest;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DCS URUGUAY");
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        // Establecer punto de entrada para la API de ubicación
        buildGoogleApiClient();

        // Crear configuración de peticiones
        createLocationRequest();

        // Crear opciones de peticiones
        buildLocationSettingsRequest();

        checkLocationSettings();

        funcionesGenerales = new FuncionesGenerales(this);
        controllerLogin = new ControllerLogin(this);
        controllerCategoria = new ControllerCategoria(this);
        controllerDepartamento = new ControllerDepartamento(this);
        controllerMunicipio = new ControllerMunicipio(this);
        controllerDireccion = new ControllerDireccion(this);
        controllerEstadoC = new ControllerEstadoC(this);
        TextView txtVersion = (TextView) findViewById(R.id.txtVersion);
        String versionNametext = BuildConfig.VERSION_NAME;

        txtVersion.setText(String.format("Versión: %1$s", versionNametext));
        btnIngresar.setOnClickListener(this);

        connectionDetector = new ConnectionDetector(this);

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("DCS URUGUAY");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            toolbar.setTitle("DCS URUGUAY OFFLINE");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.rojo_progress));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Alerta!");
            builder.setMessage("¿ Estas seguro de eliminar toda la información ?");
            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    progressDialog = new ProgressDialog(ActLogUru.this);
                    progressDialog.setTitle("Eliminar datos");
                    progressDialog.setMessage("Eliminando datos guardados en el dispositivo local.");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Thread(new Runnable() {
                        public void run() {

                            final String datos[] = {"indicadoresdas", "indicadoresdas_detalle", "login", "departamento", "municipios", "nomenclaturas", "categoria", "estado_comercial", "territorio", "zona", "punto",
                                    "refes_sims", "img_catalogo", "catalogo", "detalle_catalogo", "lista_precios", "inventario", "carrito_detalle"};

                            for (String elemento: datos) {
                                funcionesGenerales.deleteObject(elemento);
                            }

                            handler.post(new Runnable() {
                                public void run() {
                                    TastyToast.makeText(ActLogUru.this, "Registros eliminados exitosamente", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                }
                            });

                            progressDialog.dismiss();
                        }
                    }).start();

                }

            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(Constants.UPDATE_INTERVAL)
                .setFastestInterval(Constants.UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .enableAutoManage(this, this)
                .build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d(TAG, "Los ajustes de ubicación satisfacen la configuración.");
                        //startLocationUpdates();
                        if (ActivityCompat.checkSelfPermission(ActLogUru.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ActLogUru.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.d(TAG, "Los ajustes de ubicación no satisfacen la configuración. " +
                                    "Se mostrará un diálogo de ayuda.");
                            status.startResolutionForResult(ActLogUru.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.d(TAG, "El Intent del diálogo no funcionó.");
                            // Sin operaciones
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Implementamos los permisos
                        Log.d(TAG, "Los ajustes de ubicación no son apropiados.");
                        break;

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresar:
                if (isValidNumber(editUsuario.getText().toString().trim())) {
                    editUsuario.setError("Campo requerido");
                    editUsuario.requestFocus();
                } else if (isValidNumber(editPassword.getText().toString().trim())) {
                    editPassword.setError("Campo requerido");
                    editPassword.requestFocus();
                } else {
                    if (connectionDetector.isConnected()) {
                        loginServices();
                    } else {

                        if (controllerLogin.validateLoginUser(editUsuario.getText().toString().toUpperCase(), editPassword.getText().toString())) {
                            //Existe el usuario
                            startActivity(new Intent(this, ActMenu.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        } else {
                            Snackbar.make(coordinatorLayout, "Usuario no estan registrados Offline", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                }

                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "El usuario permitió el cambio de ajustes de ubicación.");
                        // Si acepto la activación de la ubicación
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        // No acepto la activación de la ubicación
                        Log.d(TAG, "El usuario no permitió el cambio de ajustes de ubicación");
                        TastyToast.makeText(this, "La aplicación solo funciona si tiene activado el GPS", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        finishAffinity();
                        break;
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permisos otorgados
            } else {
                TastyToast.makeText(this, "La aplicación solo funciona si otorga los permisos de localización", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                finishAffinity();
            }
        }
    }

    private void loginServices() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere");
        progressDialog.setMessage("Cargando información.");
        progressDialog.show();

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "login_user");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJsonLogin(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(ActLogUru.this, "Error de tiempo de espera", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(ActLogUru.this, "Error Servidor", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(ActLogUru.this, "Server Error"+error.getClass().getSimpleName(), Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(ActLogUru.this, "Error de red", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(ActLogUru.this, "Error al serializar los datos", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                params.put("user", editUsuario.getText().toString().trim());
                params.put("pass", editPassword.getText().toString().trim());
                params.put("versionCode", String.valueOf(versionCode));
                params.put("versionName", versionName);
                params.put("indicador", String.valueOf(funcionesGenerales.validarTablas()));
                params.put("fecha", controllerLogin.getUserLogin().getFecha_sincroniza_offline() == null ? "" : controllerLogin.getUserLogin().getFecha_sincroniza_offline());

                return params;

            }

        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void parseJsonLogin(String response) {
        Gson gson = new Gson();

        final EntLoginR loginR = gson.fromJson(response, EntLoginR.class);

        progressDialog.dismiss();

        if (loginR.getEstado() == -1) {
            Snackbar.make(coordinatorLayout, loginR.getMsg(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else if (loginR.getEstado() == -2) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, loginR.getMsg(), Snackbar.LENGTH_INDEFINITE)
                    .setAction("DESCARGAR", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=net.movilbox.dcsuruguay"));
                                startActivity(intent);
                            } catch (Exception e) { //google play app is not installed
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=net.movilbox.dcsuruguay"));
                                startActivity(intent);
                            }
                        }
                    });
            snackbar.show();
        } else if (loginR.getEstado() == 1) {

            if (!controllerLogin.validateLoginUser(editUsuario.getText().toString().toUpperCase(), editPassword.getText().toString())) {

                loginR.setPassword(editPassword.getText().toString().trim());
                funcionesGenerales.deleteObject("login");
                controllerLogin.insertLoginUser(loginR);
                funcionesGenerales.deleteObject("inventario");

            }

            funcionesGenerales.deleteAllServiTime();

            TimeService timeService = new TimeService();

            timeService.setIdUser(loginR.getId());
            timeService.setTraking(loginR.getIntervalo());
            timeService.setTimeservice(loginR.getCantidad_envios());
            timeService.setIdDistri(String.valueOf(loginR.getId_distri()));
            timeService.setDataBase(loginR.getBd());
            timeService.setFechainicial(loginR.getHora_inicial());
            timeService.setFechaFinal(loginR.getHora_final());

            funcionesGenerales.insertTimeServices(timeService);


            if (loginR.getDatosGenerales().size() > 0) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Sincronización de Información");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("");
                progressDialog.setCancelable(false);
                progressDialog.setMax(loginR.getDatosGenerales().size());
                progressDialog.setIndeterminate(false);
                progressDialog.show();

                mensaje = "";
                progressStatus = 0;

                // Start lengthy operation in a background thread
                new Thread(new Runnable() {
                    public void run() {

                        while (progressStatus < loginR.getDatosGenerales().size()) {

                            switch (loginR.getDatosGenerales().get(progressStatus).getTipoTabla()) {
                                case 1:
                                    mensaje = "Sincronizando Departamentos.";
                                    controllerDepartamento.insertDepartamento(loginR.getDatosGenerales().get(progressStatus));
                                    break;
                                case 2:
                                    mensaje = "Sincronizando Municipios.";
                                    controllerMunicipio.insertMunicipios(loginR.getDatosGenerales().get(progressStatus));
                                    break;
                                case 3:
                                    mensaje = "Sincronizando Tipos de dirección";
                                    controllerDireccion.insertNomenclaturas(loginR.getDatosGenerales().get(progressStatus));
                                    break;
                                case 4:
                                    mensaje = "Sincronizando Categorias de los Puntos de Venta.";
                                    controllerCategoria.insertCategoria(loginR.getDatosGenerales().get(progressStatus));
                                    break;
                                case 5:
                                    mensaje = "Sincronizando Estados comerciales de los Puntos de Venta.";
                                    controllerEstadoC.insertEstadoComercial(loginR.getDatosGenerales().get(progressStatus));
                                    break;
                            }

                            progressStatus++;

                            // Update the progress bar
                            handler.post(new Runnable() {
                                public void run() {
                                    progressDialog.setMessage(mensaje);
                                    progressDialog.setProgress(progressStatus);
                                }
                            });

                        }
                        setIndicador_refres(1);

                        controllerLogin.updateFechaSincroLogin(loginR.getFecha_sincroniza(), loginR.getId());

                        startActivity(new Intent(ActLogUru.this, ActMenu.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();

                        progressDialog.dismiss();

                    }

                }).start();

            } else {
                setIndicador_refres(1);
                controllerLogin.updateFechaSincroLogin(loginR.getFecha_sincroniza(), loginR.getId());

                startActivity(new Intent(ActLogUru.this, ActMenu.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

                progressDialog.dismiss();

            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("¿ Seguro que quieres salir ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface arg0, int arg1) {
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        finishAffinity();
                    }
                }).create().show();
    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(
                this,
                "Error de conexión con el código:" + connectionResult.getErrorCode(),
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onResult(@NonNull Status status) {

    }


}
