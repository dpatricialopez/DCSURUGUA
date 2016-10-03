package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerCatalogo;
import net.movilbox.dcsuruguay.Controller.ControllerCategoria;
import net.movilbox.dcsuruguay.Controller.ControllerDepartamento;
import net.movilbox.dcsuruguay.Controller.ControllerDireccion;
import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Controller.ControllerListPrecio;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerMunicipio;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Controller.ControllerSim;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class BaseActivity extends AppCompatActivity {

    public RequestQueue rq;
    public static final String TAG = "MyTag";
    public ProgressDialog progressDialog;
    public FuncionesGenerales funcionesGenerales;
    public ControllerLogin controllerLogin;
    public ControllerCategoria controllerCategoria;
    public ControllerDepartamento controllerDepartamento;
    public ControllerMunicipio controllerMunicipio;
    public ControllerDireccion controllerDireccion;
    public ControllerEstadoC controllerEstadoC;
    public ControllerInventario controllerInventario;
    public ControllerListPrecio controllerListPrecio;
    public ControllerCatalogo controllerCatalogo;
    public ControllerSim controllerSim;
    public ControllerPunto controllerPunto;
    public ControllerZona controllerZona;
    public ControllerTerritorio controllerTerritorio;
    public ControllerCarrito controllerCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = Volley.newRequestQueue(this);

    }

    public boolean isValidNumber(String number) { return number == null || number.length() == 0; }

    public boolean isValidSpinner(int number) { return number == 0; }

    public boolean isValiEmail(String dato) { return !dato.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"); }

    public void cargarDialogo(Context context, String title, String msg) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.show();

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
