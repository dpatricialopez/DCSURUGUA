package net.movilbox.dcsuruguay.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.ControllerTomarPedido;
import net.movilbox.dcsuruguay.R;

import net.movilbox.dcsuruguay.Adapter.TabsAdapter;
import net.movilbox.dcsuruguay.DataBase.DBHelper;
import net.movilbox.dcsuruguay.Model.ResponseMarcarPedido;
import net.movilbox.dcsuruguay.Fragment.FragmentCombos;
import net.movilbox.dcsuruguay.Fragment.FragmentSimcardP;

import net.movilbox.dcsuruguay.Services.ConnectionDetector;
import net.movilbox.dcsuruguay.Services.ConnectionDetector;

public class ActTomarPedido extends AppCompatActivity {

    private Bundle bundle;
    private ResponseMarcarPedido thumbs = new ResponseMarcarPedido();
    private String indicadorPage;

    private ConnectionDetector connectionDetector;
    private ControllerTomarPedido mydb;
    private ControllerLogin controllerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tomar_pedido);
        connectionDetector = new ConnectionDetector(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (connectionDetector.isConnected()) {
            toolbar.setTitle("Tomar Pedido");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            toolbar.setBackgroundColor(Color.RED);
            toolbar.setTitle("Tomar Pedido Offline");
        }

        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            thumbs = (ResponseMarcarPedido) bundle.getSerializable("value");
            indicadorPage = bundle.getString("page");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (indicadorPage.equals("marcar_visita")) {
                    finish();
                } else if (indicadorPage.equals("marcar_rutero")) {
                    finish();
                }

            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        tabsAdapter.addFragment(new FragmentSimcardP(thumbs.getId_pos(), thumbs.getIdZona()), "SIMCARD");

        tabsAdapter.addFragment(new FragmentCombos(thumbs.getId_pos(), thumbs.getIdZona()), "COMBOS");
        mydb = new ControllerTomarPedido(this);
         if (mydb.getFuncionesHabilitadasByTag("tomar_pedido_equipos"))
             tabsAdapter.addFragment(new FragmentCombos(thumbs.getId_pos(), thumbs.getIdZona()), "EQUIPOS");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

}
