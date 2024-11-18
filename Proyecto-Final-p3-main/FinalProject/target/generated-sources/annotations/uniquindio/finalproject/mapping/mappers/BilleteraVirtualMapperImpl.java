package uniquindio.finalproject.mapping.mappers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.mapping.dto.*;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-15T16:45:30-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
*/
public class BilleteraVirtualMapperImpl implements BilleteraMapper {

    @Override
    public CategoriaDto categoriaToCategoriaDto(Categoria categoria) {
        if ( categoria == null ) {
            return null;
        }

        String idCategoria = null;

        idCategoria = categoria.getIdCategoria();

        String nombre = null;
        String descripcion = null;

        CategoriaDto categoriaDto = new CategoriaDto( idCategoria, nombre, descripcion );

        return categoriaDto;
    }

    @Override
    public CuentaDto cuentaToCuentaDto(Cuenta cuenta) {
        if (cuenta == null) {
            return null;
        }
        return new CuentaDto(
                cuenta.getIdCuenta(),
                cuenta.getNombreBanco(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getUsuario(),
                cuenta.getSaldo()
        );
    }


    @Override
    public PresupuestoDto presupuestoToPresupuestoDto(Presupuesto presupuesto) {
        if ( presupuesto == null ) {
            return null;
        }

        Categoria categoria = null;
        String idPresupuesto = null;
        Double montoTotal = null;
        Double montoGastado = null;

        categoria = presupuesto.getCategoria();
        idPresupuesto = presupuesto.getIdPresupuesto();
        montoTotal = presupuesto.getMontoTotal();
        montoGastado = presupuesto.getMontoGastado();

        String nombre = null;

        PresupuestoDto presupuestoDto = new PresupuestoDto( idPresupuesto, nombre, montoTotal, montoGastado, categoria );

        return presupuestoDto;
    }

    @Override
    public TransaccionDto transaccionToTransaccionDto(Transaccion transaccion) {
        if ( transaccion == null ) {
            return null;
        }

        Cuenta cuentaOrigen = null;
        Cuenta cuentaDestino = null;
        Categoria categoria = null;
        String idTransaccion = null;
        LocalDate fecha = null;
        TipoTransaccion tipoTransaccion = null;
        Double monto = null;
        String descripcion = null;

        cuentaOrigen = transaccion.getCuentaOrigen();
        cuentaDestino = transaccion.getCuentaDestino();
        categoria = transaccion.getCategoria();
        idTransaccion = transaccion.getIdTransaccion();
        fecha = transaccion.getFecha();
        tipoTransaccion = transaccion.getTipoTransaccion();
        monto = transaccion.getMonto();
        descripcion = transaccion.getDescripcion();

        TransaccionDto transaccionDto = new TransaccionDto( idTransaccion, fecha, tipoTransaccion, monto, descripcion, cuentaOrigen, cuentaDestino, categoria );

        return transaccionDto;
    }

    @Override
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        String usuarioID = null;
        String nombre = null;
        String correo = null;
        String numeroTelefono = null;
        String direccion = null;
        Double saldoTotal = null;
        String contraseña = null;

        usuarioID = usuario.getUsuarioID();
        nombre = usuario.getNombre();
        correo = usuario.getCorreo();
        numeroTelefono = usuario.getNumeroTelefono();
        direccion = usuario.getDireccion();
        saldoTotal = usuario.getSaldoTotal();
        contraseña = usuario.getContraseña();

        UsuarioDto usuarioDto = new UsuarioDto( usuarioID, nombre, correo, numeroTelefono, direccion, saldoTotal, contraseña );

        return usuarioDto;
    }

    @Override
    public List<UsuarioDto> convertirUsuariosDto(List<Usuario> usuarios) {
        // Convertimos cada Usuario a UsuarioDto usando usuarioToUsuarioDto
        return usuarios.stream()
                .map(this::usuarioToUsuarioDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdministradorDto administradorToAdministradorDto(Administrador administrador) {
        // Convertimos Administrador a AdministradorDto mapeando los atributos manualmente
        return new AdministradorDto(
                administrador.getUsuarioID(),
                administrador.getNombre(),
                administrador.getCorreo(),
                administrador.getNumeroTelefono(),
                administrador.getDireccion(),
                administrador.getSaldoTotal(),
                administrador.getContraseña()
        );
    }

    @Override
    public Administrador administradorDtoToAdministrador(AdministradorDto administradorDto) {
        // Convertimos AdministradorDto a Administrador mapeando los atributos manualmente
        return new Administrador(
                administradorDto.usuarioID(),
                administradorDto.contraseña()
        );
    }

}
