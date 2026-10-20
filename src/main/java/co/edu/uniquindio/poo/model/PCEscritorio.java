package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public class PCEscritorio extends Equipo{

    private final String tipoCPU;
    private final double gbRAM;
    private final double gbAlmacenamiento;
    private final boolean graficaDedicada;

    public PCEscritorio(String codigo, String serie, String marca, String modelo, LocalDate anio,
                        String estado, Cliente cliente, String tipoCPU, double gbRAM,
                        double gbAlmacenamiento, boolean graficaDedicada) {
        super(codigo, serie, marca, modelo, anio, estado, cliente);
        this.tipoCPU = tipoCPU;
        this.gbRAM = gbRAM;
        this.gbAlmacenamiento = gbAlmacenamiento;
        this.graficaDedicada = graficaDedicada;
    }

    public String getTipoCPU() { return tipoCPU; }
    public double getGbRAM() { return gbRAM; }
    public double getGbAlmacenamiento() { return gbAlmacenamiento; }
    public boolean isGraficaDedicada() { return graficaDedicada; }
    @Override public TipoEquipo getTipoEquipo() { return TipoEquipo.PC_ESCRITORIO; }

}
