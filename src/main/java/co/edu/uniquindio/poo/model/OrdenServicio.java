package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdenServicio {
    private final String codigo;
    private final LocalDate fecha;
    private final Cliente cliente;
    private final String observaciones;
    private EstadoOrden estadoOrden;
    private final List<DetalleServicio> listaDetallesServicio = new ArrayList<>();

    public OrdenServicio(String codigo, LocalDate fecha, Cliente cliente, String observaciones) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.observaciones = observaciones;
        this.estadoOrden = EstadoOrden.ABIERTA;
    }

    public String getCodigo() { return codigo; }
    public LocalDate getFecha() { return fecha; }
    public Cliente getCliente() { return cliente; }
    public String getObservaciones() { return observaciones; }
    public EstadoOrden getEstadoOrden() { return estadoOrden; }
    public List<DetalleServicio> getListaDetallesServicio() {
        return Collections.unmodifiableList(listaDetallesServicio);
    }

    public void agregarDetalle(DetalleServicio detalle) { listaDetallesServicio.add(detalle); }
    public void setEstadoOrden(EstadoOrden estadoOrden) { this.estadoOrden = estadoOrden; }

    public double calcularCostoFinal() {
        double total = 0;
        for (DetalleServicio detalle : listaDetallesServicio) total += detalle.calcularValorDetalle();
        return total;
    }

    @Override
    public String toString() {
        return codigo + " | " + fecha + " | Cliente: " + cliente.getNombre()
                + " | Estado: " + estadoOrden + " | Detalles: " + listaDetallesServicio.size()
                + " | Total: $" + String.format("%,.0f", calcularCostoFinal());
    }
}
