package uniquindio.finalproject.mapping.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniquindio.finalproject.Model.Administrador;
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.Presupuesto;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.Model.TipoTransaccion;
import uniquindio.finalproject.Model.Transaccion;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.mapping.dto.AdministradorDto;
import uniquindio.finalproject.mapping.dto.CategoriaDto;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.PresupuestoDto;
import uniquindio.finalproject.mapping.dto.TransaccionDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-15T23:04:59-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
*/
public class BilleteraMapperImpl implements BilleteraMapper {

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
        if ( cuenta == null ) {
            return null;
        }

        String idCuenta = null;
        String nombreBanco = null;
        String numeroCuenta = null;
        TipoCuenta tipoCuenta = null;

        idCuenta = cuenta.getIdCuenta();
        nombreBanco = cuenta.getNombreBanco();
        numeroCuenta = cuenta.getNumeroCuenta();
        tipoCuenta = cuenta.getTipoCuenta();

        CuentaDto cuentaDto = new CuentaDto( idCuenta, nombreBanco, numeroCuenta, tipoCuenta );

        return cuentaDto;
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
        if ( usuarios == null ) {
            return null;
        }

        List<UsuarioDto> list = new ArrayList<UsuarioDto>( usuarios.size() );
        for ( Usuario usuario : usuarios ) {
            list.add( usuarioToUsuarioDto( usuario ) );
        }

        return list;
    }

    @Override
    public AdministradorDto administradorToAdministradorDto(Administrador administrador) {
        if ( administrador == null ) {
            return null;
        }

        String usuarioID = null;
        String nombre = null;
        String correo = null;
        String numeroTelefono = null;
        String direccion = null;
        Double saldoTotal = null;
        String contraseña = null;

        usuarioID = administrador.getUsuarioID();
        nombre = administrador.getNombre();
        correo = administrador.getCorreo();
        numeroTelefono = administrador.getNumeroTelefono();
        direccion = administrador.getDireccion();
        saldoTotal = administrador.getSaldoTotal();
        contraseña = administrador.getContraseña();

        AdministradorDto administradorDto = new AdministradorDto( usuarioID, nombre, correo, numeroTelefono, direccion, saldoTotal, contraseña );

        return administradorDto;
    }

    @Override
    public Administrador administradorDtoToAdministrador(AdministradorDto administradorDto) {
        if ( administradorDto == null ) {
            return null;
        }

        String contraseña = null;
        String usuarioID = null;

        contraseña = administradorDto.contraseña();
        usuarioID = administradorDto.usuarioID();

        Administrador administrador = new Administrador( usuarioID, contraseña );

        administrador.setSaldoTotal( administradorDto.saldoTotal() );
        administrador.setNombre( administradorDto.nombre() );
        administrador.setCorreo( administradorDto.correo() );
        administrador.setNumeroTelefono( administradorDto.numeroTelefono() );
        administrador.setDireccion( administradorDto.direccion() );

        return administrador;
    }
}