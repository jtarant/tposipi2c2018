<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>APPointment</title>
</head>
<body>
<table cellspacing="0" cellpadding="44" bgcolor="#A9A9A9" border="0" width="50%" align="center">
 <tbody><tr><td>
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
   <tbody>
    <tr>
     <td bgcolor="#d84938">
      <div style="font-family:Arial;font-size:14px;padding:8px 14px">
       <div style="color:#ffffff;width:310px;margin:0;padding:0;padding:1px 0">
        <strong>CONFIRMACION CANCELACION DE TURNO</strong>
       </div>
      </div>
     </td>
    </tr>	
    <tr>
    <td>
     <br>
     <table bgcolor="#ECEBEB" cellspacing="0" cellpadding="0" border="0" width="100%">
      <tbody>
       <tr>
        <td valign="top" style="padding-left:5%">
         <div style="font-family:Arial;font-size:14px">
          <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           <strong>Paciente: </strong> <span style="color:#978474">${paciente}</span>
          </div>
          <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           <strong>Profesional: </strong> <span style="color:#978474">${profesional}</span>
          </div>
		  <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           <strong>Fecha - Hora: </strong> <span style="color:#978474">${fechaHora}</span>
          </div>
          <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           <strong>Consultorio: </strong> <span style="color:#978474">${consultorio}</span>
          </div>
          <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           	 <strong>Dirección: </strong> <span style="color:#978474">${direccion}</span>
          </div>
          <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           <strong>Teléfono: </strong> <span style="color:#978474">${telefono}</span>
          </div>
          <div style="font-family:Arial;font-size:14px;width:310px;margin:0;padding:0;padding:3px 0">
           <strong>Email: </strong> <span style="color:#978474"><a href="mailto:${mail}" target="_blank"> ${mail} </a></span>
          </div>
         </div>
        </td>
        <td>
        </td>
       </tr>
      </tbody>
     </table>
	 <br>
	 <table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tbody>
       <tr>
        <td align="center">
         <div style="color:#ffffff;width:310px;margin:0;padding:0;padding:1px 0">
          <a style="text-align:center;font-family:Arial;font-size:14px;padding:10px 5px;background-color:rgb(216,73,56);margin-left:10px;margin-right:10px;margin-bottom:10px;color:white;display:block" href="/APPointmentWeb/Cancelar?id=${id}" >Cancelar Turno</a>
         </div>
        </td>
       </tr>
      </tbody>
     </table>
     </td></tr>
    </tbody></table>
   </td></tr>
  </tbody></table>
</body>
</html>