package uniquindio.finalproject.mapping.dto;

import java.io.Serializable;

public record CategoriaDto (
        String idCategoria,
        String nombre,
        String descripcion) implements Serializable {
    private static final long serialVersionUID = 1L;
}
