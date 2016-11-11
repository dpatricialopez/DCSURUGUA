package net.movilbox.dcsuruguay.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Controller.ControllerCatalogo;
import net.movilbox.dcsuruguay.Controller.ControllerInventario;
import net.movilbox.dcsuruguay.Controller.ControllerListPrecio;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerPunto;
import net.movilbox.dcsuruguay.Controller.ControllerSim;
import net.movilbox.dcsuruguay.Controller.ControllerTerritorio;
import net.movilbox.dcsuruguay.Controller.ControllerZona;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;
import net.movilbox.dcsuruguay.Fragment.FragmentBajaVendedor;
import net.movilbox.dcsuruguay.Fragment.FragmentCrearPunto;
import net.movilbox.dcsuruguay.Fragment.FragmentInicioSupervisor;
import net.movilbox.dcsuruguay.Fragment.FragmentInicioVendedor;
import net.movilbox.dcsuruguay.Fragment.FragmentInventarioVende;
import net.movilbox.dcsuruguay.Fragment.FragmentMenuRutero;
import net.movilbox.dcsuruguay.Fragment.FragmentPlanificarVisita;
import net.movilbox.dcsuruguay.Fragment.FragmentSolPenVendedor;
import net.movilbox.dcsuruguay.Fragment.FragmentSolProducto;
import net.movilbox.dcsuruguay.Fragment.FragmentSolicitudApro;
import net.movilbox.dcsuruguay.Fragment.FragmentVentaCliente;
import net.movilbox.dcsuruguay.Fragment.FragmentVentasVendedor;
import net.movilbox.dcsuruguay.Fragment.FragmentVisitarPunto;
import net.movilbox.dcsuruguay.Model.EntSincronizar;
import net.movilbox.dcsuruguay.R;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Services.MonitoringService;
import net.movilbox.dcsuruguay.Services.SetTracingServiceWeb;
import static net.movilbox.dcsuruguay.Model.EntLoginR.getIndicador_refres;
import static net.movilbox.dcsuruguay.Model.EntLoginR.setIndicador_refres;
import java.util.HashMap;
import java.util.Map;

