package uniquindio.finalproject.mapping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uniquindio.finalproject.Model.*;
import uniquindio.finalproject.mapping.dto.*;

import java.util.List;

@Mapper
public interface BilleteraMapper {
    BilleteraMapper INSTANCE = Mappers.getMapper(BilleteraMapper.class);

    // Mapeo para Categoria y CategoriaDto
    CategoriaDto categoriaToCategoriaDto(Categoria categoria);

    // Mapeo para Cuenta y CuentaDto
    CuentaDto cuentaToCuentaDto(Cuenta cuenta);

    // Mapeo para Presupuesto y PresupuestoDto
    @Mapping(target = "categoria", source = "categoria") // Mapea el campo Categoria dentro de Presupuesto
    PresupuestoDto presupuestoToPresupuestoDto(Presupuesto presupuesto);

    // Mapeo para Transaccion y TransaccionDto
    @Mapping(target = "cuentaOrigen", source = "cuentaOrigen")
    @Mapping(target = "cuentaDestino", source = "cuentaDestino")
    @Mapping(target = "categoria", source = "categoria")
    TransaccionDto transaccionToTransaccionDto(Transaccion transaccion);

    // Mapeo para Usuario y UsuarioDto
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    List<UsuarioDto> convertirUsuariosDto(List<Usuario> usuarios);

    AdministradorDto administradorToAdministradorDto(Administrador administrador);
    Administrador administradorDtoToAdministrador(AdministradorDto administradorDto);

}
