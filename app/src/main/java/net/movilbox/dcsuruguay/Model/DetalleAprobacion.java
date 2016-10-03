package net.movilbox.dcsuruguay.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetalleAprobacion implements Serializable {

    @SerializedName("campo")
    private String campo;

    @SerializedName("valor")
    private String valor;

    @SerializedName("cambio")
    private String cambio;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }
}
