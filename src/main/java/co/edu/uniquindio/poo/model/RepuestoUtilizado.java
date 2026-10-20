package co.edu.uniquindio.poo.model;

public class RepuestoUtilizado {

    private final Repuesto repuesto;
    private final int cantidad;
    private final double precioUnitarioAplicado;

    public RepuestoUtilizado(Repuesto repuesto, int cantidad) {
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioUnitarioAplicado = repuesto.getPrecioUD();
    }

    public Repuesto getRepuesto() { return repuesto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitarioAplicado() { return precioUnitarioAplicado; }
    public double calcularCosto() { return cantidad * precioUnitarioAplicado; }

    @Override
    public String toString() {
        return repuesto.getNombre() + " x " + cantidad + " | $"
                + String.format("%,.0f", precioUnitarioAplicado) + " c/u";
    }
}
