package uniquindio.finalproject.request;

import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.Serializable;

public class UsuarioRequest implements Serializable {
    private final UsuarioRequestType requestType;
    private final UsuarioDto usuario;

    public UsuarioRequest(UsuarioRequestType requestType, UsuarioDto usuario) {
        this.requestType = requestType;
        this.usuario = usuario;
    }

    public UsuarioRequestType getRequestType() {
        return requestType;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }
}
