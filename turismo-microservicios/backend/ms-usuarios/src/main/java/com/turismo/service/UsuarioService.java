package com.turismo.service;

import com.turismo.dto.ClienteRegistroDTO;
import com.turismo.dto.ClienteRespuestaDTO;
import com.turismo.dto.UsuarioActualizacionBasicaDTO;
import com.turismo.dto.UsuarioRegistroDTO;
import com.turismo.dto.UsuarioRespuestaDTO;
import com.turismo.model.Cliente;
import com.turismo.model.Rol;
import com.turismo.model.RolNombre;
import com.turismo.model.Usuario;
import com.turismo.repository.ClienteRepository;
import com.turismo.repository.RolRepository;
import com.turismo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final RolRepository rolRepository;

    public UsuarioRespuestaDTO registrarUsuario(UsuarioRegistroDTO dto) {
        validarDuplicadosUsuario(dto.getEmail(), dto.getUsername(), null);

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setUsername(dto.getUsername());
        usuario.setPasswordHash(dto.getPassword());
        usuario.setActivo(dto.getActivo());
        usuario.setRol(rol);

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado con id {}", guardado.getId());
        return toUsuarioRespuestaDTO(guardado);
    }

    public ClienteRespuestaDTO registrarCliente(ClienteRegistroDTO dto) {
        validarDuplicadosUsuario(dto.getEmail(), dto.getUsername(), dto.getNumeroDocumento());

        Rol rolCliente = rolRepository.findByNombre(RolNombre.CLIENTE)
                .orElseThrow(() -> new IllegalArgumentException("No existe rol CLIENTE configurado"));

        Usuario usuario = new Usuario();
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setUsername(dto.getUsername());
        usuario.setPasswordHash(dto.getPassword());
        usuario.setActivo(true);
        usuario.setRol(rolCliente);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setTipoDocumento(dto.getTipoDocumento());
        cliente.setNumeroDocumento(dto.getNumeroDocumento());
        cliente.setDireccion(dto.getDireccion());
        cliente.setUsuario(usuarioGuardado);

        Cliente clienteGuardado = clienteRepository.save(cliente);
        log.info("Cliente registrado con id {} y usuario {}", clienteGuardado.getId(), usuarioGuardado.getId());
        return toClienteRespuestaDTO(clienteGuardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespuestaDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toUsuarioRespuestaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioRespuestaDTO buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return toUsuarioRespuestaDTO(usuario);
    }

    @Transactional(readOnly = true)
    public ClienteRespuestaDTO buscarClientePorDocumento(String numeroDocumento) {
        Cliente cliente = clienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return toClienteRespuestaDTO(cliente);
    }

    public UsuarioRespuestaDTO actualizarDatosBasicos(Long id, UsuarioActualizacionBasicaDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        validarDuplicadosUsuario(dto.getEmail(), dto.getUsername(), null, id);

        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setUsername(dto.getUsername());

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Datos básicos actualizados para usuario {}", id);
        return toUsuarioRespuestaDTO(actualizado);
    }

    public UsuarioRespuestaDTO desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setActivo(false);
        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario {} desactivado", id);
        return toUsuarioRespuestaDTO(actualizado);
    }

    private void validarDuplicadosUsuario(String email, String username, String numeroDocumento) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El username ya está registrado");
        }
        if (numeroDocumento != null && clienteRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }
    }

    private void validarDuplicadosUsuario(String email, String username, String numeroDocumento, Long idUsuarioActual) {
        if (usuarioRepository.existsByEmailAndIdNot(email, idUsuarioActual)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (usuarioRepository.existsByUsernameAndIdNot(username, idUsuarioActual)) {
            throw new IllegalArgumentException("El username ya está registrado");
        }
        if (numeroDocumento != null && clienteRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }
    }

    private UsuarioRespuestaDTO toUsuarioRespuestaDTO(Usuario usuario) {
        return UsuarioRespuestaDTO.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .username(usuario.getUsername())
                .activo(usuario.getActivo())
                .rolId(usuario.getRol().getId())
                .rolNombre(usuario.getRol().getNombre().name())
                .build();
    }

    private ClienteRespuestaDTO toClienteRespuestaDTO(Cliente cliente) {
        return ClienteRespuestaDTO.builder()
                .id(cliente.getId())
                .tipoDocumento(cliente.getTipoDocumento())
                .numeroDocumento(cliente.getNumeroDocumento())
                .direccion(cliente.getDireccion())
                .usuario(toUsuarioRespuestaDTO(cliente.getUsuario()))
                .build();
    }
}
