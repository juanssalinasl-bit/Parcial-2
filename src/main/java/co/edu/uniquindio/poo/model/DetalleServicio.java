package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DetalleServicio {
    private final String codigo;
    private final Equipo equipo;
    private final Servicio servicio;
    private final String descripcion;
    private Optional<Tecnico> tecnico = Optional.empty();
    private final int horasTrabajo;
    private String observacionesTec;
    private EstadoDetalle estadoDetalle;
    private final List<RepuestoUtilizado> repuestosUtilizados = new ArrayList<>();

    public DetalleServicio(String codigo, Equipo equipo, Servicio servicio, String descripcion,
                           int horasTrabajo) {
        this.codigo = codigo;
        this.equipo = equipo;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.horasTrabajo = horasTrabajo;
        this.observacionesTec = "Sin observaciones";
        this.estadoDetalle = EstadoDetalle.ABIERTA;
    }

    public String getCodigo() { return codigo; }
    public Equipo getEquipo() { return equipo; }
    public Servicio getServicio() { return servicio; }
    public String getDescripcion() { return descripcion; }
    public Optional<Tecnico> getTecnico() { return tecnico; }
    public int getHorasTrabajo() { return horasTrabajo; }
    public String getObservacionesTec() { return observacionesTec; }
    public EstadoDetalle getEstadoDetalle() { return estadoDetalle; }
    public List<RepuestoUtilizado> getRepuestosUtilizados() {
        return Collections.unmodifiableList(repuestosUtilizados);
    }

    public void asignarTecnico(Tecnico tecnico) { this.tecnico = Optional.of(tecnico); }
    public void agregarRepuesto(RepuestoUtilizado repuesto) { repuestosUtilizados.add(repuesto); }
    public void setObservacionesTec(String observacionesTec) { this.observacionesTec = observacionesTec; }
    public void setEstadoDetalle(EstadoDetalle estadoDetalle) { this.estadoDetalle = estadoDetalle; }
    public double calcularValorDetalle() {
        return servicio.calcularCosto(equipo, horasTrabajo, repuestosUtilizados);
    }

    @Override
    public String toString() {
        String nombreTecnico = tecnico.map(Tecnico::getNombre).orElse("Sin asignar");
        return codigo + " | " + equipo.getCodigo() + " " + equipo.getMarca() + " "
                + equipo.getModelo() + " | " + servicio.getNombre() + " | " + estadoDetalle
                + " | Técnico: " + nombreTecnico + " | Valor: $"
                + String.format("%,.0f", calcularValorDetalle());
    }
}
