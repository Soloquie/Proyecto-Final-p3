package uniquindio.finalproject.Model;


import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {
    private static final long serialVersionUID = -6325714660046829272L;
    public Administrador(String usuarioID, String contraseña) {
        super(usuarioID, "Administrador", "", "", "", 0.0, contraseña);
    }

    public Administrador() {super();}

    @Override
    public String toString() {
        return "Administrador{}";
    }

    // Métodos específicos de administrador
}
