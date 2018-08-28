/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import sv.gob.mined.paquescolar.model.pojos.*;

/**
 *
 * @author reynaldo
 */
@Deprecated
public class SmaelDao {

    //private DataSource origendatos = null;
    private String queryEstadoPlanilla = "select * from PLANILLAS where CODIGO_PLANILLA =? and codigo_departamento=? and estado_planilla = 1";
    private String queryEncabezadoMatriz = "select distinct TIPO_CONCEPTO_BONO.TIPO_CONCEPTO_BONO, BONO.NOMBRE_BONO,    FUENTE_FINANCIAMIENTO.NOMBRE,"
            + " REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO, TRANSFERENCIAS.CODIGO_PLANILLA,     0, BANCOS.NOMBRE_BANCO, TRANSFERENCIAS.ID_PRE_CARGA  "
            + " from  TRANSFERENCIAS    inner join REQUERIMIENTO_FONDOS on TRANSFERENCIAS.CODIGO_REQUERIMIENTO = REQUERIMIENTO_FONDOS.CODIGO_REQUERIMIENTO"
            + " and TRANSFERENCIAS.CODIGO_DEPARTAMENTO = REQUERIMIENTO_FONDOS.CODIGO_DEPARTAMENTO"
            + " inner join CUENTAS_PAGADURIA on CUENTAS_PAGADURIA.ID_CUENTA_PAGADURIA = REQUERIMIENTO_FONDOS.ID_CUENTA_PAGADURIA"
            + " inner join BANCOS on CUENTAS_PAGADURIA.ID_BANCO = BANCOS.ID_BANCO"
            + " inner join FUENTE_FINANCIAMIENTO on REQUERIMIENTO_FONDOS.ID_FUENTE = FUENTE_FINANCIAMIENTO.ID_FUENTE "
            + " inner join BONO on BONO.ID_BONO = REQUERIMIENTO_FONDOS.ID_BONO"
            + "    and BONO.ID_BONO = TRANSFERENCIAS.ID_BONO"
            + " inner join TIPO_CONCEPTO_BONO on BONO.ID_TIPO_CONCEPTO_BONO = TIPO_CONCEPTO_BONO.ID_TIPO_CONCEPTO_BONO"
            + " WHERE  REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO=? and TRANSFERENCIAS.CODIGO_PLANILLA is not null";
    private String queryEncabezado = "select distinct TIPO_CONCEPTO_BONO.TIPO_CONCEPTO_BONO, "
            + "    BONO.NOMBRE_BONO,    "
            + "    FUENTE_FINANCIAMIENTO.NOMBRE, "
            + "    REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO, "
            + "    BANCOS.NOMBRE_BANCO, "
            + "    TRANSFERENCIAS.ID_PRE_CARGA "
            + "from  TRANSFERENCIAS    "
            + "    inner join REQUERIMIENTO_FONDOS on TRANSFERENCIAS.CODIGO_REQUERIMIENTO = REQUERIMIENTO_FONDOS.CODIGO_REQUERIMIENTO "
            + "        and TRANSFERENCIAS.CODIGO_DEPARTAMENTO = REQUERIMIENTO_FONDOS.CODIGO_DEPARTAMENTO "
            + "    inner join CUENTAS_PAGADURIA on CUENTAS_PAGADURIA.ID_CUENTA_PAGADURIA = REQUERIMIENTO_FONDOS.ID_CUENTA_PAGADURIA "
            + "    inner join BANCOS on CUENTAS_PAGADURIA.ID_BANCO = BANCOS.ID_BANCO "
            + "    inner join FUENTE_FINANCIAMIENTO on TRANSFERENCIAS.ID_FUENTE = FUENTE_FINANCIAMIENTO.ID_FUENTE  "
            + "    inner join BONO on BONO.ID_BONO = REQUERIMIENTO_FONDOS.ID_BONO    "
            + "        and BONO.ID_BONO = TRANSFERENCIAS.ID_BONO   "
            + "        and REQUERIMIENTO_FONDOS.ID_BONO = BONO.ID_BONO "
            + "    inner join TIPO_CONCEPTO_BONO on BONO.ID_TIPO_CONCEPTO_BONO = TIPO_CONCEPTO_BONO.ID_TIPO_CONCEPTO_BONO "
            + " WHERE  REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO=?";
    private String queryEncabezadoPlanillas = "select distinct TIPO_CONCEPTO_BONO.TIPO_CONCEPTO_BONO, BONO.NOMBRE_BONO,    FUENTE_FINANCIAMIENTO.NOMBRE,"
            + " REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO, TRANSFERENCIAS.CODIGO_PLANILLA,     PLANILLA_PARCIAL.ID_PLANILLA_PARCIAL, BANCOS.NOMBRE_BANCO, TRANSFERENCIAS.ID_PRE_CARGA,  ESTADO_PLANILLAS.NOMBRE_EST_PLANILLA "
            + " from  TRANSFERENCIAS    inner join REQUERIMIENTO_FONDOS on TRANSFERENCIAS.CODIGO_REQUERIMIENTO = REQUERIMIENTO_FONDOS.CODIGO_REQUERIMIENTO"
            + " and TRANSFERENCIAS.CODIGO_DEPARTAMENTO = REQUERIMIENTO_FONDOS.CODIGO_DEPARTAMENTO"
            + " inner join CUENTAS_PAGADURIA on CUENTAS_PAGADURIA.ID_CUENTA_PAGADURIA = REQUERIMIENTO_FONDOS.ID_CUENTA_PAGADURIA"
            + " inner join BANCOS on CUENTAS_PAGADURIA.ID_BANCO = BANCOS.ID_BANCO"
            + " inner join PLANILLAS on TRANSFERENCIAS.CODIGO_PLANILLA = PLANILLAS.CODIGO_PLANILLA"
            + " and TRANSFERENCIAS.CODIGO_DEPARTAMENTO = PLANILLAS.CODIGO_DEPARTAMENTO "
            + " inner join PLANILLA_PARCIAL on PLANILLA_PARCIAL.CODIGO_PLANILLA = PLANILLAS.CODIGO_PLANILLA "
            + " and PLANILLA_PARCIAL.CODIGO_DEPARTAMENTO = PLANILLAS.CODIGO_DEPARTAMENTO "
            + " inner join ESTADO_PLANILLAS on PLANILLA_PARCIAL.ESTADO_PLA_DET = ESTADO_PLANILLAS.ESTADO_PLANILLA"
            + " inner join FUENTE_FINANCIAMIENTO on PLANILLAS.ID_FUENTE = FUENTE_FINANCIAMIENTO.ID_FUENTE "
            + " inner join BONO on BONO.ID_BONO = PLANILLAS.ID_BONO"
            + "    and BONO.ID_BONO = TRANSFERENCIAS.ID_BONO"
            + "   and REQUERIMIENTO_FONDOS.ID_BONO = BONO.ID_BONO"
            + " inner join TIPO_CONCEPTO_BONO on BONO.ID_TIPO_CONCEPTO_BONO = TIPO_CONCEPTO_BONO.ID_TIPO_CONCEPTO_BONO"
            + " WHERE  REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO=?";
    private String queryBanco = "select ID_BANCO, NOMBRE_BANCO from BANCOS ORDER BY ID_BANCO";
    private String requerimientoCodigoEntidad = "select TRANSFERENCIAS.CODIGO_ENTIDAD "
            + "from REQUERIMIENTO_FONDOS "
            + "    inner join TRANSFERENCIAS on REQUERIMIENTO_FONDOS.CODIGO_DEPARTAMENTO = TRANSFERENCIAS.CODIGO_DEPARTAMENTO "
            + "        and REQUERIMIENTO_FONDOS.CODIGO_REQUERIMIENTO = TRANSFERENCIAS.CODIGO_REQUERIMIENTO "
            + "where REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO=?";
    //restriccion para id_bono 436,438,
    private String queryTrasferencias = "update transferencias set monto_acumulado=? "
            + "where codigo_planilla=? "
            + "and codigo_entidad=? "
            + "and codigo_departamento=? ";
    private String queryDetTransferencias = "UPDATE det_transferencia "
            + "SET det_transferencia.monto = transferencias.monto_acumulado, "
            + "    det_transferencia.monto_calculado = transferencias.monto_acumulado, "
            //+ "     -- campo que no se ocupa "
            + "    det_transferencia.monto_actual=0.0, "
            //+ "     -- campo que no se ocupa "
            + "    det_transferencia.monto_pagado =0.0 "
            + "FROM det_transferencia AS U "
            + "   INNER JOIN transferencias ON   U.id_transferencia = transferencias.id_transferencia and "
            + "      U.codigo_departamento=transferencias.codigo_departamento "
            + "WHERE U.codigo_planilla =?  and "
            + "	  U.id_planilla_parcial=? ";
    private String queryDetPlaParCheques = "update det_pla_par_cheques set monto_cheque=? where codigo_planilla=? and codigo_departamento=?  and id_planilla_parcial=? and id_banco=?";

