<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.Collection" %>
<%@ page import="it.unisa.storage.model.*" %>
<%@ page import="it.unisa.storage.dao.IndirizzoDaoImpl" %>

<%
String risultato = "";

try {

    DataSource ds =
    (DataSource)application.getAttribute("DataSource");

    IndirizzoDaoImpl dao = new IndirizzoDaoImpl(ds);

    IndirizzoBean m = new IndirizzoBean();

    m.setCAP("5090");
    m.setCitta("");
    m.setCivico("");
    m.setProvincia("");
    m.setVia("");

    dao.doSave(m);

    
    risultato = "OK";

} catch(Exception e) {

    e.printStackTrace();

    risultato = e.toString(); 
}%>

<h1><%= risultato %></h1>