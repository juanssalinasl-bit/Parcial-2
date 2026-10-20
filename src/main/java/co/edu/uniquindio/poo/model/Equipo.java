package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public abstract class Equipo {

    protected final String codigo;
    protected final String numeroSerie;
    protected final String marca;
    protected final String modelo;
    protected final LocalDate anioFabricacion;
    protected final String descripcionEstado;
    protected final Cliente cliente;

    public Equipo(String codigo, String numeroSerie, String marca, String modelo,
                  LocalDate anioFabricacion, String descripcionEstado, Cliente cliente) {
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.descripcionEstado = descripcionEstado;
        this.cliente = cliente;
    }

    public String getCodigo() { return codigo; }
    public String getNumeroSerie() { return numeroSerie; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public LocalDate getAnioFabricacion() { return anioFabricacion; }
    public String getDescripcionEstado() { return descripcionEstado; }
    public Cliente getCliente() { return cliente; }
    public abstract TipoEquipo getTipoEquipo();

    @Override
    public String toString() {
        return codigo + " | " + getTipoEquipo() + " | " + marca + " " + modelo
                + " | Serie: " + numeroSerie + " | Año: " + anioFabricacion.getYear()
                + " | Cliente: " + cliente.getNombre() + " | Estado físico: " + descripcionEstado;
    }
}
