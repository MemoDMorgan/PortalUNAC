/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.Calendar;

/**
 *
 * @author Guillermo
 */
public class Mensaje {
    int Id,nEstado;
    String mensaje,mailDe,mailPara,NombreDe,NombrePara,descripcionEstado;
    Calendar fechaMensaje;

    public Calendar getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(Calendar fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public Mensaje(int Id, int nEstado, String mensaje, String mailDe, String mailPara) {
        this.Id = Id;
        this.nEstado = nEstado;
        this.mensaje = mensaje;
        this.mailDe = mailDe;
        this.mailPara = mailPara;
    }

    public Mensaje() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombreDe() {
        return NombreDe;
    }

    public void setNombreDe(String NombreDe) {
        this.NombreDe = NombreDe;
    }

    public String getNombrePara() {
        return NombrePara;
    }

    public void setNombrePara(String NombrePara) {
        this.NombrePara = NombrePara;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getMailDe() {
        return mailDe;
    }

    public void setMailDe(String mailDe) {
        this.mailDe = mailDe;
    }

    public String getMailPara() {
        return mailPara;
    }

    public void setMailPara(String mailPara) {
        this.mailPara = mailPara;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getnEstado() {
        return nEstado;
    }

    public void setnEstado(int nEstado) {
        this.nEstado = nEstado;
    }

        
}
