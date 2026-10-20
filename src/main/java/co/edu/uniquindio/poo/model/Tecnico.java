package co.edu.uniquindio.poo.model;

public class Tecnico {

    private final String id;
    private final String nombre;
    private final String identificacion;
    private final Especialidad especialidad;
    private EstadoLaboral estadoLaboral;
    private final String direccion;

    public Tecnico(String id, String nombre, String identificacion, Especialidad especialidad,
                   EstadoLaboral estadoLaboral, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.especialidad = especialidad;
        this.estadoLaboral = estadoLaboral;
        this.direccion = direccion;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public Especialidad getEspecialidad() { return especialidad; }
    public EstadoLaboral getEstadoLaboral() { return estadoLaboral; }
    public String getDireccion() { return direccion; }
    public void setEstadoLaboral(EstadoLaboral estadoLaboral) { this.estadoLaboral = estadoLaboral; }
}
