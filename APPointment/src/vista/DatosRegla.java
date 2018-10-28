package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.time.DayOfWeek;
import javax.swing.SpinnerNumberModel;

public class DatosRegla extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Boolean modoEdicion;
	private Boolean cancelado;
	private int id;
	private JLabel lblProfesional;
	private JLabel lblPaciente;
	private JFormattedTextField ftfFechaInicio;
	private JFormattedTextField ftfFechaFin;
	private JComboBox cboRepiteDias;
	private JSpinner spRepiteCada;
	private JFormattedTextField ftfHora;
	private JCheckBox chkActiva;

	/**
	 * Create the dialog.
	 */
	public DatosRegla() 
	{
		setTitle("Regla de programacion de turnos");
		setModal(true);
		setBounds(100, 100, 619, 447);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Profesional");
		lblNewLabel.setBounds(15, 16, 133, 20);
		contentPanel.add(lblNewLabel);
		
		lblProfesional = new JLabel("lblProfesional");
		lblProfesional.setBounds(130, 16, 405, 20);
		contentPanel.add(lblProfesional);
		
		JLabel lblNewLabel2 = new JLabel("Paciente");
		lblNewLabel2.setBounds(15, 63, 69, 20);
		contentPanel.add(lblNewLabel2);
		
		lblPaciente = new JLabel("lblPaciente");
		lblPaciente.setBounds(130, 63, 452, 20);
		contentPanel.add(lblPaciente);
		
		JLabel lblFechaInicio = new JLabel("Fecha Inicio");
		lblFechaInicio.setBounds(15, 116, 107, 20);
		contentPanel.add(lblFechaInicio);
		
		try {
			ftfFechaInicio = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ftfFechaInicio.setBounds(130, 113, 141, 26);
		contentPanel.add(ftfFechaInicio);
		
		JLabel lblFechaFin = new JLabel("Fecha Fin");
		lblFechaFin.setBounds(15, 164, 69, 20);
		contentPanel.add(lblFechaFin);
		
		try {
			ftfFechaFin = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ftfFechaFin.setBounds(130, 160, 141, 26);
		contentPanel.add(ftfFechaFin);
		
		JLabel lblRepiteLosDias = new JLabel("Repite los dias");
		lblRepiteLosDias.setBounds(15, 218, 115, 20);
		contentPanel.add(lblRepiteLosDias);
		
		cboRepiteDias = new JComboBox();
		cboRepiteDias.setModel(new DefaultComboBoxModel(DayOfWeek.values()));
		cboRepiteDias.setBounds(130, 212, 141, 26);
		contentPanel.add(cboRepiteDias);
		
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setBounds(294, 218, 69, 20);
		contentPanel.add(lblHora);
		
		try {
			ftfHora = new JFormattedTextField(new MaskFormatter("##:##"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ftfHora.setBounds(345, 212, 69, 26);
		contentPanel.add(ftfHora);
		
		JLabel lblRepiteCada = new JLabel("Repite cada");
		lblRepiteCada.setBounds(15, 268, 107, 20);
		contentPanel.add(lblRepiteCada);
		
		spRepiteCada = new JSpinner();
		spRepiteCada.setModel(new SpinnerNumberModel(1, 1, 52, 1));
		spRepiteCada.setBounds(130, 265, 40, 23);
		contentPanel.add(spRepiteCada);
		
		JLabel lblSemanas = new JLabel("semana(s)");
		lblSemanas.setBounds(185, 268, 141, 20);
		contentPanel.add(lblSemanas);
		
		JLabel lblActiva = new JLabel("Activa");
		lblActiva.setBounds(15, 320, 69, 20);
		contentPanel.add(lblActiva);
		
		chkActiva = new JCheckBox("");
		chkActiva.setEnabled(false);
		chkActiva.setBounds(130, 311, 139, 29);
		contentPanel.add(chkActiva);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String msgErrorValidaciones = getValidacionesFallidas();
						if (msgErrorValidaciones.length() > 0)
							JOptionPane.showMessageDialog(null, msgErrorValidaciones);
						else
						{
							cancelado = false;
							setVisible(false);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						cancelado = true;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		modoEdicion = false;
		cancelado = true;
	}
	public void setId(int id)
	{
		this.setTitle("Modificar " + this.getTitle());
		this.modoEdicion = true;
		this.id = id;
		this.chkActiva.setEnabled(true);
	}
	public void setNombreProfesional(String nombre)
	{
		this.lblProfesional.setText(nombre);
	}
	public void setNombrePaciente(String nombre)
	{
		this.lblPaciente.setText(nombre);
	}
	public void setFechaInicio(Date fi)
	{
		ftfFechaInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(fi));
	}
	public void setFechaFin(Date ff)
	{
		if (ff != null) ftfFechaFin.setText(new SimpleDateFormat("dd/MM/yyyy").format(ff));
	}
	public void setRepiteDias(DayOfWeek d)
	{
		cboRepiteDias.setSelectedItem(d);
	}
	public void setHora(Date hora)
	{
		ftfHora.setText(new SimpleDateFormat("HH:mm").format(hora));
	}
	public void setRepiteCada(int r)
	{
		spRepiteCada.setValue(r);
	}
	public void setActiva(Boolean a)
	{
		chkActiva.setSelected(a);
	}
	private String getValidacionesFallidas()
	{
		StringBuilder msgError = new StringBuilder("");
		
		if (!ValidadorTexto.esFechaValida(ftfFechaInicio.getText())) msgError.append("* Debe ingresar una fecha de inicio valida.\n");
		String fechaFin = ftfFechaFin.getText().replace("/", "").trim();
		if (!fechaFin.isEmpty())
			if (!ValidadorTexto.esFechaValida(ftfFechaFin.getText())) msgError.append("* La fecha de fin no es valida.\n");
			
		if (!ValidadorTexto.esHoraValida(ftfHora.getText())) msgError.append("* Debe ingresar una hora valida.\n");
		return msgError.toString();
	}
	public Boolean getCancelado()
	{
		return cancelado;
	}
	public Date getFechaInicio() throws ParseException
	{
		return new SimpleDateFormat("dd/MM/yyyy").parse(ftfFechaInicio.getText());
	}
	public Date getFechaFin() throws ParseException
	{
		String fechaFin = ftfFechaFin.getText().replace("/", "").trim();
		if (!fechaFin.isEmpty())
			return new SimpleDateFormat("dd/MM/yyyy").parse(ftfFechaFin.getText());
		else return null;
	}
	public DayOfWeek getRepiteDias()
	{
		return (DayOfWeek)cboRepiteDias.getSelectedItem();
	}
	public Date getHora() throws ParseException
	{
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(ftfFechaInicio.getText()+" "+ftfHora.getText());
	}
	public int getRepiteCada()
	{
		return (Integer)spRepiteCada.getValue();
	}
	public Boolean getActiva()
	{
		return chkActiva.isSelected();
	}
	public int getId()
	{
		return id;
	}
}

