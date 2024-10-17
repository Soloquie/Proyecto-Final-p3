package uniquindio.finalproject.persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Respaldo {

    public static void respaldarArchivoXML(String rutaOrigen, String rutaDestinoDir) throws IOException {
        File archivoOrigen = new File(rutaOrigen);
        if (!archivoOrigen.exists()) {
            throw new IOException("El archivo de origen no existe: " + rutaOrigen);
        }

        File carpetaRespaldo = new File(rutaDestinoDir);
        if (!carpetaRespaldo.exists()) {
            carpetaRespaldo.mkdir();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivoRespaldo = archivoOrigen.getName().replace(".xml", "") + "_" + timeStamp + ".xml";

        Path destino = Path.of(rutaDestinoDir, nombreArchivoRespaldo);
        Files.copy(archivoOrigen.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Respaldo realizado en: " + destino.toString());
    }
}