    public SmaelDao() {
    }

    public List<SmaelEncabezadoDTO> findEncabezado(String codDep, String no_requerimiento, Boolean matriz) {
        SmaelEncabezadoDTO encabezado;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<SmaelEncabezadoDTO> lst = new ArrayList<SmaelEncabezadoDTO>();
        try {
            Connection conn = null;

            DataSource origendatos = this.getOrigendatos(codDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    if (matriz) {
                        pstm = conn.prepareStatement(queryEncabezado);
                    } else {
                        pstm = conn.prepareStatement(queryEncabezadoPlanillas);
                    }

                    pstm.setString(1, no_requerimiento);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        encabezado = new SmaelEncabezadoDTO();
                        if (matriz) {
                            encabezado.setTIPO_CONCEPTO_BONO(rs.getString(1));
                            encabezado.setNOMBRE_BONO(rs.getString(2));
                            encabezado.setNOMBRE(rs.getString(3));
                            encabezado.setNO_REQUERIMIENTO(rs.getString(4));
                            encabezado.setNOMBRE_BANCO(rs.getString(5));
                            encabezado.setID_PRE_CARGA(rs.getString(6));
                            //encabezado.setMONTO(rs.getString(7));
                        } else {
                            encabezado = new SmaelEncabezadoDTO();
                            encabezado.setTIPO_CONCEPTO_BONO(rs.getString(1));
                            encabezado.setNOMBRE_BONO(rs.getString(2));
                            encabezado.setNOMBRE(rs.getString(3));
                            encabezado.setNO_REQUERIMIENTO(rs.getString(4));
                            encabezado.setCODIGO_PLANILLA(rs.getString(5));
                            encabezado.setID_PLANILLA_PARCIAL(rs.getString(6));
                            encabezado.setNOMBRE_BANCO(rs.getString(7));
                            encabezado.setID_PRE_CARGA(rs.getString(8));
                            encabezado.setNOMBRE_EST_PLANILLA(rs.getString(9));
                        }
                        lst.add(encabezado);
                    }
                } else {
                    System.out.println("Error al obtener la conexion");
                }
            } catch (SQLException ex) {
                //Mensaje.mensajeError("Ha ocurrido un error en la creación de la conexión. " + ex.getMessage());
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {

                    if (rs != null) {
                        rs.close();
                    }
                    if (pstm != null) {
                        pstm.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return lst;
        }
    }

    public SmaelEncabezadoDTO findEncabezadoByRequerimiento(String codDep, String no_requerimiento) {
        SmaelEncabezadoDTO encabezado = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            Connection conn = null;
            String sql = "select distinct TIPO_CONCEPTO_BONO.TIPO_CONCEPTO_BONO, "
                    + "    BONO.NOMBRE_BONO,    "
                    + "    FUENTE_FINANCIAMIENTO.NOMBRE,"
                    + "    REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO, "
                    + "    TRANSFERENCIAS.CODIGO_PLANILLA,"
                    + "    BANCOS.NOMBRE_BANCO, "
                    + "    TRANSFERENCIAS.ID_PRE_CARGA  "
                    + "from  TRANSFERENCIAS    "
                    + "    inner join REQUERIMIENTO_FONDOS on TRANSFERENCIAS.CODIGO_REQUERIMIENTO = REQUERIMIENTO_FONDOS.CODIGO_REQUERIMIENTO "
                    + "        and TRANSFERENCIAS.CODIGO_DEPARTAMENTO = REQUERIMIENTO_FONDOS.CODIGO_DEPARTAMENTO "
                    + "    inner join CUENTAS_PAGADURIA on CUENTAS_PAGADURIA.ID_CUENTA_PAGADURIA = REQUERIMIENTO_FONDOS.ID_CUENTA_PAGADURIA "
                    + "    inner join BANCOS on CUENTAS_PAGADURIA.ID_BANCO = BANCOS.ID_BANCO "
                    + "    inner join FUENTE_FINANCIAMIENTO on FUENTE_FINANCIAMIENTO.ID_FUENTE = REQUERIMIENTO_FONDOS.ID_FUENTE  "
                    + "    inner join BONO on BONO.ID_BONO = TRANSFERENCIAS.ID_BONO "
                    + "        and REQUERIMIENTO_FONDOS.ID_BONO = BONO.ID_BONO "
                    + "    inner join TIPO_CONCEPTO_BONO on BONO.ID_TIPO_CONCEPTO_BONO = TIPO_CONCEPTO_BONO.ID_TIPO_CONCEPTO_BONO "
                    + "WHERE  REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO='" + no_requerimiento + "'";
            DataSource origendatos = this.getOrigendatos(codDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    pstm = conn.prepareStatement(sql);
                    pstm.setString(1, no_requerimiento);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        encabezado = new SmaelEncabezadoDTO();
                        encabezado.setTIPO_CONCEPTO_BONO(rs.getString(1));
                        encabezado.setNOMBRE_BONO(rs.getString(2));
                        encabezado.setNOMBRE(rs.getString(3));
                        encabezado.setNO_REQUERIMIENTO(rs.getString(4));
                        encabezado.setCODIGO_PLANILLA(rs.getString(5));
                        encabezado.setID_PLANILLA_PARCIAL(rs.getString(6));
                        encabezado.setNOMBRE_BANCO(rs.getString(7));
                        encabezado.setID_PRE_CARGA(rs.getString(8));
                    }
                } else {
                    System.out.println("Error al obtener la conexion");
                }
            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {

                    if (rs != null) {
                        rs.close();
                    }
                    if (pstm != null) {
                        pstm.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return encabezado;
        }
    }

    private DataSource getOrigendatos(String codDep) throws ServletException {
        DataSource origendatos = null;
        String recurso = null;
        if (codDep.equals("01")) {
            recurso = "java:comp/env/jdbc/Smael01";
        }
        if (codDep.equals("02")) {
            recurso = "java:comp/env/jdbc/Smael02";
        }
        if (codDep.equals("03")) {
            recurso = "java:comp/env/jdbc/Smael03";
        }
        if (codDep.equals("04")) {
            recurso = "java:comp/env/jdbc/Smael04";
        }
        if (codDep.equals("05")) {
            recurso = "java:comp/env/jdbc/Smael05";
        }
        if (codDep.equals("06")) {
            recurso = "java:comp/env/jdbc/Smael06";
        }
        if (codDep.equals("07")) {
            recurso = "java:comp/env/jdbc/Smael07";
        }
        if (codDep.equals("08")) {
            recurso = "java:comp/env/jdbc/Smael08";
        }
        if (codDep.equals("09")) {
            recurso = "java:comp/env/jdbc/Smael09";
        }
        if (codDep.equals("10")) {
            recurso = "java:comp/env/jdbc/Smael10";
        }
        if (codDep.equals("11")) {
            recurso = "java:comp/env/jdbc/Smael11";
        }
        if (codDep.equals("12")) {
            recurso = "java:comp/env/jdbc/Smael12";
        }
        if (codDep.equals("13")) {
            recurso = "java:comp/env/jdbc/Smael13";
        }
        if (codDep.equals("14")) {
            recurso = "java:comp/env/jdbc/Smael14";
        }

        if (recurso != null) {
            try {
                Context ctx = new InitialContext();
                origendatos = (DataSource) ctx.lookup(recurso);
            } catch (Exception e) {
                throw new ServletException("Error al crear el datasource" + e);
            }
        } else {
            System.out.println("Error en codigo Departamento");
        }


        return origendatos;
    }

    public void modificarMontos(List<MontosEntidadSmaelDto> montos, String codigoPlanilla, String codDep, String idPlanillaParcial, List<DetPlaParChequesDTO> lstMontosCheques) {

        Connection conn = null;
        DataSource origendatos = null;
        PreparedStatement pstmTranferencia = null;
        PreparedStatement pstmDetTranferencia = null;
        PreparedStatement pstmDetPlaParCheques = null;
        try {
            origendatos = this.getOrigendatos(codDep);
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            synchronized (origendatos) {
                conn = origendatos.getConnection();
            }
            if (conn != null) {
                conn.setAutoCommit(false);
                Boolean continuar = false;

                pstmTranferencia = conn.prepareStatement(queryEstadoPlanilla);
                pstmTranferencia.setString(1, codigoPlanilla);
                pstmTranferencia.setString(2, codDep);
                ResultSet rs = pstmTranferencia.executeQuery();

                if (rs.next()) {
                    continuar = true;
                }

                if (continuar) {
                    pstmTranferencia = conn.prepareStatement(queryTrasferencias);
                    for (MontosEntidadSmaelDto montosEntidadSmaelDto : montos) {
                        pstmTranferencia.setString(1, montosEntidadSmaelDto.getMonto().toString());
                        pstmTranferencia.setString(2, codigoPlanilla);
                        pstmTranferencia.setString(3, montosEntidadSmaelDto.getCodigoEntidad());
                        pstmTranferencia.setString(4, codDep);
                        //Se Modifica TRANSFERENCIA
                        pstmTranferencia.executeUpdate();
                    }
                    //Se modifica DET_TRANSFERENCIA
                    pstmDetTranferencia = conn.prepareStatement(queryDetTransferencias);
                    pstmDetTranferencia.setString(1, codigoPlanilla);
                    pstmDetTranferencia.setString(2, idPlanillaParcial);
                    pstmDetTranferencia.executeUpdate();

                    //Se modifica DET_PLA_PAR_CHEQUES
                    pstmDetPlaParCheques = conn.prepareStatement(queryDetPlaParCheques);
                    for (DetPlaParChequesDTO detPlaParChequesDTO : lstMontosCheques) {
                        pstmDetPlaParCheques.setString(1, detPlaParChequesDTO.getMontoCheque().toString());
                        pstmDetPlaParCheques.setString(2, codigoPlanilla);
                        pstmDetPlaParCheques.setString(3, codDep);
                        pstmDetPlaParCheques.setString(4, idPlanillaParcial);
                        pstmDetPlaParCheques.setString(5, detPlaParChequesDTO.getIdBanco().toString());
                        pstmDetPlaParCheques.executeUpdate();
                    }
                    //JsfUtil.addSuccessMessage("Actualización completa");
                } else {
                    //JsfUtil.addErrorMessage("El estado de la planilla es invalido. Su estado debe de ser ELABORADA.");
                }
                conn.commit();
            } else {
                System.out.println("Error al obtener la conexion");
            }
        } catch (SQLException e) {
            //JsfUtil.addErrorMessage("Hubo un error en la actualización, favor reportarlo");
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.print("La transacción se deshace.");
                    conn.rollback();
                } catch (SQLException excep) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    if (pstmTranferencia != null) {
                        pstmTranferencia.close();
                    }
                    if (pstmDetTranferencia != null) {
                        pstmDetTranferencia.close();
                    }

                    if (pstmDetPlaParCheques != null) {
                        pstmDetPlaParCheques.close();
                    }

                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public List<Descriptor> getLstBancos(String codigoDep) {
        List<Descriptor> lst = new ArrayList<Descriptor>();
        try {
            Connection conn = null;

            DataSource origendatos = this.getOrigendatos(codigoDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    PreparedStatement pstm = conn.prepareStatement(queryBanco);
                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()) {
                        lst.add(new Descriptor(rs.getInt(1), rs.getString(2)));

                    }

                    //lstBancos = JsfUtil.getSelectItems(lst, false);

                    rs.close();
                    pstm.close();
                } else {
                    System.out.println("Error al obtener la conexion");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return lst;
        }
    }

    public List<Descriptor> getLstPlanillasByNoReq(String noRequerimiento, String codigoDep) {
        List<Descriptor> lst = new ArrayList<Descriptor>();
        try {
            Connection conn = null;
            String sql = "select distinct codigo_planilla, id_pre_carga "
                    + "from "
                    + "REQUERIMIENTO_FONDOS "
                    + "inner join TRANSFERENCIAS  on TRANSFERENCIAS.CODIGO_DEPARTAMENTO = REQUERIMIENTO_FONDOS.CODIGO_DEPARTAMENTO "
                    + "    and TRANSFERENCIAS.CODIGO_REQUERIMIENTO = REQUERIMIENTO_FONDOS.CODIGO_REQUERIMIENTO "
                    + "where REQUERIMIENTO_FONDOS.NO_REQUERIMIENTO='" + noRequerimiento + "' and CODIGO_PLANILLA is not null";
            DataSource origendatos = this.getOrigendatos(codigoDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()) {
                        lst.add(new Descriptor(Integer.parseInt(rs.getString(1).trim()), "PLANILLA " + rs.getString(1)));
                    }

                    //lstBancos = JsfUtil.getSelectItems(lst, false);

                    rs.close();
                    pstm.close();
                } else {
                    System.out.println("Error al obtener la conexion");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return lst;
        }
    }

    public String getCEscDeRequerimiento(String noRequerimiento, String codigoDep) {
        String codigos = "";
        try {
            Connection conn = null;

            DataSource origendatos = this.getOrigendatos(codigoDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    PreparedStatement pstm = conn.prepareStatement(requerimientoCodigoEntidad);
                    pstm.setString(1, noRequerimiento);
                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()) {
                        codigos = codigos.concat(rs.getString(1)).concat(",");
                    }

                    codigos = codigos.substring(0, codigos.length() - 1);

                    rs.close();
                    pstm.close();
                } else {
                    System.out.println("Error al obtener la conexion");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return codigos;
        }
    }

    public Double getMontoRecibo(String codigoRequerimiento, String codigoEntidad, String codigoDep) {
        Double montoRecibo = 0d;
        try {
            Connection conn = null;
            String sql = "select MONTO from TRANSFERENCIAS where CODIGO_REQUERIMIENTO='" + codigoRequerimiento + "' and CODIGO_ENTIDAD='" + codigoEntidad + "'";

            DataSource origendatos = this.getOrigendatos(codigoDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()) {
                        montoRecibo = rs.getDouble(1);
                    }
                    rs.close();
                    pstm.close();
                } else {
                    System.out.println("Error al obtener la conexion");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return montoRecibo;
        }
    }

    public String getNoCuentaCe(String codigoDep, String codRequerimiento, String codigoEntidad) {
        String noCuenta = "";
        try {
            Connection conn = null;

            DataSource origendatos = this.getOrigendatos(codigoDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    PreparedStatement pstm = conn.prepareStatement("select CUENTAS_BANCO.NUMERO_CUENTA "
                            + " from dbo.TRANSFERENCIAS inner join CUENTAS_BANCO on TRANSFERENCIAS.ID_CUENTA = CUENTAS_BANCO.ID_CUENTA "
                            + " where TRANSFERENCIAS.CODIGO_REQUERIMIENTO='" + codRequerimiento + "' and "
                            + " TRANSFERENCIAS.CODIGO_DEPARTAMENTO='" + codigoDep + "' and TRANSFERENCIAS.CODIGO_ENTIDAD='" + codigoEntidad + "'");
                    /*pstm.setString(1, codRequerimiento);
                     pstm.setString(2, codigoDep);
                     pstm.setString(3, codigoEntidad);*/

                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()) {
                        noCuenta = rs.getString(1);
                    }

                    rs.close();
                    pstm.close();
                } else {
                    System.out.println("Error al obtener la conexion");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return noCuenta;
        }
    }

    public BigDecimal getIdBancoCe(String codigoPlanilla, String codigoDep, String codigoEntidad) {
        int noCuenta = 0;
        try {
            Connection conn = null;

            DataSource origendatos = this.getOrigendatos(codigoDep);
            try {
                synchronized (origendatos) {
                    conn = origendatos.getConnection();
                }
                if (conn != null) {
                    PreparedStatement pstm = conn.prepareStatement("select CUENTAS_BANCO.ID_BANCO "
                            + " from dbo.TRANSFERENCIAS inner join CUENTAS_BANCO on TRANSFERENCIAS.ID_CUENTA = CUENTAS_BANCO.ID_CUENTA "
                            + " where TRANSFERENCIAS.CODIGO_PLANILLA = '" + codigoPlanilla + "' and TRANSFERENCIAS.CODIGO_DEPARTAMENTO = '" + codigoDep + "' and TRANSFERENCIAS.CODIGO_ENTIDAD = '" + codigoEntidad + "'");

                    ResultSet rs = pstm.executeQuery();

                    while (rs.next()) {
                        noCuenta = rs.getInt(1);
                    }

                    rs.close();
                    pstm.close();
                } else {
                    System.out.println("Error al obtener la conexion");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SmaelDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return new BigDecimal(noCuenta);
        }
    }
}
