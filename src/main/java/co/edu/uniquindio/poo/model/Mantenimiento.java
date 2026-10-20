package co.edu.uniquindio.poo.model;

public class Mantenimiento extends Servicio {
    public Mantenimiento(String codigo) {
        super(codigo, "Mantenimiento preventivo", "Limpieza, revisión y optimización", 40000);
    }

    @Override public TipoServicio getTipoServicio() { return TipoServicio.MANTENIMIENTO_PREVENTIVO; }
}
