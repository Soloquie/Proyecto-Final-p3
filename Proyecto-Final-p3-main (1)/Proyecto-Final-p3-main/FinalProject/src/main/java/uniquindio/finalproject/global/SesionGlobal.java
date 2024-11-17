package uniquindio.finalproject.global;

import uniquindio.finalproject.Model.Administrador;
import uniquindio.finalproject.Model.Usuario;

import java.util.LinkedList;

public class SesionGlobal {
    public static Usuario usuarioActual;
    public static Administrador admin = new Administrador("admin123", "admin");
    public static Usuario user1 = new Usuario("usuario1", "Usuario1", "user1@correo.com", "987654321", "Calle User1", 500.0, "123");
    public static Usuario user2 = new Usuario("usuario2", "Usuario2", "user2@correo.com", "654321987", "Calle User2", 300.0, "456");

    public static LinkedList<Usuario> usuarios = new LinkedList<>();

    static {
        usuarios.add(admin);
        usuarios.add(user1);
        usuarios.add(user2);
    }

}
