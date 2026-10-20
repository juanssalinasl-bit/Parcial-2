package co.edu.uniquindio.poo.model;

public class Repuesto {

    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final CategoriaRepuesto categoriaRepuesto;
    private final double precioUD;
    private int cantidadINV;

    public Repuesto(String codigo, String nombre, String descripcion, double precioUD,
                    int cantidadINV, CategoriaRepuesto categoriaRepuesto) {
        if (precioUD < 0 || cantidadINV < 0) {
            throw new IllegalArgumentException("El precio y la cantidad deben ser positivos o cero.");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUD = precioUD;
        this.cantidadINV = cantidadINV;
        this.categoriaRepuesto = categoriaRepuesto;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecioUD() { return precioUD; }
    public int getCantidadINV() { return cantidadINV; }
    public CategoriaRepuesto getCategoriaRepuesto() { return categoriaRepuesto; }

    public boolean descontar(int cantidad) {
        if (cantidad <= 0 || cantidad > cantidadINV) return false;
        cantidadINV -= cantidad;
        return true;
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | " + categoriaRepuesto + " | $"
                + String.format("%,.0f", precioUD) + " c/u | Existencias: " + cantidadINV;
    }
}
