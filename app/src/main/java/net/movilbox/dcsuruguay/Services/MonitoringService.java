package net.movilbox.dcsuruguay.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.movilbox.dcsuruguay.Controller.FuncionesGenerales;
import net.movilbox.dcsuruguay.Model.TimeService;
import net.movilbox.dcsuruguay.Model.Tracing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoringService extends Service {

    private static final String TAG = MonitoringService.class.getSimpleName();
    TimerTask timerTask;
    private GpsServicesContext gpsServices;
    private TimeService timeService;
    private FuncionesGenerales funcionesGenerales;

    public MonitoringService() { }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer timer = new Timer();

        funcionesGenerales = new FuncionesGenerales(getApplicationContext());

        timeService = funcionesGenerales.getTimeService();

        if (timerTask == null) {

            timerTask = new TimerTask() {
                @Override
                public void run() {

                    gpsServices = new GpsServicesContext(getApplicationContext());

                    timeService = funcionesGenerales.getTimeService();

                    Date date = new Date();
                    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss");

                    if (isHourInInterval(hourdateFormat.format(date).toString(), timeService.getFechainicial(), timeService.getFechaFinal())) {
                        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                        Intent batteryStatus = registerReceiver(null, ifilter);

                        int  temperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                        int  level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

                        Tracing tracing = new Tracing();

                        tracing.setIdUser(timeService.getIdUser());

                        if (Build.VERSION.SDK_INT >= 23) {
                            tracing.setImei(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                        } else {
                            tracing.setImei(telephonyManager.getDeviceId());
                        }

                        tracing.setDateTime(getDatePhone());
                        tracing.setBatteryLavel(level);
                        tracing.setTemperatura(temperature);
                        tracing.setLatitud(gpsServices.getLatitude());
                        tracing.setLongitud(gpsServices.getLongitude());
                        tracing.setIdDistri(timeService.getIdDistri());
                        tracing.setDataBase(timeService.getDataBase());

                        if (funcionesGenerales.insertTracing(tracing))
                            Log.d(TAG, "Inserto los siguientes datos: Imei= "+ tracing.getImei() +" Latitud= "+ tracing.getLatitud() +" Longitud= "+tracing.getLongitud() +
                                    " Fecha y Hora Actual= "+getDatePhone() +" id Usuario = "+timeService.getIdUser()+" Data base = "+timeService.getDataBase()+" id Distri= "+timeService.getIdDistri());
                        else
                            Log.d(TAG, "No se esta insertando los datos...");

                    }
                }
            };

            //Tiempo Recuperado desde la base de datos

            timer.scheduleAtFixedRate(timerTask, 0, timeService.getTraking());

        }

        return START_STICKY;
    }

    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
    }

    private String getDatePhone() {

        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatteDate = df.format(date);

        return formatteDate;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
