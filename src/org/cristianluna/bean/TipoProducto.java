package org.cristianluna.bean;


public class TipoProducto {
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 09/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private int codigoTipoProducto;
    private String descripcion;

    public TipoProducto() {
    }

    public TipoProducto(int codigoTipoProducto, String descripcion) {
        this.codigoTipoProducto = codigoTipoProducto;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
