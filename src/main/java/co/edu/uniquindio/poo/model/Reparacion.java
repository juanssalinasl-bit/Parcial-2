package co.edu.uniquindio.poo.model;

import java.util.List;

public class Reparacion extends Servicio{


    public Reparacion(String codigo) {
        super(codigo, "Reparación correctiva", "Solución de fallas encontradas", 45000);
    }

    @Override public TipoServicio getTipoServicio() { return TipoServicio.REPARACION; }
    @Override
    public double calcularCosto(Equipo equipo, int horas, List<RepuestoUtilizado> repuestos) {
        double costoRepuestos = 0;
        for (RepuestoUtilizado usado : repuestos) costoRepuestos += usado.calcularCosto();
        return getTarifaBase() * horas + costoRepuestos;
    }
}
