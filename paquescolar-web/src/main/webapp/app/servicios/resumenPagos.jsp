<%@page import="com.google.gson.Gson"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="sv.gob.mined.paquescolar.model.pojos.ResumenPagoJson"%>
<%@page import="java.util.List"%>
<%@page import="sv.gob.mined.paquescolar.ejb.ServiciosJsonEJB"%>
<%
    ServiciosJsonEJB serviciosEJB = new ServiciosJsonEJB();
    serviciosEJB = (ServiciosJsonEJB) new InitialContext().lookup("java:module/ServiciosJsonEJB");
    String codigoDepa = (String) request.getParameter("codigoDepa");
    String idDetProcesoAdq = (String) request.getParameter("idDetProcesoAdq");
    Gson gson = new Gson();
    String usuarios = gson.toJson(serviciosEJB.getResumenPagoJsonByDepaAndDetProcesoAdq(codigoDepa, Integer.parseInt(idDetProcesoAdq)));
    out.println(usuarios);%>