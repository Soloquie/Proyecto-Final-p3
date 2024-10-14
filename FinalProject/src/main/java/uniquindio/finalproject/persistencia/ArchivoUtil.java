package uniquindio.finalproject.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ArchivoUtil {

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     * @param ruta es la ruta o path donde esta ubicado el archivo
     * @throws IOException
     */
    public static void guardarArchivo(String ruta,String contenido, Boolean flagAnexarContenido) throws IOException {

        FileWriter fw = new FileWriter(ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     * @param ruta
     * @return
     * @throws IOException
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<String>();
        //abrir conexion
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        // leer
        String linea="";
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        //cerrar
        bfr.close();
        fr.close();
        return contenido;
    }


    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;

        try {
            fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO, accion + "," + mensajeLog);
                    break;

                case 2:
                    LOGGER.log(Level.WARNING, accion + "," + mensajeLog);
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE, accion + "," + mensajeLog);
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {

            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {

            fileHandler.close();
        }
    }
}