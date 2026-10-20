package co.edu.uniquindio.poo.model;

import java.util.List;

public class Mantenimiento extends Servicio {
    public Mantenimiento(String codigo) {
        super(codigo, "Mantenimiento preventivo", "Limpieza, revisión y optimización", 40000);
    }

    @Override public TipoServicio getTipoServicio() { return TipoServicio.MANTENIMIENTO_PREVENTIVO; }

    @Override
    public double calcularCosto(Equipo equipo, int horas, List<RepuestoUtilizado> repuestos) {
        if (equipo.getTipoEquipo() == TipoEquipo.PC_PORTATIL) return 60000;
        if (equipo.getTipoEquipo() == TipoEquipo.PC_ESCRITORIO) return 70000;
        return 40000;
    }
}
