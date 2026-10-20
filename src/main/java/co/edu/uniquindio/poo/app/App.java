package co.edu.uniquindio.poo.app;


import co.edu.uniquindio.poo.model.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {
        private static final Empresa EMPRESA = new Empresa("TechRepair", "901234567-1");

        public static void main(String[] args) {
            cargarDatosIniciales();
            mostrarMenuPrincipal();
        }

        /** Registros de prueba para que los reportes y consultas tengan información al iniciar. */
        private static void cargarDatosIniciales() {
            EMPRESA.registrarCliente("1001", "Laura Gómez", "3001112233",
                    "laura@gmail.com", "Armenia, Quindío");
            Cliente cliente = EMPRESA.buscarCliente("1001").orElseThrow();

            Equipo portatil = new PCPortatil("EQ-001", "SN-LEN-001", "Lenovo", "IdeaPad 3",
                    LocalDate.of(2023, 1, 1), "Rasguño leve en la tapa", cliente,
                    15.6, 8, 512, true);
            Equipo celular = new CELInteligente("EQ-002", "SN-SAM-002", "Samsung", "A54",
                    LocalDate.of(2024, 1, 1), "Pantalla en buen estado", cliente,
                    "Android", 128, 5000, true);
            EMPRESA.registrarEquipo(portatil);
            EMPRESA.registrarEquipo(celular);

            EMPRESA.registrarTecnico(new Tecnico("TEC-01", "Andrés Rojas", "2001",
                    Especialidad.COMPUTADORES, EstadoLaboral.ACTIVO, "Armenia"));
            EMPRESA.registrarTecnico(new Tecnico("TEC-02", "Camila Marín", "2002",
                    Especialidad.TELEFONOS, EstadoLaboral.ACTIVO, "Armenia"));

            EMPRESA.registrarRepuesto(new Repuesto("REP-01", "Unidad SSD", "SSD de 512 GB",
                    250000, 3, CategoriaRepuesto.ALMACENAMIENTO));
            EMPRESA.registrarRepuesto(new Repuesto("REP-02", "Batería celular", "Batería compatible",
                    180000, 7, CategoriaRepuesto.BATERIA));
            EMPRESA.registrarRepuesto(new Repuesto("REP-03", "Memoria RAM", "Memoria de 8 GB",
                    120000, 2, CategoriaRepuesto.MEMORIA));

            EMPRESA.crearOrdenServicio("OS-001", "1001", "Revisión del computador portátil.");
            EMPRESA.agregarDetalle("OS-001", "DET-001", "EQ-001", TipoServicio.DIAGNOSTICO,
                    "El equipo se demora en iniciar.", 0);
            EMPRESA.asignarTecnico("OS-001", "DET-001", "TEC-01");
            EMPRESA.registrarObservacionesTecnicas("OS-001", "DET-001", "Se revisó el inicio del sistema.");
            EMPRESA.actualizarEstadoDetalle("OS-001", "DET-001", EstadoDetalle.FINALIZADA);

            EMPRESA.agregarDetalle("OS-001", "DET-002", "EQ-001", TipoServicio.REPARACION,
                    "Se recomienda cambiar la unidad de almacenamiento.", 2);
            EMPRESA.asignarTecnico("OS-001", "DET-002", "TEC-01");
            EMPRESA.registrarRepuestoUtilizado("OS-001", "DET-002", "REP-01", 1);
            EMPRESA.actualizarEstadoDetalle("OS-001", "DET-002", EstadoDetalle.EN_PROCESO);

            EMPRESA.crearOrdenServicio("OS-002", "1001", "Mantenimiento del celular.");
            EMPRESA.agregarDetalle("OS-002", "DET-003", "EQ-002", TipoServicio.MANTENIMIENTO_PREVENTIVO,
                    "Limpieza y revisión general.", 0);
            EMPRESA.asignarTecnico("OS-002", "DET-003", "TEC-02");
            EMPRESA.actualizarEstadoDetalle("OS-002", "DET-003", EstadoDetalle.FINALIZADA);
            EMPRESA.finalizarOrden("OS-002");
        }

        /** El menú principal lleva a cada grupo de funciones. */
        private static void mostrarMenuPrincipal() {
            int opcion;
            do {
                opcion = pedirOpcion("TECHREPAIR\n\n"
                        + "1. Clientes\n2. Equipos\n3. Técnicos\n4. Repuestos\n"
                        + "5. Órdenes de servicio\n6. Consultas y reportes\n7. Salir\n\n"
                        + "Escribe el número de la opción:", 7);
                switch (opcion) {
                    case 1 -> menuClientes();
                    case 2 -> menuEquipos();
                    case 3 -> menuTecnicos();
                    case 4 -> menuRepuestos();
                    case 5 -> menuOrdenes();
                    case 6 -> menuConsultasReportes();
                    case 7, 0 -> opcion = 7;
                    default -> mensaje("Selecciona un número válido.");
                }
            } while (opcion != 7);
            mensaje("Gracias por utilizar TechRepair.");
        }

        private static void menuClientes() {
            int opcion;
            do {
                opcion = pedirOpcion("CLIENTES\n\n1. Registrar cliente\n2. Listar clientes\n3. Volver", 3);
                if (opcion == 1) registrarCliente();
                else if (opcion == 2) mostrarLista("Clientes", EMPRESA.getListaClientes());
            } while (opcion != 3 && opcion != 0);
        }

        private static void registrarCliente() {
            Optional<String> documento = pedirTexto("Documento de identidad:");
            Optional<String> nombre = pedirTexto("Nombre completo:");
            Optional<String> telefono = pedirTexto("Teléfono:");
            Optional<String> correo = pedirTexto("Correo electrónico:");
            Optional<String> direccion = pedirTexto("Dirección:");
            if (faltaDato(documento, nombre, telefono, correo, direccion)) return;
            mensaje(EMPRESA.registrarCliente(documento.get(), nombre.get(), telefono.get(),
                    correo.get(), direccion.get()));
        }

        private static void menuEquipos() {
            int opcion;
            do {
                opcion = pedirOpcion("EQUIPOS\n\n1. Registrar equipo\n2. Listar equipos\n3. Volver", 3);
                if (opcion == 1) registrarEquipo();
                else if (opcion == 2) mostrarLista("Equipos", EMPRESA.getListaEquipos());
            } while (opcion != 3 && opcion != 0);
        }

        private static void registrarEquipo() {
            Optional<String> documento = pedirTexto("Documento del cliente propietario:");
            if (documento.isEmpty()) return;
            Optional<Cliente> clienteEncontrado = EMPRESA.buscarCliente(documento.get());
            if (clienteEncontrado.isEmpty()) {
                mensaje("Primero registra el cliente.");
                return;
            }
            Cliente cliente = clienteEncontrado.get();

            int tipo = pedirOpcion("Tipo de equipo\n1. PC portátil\n2. PC de escritorio\n3. Celular inteligente", 3);
            if (tipo == 0) return;
            Optional<String> codigo = pedirTexto("Código del equipo:");
            Optional<String> serie = pedirTexto("Número de serie:");
            Optional<String> marca = pedirTexto("Marca:");
            Optional<String> modelo = pedirTexto("Modelo:");
            Optional<Integer> anio = pedirEntero("Año de fabricación:");
            Optional<String> estado = pedirTexto("Descripción del estado físico al ingresar:");
            if (faltaDato(codigo, serie, marca, modelo, anio, estado)) return;
            int anioValor = anio.get();
            if (anioValor < 1900 || anioValor > LocalDate.now().getYear()) {
                mensaje("Escribe un año entre 1900 y el año actual.");
                return;
            }

            LocalDate fecha = LocalDate.of(anioValor, 1, 1);
            Equipo equipo;
            if (tipo == 1) {
                Optional<Double> pantalla = pedirDecimal("Tamaño de pantalla en pulgadas:");
                Optional<Double> ram = pedirDecimal("Memoria RAM en GB:");
                Optional<Double> almacenamiento = pedirDecimal("Almacenamiento en GB:");
                Optional<Boolean> bateria = pedirSiNo("¿Tiene batería extraíble?");
                if (faltaDato(pantalla, ram, almacenamiento, bateria)) return;
                equipo = new PCPortatil(codigo.get(), serie.get(), marca.get(), modelo.get(), fecha,
                        estado.get(), cliente, pantalla.get(), ram.get(), almacenamiento.get(), bateria.get());
            } else if (tipo == 2) {
                Optional<String> cpu = pedirTexto("Tipo de procesador:");
                Optional<Double> ram = pedirDecimal("Memoria RAM en GB:");
                Optional<Double> almacenamiento = pedirDecimal("Almacenamiento en GB:");
                Optional<Boolean> grafica = pedirSiNo("¿Tiene tarjeta gráfica dedicada?");
                if (faltaDato(cpu, ram, almacenamiento, grafica)) return;
                equipo = new PCEscritorio(codigo.get(), serie.get(), marca.get(), modelo.get(), fecha,
                        estado.get(), cliente, cpu.get(), ram.get(), almacenamiento.get(), grafica.get());
            } else {
                Optional<String> sistema = pedirTexto("Sistema operativo:");
                Optional<Double> almacenamiento = pedirDecimal("Almacenamiento en GB:");
                Optional<Double> bateria = pedirDecimal("Capacidad de batería en mAh:");
                Optional<Boolean> cincoG = pedirSiNo("¿Es compatible con 5G?");
                if (faltaDato(sistema, almacenamiento, bateria, cincoG)) return;
                equipo = new CELInteligente(codigo.get(), serie.get(), marca.get(), modelo.get(), fecha,
                        estado.get(), cliente, sistema.get(), almacenamiento.get(), bateria.get(), cincoG.get());
            }
            mensaje(EMPRESA.registrarEquipo(equipo));
        }

        private static void menuTecnicos() {
            int opcion;
            do {
                opcion = pedirOpcion("TÉCNICOS\n\n1. Registrar técnico\n2. Listar técnicos\n3. Volver", 3);
                if (opcion == 1) registrarTecnico();
                else if (opcion == 2) mostrarLista("Técnicos", EMPRESA.getListaTecnicos());
            } while (opcion != 3 && opcion != 0);
        }

        private static void registrarTecnico() {
            Optional<String> codigo = pedirTexto("Código del técnico:");
            Optional<String> nombre = pedirTexto("Nombre completo:");
            Optional<String> documento = pedirTexto("Documento de identificación:");
            if (faltaDato(codigo, nombre, documento)) return;
            int especialidad = pedirOpcion("Especialidad\n1. Computadores\n2. Teléfonos\n3. Servicio general", 3);
            if (especialidad == 0) return;
            int estado = pedirOpcion("Estado laboral\n1. Activo\n2. Inactivo", 2);
            if (estado == 0) return;
            Optional<String> direccion = pedirTexto("Dirección:");
            if (direccion.isEmpty()) return;

            Especialidad esp = especialidad == 1 ? Especialidad.COMPUTADORES
                    : especialidad == 2 ? Especialidad.TELEFONOS : Especialidad.SERVICIO_GENERAL;
            EstadoLaboral labor = estado == 1 ? EstadoLaboral.ACTIVO : EstadoLaboral.INACTIVO;
            mensaje(EMPRESA.registrarTecnico(new Tecnico(codigo.get(), nombre.get(), documento.get(),
                    esp, labor, direccion.get())));
        }

        private static void menuRepuestos() {
            int opcion;
            do {
                opcion = pedirOpcion("REPUESTOS\n\n1. Registrar repuesto\n2. Listar inventario\n"
                        + "3. Consultar inventario crítico\n4. Volver", 4);
                if (opcion == 1) registrarRepuesto();
                else if (opcion == 2) mostrarLista("Inventario", EMPRESA.getListaRepuestos());
                else if (opcion == 3) mostrarLista("Repuestos con menos de cinco unidades",
                        EMPRESA.consultarInventarioCritico());
            } while (opcion != 4 && opcion != 0);
        }

        private static void registrarRepuesto() {
            Optional<String> codigo = pedirTexto("Código del repuesto:");
            Optional<String> nombre = pedirTexto("Nombre:");
            Optional<String> descripcion = pedirTexto("Descripción:");
            Optional<Double> precio = pedirDecimal("Precio por unidad:");
            Optional<Integer> cantidad = pedirEntero("Existencias iniciales:");
            if (faltaDato(codigo, nombre, descripcion, precio, cantidad)) return;
            int categoria = pedirOpcion("Categoría\n1. Pantalla\n2. Almacenamiento\n3. Batería\n"
                    + "4. Memoria\n5. Cargador\n6. Otro", 6);
            if (categoria == 0) return;
            CategoriaRepuesto[] categorias = CategoriaRepuesto.values();
            try {
                mensaje(EMPRESA.registrarRepuesto(new Repuesto(codigo.get(), nombre.get(), descripcion.get(),
                        precio.get(), cantidad.get(), categorias[categoria - 1])));
            } catch (IllegalArgumentException e) {
                mensaje(e.getMessage());
            }
        }

        private static void menuOrdenes() {
            int opcion;
            do {
                opcion = pedirOpcion("ÓRDENES DE SERVICIO\n\n1. Crear orden\n2. Agregar detalle\n"
                        + "3. Asignar técnico\n4. Registrar repuestos usados\n5. Actualizar estado de detalle\n"
                        + "6. Calcular valor de un detalle\n7. Calcular total de una orden\n"
                        + "8. Finalizar orden\n9. Listar órdenes\n10. Consultar órdenes de un cliente\n"
                        + "11. Registrar observaciones técnicas\n12. Cambiar estado de orden\n13. Volver", 13);
                switch (opcion) {
                    case 1 -> crearOrden();
                    case 2 -> agregarDetalle();
                    case 3 -> asignarTecnico();
                    case 4 -> agregarRepuestoUsado();
                    case 5 -> actualizarEstadoDetalle();
                    case 6 -> calcularValorDetalle();
                    case 7 -> calcularTotalOrden();
                    case 8 -> finalizarOrden();
                    case 9 -> listarOrdenes();
                    case 10 -> consultarOrdenesCliente();
                    case 11 -> registrarObservacionesTecnicas();
                    case 12 -> actualizarEstadoOrden();
                }
            } while (opcion != 13 && opcion != 0);
        }

        private static void crearOrden() {
            Optional<String> codigo = pedirTexto("Código de la orden:");
            Optional<String> documento = pedirTexto("Documento del cliente:");
            Optional<String> observaciones = pedirTexto("Observaciones generales:");
            if (faltaDato(codigo, documento, observaciones)) return;
            mensaje(EMPRESA.crearOrdenServicio(codigo.get(), documento.get(), observaciones.get()));
        }

        private static void agregarDetalle() {
            Optional<String> orden = pedirTexto("Código de la orden:");
            Optional<String> detalle = pedirTexto("Código del detalle:");
            Optional<String> equipo = pedirTexto("Código del equipo:");
            if (faltaDato(orden, detalle, equipo)) return;
            int tipo = pedirOpcion("Servicio\n1. Diagnóstico\n2. Mantenimiento preventivo\n3. Reparación correctiva", 3);
            if (tipo == 0) return;
            Optional<String> descripcion = pedirTexto("Falla o necesidad reportada:");
            Optional<Integer> horas = tipo == 3 ? pedirEntero("Horas de trabajo:") : Optional.of(0);
            if (faltaDato(descripcion, horas)) return;
            TipoServicio[] tipos = TipoServicio.values();
            mensaje(EMPRESA.agregarDetalle(orden.get(), detalle.get(), equipo.get(), tipos[tipo - 1],
                    descripcion.get(), horas.get()));
        }

        private static void asignarTecnico() {
            Optional<String> orden = pedirTexto("Código de la orden:");
            Optional<String> detalle = pedirTexto("Código del detalle:");
            Optional<String> tecnico = pedirTexto("Código del técnico:");
            if (faltaDato(orden, detalle, tecnico)) return;
            mensaje(EMPRESA.asignarTecnico(orden.get(), detalle.get(), tecnico.get()));
        }

        private static void agregarRepuestoUsado() {
            Optional<String> orden = pedirTexto("Código de la orden:");
            Optional<String> detalle = pedirTexto("Código del detalle de reparación:");
            Optional<String> repuesto = pedirTexto("Código del repuesto:");
            Optional<Integer> cantidad = pedirEntero("Cantidad que se utilizará:");
            if (faltaDato(orden, detalle, repuesto, cantidad)) return;
            mensaje(EMPRESA.registrarRepuestoUtilizado(orden.get(), detalle.get(), repuesto.get(), cantidad.get()));
        }

        private static void actualizarEstadoDetalle() {
            Optional<String> orden = pedirTexto("Código de la orden:");
            Optional<String> detalle = pedirTexto("Código del detalle:");
            if (faltaDato(orden, detalle)) return;
            int estado = pedirOpcion("Estado del detalle\n1. Abierta\n2. En proceso\n3. Finalizada\n"
                    + "4. Entregada\n5. Cancelada", 5);
            if (estado == 0) return;
            mensaje(EMPRESA.actualizarEstadoDetalle(orden.get(), detalle.get(), EstadoDetalle.values()[estado - 1]));
        }

        private static void calcularValorDetalle() {
            Optional<String> orden = pedirTexto("Código de la orden:");
            Optional<String> detalle = pedirTexto("Código del detalle:");
            if (faltaDato(orden, detalle)) return;
            Optional<Double> valor = EMPRESA.calcularValorDetalle(orden.get(), detalle.get());
            mensaje(valor.map(v -> "Valor del detalle: $" + dinero(v)).orElse("No se encontró el detalle."));
        }

        private static void calcularTotalOrden() {
            Optional<String> codigo = pedirTexto("Código de la orden:");
            if (codigo.isEmpty()) return;
            Optional<Double> total = EMPRESA.calcularTotalOrden(codigo.get());
            mensaje(total.map(v -> "Total de la orden: $" + dinero(v)).orElse("No se encontró la orden."));
        }

        private static void finalizarOrden() {
            Optional<String> codigo = pedirTexto("Código de la orden que deseas finalizar:");
            codigo.ifPresent(valor -> mensaje(EMPRESA.finalizarOrden(valor)));
        }

        private static void registrarObservacionesTecnicas() {
            Optional<String> orden = pedirTexto("Código de la orden:");
            Optional<String> detalle = pedirTexto("Código del detalle:");
            Optional<String> observaciones = pedirTexto("Observaciones técnicas:");
            if (faltaDato(orden, detalle, observaciones)) return;
            mensaje(EMPRESA.registrarObservacionesTecnicas(orden.get(), detalle.get(), observaciones.get()));
        }

        private static void actualizarEstadoOrden() {
            Optional<String> codigo = pedirTexto("Código de la orden:");
            if (codigo.isEmpty()) return;
            int estado = pedirOpcion("Estado de la orden\n1. Abierta\n2. En proceso\n"
                    + "3. Finalizada\n4. Entregada\n5. Cancelada", 5);
            if (estado == 0) return;
            mensaje(EMPRESA.actualizarEstadoOrden(codigo.get(), EstadoOrden.values()[estado - 1]));
        }

        private static void listarOrdenes() {
            StringBuilder texto = new StringBuilder("ÓRDENES REGISTRADAS\n\n");
            List<OrdenServicio> ordenes = EMPRESA.getListaOrdenesServicio();
            for (OrdenServicio orden : ordenes) agregarOrdenConDetalles(texto, orden);
            if (ordenes.isEmpty()) texto.append("No hay órdenes registradas.");
            mensaje(texto.toString());
        }

        private static void consultarOrdenesCliente() {
            Optional<String> documento = pedirTexto("Documento del cliente:");
            if (documento.isEmpty()) return;
            List<OrdenServicio> ordenes = EMPRESA.consultarOrdenesCliente(documento.get());
            StringBuilder texto = new StringBuilder("ÓRDENES DEL CLIENTE\n\n");
            for (OrdenServicio orden : ordenes) agregarOrdenConDetalles(texto, orden);
            if (ordenes.isEmpty()) texto.append("No hay órdenes para ese cliente.");
            mensaje(texto.toString());
        }

        private static void agregarOrdenConDetalles(StringBuilder texto, OrdenServicio orden) {
            texto.append(orden).append("\n");
            for (DetalleServicio detalle : orden.getListaDetallesServicio()) {
                texto.append("   ").append(detalle).append("\n");
            }
            texto.append("\n");
        }

        private static void menuConsultasReportes() {
            int opcion;
            do {
                opcion = pedirOpcion("CONSULTAS Y REPORTES\n\n1. Trabajos de un técnico\n"
                        + "2. Equipos con intervenciones pendientes\n3. Reporte de servicios\n"
                        + "4. Reporte de técnicos\n5. Inventario crítico\n6. Volver", 6);
                if (opcion == 1) {
                    Optional<String> codigo = pedirTexto("Código del técnico:");
                    if (codigo.isPresent()) {
                        mensaje(EMPRESA.consultarTrabajosTecnico(codigo.get())
                                .orElse("No se encontró el técnico."));
                    }
                } else if (opcion == 2) {
                    mostrarLista("Intervenciones pendientes", EMPRESA.consultarEquiposPendientes());
                } else if (opcion == 3) {
                    mensaje(EMPRESA.generarReporteServicios());
                } else if (opcion == 4) {
                    mensaje(EMPRESA.generarReporteTecnicos());
                } else if (opcion == 5) {
                    mostrarLista("Repuestos con menos de cinco unidades", EMPRESA.consultarInventarioCritico());
                }
            } while (opcion != 6 && opcion != 0);
        }

        /** Pide números para los menús; cero indica que se canceló el cuadro. */
        private static int pedirOpcion(String texto, int maximo) {
            while (true) {
                Optional<String> entrada = Optional.ofNullable(JOptionPane.showInputDialog(texto));
                if (entrada.isEmpty()) return 0;
                try {
                    int opcion = Integer.parseInt(entrada.get().trim());
                    if (opcion >= 1 && opcion <= maximo) return opcion;
                } catch (NumberFormatException ignored) {
                    // Si la entrada no es un número, se muestra de nuevo el menú.
                }
                mensaje("Escribe un número entre 1 y " + maximo + ".");
            }
        }

        /** Texto vacío representa que el usuario canceló; un campo vacío se vuelve a pedir. */
        private static Optional<String> pedirTexto(String texto) {
            while (true) {
                Optional<String> entrada = Optional.ofNullable(JOptionPane.showInputDialog(texto))
                        .map(String::trim);
                if (entrada.isEmpty()) return Optional.empty();
                if (entrada.get().isEmpty()) mensaje("Este dato no puede quedar vacío.");
                else return entrada;
            }
        }

        private static Optional<Integer> pedirEntero(String texto) {
            while (true) {
                Optional<String> entrada = Optional.ofNullable(JOptionPane.showInputDialog(texto));
                if (entrada.isEmpty()) return Optional.empty();
                try {
                    return Optional.of(Integer.parseInt(entrada.get().trim()));
                } catch (NumberFormatException e) {
                    mensaje("Escribe un número entero.");
                }
            }
        }

        private static Optional<Double> pedirDecimal(String texto) {
            while (true) {
                Optional<String> entrada = Optional.ofNullable(JOptionPane.showInputDialog(texto));
                if (entrada.isEmpty()) return Optional.empty();
                try {
                    double valor = Double.parseDouble(entrada.get().trim().replace(',', '.'));
                    if (Double.isFinite(valor) && valor >= 0) return Optional.of(valor);
                    mensaje("Escribe un valor válido que no sea negativo.");
                } catch (NumberFormatException e) {
                    mensaje("Escribe un número válido.");
                }
            }
        }

        private static Optional<Boolean> pedirSiNo(String texto) {
            int opcion = pedirOpcion(texto + "\n1. Sí\n2. No", 2);
            if (opcion == 0) return Optional.empty();
            return Optional.of(opcion == 1);
        }

        /** Indica si alguna respuesta se canceló antes de usar los datos. */
        private static boolean faltaDato(Optional<?>... datos) {
            for (Optional<?> dato : datos) {
                if (dato.isEmpty()) return true;
            }
            return false;
        }

        private static void mostrarLista(String titulo, List<?> lista) {
            if (lista.isEmpty()) {
                mensaje("No hay registros para mostrar.");
                return;
            }
            StringBuilder texto = new StringBuilder(titulo).append("\n\n");
            for (Object elemento : lista) texto.append(elemento).append("\n");
            mensaje(texto.toString());
        }

        private static String dinero(double valor) {
            return String.format("%,.0f", valor);
        }

        private static void mensaje(String texto) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), texto);
        }
    }

}
