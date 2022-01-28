package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultParticipanteConContratoDto",
        entities = @EntityResult(entityClass = ParticipanteConContratoDto.class))
public class ParticipanteConContratoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private BigDecimal idParticipante;
    private String razonSocial;
    private String codigoEntidad;
    private Integer idDetProcesoAdq;
    private BigDecimal idContrato;

    public ParticipanteConContratoDto() {
    }

    public BigDecimal getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(BigDecimal idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Integer getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(Integer idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

}
