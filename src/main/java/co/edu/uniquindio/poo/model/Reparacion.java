package co.edu.uniquindio.poo.model;

public class Reparacion extends Servicio{


    public Reparacion(String codigo) {
        super(codigo, "Reparación correctiva", "Solución de fallas encontradas", 45000);
    }

    @Override public TipoServicio getTipoServicio() { return TipoServicio.REPARACION; }}
