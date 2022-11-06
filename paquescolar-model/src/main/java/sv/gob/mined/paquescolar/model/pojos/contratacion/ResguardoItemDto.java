package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@SqlResultSetMapping(name = "defaultResguardoItemDto",
        entities = @EntityResult(entityClass = ResguardoItemDto.class))
public class ResguardoItemDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private BigDecimal idNivelEducativo;
    private BigDecimal idProducto;
    private String descripcionNivel;
    private String nombreProducto;
    private BigDecimal total;
    private BigDecimal cantidad;
    private BigDecimal totalComprar;

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(BigDecimal idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }

    public BigDecimal getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(BigDecimal idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionNivel() {
        return descripcionNivel;
    }

    public void setDescripcionNivel(String descripcionNivel) {
        this.descripcionNivel = descripcionNivel;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotalComprar() {
        return totalComprar;
    }

    public void setTotalComprar(BigDecimal totalComprar) {
        this.totalComprar = totalComprar;
    }

}
