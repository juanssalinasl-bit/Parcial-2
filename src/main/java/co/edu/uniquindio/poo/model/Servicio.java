package co.edu.uniquindio.poo.model;

import java.util.List;

public abstract class Servicio {

    protected final String codigo;
    protected final String nombre;
    protected final String descripcion;
    protected final double tarifaBase;

    public Servicio(String codigo, String nombre, String descripcion, double tarifaBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tarifaBase = tarifaBase;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getTarifaBase() { return tarifaBase; }
    public abstract TipoServicio getTipoServicio();
    public abstract double calcularCosto(Equipo equipo, int horas, List<RepuestoUtilizado> repuestos);

    @Override
    public String toString() { return codigo + " | " + nombre + " | " + descripcion; }

}
