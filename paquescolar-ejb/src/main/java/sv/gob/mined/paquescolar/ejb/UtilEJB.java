/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Municipio;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class UtilEJB {

    private final List<SelectItem> lstDocumentosImp = new ArrayList<>();
    private final SelectItem garantiaUsoTela = new SelectItem(6, "Garantía  de buen uso y resguardo de la tela");

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public void executeSql(String sql) {
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
        em.flush();
    }

    /**
     * @param <T>
     * @param type
     * @param o
     * @return 
     */
    public <T extends Object> T find(Class<T> type, Object o) {
        if (o == null) {
            return null;
        } else {
            return em.find(type, o);
        }
    }

    public void refreshEntity(Object obj) {
        em.refresh(obj);
    }
    
    public void updateEntity(Object obj){
        em.merge(obj);
    }

    public Municipio getMunicipioPrimerByDepartamento(String departamento) {
        Query q = em.createQuery("SELECT  m FROM Municipio m WHERE m.codigoDepartamento.codigoDepartamento=:depa", Municipio.class);
        q.setParameter("depa", departamento);
        return (Municipio) q.getResultList().get(0);
    }

    public List<SelectItem> getLstDocumentosImp(Boolean uniforme) {
        if (lstDocumentosImp.isEmpty()) {
            //Id son los mismos que estan el la tabla TIPO_RPT
            lstDocumentosImp.add(new SelectItem(3, "Acta Adjudicación"));
            lstDocumentosImp.add(new SelectItem(4, "Nota Adjudicación"));
            lstDocumentosImp.add(new SelectItem(7, "Contrato"));
            lstDocumentosImp.add(new SelectItem(5, "Garantía Contrato"));
        }
        if (uniforme) {
            if (!lstDocumentosImp.contains(garantiaUsoTela)) {
                lstDocumentosImp.add(garantiaUsoTela);
            }
        } else {
            lstDocumentosImp.remove(garantiaUsoTela);
        }

        return lstDocumentosImp;
    }
}
