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
    date = "2024-11-18T11:11:01-0500",
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
        Usuario usuario = null;
        Double saldo = null;

        idCuenta = cuenta.getIdCuenta();
        nombreBanco = cuenta.getNombreBanco();
        numeroCuenta = cuenta.getNumeroCuenta();
        tipoCuenta = cuenta.getTipoCuenta();
        usuario = cuenta.getUsuario();
        saldo = cuenta.getSaldo();

        CuentaDto cuentaDto = new CuentaDto( idCuenta, nombreBanco, numeroCuenta, tipoCuenta, usuario, saldo );

        return cuentaDto;
    }

    @Override
    public PresupuestoDto presupuestoToPresupuestoDto(Presupuesto presupuesto) {
        if ( presupuesto == null ) {
            return null;
        }

        Categoria categoria = null;
        String idPresupuesto = null;
        String nombrePresupuesto = null;
        Double montoTotal = null;
        Double montoGastado = null;

        categoria = presupuesto.getCategoria();
        idPresupuesto = presupuesto.getIdPresupuesto();
        nombrePresupuesto = presupuesto.getNombrePresupuesto();
        montoTotal = presupuesto.getMontoTotal();
        montoGastado = presupuesto.getMontoGastado();

        PresupuestoDto presupuestoDto = new PresupuestoDto( idPresupuesto, nombrePresupuesto, montoTotal, montoGastado, categoria );

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
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        if ( usuarioDto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setContraseña( usuarioDto.contraseña() );
        usuario.setSaldoTotal( usuarioDto.saldoTotal() );
        usuario.setUsuarioID( usuarioDto.usuarioID() );
        usuario.setNombre( usuarioDto.nombre() );
        usuario.setCorreo( usuarioDto.correo() );
        usuario.setNumeroTelefono( usuarioDto.numeroTelefono() );
        usuario.setDireccion( usuarioDto.direccion() );

        return usuario;
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

        Administrador administrador = new Administrador();

        administrador.setContraseña( administradorDto.contraseña() );
        administrador.setSaldoTotal( administradorDto.saldoTotal() );
        administrador.setUsuarioID( administradorDto.usuarioID() );
        administrador.setNombre( administradorDto.nombre() );
        administrador.setCorreo( administradorDto.correo() );
        administrador.setNumeroTelefono( administradorDto.numeroTelefono() );
        administrador.setDireccion( administradorDto.direccion() );

        return administrador;
    }

    @Override
    public Categoria categoriaDtoToCategoria(CategoriaDto categoriaDto) {
        if ( categoriaDto == null ) {
            return null;
        }

        Categoria categoria = new Categoria();

        categoria.setIdCategoria( categoriaDto.idCategoria() );
        categoria.setNombre( categoriaDto.nombre() );
        categoria.setDescripcion( categoriaDto.descripcion() );

        return categoria;
    }

    @Override
    public Cuenta cuentaDtoTOCuenta(CuentaDto cuentaDto) {
        if ( cuentaDto == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setIdCuenta( cuentaDto.idCuenta() );
        cuenta.setNombreBanco( cuentaDto.nombreBanco() );
        cuenta.setNumeroCuenta( cuentaDto.numeroCuenta() );
        cuenta.setTipoCuenta( cuentaDto.tipoCuenta() );
        cuenta.setUsuario( cuentaDto.usuario() );
        cuenta.setSaldo( cuentaDto.saldo() );

        return cuenta;
    }

    @Override
    public Presupuesto presupuestoDtoToPresupuesto(PresupuestoDto presupuestoDto) {
        if ( presupuestoDto == null ) {
            return null;
        }

        Presupuesto presupuesto = new Presupuesto();

        presupuesto.setIdPresupuesto( presupuestoDto.idPresupuesto() );
        presupuesto.setMontoTotal( presupuestoDto.montoTotal() );
        presupuesto.setMontoGastado( presupuestoDto.montoGastado() );
        presupuesto.setCategoria( presupuestoDto.categoria() );

        return presupuesto;
    }
}
