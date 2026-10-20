package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.*;

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


    /** Registra un cliente si el documento todavía no está en la lista. */
    public String registrarCliente(String identificacion, String nombre, String telefono,
                                   String correo, String direccion) {
        if (buscarCliente(identificacion).isPresent()) {
            return "Ya existe un cliente con ese documento.";
        }
        Cliente cliente = new Cliente("C" + siguienteCliente++, identificacion,
                nombre, telefono, correo, direccion);
        listaClientes.add(cliente);
        return "Cliente registrado: " + cliente.getNombre();
    }

    /** Busca un cliente por documento. Optional.empty significa que no existe. */
    public Optional<Cliente> buscarCliente(String identificacion) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getIdentificacion().equalsIgnoreCase(identificacion)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    /** Busca un equipo por su código único. */
    public Optional<Equipo> buscarEquipo(String codigo) {
        for (Equipo equipo : listaEquipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo)) return Optional.of(equipo);
        }
        return Optional.empty();
    }

    /** Registra el equipo en la empresa y también en la lista de su cliente. */
    public String registrarEquipo(Equipo equipo) {
        if (Optional.ofNullable(equipo).isEmpty() || Optional.ofNullable(equipo.getCliente()).isEmpty()) {
            return "Faltan datos del equipo o del cliente.";
        }
        if (buscarCliente(equipo.getCliente().getIdentificacion()).isEmpty()) {
            return "El cliente no está registrado.";
        }
        if (buscarEquipo(equipo.getCodigo()).isPresent()) return "Ya existe un equipo con ese código.";
        for (Equipo registrado : listaEquipos) {
            if (registrado.getNumeroSerie().equalsIgnoreCase(equipo.getNumeroSerie())) {
                return "Ya existe un equipo con ese número de serie.";
            }
        }
        listaEquipos.add(equipo);
        equipo.getCliente().agregarEquipo(equipo);
        return "Equipo registrado correctamente.";
    }

    /** Agrega un técnico y evita repetir su código o documento. */
    public String registrarTecnico(Tecnico tecnico) {
        for (Tecnico registrado : listaTecnicos) {
            if (registrado.getId().equalsIgnoreCase(tecnico.getId())
                    || registrado.getIdentificacion().equalsIgnoreCase(tecnico.getIdentificacion())) {
                return "Ya existe un técnico con ese código o documento.";
            }
        }
        listaTecnicos.add(tecnico);
        return "Técnico registrado correctamente.";
    }

    /** Busca un técnico por su código interno. */
    public Optional<Tecnico> buscarTecnico(String id) {
        for (Tecnico tecnico : listaTecnicos) {
            if (tecnico.getId().equalsIgnoreCase(id)) return Optional.of(tecnico);
        }
        return Optional.empty();
    }

    /** Agrega un repuesto al inventario si no se repite su código. */
    public String registrarRepuesto(Repuesto repuesto) {
        if (buscarRepuesto(repuesto.getCodigo()).isPresent()) {
            return "Ya existe un repuesto con ese código.";
        }
        listaRepuestos.add(repuesto);
        return "Repuesto registrado correctamente.";
    }

    /** Busca un repuesto por su código. */
    public Optional<Repuesto> buscarRepuesto(String codigo) {
        for (Repuesto repuesto : listaRepuestos) {
            if (repuesto.getCodigo().equalsIgnoreCase(codigo)) return Optional.of(repuesto);
        }
        return Optional.empty();
    }

    /** Crea una orden abierta asociada al cliente indicado. */
    public String crearOrdenServicio(String codigo, String documentoCliente, String observaciones) {
        if (buscarOrden(codigo).isPresent()) return "Ya existe una orden con ese código.";
        Optional<Cliente> clienteEncontrado = buscarCliente(documentoCliente);
        if (clienteEncontrado.isEmpty()) return "No se encontró el cliente.";
        Cliente cliente = clienteEncontrado.get();
        OrdenServicio orden = new OrdenServicio(codigo, LocalDate.now(), cliente, observaciones);
        listaOrdenesServicio.add(orden);
        cliente.agregarOrden(orden);
        return "Orden creada correctamente: " + codigo;
    }

    /** Busca una orden por su código. */
    public Optional<OrdenServicio> buscarOrden(String codigo) {
        for (OrdenServicio orden : listaOrdenesServicio) {
            if (orden.getCodigo().equalsIgnoreCase(codigo)) return Optional.of(orden);
        }
        return Optional.empty();
    }

    /** Crea un detalle, su servicio y lo agrega a la orden seleccionada. */
    public String agregarDetalle(String codigoOrden, String codigoDetalle, String codigoEquipo,
                                 TipoServicio tipo, String descripcion, int horas) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigoOrden);
        Optional<Equipo> equipoEncontrado = buscarEquipo(codigoEquipo);
        if (ordenEncontrada.isEmpty()) return "No se encontró la orden.";
        if (equipoEncontrado.isEmpty()) return "No se encontró el equipo.";
        OrdenServicio orden = ordenEncontrada.get();
        Equipo equipo = equipoEncontrado.get();
        if (equipo.getCliente() != orden.getCliente()) return "El equipo no pertenece al cliente de la orden.";
        if (orden.getEstadoOrden() == EstadoOrden.CANCELADA
                || orden.getEstadoOrden() == EstadoOrden.FINALIZADA
                || orden.getEstadoOrden() == EstadoOrden.ENTREGADA) {
            return "No se pueden agregar detalles a una orden cerrada.";
        }
        if (horas < 0 || (tipo == TipoServicio.REPARACION && horas == 0)) {
            return "La reparación requiere al menos una hora de trabajo.";
        }
        for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
            if (detalle.getCodigo().equalsIgnoreCase(codigoDetalle)) {
                return "Ya existe un detalle con ese código en la orden.";
            }
        }

        Servicio servicio;
        String codigoServicio = "S" + siguienteServicio++;
        if (tipo == TipoServicio.DIAGNOSTICO) servicio = new Diagnostico(codigoServicio);
        else if (tipo == TipoServicio.MANTENIMIENTO_PREVENTIVO) servicio = new Mantenimiento(codigoServicio);
        else servicio = new Reparacion(codigoServicio);
        listaServicios.add(servicio);
        orden.agregarDetalle(new DetalleServicio(codigoDetalle, equipo, servicio, descripcion, horas));
        return "Detalle agregado correctamente.";
    }

    /** Busca un detalle dentro de una orden existente. */
    private Optional<DetalleServicio> buscarDetalle(OrdenServicio orden, String codigoDetalle) {
        for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
            if (detalle.getCodigo().equalsIgnoreCase(codigoDetalle)) return Optional.of(detalle);
        }
        return Optional.empty();
    }

    private Optional<DetalleServicio> buscarDetalle(String codigoOrden, String codigoDetalle) {
        return buscarOrden(codigoOrden).flatMap(orden -> buscarDetalle(orden, codigoDetalle));
    }

    /** Asigna un técnico activo cuya especialidad permita trabajar sobre el equipo. */
    public String asignarTecnico(String codigoOrden, String codigoDetalle, String codigoTecnico) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigoOrden);
        if (ordenEncontrada.isEmpty()) return "No se encontró la orden.";
        OrdenServicio orden = ordenEncontrada.get();
        Optional<DetalleServicio> detalleEncontrado = buscarDetalle(orden, codigoDetalle);
        if (detalleEncontrado.isEmpty()) return "No se encontró el detalle.";
        Optional<Tecnico> tecnicoEncontrado = buscarTecnico(codigoTecnico);
        if (tecnicoEncontrado.isEmpty()) return "No se encontró el técnico.";
        if (orden.getEstadoOrden() == EstadoOrden.CANCELADA
                || orden.getEstadoOrden() == EstadoOrden.FINALIZADA
                || orden.getEstadoOrden() == EstadoOrden.ENTREGADA) return "La orden está cerrada.";
        Tecnico tecnico = tecnicoEncontrado.get();
        DetalleServicio detalle = detalleEncontrado.get();
        if (tecnico.getEstadoLaboral() != EstadoLaboral.ACTIVO) return "El técnico está inactivo.";
        if (!tecnico.puedeAtender(detalle.getEquipo())) return "La especialidad no corresponde al equipo.";
        detalle.asignarTecnico(tecnico);
        return "Técnico asignado correctamente.";
    }

    /** Descuenta del inventario un repuesto aplicado a un detalle de reparación. */
    public String registrarRepuestoUtilizado(String codigoOrden, String codigoDetalle,
                                             String codigoRepuesto, int cantidad) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigoOrden);
        if (ordenEncontrada.isEmpty()) return "No se encontró la orden.";
        OrdenServicio orden = ordenEncontrada.get();
        Optional<DetalleServicio> detalleEncontrado = buscarDetalle(orden, codigoDetalle);
        if (detalleEncontrado.isEmpty()) return "No se encontró el detalle.";
        Optional<Repuesto> repuestoEncontrado = buscarRepuesto(codigoRepuesto);
        if (repuestoEncontrado.isEmpty()) return "No se encontró el repuesto.";
        DetalleServicio detalle = detalleEncontrado.get();
        Repuesto repuesto = repuestoEncontrado.get();
        if (orden.getEstadoOrden() == EstadoOrden.CANCELADA
                || orden.getEstadoOrden() == EstadoOrden.FINALIZADA
                || orden.getEstadoOrden() == EstadoOrden.ENTREGADA) return "La orden está cerrada.";
        if (detalle.getServicio().getTipoServicio() != TipoServicio.REPARACION) {
            return "Los repuestos solo se agregan a una reparación.";
        }
        if (detalle.getEstadoDetalle() == EstadoDetalle.FINALIZADA
                || detalle.getEstadoDetalle() == EstadoDetalle.ENTREGADA
                || detalle.getEstadoDetalle() == EstadoDetalle.CANCELADA) {
            return "El detalle ya está cerrado.";
        }
        if (cantidad <= 0) return "La cantidad debe ser mayor que cero.";
        if (cantidad > repuesto.getCantidadINV()) return "No hay suficientes unidades disponibles.";
        repuesto.descontar(cantidad);
        detalle.agregarRepuesto(new RepuestoUtilizado(repuesto, cantidad));
        return "Repuesto agregado. Existencias actuales: " + repuesto.getCantidadINV();
    }

    /** Cambia el estado de una intervención y verifica si tiene técnico asignado. */
    public String actualizarEstadoDetalle(String codigoOrden, String codigoDetalle,
                                          EstadoDetalle estado) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigoOrden);
        if (ordenEncontrada.isEmpty()) return "No se encontró la orden.";
        OrdenServicio orden = ordenEncontrada.get();
        Optional<DetalleServicio> detalleEncontrado = buscarDetalle(orden, codigoDetalle);
        if (detalleEncontrado.isEmpty()) return "No se encontró el detalle.";
        DetalleServicio detalle = detalleEncontrado.get();
        if (orden.getEstadoOrden() == EstadoOrden.CANCELADA
                || orden.getEstadoOrden() == EstadoOrden.ENTREGADA) return "La orden está cerrada.";
        if (estado == EstadoDetalle.ENTREGADA && orden.getEstadoOrden() != EstadoOrden.FINALIZADA) {
            return "Primero debes finalizar la orden.";
        }
        if (orden.getEstadoOrden() == EstadoOrden.FINALIZADA && estado != EstadoDetalle.ENTREGADA) {
            return "Una orden finalizada solo permite entregar el detalle.";
        }
        if (detalle.getEstadoDetalle() == EstadoDetalle.FINALIZADA
                && estado != EstadoDetalle.ENTREGADA) return "El detalle ya está finalizado.";
        if (detalle.getEstadoDetalle() == EstadoDetalle.ENTREGADA
                || detalle.getEstadoDetalle() == EstadoDetalle.CANCELADA) return "El detalle está cerrado.";
        if ((estado == EstadoDetalle.EN_PROCESO || estado == EstadoDetalle.FINALIZADA)
                && detalle.getTecnico().isEmpty()) {
            return "Primero debe asignar un técnico.";
        }
        detalle.setEstadoDetalle(estado);
        if (estado == EstadoDetalle.EN_PROCESO) orden.setEstadoOrden(EstadoOrden.EN_PROCESO);
        return "Estado del detalle actualizado a " + estado + ".";
    }

    /** Guarda las notas escritas por el técnico en una intervención. */
    public String registrarObservacionesTecnicas(String codigoOrden, String codigoDetalle,
                                                 String observaciones) {
        Optional<DetalleServicio> detalleEncontrado = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalleEncontrado.isEmpty()) return "No se encontró el detalle.";
        detalleEncontrado.get().setObservacionesTec(observaciones);
        return "Observaciones técnicas guardadas.";
    }

    /** Actualiza el estado general de una orden. */
    public String actualizarEstadoOrden(String codigo, EstadoOrden estado) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigo);
        if (ordenEncontrada.isEmpty()) return "No se encontró la orden.";
        OrdenServicio orden = ordenEncontrada.get();
        if (estado == EstadoOrden.FINALIZADA) return finalizarOrden(codigo);
        if (orden.getEstadoOrden() == EstadoOrden.ENTREGADA
                || orden.getEstadoOrden() == EstadoOrden.CANCELADA) return "La orden ya está cerrada.";
        if (orden.getEstadoOrden() == EstadoOrden.FINALIZADA && estado != EstadoOrden.ENTREGADA) {
            return "Una orden finalizada solo puede cambiar a ENTREGADA.";
        }
        if (estado == EstadoOrden.ENTREGADA && orden.getEstadoOrden() != EstadoOrden.FINALIZADA) {
            return "Primero debes finalizar la orden.";
        }
        if (estado == EstadoOrden.ENTREGADA) {
            for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
                if (detalle.getEstadoDetalle() != EstadoDetalle.ENTREGADA) {
                    return "Primero debes marcar como entregados todos los detalles.";
                }
            }
        }
        orden.setEstadoOrden(estado);
        return "Estado de la orden actualizado a " + estado + ".";
    }

    /** Finaliza una orden cuando ya no quedan detalles pendientes. */
    public String finalizarOrden(String codigo) {
        Optional<OrdenServicio> ordenEncontrada = buscarOrden(codigo);
        if (ordenEncontrada.isEmpty()) return "No se encontró la orden.";
        OrdenServicio orden = ordenEncontrada.get();
        if (orden.getEstadoOrden() == EstadoOrden.CANCELADA
                || orden.getEstadoOrden() == EstadoOrden.ENTREGADA) return "La orden ya está cerrada.";
        if (orden.getListaDetallesServicio().isEmpty()) return "La orden no tiene detalles.";
        for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
            if (detalle.getEstadoDetalle() != EstadoDetalle.FINALIZADA) {
                return "Todos los detalles deben estar FINALIZADOS.";
            }
        }
        orden.setEstadoOrden(EstadoOrden.FINALIZADA);
        return "Orden finalizada correctamente.";
    }

    /** Devuelve el total solo cuando la orden existe. */
    public Optional<Double> calcularTotalOrden(String codigo) {
        return buscarOrden(codigo).map(OrdenServicio::calcularCostoFinal);
    }

    /** Devuelve el valor del detalle solo cuando se encuentran la orden y el detalle. */
    public Optional<Double> calcularValorDetalle(String codigoOrden, String codigoDetalle) {
        return buscarDetalle(codigoOrden, codigoDetalle).map(DetalleServicio::calcularValorDetalle);
    }

    /** Busca las órdenes asociadas al documento del cliente. */
    public List<OrdenServicio> consultarOrdenesCliente(String identificacion) {
        List<OrdenServicio> resultado = new ArrayList<>();
        for (OrdenServicio orden : listaOrdenesServicio) {
            if (orden.getCliente().getIdentificacion().equalsIgnoreCase(identificacion)) resultado.add(orden);
        }
        return resultado;
    }

    /** Organiza los trabajos de un técnico por estado de intervención. */
    public Optional<String> consultarTrabajosTecnico(String codigoTecnico) {
        Optional<Tecnico> tecnicoEncontrado = buscarTecnico(codigoTecnico);
        if (tecnicoEncontrado.isEmpty()) return Optional.empty();
        Tecnico tecnico = tecnicoEncontrado.get();
        StringBuilder texto = new StringBuilder("Trabajos de " + tecnico.getNombre() + "\n");
        for (EstadoDetalle estado : EstadoDetalle.values()) {
            texto.append("\n").append(estado).append(":\n");
            boolean encontro = false;
            for (OrdenServicio orden : listaOrdenesServicio) {
                for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
                    if (detalle.getTecnico().filter(asignado -> asignado == tecnico).isPresent()
                            && detalle.getEstadoDetalle() == estado) {
                        texto.append("Orden ").append(orden.getCodigo()).append(" - ")
                                .append(detalle).append("\n");
                        encontro = true;
                    }
                }
            }
            if (!encontro) texto.append("Sin trabajos\n");
        }
        return Optional.of(texto.toString());
    }

    /** Devuelve las intervenciones que aún no están finalizadas ni cerradas. */
    public List<DetalleServicio> consultarEquiposPendientes() {
        List<DetalleServicio> pendientes = new ArrayList<>();
        for (OrdenServicio orden : listaOrdenesServicio) {
            for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
                if (detalle.getEstadoDetalle() != EstadoDetalle.FINALIZADA
                        && detalle.getEstadoDetalle() != EstadoDetalle.ENTREGADA
                        && detalle.getEstadoDetalle() != EstadoDetalle.CANCELADA) pendientes.add(detalle);
            }
        }
        return pendientes;
    }

    /** Cuenta servicios y suma lo facturado por tipo de servicio. */
    public String generarReporteServicios() {
        Map<TipoServicio, Integer> cantidades = new EnumMap<>(TipoServicio.class);
        Map<TipoServicio, Double> valores = new EnumMap<>(TipoServicio.class);
        for (TipoServicio tipo : TipoServicio.values()) {
            cantidades.put(tipo, 0);
            valores.put(tipo, 0.0);
        }
        for (OrdenServicio orden : listaOrdenesServicio) {
            for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
                TipoServicio tipo = detalle.getServicio().getTipoServicio();
                cantidades.put(tipo, cantidades.get(tipo) + 1);
                if (detalle.getEstadoDetalle() == EstadoDetalle.FINALIZADA
                        || detalle.getEstadoDetalle() == EstadoDetalle.ENTREGADA) {
                    valores.put(tipo, valores.get(tipo) + detalle.calcularValorDetalle());
                }
            }
        }
        StringBuilder texto = new StringBuilder("REPORTE DE SERVICIOS\n");
        for (TipoServicio tipo : TipoServicio.values()) {
            texto.append(tipo).append(" | Cantidad: ").append(cantidades.get(tipo))
                    .append(" | Facturado: $").append(String.format("%,.0f", valores.get(tipo))).append("\n");
        }
        return texto.toString();
    }

    /** Cuenta los detalles finalizados y sus valores para cada técnico. */
    public String generarReporteTecnicos() {
        StringBuilder texto = new StringBuilder("REPORTE DE TÉCNICOS\n");
        for (Tecnico tecnico : listaTecnicos) {
            int cantidad = 0;
            double valor = 0;
            for (OrdenServicio orden : listaOrdenesServicio) {
                for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
                    if (detalle.getTecnico().filter(asignado -> asignado == tecnico).isPresent()
                            && (detalle.getEstadoDetalle() == EstadoDetalle.FINALIZADA
                            || detalle.getEstadoDetalle() == EstadoDetalle.ENTREGADA)) {
                        cantidad++;
                        valor += detalle.calcularValorDetalle();
                    }
                }
            }
            texto.append(tecnico.getNombre()).append(" | Intervenciones finalizadas: ")
                    .append(cantidad).append(" | Valor atendido: $")
                    .append(String.format("%,.0f", valor)).append("\n");
        }
        if (listaTecnicos.isEmpty()) texto.append("No hay técnicos registrados.");
        return texto.toString();
    }

    /** Devuelve repuestos con menos de cinco unidades disponibles. */
    public List<Repuesto> consultarInventarioCritico() {
        List<Repuesto> criticos = new ArrayList<>();
        for (Repuesto repuesto : listaRepuestos) {
            if (repuesto.getCantidadINV() < 5) criticos.add(repuesto);
        }
        return criticos;
    }
}
