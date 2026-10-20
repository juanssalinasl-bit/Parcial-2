package co.edu.uniquindio.poo.model;

import java.util.List;

public class Diagnostico extends Servicio {
    public Diagnostico(String codigo) {
        super(codigo, "Diagnóstico", "Identificación de fallas del equipo", 35000);
    }

    @Override public TipoServicio getTipoServicio() { return TipoServicio.DIAGNOSTICO; }
    @Override public double calcularCosto(Equipo equipo, int horas, List<RepuestoUtilizado> repuestos) {
        return getTarifaBase();
    }
    }

