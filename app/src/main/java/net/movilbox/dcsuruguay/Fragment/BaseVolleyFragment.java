package net.movilbox.dcsuruguay.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Controller.ControllerCategoria;
import net.movilbox.dcsuruguay.Controller.ControllerDepartamento;
import net.movilbox.dcsuruguay.Controller.ControllerDireccion;
import net.movilbox.dcsuruguay.Controller.ControllerEstadoC;
import net.movilbox.dcsuruguay.Controller.ControllerIndicadores;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerMunicipio;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;

public class BaseVolleyFragment extends Fragment {

    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    public FuncionesGenerales funcionesGenerales;
    public ControllerIndicadores controllerIndicadores;
    public ControllerPunto controllerPunto;
    public ControllerDepartamento controllerDepartamento;
    public ControllerMunicipio controllerMunicipio;
    public ControllerDireccion controllerDireccion;
    public ControllerEstadoC controllerEstadoC;
    public ControllerTerritorio controllerTerritorio;
    public ControllerZona controllerZona;
    public ControllerCategoria controllerCategoria;
    public ControllerLogin controllerLogin;
    public ControllerInventario controllerInventario;
    public ControllerCarrito controllerCarrito;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(getActivity().getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fRequestQueue != null) {
            fRequestQueue.cancelAll(this);
        }
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();

            request.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            fRequestQueue.add(request);
        }
    }

    public boolean isValidNumber(String number) { return number == null || number.length() == 0; }

    public boolean isValiEmail(String dato) { return !dato.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"); }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
