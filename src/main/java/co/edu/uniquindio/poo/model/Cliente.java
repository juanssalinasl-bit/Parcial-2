package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {

    private final String id;
    private final String identificacion;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private final List<Equipo> listaEquipos = new ArrayList<>();
    private final List<OrdenServicio> listaOrdenes = new ArrayList<>();

    public Cliente(String id, String identificacion, String nombre, String telefono,
                   String correo, String direccion) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getId() { return id; }
    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
    public List<Equipo> getListaEquipos() { return Collections.unmodifiableList(listaEquipos); }
    public List<OrdenServicio> getListaOrdenes() { return Collections.unmodifiableList(listaOrdenes); }
}
