package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Adapter.ViewPagerAdapter;
import net.movilbox.dcsuruguay.Model.ReferenciasCombos;
import net.movilbox.dcsuruguay.R;

import java.text.DecimalFormat;

public class ActDetalleProducto extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ViewPager viewPager;
    PagerAdapter adapter;
    private LinearLayout pager_indicator;
    private ReferenciasCombos mDescribable = new ReferenciasCombos();
    private ImageView[] dots;
    private int dotsCount;

    public TextView txtReferencia;
    public TextView txtValorR;
    public TextView txtTaPantalla;
    public TextView txtCamara;
    public TextView txtCamaraFron;
    public TextView txtMemoria;
    public TextView txtSpech;
    public TextView txtFlash;
    public TextView txtBanda;
    public TextView txtExpandible;
    public TextView txtBateria;
    public TextView txtBluetooth;
    public TextView txtTactil;
    public TextView txtTecFisico;
    public TextView txtCorreo;
    public TextView txtWifi;
    public TextView txtGps;
    public TextView txtSo;
    public TextView txtWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // To make activity full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ReferenciasCombos) bundle.getSerializable("value");
        }

        DecimalFormat format = new DecimalFormat("#,###.##");

        txtReferencia = (TextView) findViewById(R.id.txtNombre);
        txtValorR = (TextView) findViewById(R.id.txtPecioP);
        txtTaPantalla = (TextView) findViewById(R.id.txtPantalla);
        txtCamara = (TextView) findViewById(R.id.txtCamara);
        txtCamaraFron = (TextView) findViewById(R.id.txtCamaraFrontal);
        txtMemoria = (TextView) findViewById(R.id.txtMemoria);

        txtReferencia = (TextView) findViewById(R.id.txtNombre);
        txtValorR = (TextView) findViewById(R.id.txtPecioP);
        txtTaPantalla = (TextView) findViewById(R.id.txtPantalla);
        txtCamaraFron = (TextView) findViewById(R.id.txtCamaraFrontal);
        txtMemoria = (TextView) findViewById(R.id.txtMemoria);

        txtSpech = (TextView) findViewById(R.id.txtspech);

        txtFlash = (TextView) findViewById(R.id.txtFlash);
        txtBanda = (TextView) findViewById(R.id.txtbanda);
        txtExpandible = (TextView) findViewById(R.id.txtExpandible);
        txtBateria = (TextView) findViewById(R.id.txtBateria);
        txtBluetooth = (TextView) findViewById(R.id.txtBluetooth);
        txtTactil = (TextView) findViewById(R.id.txtTactil);
        txtTecFisico = (TextView) findViewById(R.id.txtTecFisico);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);
        txtWifi = (TextView) findViewById(R.id.txtWifi);
        txtGps = (TextView) findViewById(R.id.txtGps);
        txtSo = (TextView) findViewById(R.id.txtSo);
        txtWeb = (TextView) findViewById(R.id.txtWeb);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(ActDetalleProducto.this, mDescribable);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(this);

        setUiPageViewController();

        txtReferencia.setText(mDescribable.getDescripcion());
        txtValorR.setText(String.format("Precio: S/. %s", format.format(mDescribable.getPrecio_referencia())));
        txtTaPantalla.setText(String.format("Pantalla: %s", mDescribable.getPantalla()));
        txtCamara.setText(String.format("Camara: %s", mDescribable.getCam_tras()));
        txtSpech.setText(String.format("%s", mDescribable.getSpeech()));
        txtCamaraFron.setText(String.format("Camara Fro: %s", mDescribable.getCam_frontal()));
        txtMemoria.setText(String.format("Memoria: %s", mDescribable.getMemoria()));
        txtFlash.setText(String.format("Flash: %s", mDescribable.getFlash()));
        txtBanda.setText(String.format("Banda: %s", mDescribable.getBanda()));
        txtExpandible.setText(String.format("Expandible: %s", mDescribable.getExpandible()));
        txtBateria.setText(String.format("Bateria: %s", mDescribable.getBateria()));
        txtBluetooth.setText(String.format("Bluetooth: %s", mDescribable.getBluetooth()));
        txtTactil.setText(String.format("Tactil: %s", mDescribable.getTactil()));
        txtTecFisico.setText(String.format("Tec Fisico: %s", mDescribable.getTec_fisico()));
        txtCorreo.setText(String.format("Correo: %s", mDescribable.getCorreo()));
        txtWifi.setText(String.format("Wifi: %s", mDescribable.getWifi()));
        txtGps.setText(String.format("Gps: %s", mDescribable.getGps()));
        txtSo.setText(String.format("So: %s", mDescribable.getSo()));
        txtWeb.setText(String.format("Web: %s", mDescribable.getWeb()));

        if (mDescribable.getCam_tras() != null) {
            if(mDescribable.getCam_tras().equals("NO"))
                txtCamara.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getCam_frontal() != null) {
            if(mDescribable.getCam_frontal().equals("NO"))
                txtCamaraFron.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getMemoria() != null) {
            if(mDescribable.getMemoria().equals("NO"))
                txtMemoria.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getFlash() != null) {
            if (mDescribable.getFlash().equals("NO"))
                txtFlash.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getExpandible() != null) {
            if(mDescribable.getExpandible().equals("NO"))
                txtExpandible.setTextColor(getResources().getColor(R.color.rojo_progress));
        }


        if (mDescribable.getBateria() != null) {
            if (mDescribable.getBateria().equals("NO"))
                txtBateria.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getBluetooth() != null) {
            if (mDescribable.getBluetooth().equals("NO"))
                txtBluetooth.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getTactil() != null) {
            if (mDescribable.getTactil().equals("NO"))
                txtTactil.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getTec_fisico() != null) {
            if (mDescribable.getTec_fisico().equals("NO"))
                txtTecFisico.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getCorreo() != null) {
            if (mDescribable.getCorreo().equals("NO"))
                txtCorreo.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getWifi() != null) {
            if (mDescribable.getWifi().equals("NO"))
                txtWifi.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getGps() != null) {
            if (mDescribable.getGps().equals("NO"))
                txtGps.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

        if (mDescribable.getWeb() != null) {
            if (mDescribable.getWeb().equals("NO"))
                txtWeb.setTextColor(getResources().getColor(R.color.rojo_progress));
        }

    }

    private void setUiPageViewController() {

        dotsCount = adapter.getCount();

        if (dotsCount > 0) {

            dots = new ImageView[dotsCount];

            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(4, 0, 4, 0);

                pager_indicator.addView(dots[i], params);
            }

            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.btn_next:
                viewPager.setCurrentItem((viewPager.getCurrentItem() < dotsCount)
                        ? viewPager.getCurrentItem() + 1 : 0);
                break;

            case R.id.btn_finish:
                finish();
                break;
        }*/
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {
            //btnNext.setVisibility(View.GONE);
            //btnFinish.setVisibility(View.VISIBLE);
        } else {
            //btnNext.setVisibility(View.VISIBLE);
            //btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
