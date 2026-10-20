package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public class CELInteligente extends Equipo {
    private final String tipoSistema;
    private final double gbAlmacenamiento;
    private final double capacidadBateriaMAh;
    private final boolean compatible5G;

    public CELInteligente(String codigo, String serie, String marca, String modelo, LocalDate anio,
                          String estado, Cliente cliente, String tipoSistema,
                          double gbAlmacenamiento, double capacidadBateriaMAh,
                          boolean compatible5G) {
        super(codigo, serie, marca, modelo, anio, estado, cliente);
        this.tipoSistema = tipoSistema;
        this.gbAlmacenamiento = gbAlmacenamiento;
        this.capacidadBateriaMAh = capacidadBateriaMAh;
        this.compatible5G = compatible5G;
    }

    public String getTipoSistema() { return tipoSistema; }
    public double getGbAlmacenamiento() { return gbAlmacenamiento; }
    public double getCapacidadBateriaMAh() { return capacidadBateriaMAh; }
    public boolean isCompatible5G() { return compatible5G; }
    @Override public TipoEquipo getTipoEquipo() { return TipoEquipo.CELULAR_INTELIGENTE; }
}
