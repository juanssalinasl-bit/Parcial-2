package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public class PCPortatil extends Equipo{

    private final double tamanoPantalla;
    private final double gbRAM;
    private final double gbAlmacenamiento;
    private final boolean bateriaExtraible;

    public PCPortatil(String codigo, String serie, String marca, String modelo, LocalDate anio,
                      String estado, Cliente cliente, double tamanoPantalla, double gbRAM,
                      double gbAlmacenamiento, boolean bateriaExtraible) {
        super(codigo, serie, marca, modelo, anio, estado, cliente);
        this.tamanoPantalla = tamanoPantalla;
        this.gbRAM = gbRAM;
        this.gbAlmacenamiento = gbAlmacenamiento;
        this.bateriaExtraible = bateriaExtraible;
    }

    public double getTamanoPantalla() { return tamanoPantalla; }
    public double getGbRAM() { return gbRAM; }
    public double getGbAlmacenamiento() { return gbAlmacenamiento; }
    public boolean isBateriaExtraible() { return bateriaExtraible; }
    @Override public TipoEquipo getTipoEquipo() { return TipoEquipo.PC_PORTATIL; }

    @Override
    public String toString() {
        return super.toString() + " | Pantalla: " + tamanoPantalla + " pulgadas | RAM: " + gbRAM
                + " GB | Almacenamiento: " + gbAlmacenamiento + " GB | Batería extraíble: "
                + (bateriaExtraible ? "Sí" : "No");
    }
}