public class ActMenu extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private Handler handler = new Handler();
    public ProgressDialog progressDialog;
    private int progressStatus = 0;
    private String mensaje = "";
    private ConnectionDetector connectionDetector;
    private NavigationView navigationView;
    private String indicadorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menú Inicio");
        setSupportActionBar(toolbar);

        funcionesGenerales = new FuncionesGenerales(this);

        controllerInventario = new ControllerInventario(this);

        controllerListPrecio = new ControllerListPrecio(this);

        controllerLogin = new ControllerLogin(this);

        controllerCatalogo = new ControllerCatalogo(this);

        controllerSim = new ControllerSim(this);

        controllerPunto = new ControllerPunto(this);

        controllerZona = new ControllerZona(this);

        controllerTerritorio = new ControllerTerritorio(this);

        connectionDetector = new ConnectionDetector(this);

        fragmentManager = getSupportFragmentManager();

        //Colocar en una Estacion de radio..
        startService(new Intent(this, MonitoringService.class));
        startService(new Intent(this, SetTracingServiceWeb.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 1 Supervisor..
        // 2 Repartidor..

        if (controllerLogin.getUserLogin().getPerfil() == 2) {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_vendedor);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_inicio_vendedor));

        } else if (controllerLogin.getUserLogin().getPerfil() == 1) {


            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_supervisor);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_inicio_supervisor));

        }

        View header = navigationView.getHeaderView(0);

        TextView txtNombreUser = (TextView) header.findViewById(R.id.txtNombreUser);
        txtNombreUser.setText(controllerLogin.getUserLogin().getNombre());
        TextView txtVersion = (TextView) header.findViewById(R.id.txtApellidos);
        txtVersion.setText(controllerLogin.getUserLogin().getApellido());

    }

    @Override
    public void onBackPressed() {

        final FragmentManager fm = this.getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentByTag("inicio_vendedor");

        if(fragment != null && fragment.isVisible()) {
            new AlertDialog.Builder(this)
                    .setTitle("Salir")
                    .setMessage("¿ Seguro que quieres salir ?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(ActMenu.this, ActLogUru.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                        }
                    }).create().show();

        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.drawer_vendedor);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_inicio_vendedor));

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String tituloOff = "";
        indicadorFragment = "normal";
        if (connectionDetector.isConnected()) {
            toolbar.setTitle("Menú Inicio");
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            tituloOff =  "OFFLINE";
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.rojo_progress));
        }

        boolean indicadorService = false;
        Class fragmentClass = null;
        if (id == R.id.nav_inicio_vendedor) {
            toolbar.setTitle(String.format("Menú Inicio %s", tituloOff));
            fragmentClass = FragmentInicioVendedor.class;
            indicadorFragment = "inicio_vendedor";
        } else if (id == R.id.nav_vistar_pdv) {
            toolbar.setTitle(String.format("Visitar PDV %s", tituloOff));
            fragmentClass = FragmentVisitarPunto.class;
        } else if (id == R.id.nav_venta_cliente) {
            toolbar.setTitle(String.format("Venta Cliente %s", tituloOff));
            fragmentClass = FragmentVentaCliente.class;
        } else if (id == R.id.nav_gestor_pdv) {
            toolbar.setTitle(String.format("Gestor PDV %s", tituloOff));
            fragmentClass = FragmentCrearPunto.class;
        } else if (id == R.id.nav_planificar_visita) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle(String.format("Planificar Visita %s", tituloOff));
                fragmentClass = FragmentPlanificarVisita.class;
            } else {
                TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }


        } else if (id == R.id.nav_sol_producto) {

            if (connectionDetector.isConnected()) {
/*                toolbar.setTitle(String.format("Solicitar Producto %s", tituloOff));
                fragmentClass = FragmentSolProducto.class;*/
                Intent intent = new Intent(this, ActSolProd.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

            //Area de Reportes del vendedor
        } else if (id == R.id.nav_inventario_vendedor) {
            toolbar.setTitle(String.format("Mi Inventario %s", tituloOff));
            fragmentClass = FragmentInventarioVende.class;
        } else if (id == R.id.nav_ventas_vendedor) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle(String.format("Mis Ventas %s", tituloOff));
                fragmentClass = FragmentVentasVendedor.class;
            } else {
                TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

        } else if (id == R.id.nav_rutero_vendedor) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle(String.format("Mi Rutero %s", tituloOff));
                fragmentClass = FragmentMenuRutero.class;
            } else {
                TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

        } else if (id == R.id.nav_baja_vendedor) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle(String.format("Mis Bajas %s", tituloOff));
                fragmentClass = FragmentBajaVendedor.class;
            } else {
                TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

        } else if (id == R.id.nav_sol_vendedor) {

            if (connectionDetector.isConnected()) {
                toolbar.setTitle(String.format("Mis Solicitudes %s", tituloOff));
                fragmentClass = FragmentSolPenVendedor.class;
            } else {
                TastyToast.makeText(this, "El modulo solo funciona en modo ONLINE", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

        }
        // Menu - Supervisor.
        else if (id == R.id.nav_inicio_supervisor) {
            toolbar.setTitle(String.format("Menú Inicio %s", tituloOff));
            fragmentClass = FragmentInicioSupervisor.class;
        } else if (id == R.id.nav_apro_punto) {
            toolbar.setTitle(String.format("Aprobación de solicitud %s", tituloOff));
            fragmentClass = FragmentSolicitudApro.class;
        } else if (id == R.id.nav_inventario_supervisor) {
            toolbar.setTitle(String.format("Inventario %s", tituloOff));
        } else if (id == R.id.nav_ventas_supervisor) {
            toolbar.setTitle(String.format("Reporte %s", tituloOff));
        } else if (id == R.id.nav_rutero_supervisor) {
            toolbar.setTitle(String.format("Reporte %s", tituloOff));
        } else if (id == R.id.nav_baja_supervisor) {
            toolbar.setTitle(String.format("Reporte %s", tituloOff));
        } else if (id == R.id.nav_sol_supervisor) {
            toolbar.setTitle(String.format("Reporte %s", tituloOff));
        } else if (id == R.id.nav_cerrar_sesion) {
            startActivity(new Intent(this, ActLogUru.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
            indicadorService = true;
        }

        if (!indicadorService) {
            if (connectionDetector.isConnected()) {
                offLineDataVendedor();
            }
        }

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fragment, indicadorFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    private void offLineDataVendedor() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sincronización de Información");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("");
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setIndeterminate(false);

        String url = String.format("%1$s%2$s", getString(R.string.url_base), "servicio_offline");
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJSONVendedor(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("iduser", String.valueOf(controllerLogin.getUserLogin().getId()));
                params.put("iddis", String.valueOf(controllerLogin.getUserLogin().getId_distri()));
                params.put("fecha_sincroniza_offline", controllerLogin.getUserLogin().getFecha_sincroniza_offline() == null ? "": controllerLogin.getUserLogin().getFecha_sincroniza_offline());
                params.put("perfil", String.valueOf(controllerLogin.getUserLogin().getPerfil()));
                params.put("db", controllerLogin.getUserLogin().getBd());

                return params;
            }

        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);
    }

    private void parseJSONVendedor(String response) {

        Gson gson = new Gson();

        final EntSincronizar sincronizar = gson.fromJson(response, EntSincronizar.class);

        if (sincronizar.getEntLisSincronizars().size() > 0) {

            progressDialog.setMax(sincronizar.getEntLisSincronizars().size());
            mensaje = "";
            progressStatus = 0;

            if (sincronizar.getTerritorios() > 0) { funcionesGenerales.deleteObject("territorio"); }

            if (sincronizar.getZonas() > 0) { funcionesGenerales.deleteObject("zona"); }

            if (sincronizar.getPuntos() > 0) { funcionesGenerales.deleteObjectPuntos("punto"); }

            if (sincronizar.getRefes_sims() > 0) { funcionesGenerales.deleteObject("refes_sims"); }

            if (sincronizar.getRefes_combo() > 0) {
                funcionesGenerales.deleteObject("catalogo");
                funcionesGenerales.deleteObject("detalle_catalogo");
                funcionesGenerales.deleteObject("img_catalogo");
            }

            if(sincronizar.getLista_precios() > 0) {
                funcionesGenerales.deleteObject("lista_precios");
            }

            new Thread(new Runnable() {
                public void run() {

                    while (progressStatus < sincronizar.getEntLisSincronizars().size()) {

                        switch (sincronizar.getEntLisSincronizars().get(progressStatus).getTipo_tabla()) {
                            case 1:
                                mensaje = "Sincronizando Territorios.";
                                controllerTerritorio.insertTerritorio(sincronizar.getEntLisSincronizars().get(progressStatus));
                                break;
                            case 2:
                                mensaje = "Sincronizando Zonas.";
                                controllerZona.insertZona(sincronizar.getEntLisSincronizars().get(progressStatus));
                                break;
                            case 3:
                                mensaje = "Sincronizando Puntos.";
                                controllerPunto.insertPunto(sincronizar.getEntLisSincronizars().get(progressStatus), 0);
                                break;
                            case 4:
                                mensaje = "Sincronizando Referencias SIM";
                                controllerSim.insertReferenciaSim(sincronizar.getEntLisSincronizars().get(progressStatus));
                                break;
                            case 5:
                                mensaje = "Sincronizando Referencias CATALOGO";
                                controllerCatalogo.insertReferenciaCombos(sincronizar.getEntLisSincronizars().get(progressStatus));
                                break;
                            case 6:
                                mensaje = "Sincronizando Listas de Precios";
                                controllerListPrecio.insertLisPrecios(sincronizar.getEntLisSincronizars().get(progressStatus));
                                break;
                            case 7:
                                mensaje = "Sincronizando Listas de inventario";
                                controllerInventario.insertLisInventario(sincronizar.getEntLisSincronizars().get(progressStatus));
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

                    funcionesGenerales.updateFechaSincro(sincronizar.getFecha_sincroniza(), controllerLogin.getUserLogin().getId());

                    progressDialog.dismiss();

                    if (getIndicador_refres() == 1) {
                        funcionesGenerales.updateFechaSincro(sincronizar.getFecha_sincroniza(), controllerLogin.getUserLogin().getId());
                        setIndicador_refres(0);
                        progressDialog.dismiss();
                        if (controllerLogin.getUserLogin().getPerfil() == 2) {
                            try {

                                Class fragmentClass = FragmentInicioVendedor.class;
                                Fragment fragment = (Fragment) fragmentClass.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.contentPanel, fragment, indicadorFragment).commit();

                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        funcionesGenerales.updateFechaSincro(sincronizar.getFecha_sincroniza(), controllerLogin.getUserLogin().getId());
                        progressDialog.dismiss();
                    }
                }

            }).start();

        } else {
            funcionesGenerales.updateFechaSincro(sincronizar.getFecha_sincroniza(), controllerLogin.getUserLogin().getId());
            progressDialog.dismiss();
        }

    }

}
