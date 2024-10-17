package uniquindio.finalproject.persistencia;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.LinkedList;
import uniquindio.finalproject.Model.Usuario;

public class Serializador {

    public static void serializarBinario(Object objeto, String ruta) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(ruta);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(objeto);
        }
    }

    public static Object deserializarBinario(String ruta) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(ruta);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return in.readObject();
        }
    }

    public static void serializarXML(LinkedList<Usuario> usuarios, String ruta) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("Usuarios");
        doc.appendChild(rootElement);

        for (Usuario usuario : usuarios) {
            Element usuarioElement = doc.createElement("Usuario");
            rootElement.appendChild(usuarioElement);

            Element id = doc.createElement("ID");
            id.appendChild(doc.createTextNode(usuario.getUsuarioID()));
            usuarioElement.appendChild(id);

            Element nombre = doc.createElement("Nombre");
            nombre.appendChild(doc.createTextNode(usuario.getNombre()));
            usuarioElement.appendChild(nombre);

            Element correo = doc.createElement("Correo");
            correo.appendChild(doc.createTextNode(usuario.getCorreo()));
            usuarioElement.appendChild(correo);

            Element saldo = doc.createElement("SaldoTotal");
            saldo.appendChild(doc.createTextNode(Double.toString(usuario.getSaldoTotal())));
            usuarioElement.appendChild(saldo);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(ruta));
        transformer.transform(source, result);
    }
}
