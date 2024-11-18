package uniquindio.finalproject.Interfaces;

import uniquindio.finalproject.Model.Usuario;

public interface GestionUsuarioInterface {
    void crearUsuario(Usuario usuario);
    boolean actualizarUsuario(Usuario usuario, Usuario usuarioActualizado);
    boolean eliminarUsuario(Usuario usuario);
}
