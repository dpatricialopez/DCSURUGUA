package net.movilbox.dcsuruguay.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.movilbox.dcsuruguay.Controller.ControllerIndicadores;
import net.movilbox.dcsuruguay.Controller.ControllerLogin;
import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;
import net.movilbox.dcsuruguay.Model.EntPuntoIndicado;
import net.movilbox.dcsuruguay.R;

import java.util.List;

public class FragmentDasboardVendedor extends BaseVolleyFragment {

    private ProgressBar mProgress1;
    private ProgressBar mProgress2;
    private TextView txtFinal;
    private int mProgressStatus = 0;
    private int visitasTotal = 0;
    private int totalConPedido = 0;
    private Handler mHandler = new Handler();
    private TextView txtPromedio;
    private TextView txtFinalPedido;
    private TextView txtPromediopedido;
    private TextView txtPorCum;
    private TextView txtPorEfec;

    public FragmentDasboardVendedor() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dasboard_vendedor, container, false);

        mProgress1 = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgress2 = (ProgressBar) view.findViewById(R.id.progressBar2);

        txtFinal = (TextView) view.findViewById(R.id.txtFinal);
        txtPromedio = (TextView) view.findViewById(R.id.txtPromedio);
        txtFinalPedido = (TextView) view.findViewById(R.id.txtFinalPedido);
        txtPromediopedido = (TextView) view.findViewById(R.id.txtPromediopedido);
        txtPorCum = (TextView) view.findViewById(R.id.txtPorCum);
        txtPorEfec = (TextView) view.findViewById(R.id.txtPorEfec);

        funcionesGenerales = new FuncionesGenerales(getActivity());
        controllerIndicadores = new ControllerIndicadores(getActivity());
        controllerLogin = new ControllerLogin(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        indicadorVendedor();
    }

    private void indicadorVendedor() {

        final List<EntPuntoIndicado> puntoIndicadoList = controllerIndicadores.getIndicadores(controllerLogin.getUserLogin().getId(), controllerLogin.getUserLogin().getId_distri());

        txtFinal.setText(String.format("%1$s", puntoIndicadoList.size()));

        //txtFinalCumpli.setText(String.format("%1$s", entIndicadoresLocal.getCant_cumplimiento_sim()));
        //txtFinalPedidoCumpli.setText(String.format("%1$s", entIndicadoresLocal.getCant_cumplimiento_combo()));

        //txtCantidadCumpli.setText(String.format("%1$s",entIndicadoresLocal.getCant_ventas_sim()));
        //txtPromediopedidoCumpli.setText(String.format("%1$s",entIndicadoresLocal.getCant_ventas_combo()));

        new Thread(new Runnable() {
            public void run() {

                while (mProgressStatus < puntoIndicadoList.size()) {

                    if (puntoIndicadoList.get(mProgressStatus).getTipo_visita() == 1 || puntoIndicadoList.get(mProgressStatus).getTipo_visita() == 2)
                        visitasTotal++;

                    if (puntoIndicadoList.get(mProgressStatus).getTipo_visita() == 1)
                        totalConPedido++;
                    mProgressStatus++;

                }
                // Update the progress bar
                mHandler.post(new Runnable() {
                    public void run() {
                        //Visitas
                        // Progress 1
                        float promedio = (float) visitasTotal / (float) puntoIndicadoList.size();
                        promedio = promedio * 100;
                        mProgress1.setProgress((int) promedio);
                        txtPromedio.setText(String.format("%1$s", visitasTotal));

                        // Progress 2
                        txtFinalPedido.setText(String.format("%1$s", visitasTotal));
                        txtPromediopedido.setText(String.format("%1$s", totalConPedido));
                        float promedio2 = (float) totalConPedido / (float) visitasTotal;

                        promedio2 = promedio2 * 100;

                        mProgress2.setProgress((int) promedio2);

                        txtPorCum.setText((int) promedio + "%");
                        txtPorEfec.setText((int) promedio2 + "%");
                        }
                    });


            }
        }).start();

    }

}
