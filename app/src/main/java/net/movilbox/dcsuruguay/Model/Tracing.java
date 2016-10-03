package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by germangarcia on 18/05/16.
 */
public class Tracing {

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("imei")
    private String imei;

    @SerializedName("dateTime")
    private String dateTime;

    @SerializedName("batteryLavel")
    private int batteryLavel;

    @SerializedName("temperatura")
    private int temperatura;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("idDistri")
    private String idDistri;

    @SerializedName("dataBase")
    private String dataBase;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getBatteryLavel() {
        return batteryLavel;
    }

    public void setBatteryLavel(int batteryLavel) {
        this.batteryLavel = batteryLavel;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getIdDistri() {
        return idDistri;
    }

    public void setIdDistri(String idDistri) {
        this.idDistri = idDistri;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
}
