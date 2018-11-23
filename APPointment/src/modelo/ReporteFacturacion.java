package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import controlador.ItemReporteFacturacionView;
import persistencia.AdmPersistenciaTurnos;

public class ReporteFacturacion 
{
	private String nombreArchivo;
	private int mes;
	private int anio;
	private Profesional profesional;
	private ObraSocial obraSocial;
	private Paciente paciente;
	private List<ItemReporteFacturacionView> items;
	private Workbook workbook;

	public ReporteFacturacion(String nombreArchivo, int mes, int anio, Profesional profesional, ObraSocial os,
			Paciente paciente) throws Exception {
		this.nombreArchivo = nombreArchivo;
		this.mes = mes;
		this.anio = anio;
		this.profesional = profesional;
		this.obraSocial = os;
		this.paciente = paciente;
		
		Integer idPaciente = (paciente != null)? paciente.getId(): null;
		items = AdmPersistenciaTurnos.getInstancia().obtenerReporteFacturacion(mes, anio, profesional.getId(), os.getId(), idPaciente);
		if (items.size() == 0)
			throw new ExceptionDeNegocio("No hay admisiones para los parametros seleccionados.");

		workbook = new XSSFWorkbook();
		generarCaratula();
		generarDetalle();
	}
	
	private void generarCaratula()
	{
		Sheet hoja1 = workbook.createSheet("Caratula");
		Row fila1 = hoja1.createRow(0);
		fila1.createCell(0).setCellValue("Periodo");
		fila1.createCell(1).setCellValue(Integer.toString(mes) + "/" + Integer.toString(anio));
		Row fila2 = hoja1.createRow(1);
		fila2.createCell(0).setCellValue("Obra Social");
		fila2.createCell(1).setCellValue(obraSocial.getNombre());
		Row fila3 = hoja1.createRow(2);
		fila3.createCell(0).setCellValue("Profesional");
		fila3.createCell(1).setCellValue(profesional.getApellido() + ", " + profesional.getNombre());
		Row fila4 = hoja1.createRow(3);
		fila4.createCell(0).setCellValue("Especialidad");
		fila4.createCell(1).setCellValue(profesional.getEspecialidad());
		Row fila5 = hoja1.createRow(4);
		fila5.createCell(0).setCellValue("Fecha reporte");
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		fila5.createCell(1).setCellValue(dtFormatter.format(LocalDateTime.now()));
		hoja1.autoSizeColumn(0);
	}
	
	private void generarDetalle()
	{
		Sheet hoja2 = workbook.createSheet("Facturacion");
		CreationHelper createHelper = workbook.getCreationHelper();
		
		Row titulosHoja2 = hoja2.createRow(0);
		titulosHoja2.createCell(0).setCellValue("Fecha");
		titulosHoja2.createCell(1).setCellValue("Hora Inicio");
		titulosHoja2.createCell(2).setCellValue("Hora Fin");
		titulosHoja2.createCell(3).setCellValue("Apellido");
		titulosHoja2.createCell(4).setCellValue("Nombre");
		titulosHoja2.createCell(5).setCellValue("Credencial");
		titulosHoja2.createCell(6).setCellValue("Practica Medica");
		titulosHoja2.createCell(7).setCellValue("Descripcion");
		CellStyle estiloTitulo = workbook.createCellStyle();
		estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estiloTitulo.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		Font fontTitulo = workbook.createFont();
		fontTitulo.setBold(true);
		fontTitulo.setColor(IndexedColors.WHITE.getIndex());
		estiloTitulo.setFont(fontTitulo);
		for (int i = 0; i < 8; i++) {
			titulosHoja2.getCell(i).setCellStyle(estiloTitulo);
		}
		
		int i = 1;
		Row filaHoja2;
		for (ItemReporteFacturacionView item : items)
		{
			filaHoja2 = hoja2.createRow(i);
			filaHoja2.createCell(0).setCellValue(item.getFechaHoraInicio());
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			filaHoja2.getCell(0).setCellStyle(cellStyle);
			filaHoja2.createCell(1).setCellValue(new SimpleDateFormat("HH:mm").format(item.getFechaHoraFin()));
			filaHoja2.createCell(2).setCellValue(new SimpleDateFormat("HH:mm").format(item.getFechaHoraFin()));
			filaHoja2.createCell(3).setCellValue(item.getApellido());
			filaHoja2.createCell(4).setCellValue(item.getNombre());
			filaHoja2.createCell(5).setCellValue(item.getNroCredencial().trim());
			filaHoja2.createCell(6).setCellValue(item.getCodigoPractica());
			filaHoja2.createCell(7).setCellValue(item.getDescripcionPractica());
			i++;
		}
		
		for (int i2 = 0; i2 < 8; i2++) {
			hoja2.autoSizeColumn(i2);
		}
	}
	

	public void guardar() throws IOException
	{
		FileOutputStream xlsx = null;
		try
		{
			xlsx = new FileOutputStream(nombreArchivo);
			workbook.write(xlsx);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (xlsx != null) xlsx.close();
		}		
	}
}
