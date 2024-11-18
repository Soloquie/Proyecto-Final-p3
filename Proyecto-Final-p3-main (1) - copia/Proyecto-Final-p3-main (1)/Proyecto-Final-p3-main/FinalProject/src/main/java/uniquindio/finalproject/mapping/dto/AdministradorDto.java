package uniquindio.finalproject.mapping.dto;

import java.io.Serializable;

public record AdministradorDto(
        String usuarioID,
        String nombre,
        String correo,
        String numeroTelefono,
        String direccion,
        Double saldoTotal,
        String contraseña
) implements Serializable {
    private static final long serialVersionUID = 1L;
}

