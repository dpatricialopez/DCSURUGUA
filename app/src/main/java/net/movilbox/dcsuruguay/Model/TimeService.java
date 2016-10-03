package net.movilbox.dcsuruguay.Model;

/**
 * Created by germangarcia on 18/05/16.
 */
public class TimeService {

    private int idUser;

    private int traking;

    private int timeservice;

    private String idDistri;

    private String dataBase;

    private String fechainicial;

    private String fechaFinal;

    public String getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(String fechainicial) {
        this.fechainicial = fechainicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getTraking() {
        return traking;
    }

    public void setTraking(int traking) {
        this.traking = traking;
    }

    public int getTimeservice() {
        return timeservice;
    }

    public void setTimeservice(int timeservice) {
        this.timeservice = timeservice;
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
