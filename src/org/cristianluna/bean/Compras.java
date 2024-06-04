package org.cristianluna.bean;

import java.time.LocalDate;


public class Compras {
    /*
    Nombre: Cristian Alfredo Luna Sisimit
    Fecha de creacion: 09/05/2024
    
    Fecha de modificacion: --/--/----
    */
    
    private int numeroDocumento;
    private LocalDate fechaDocumento;
    private String descripcion;
    private Double totalDocumento;

    public Compras() {
    }

    public Compras(int numeroDocumento, LocalDate fechaDocumento, String descripcion, Double totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(LocalDate fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(Double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }
    
    @Override
    public String toString() {
        return getNumeroDocumento() + " | " +
                getFechaDocumento() + " | " +
                getDescripcion();
    }
}
