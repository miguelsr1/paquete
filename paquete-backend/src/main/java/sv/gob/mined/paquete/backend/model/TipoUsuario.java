package sv.gob.mined.paquete.backend.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author MISanchez
 */
public class TipoUsuario {
    private BigDecimal idTipoUsuario;
    private String descripcion;
    private BigInteger administrador;

    public TipoUsuario() {
    }

    public BigDecimal getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(BigDecimal idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getAdministrador() {
        return administrador;
    }

    public void setAdministrador(BigInteger administrador) {
        this.administrador = administrador;
    }

    @Override
    public String toString() {
        return "TipoUsuario{" + "idTipoUsuario=" + idTipoUsuario + ", descripcion=" + descripcion + ", administrador=" + administrador + '}';
    }
}