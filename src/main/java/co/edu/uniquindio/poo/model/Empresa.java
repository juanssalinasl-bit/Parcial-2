package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private final String nombre;
    private final String nit;
    private final List<Cliente> listaClientes = new ArrayList<>();
    private final List<Tecnico> listaTecnicos = new ArrayList<>();
    private final List<Equipo> listaEquipos = new ArrayList<>();
    private final List<Servicio> listaServicios = new ArrayList<>();
    private final List<Repuesto> listaRepuestos = new ArrayList<>();
    private final List<OrdenServicio> listaOrdenesServicio = new ArrayList<>();
    private int siguienteCliente = 1;
    private int siguienteServicio = 1;

    public Empresa(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
    }

    public String getNombre() { return nombre; }
    public String getNit() { return nit; }
    public List<Cliente> getListaClientes() { return new ArrayList<>(listaClientes); }
    public List<Tecnico> getListaTecnicos() { return new ArrayList<>(listaTecnicos); }
    public List<Equipo> getListaEquipos() { return new ArrayList<>(listaEquipos); }
    public List<Servicio> getListaServicios() { return new ArrayList<>(listaServicios); }
    public List<Repuesto> getListaRepuestos() { return new ArrayList<>(listaRepuestos); }
    public List<OrdenServicio> getListaOrdenesServicio() { return new ArrayList<>(listaOrdenesServicio); }

}
