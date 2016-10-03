package net.movilbox.dcsuruguay.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.tastytoast.TastyToast;

import net.movilbox.dcsuruguay.Activity.ActCarrito;
import net.movilbox.dcsuruguay.Adapter.TabsAdapter;
import net.movilbox.dcsuruguay.Controller.ControllerCarrito;
import net.movilbox.dcsuruguay.Model.EntCarritoVenta;
import net.movilbox.dcsuruguay.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVentaCliente extends BaseVolleyFragment {

    public FragmentVentaCliente() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_venta_cliente, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());

        tabsAdapter.addFragment(new FragmentSimcard(), "SIMCARD");
        tabsAdapter.addFragment(new FragmentProducto(), "EQUIPO");

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        controllerCarrito = new ControllerCarrito(getActivity());

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item2 = menu.add("Carrito");
        item2.setIcon(R.drawable.ic_shopping_cart_white_24dp); // sets icon
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                List<EntCarritoVenta> entCarritoVentaList = controllerCarrito.listCarrito(1);

                if (entCarritoVentaList.size() > 0) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getActivity(), ActCarrito.class);
                    bundle.putInt("id_pos", 1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    TastyToast.makeText(getActivity(), "No hay productos en el carrito", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                return true;
            }

        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
