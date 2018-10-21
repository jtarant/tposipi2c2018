package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatosTurno extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private BuscarPacientePanel pnlBuscarPaciente;
	private JLabel lblNombreprofesional;
	private JFormattedTextField ftfHora;
	private JFormattedTextField ftfFecha;
	private Boolean cancelado;

	/**
	 * Create the dialog.
	 */
	public DatosTurno() {
		setTitle("Turno");
		setModal(true);
		setBounds(100, 100, 839, 463);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			pnlBuscarPaciente = new BuscarPacientePanel();
			pnlBuscarPaciente.setBounds(130, 130, 673, 228);
			contentPanel.add(pnlBuscarPaciente);
		}
		
		JLabel lblProfesional = new JLabel("Profesional");
		lblProfesional.setBounds(15, 16, 100, 20);
		contentPanel.add(lblProfesional);
		
		lblNombreprofesional = new JLabel("NombreProfesional");
		lblNombreprofesional.setBounds(130, 16, 398, 20);
		contentPanel.add(lblNombreprofesional);
		
		JLabel lblHora = new JLabel("Hora");
		lblHora.setBounds(15, 91, 69, 20);
		contentPanel.add(lblHora);

		try {
			ftfHora = new JFormattedTextField(new MaskFormatter("##:##"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ftfHora.setBounds(130, 88, 69, 26);
		contentPanel.add(ftfHora);
		
		JLabel lblPaciente = new JLabel("Paciente");
		lblPaciente.setBounds(15, 130, 69, 20);
		contentPanel.add(lblPaciente);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(15, 52, 69, 20);
		contentPanel.add(lblFecha);
		
		try {
			ftfFecha = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ftfFecha.setBounds(130, 49, 136, 26);
		contentPanel.add(ftfFecha);

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
				public void actionPerformed(ActionEvent e) {
					cancelado = true;
					setVisible(false);
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
		cancelado = true;
	}
	
	private String getValidacionesFallidas()
	{
		StringBuilder msgError = new StringBuilder("");
		
		if (!ValidadorTexto.esFechaValida(ftfFecha.getText())) msgError.append("* Debe ingresar una fecha valida.\n");
		if (!ValidadorTexto.esHoraValida(ftfHora.getText())) msgError.append("* Debe ingresar una hora valida.\n");
		if (!pnlBuscarPaciente.isPacienteSeleccionado()) msgError.append("* Debe seleccionar un paciente.\n");
		return msgError.toString();
	}
	
	public void setNombreProfesional(String nombre)
	{
		lblNombreprofesional.setText(nombre);
	}
	public void setHora(Date hora)
	{
		ftfHora.setText(new SimpleDateFormat("HH:mm").format(hora));
	}
	public void setFecha(Date fecha)
	{
		ftfFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(fecha));
	}
	public int getIDPaciente()
	{
		return pnlBuscarPaciente.getIDSeleccionado();
	}
	public String getApellido()
	{
		return pnlBuscarPaciente.getApellido();
	}
	public String getNombre()
	{
		return pnlBuscarPaciente.getNombre();
	}
	public Integer getDNI()
	{
		return pnlBuscarPaciente.getDNI();
	}
	public Date getFechaHora() throws ParseException
	{
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(ftfFecha.getText()+" "+ftfHora.getText());
	}
	public Boolean getCancelado() 
	{
		return cancelado;
	}
}
