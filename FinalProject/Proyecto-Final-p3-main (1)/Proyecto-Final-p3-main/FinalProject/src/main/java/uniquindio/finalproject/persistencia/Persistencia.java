package uniquindio.finalproject.persistencia;

import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.Presupuesto;
import uniquindio.finalproject.Model.TipoCuenta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Persistencia {

    private String rutaArchivoCuenta = "";
    private String rutaArchivoCategoria = "";
    private String rutaArchivoPresupuesto = "";

    // Método para obtener las rutas desde el archivo de propiedades
    private String obtenerRutasProperties(String propiedad) {
        Properties properties = new Properties();
        String rutaArchivo = "";
        try {
            // Cargar el archivo de propiedades
            properties.load(new FileInputStream("C:\\Users\\ACER\\Documents\\Programacion III\\Proyecto-Final-p3-main (1)\\Proyecto-Final-p3-main (1)\\Proyecto-Final-p3-main\\FinalProject\\src\\main\\java\\uniquindio\\finalproject\\rutasProperties.txt"));

            // Obtener la ruta según la propiedad solicitada (cuenta, categoría o presupuesto)
            rutaArchivo = properties.getProperty(propiedad);
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de propiedades no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de propiedades: " + e.getMessage());
        }
        return rutaArchivo;  // Devolver la ruta obtenida
    }

    public void guardarCuenta(Cuenta cuenta) throws IOException {
        // Obtener la ruta del archivo desde el archivo de propiedades
        rutaArchivoCuenta = obtenerRutasProperties("rutaArchivoCuenta"); // Asignar la ruta correcta
        StringBuilder textoCuenta = new StringBuilder();
        textoCuenta.append(cuenta.getIdCuenta()+"@@");
        textoCuenta.append(cuenta.getNombreBanco()+"@@");
        textoCuenta.append(cuenta.getNumeroCuenta()+"@@");
        textoCuenta.append(cuenta.getTipoCuenta()+"@@");
        textoCuenta.append("\n");
        ArchivoUtil.guardarArchivo(rutaArchivoCuenta, textoCuenta.toString(), true);
        ArchivoUtil.guardarRegistroLog("Cuenta guardada: " + cuenta.getIdCuenta() + " - " + cuenta.getNombreBanco(), 1, "ClickGuardar", "C:\\td\\Persistencia\\Log\\log.txt");
    }

    public void guardarCategoria(Categoria categoria) throws IOException {
        // Obtener la ruta del archivo desde el archivo de propiedades
        rutaArchivoCategoria = obtenerRutasProperties("rutaArchivoCategoria"); // Asignar la ruta correcta
        StringBuilder textoCategoria = new StringBuilder();
        textoCategoria.append(categoria.getIdCategoria() + "@@");
        textoCategoria.append(categoria.getNombreCategoria() + "@@");
        textoCategoria.append(categoria.getDescripcionCategoria() + "@@");
        textoCategoria.append("\n");
        ArchivoUtil.guardarArchivo(rutaArchivoCategoria, textoCategoria.toString(), true);
        ArchivoUtil.guardarRegistroLog("Categoría guardada: " + categoria.getIdCategoria() + " - " + categoria.getNombreCategoria() + " - " + categoria.getDescripcionCategoria(), 1, "ClickGuardar", "C:\\td\\Persistencia\\Log\\log.txt");
    }


    public void guardarPresupuesto(Presupuesto presupuesto) throws IOException {
        // Obtener la ruta del archivo desde el archivo de propiedades
        rutaArchivoPresupuesto = obtenerRutasProperties("rutaArchivoPresupuesto"); // Asignar la ruta correcta
        StringBuilder textoPresupuesto = new StringBuilder();
        textoPresupuesto.append(presupuesto.getIdPresupuesto() + "@@");
        textoPresupuesto.append(presupuesto.getNombrePresupuesto() + "@@");
        textoPresupuesto.append(presupuesto.getMontoTotal() + "@@");
        textoPresupuesto.append(presupuesto.getMontoGastado() + "@@");
        textoPresupuesto.append(presupuesto.getCategoria().getIdCategoria() + "@@");
        textoPresupuesto.append("\n");

        // Guardar la información del presupuesto en el archivo
        ArchivoUtil.guardarArchivo(rutaArchivoPresupuesto, textoPresupuesto.toString(), true);

        // Guardar en el log
        ArchivoUtil.guardarRegistroLog("Presupuesto guardado: " + presupuesto.getIdPresupuesto() + " - " + presupuesto.getNombrePresupuesto() + " - " + presupuesto.getMontoTotal() + " - " + presupuesto.getMontoGastado() + " - " + presupuesto.getCategoria().getIdCategoria(), 1, "ClickGuardar", "C:\\td\\Persistencia\\Log\\log.txt");
    }

    // Método para cargar cuentas desde el archivo
    public List<Cuenta> cargarCuentas() throws IOException {
        rutaArchivoCuenta = obtenerRutasProperties("rutaArchivoCuenta");  // Cargar la ruta correcta
        List<Cuenta> cuentas = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivoCuenta);

        for (String cuentaTexto : contenido) {
            String[] split = cuentaTexto.split("@@");
            if (split.length != 4) {
                System.err.println("Línea incorrecta: " + cuentaTexto);
                continue;
            }

            try {
                Cuenta cuenta = new Cuenta(
                        split[0],  // idCuenta
                        split[1],  // nombreBanco
                        split[2],  // numeroCuenta
                        TipoCuenta.valueOf(split[3])  // tipoCuenta
                );
                cuentas.add(cuenta);
            } catch (IllegalArgumentException e) {
                System.err.println("Error al convertir el tipo de cuenta: " + e.getMessage());
            }
        }
        return cuentas;
    }

    // Método para cargar categorías desde el archivo
    public List<Categoria> cargarCategorias() throws IOException {
        rutaArchivoCategoria = obtenerRutasProperties("rutaArchivoCategoria");  // Cargar la ruta correcta
        List<Categoria> categorias = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivoCategoria);

        for (String categoriaTexto : contenido) {
            String[] split = categoriaTexto.split("@@");
            if (split.length != 3) {
                System.err.println("Línea incorrecta: " + categoriaTexto);
                continue;
            }

            Categoria categoria = new Categoria(
                    split[0],  // idCategoria
                    split[1],  // nombre
                    split[2]   // descripcion
            );
            categorias.add(categoria);
        }
        return categorias;
    }

    // Método para cargar presupuestos desde el archivo
    public List<Presupuesto> cargarPresupuestos() throws IOException {
        rutaArchivoPresupuesto = obtenerRutasProperties("rutaArchivoPresupuesto");  // Cargar la ruta correcta
        List<Presupuesto> presupuestos = new ArrayList<>();
        List<Categoria> categorias = cargarCategorias();  // Se cargan las categorías para asignarlas a los presupuestos
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivoPresupuesto);

        for (String presupuestoTexto : contenido) {
            String[] split = presupuestoTexto.split("@@");
            if (split.length != 5) {
                System.err.println("Línea incorrecta: " + presupuestoTexto);
                continue;
            }

            // Buscar la categoría correspondiente
            Categoria categoria = categorias.stream()
                    .filter(cat -> cat.getIdCategoria().equals(split[4]))
                    .findFirst().orElse(null);

            if (categoria != null) {
                Presupuesto presupuesto = new Presupuesto(
                        split[0],  // idPresupuesto
                        split[1],  // nombre
                        Double.valueOf(split[2]),  // montoTotal
                        Double.valueOf(split[3]),  // montoGastado
                        categoria  // categoría
                );
                presupuestos.add(presupuesto);
            }
        }
        return presupuestos;
    }
}
