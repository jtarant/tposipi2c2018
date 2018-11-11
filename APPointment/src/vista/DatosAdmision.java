package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdminPacientes;
import controlador.AdminProfesionales;
import controlador.AdminTurnos;
import controlador.IdNombreView;
import controlador.PacienteView;
import controlador.PrestacionView;
import controlador.ProfesionalView;
import controlador.TurnoView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class DatosAdmision extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private TurnoView turno;
	private PacienteView paciente;
	private ProfesionalView profesional;
	private JComboBox<PrestacionView> cboServicios;

	/**
	 * Create the dialog.
	 */
	public DatosAdmision(int idTurno) {
		setTitle("Admision");
		setModal(true);
		setBounds(100, 100, 877, 527);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblPaciente = new JLabel("Paciente");
		lblPaciente.setBounds(15, 16, 69, 20);
		contentPanel.add(lblPaciente);
		
		JLabel lblProfesional = new JLabel("Profesional");
		lblProfesional.setBounds(15, 52, 104, 20);
		contentPanel.add(lblProfesional);
		
		JLabel lblTurno = new JLabel("Turno");
		lblTurno.setBounds(15, 88, 69, 20);
		contentPanel.add(lblTurno);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 124, 801, 2);
		contentPanel.add(separator);
		
		JLabel lblServicioARealizar = new JLabel("Servicio a realizar:");
		lblServicioARealizar.setBounds(15, 142, 150, 20);
		contentPanel.add(lblServicioARealizar);
		
		cboServicios = new JComboBox<PrestacionView>();
		cboServicios.setBounds(160, 139, 551, 23);
		contentPanel.add(cboServicios);
		
		JLabel lblCobertura = new JLabel("Cobertura");
		lblCobertura.setBounds(15, 189, 128, 20);
		contentPanel.add(lblCobertura);
		
		JRadioButton rdbtnObraSocial = new JRadioButton("Obra Social");
		rdbtnObraSocial.setBounds(155, 185, 124, 29);
		contentPanel.add(rdbtnObraSocial);
		
		JRadioButton rdbtnParticular = new JRadioButton("Particular");
		rdbtnParticular.setBounds(155, 216, 128, 29);
		contentPanel.add(rdbtnParticular);
		
		JComboBox cboCoberturas = new JComboBox();
		cboCoberturas.setBounds(290, 186, 421, 26);
		contentPanel.add(cboCoberturas);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		inicializarFormulario(idTurno);
		cargarServicios();
	}
	
	private void inicializarFormulario(int id)
	{
		try
		{
			this.turno = AdminTurnos.getInstancia().obtenerTurno(id);
			this.profesional = AdminProfesionales.getInstancia().obtener(turno.getProfesional().getId());
			this.paciente = AdminPacientes.getInstancia().obtener(turno.getPaciente().getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar el turno, profesional o paciente:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	private void cargarServicios()
	{
		try
		{
			List<PrestacionView> prestaciones = profesional.getPrestaciones();
			PrestacionView[] arrPrestaciones = prestaciones.toArray(new PrestacionView[prestaciones.size()]);
			cboServicios.setModel(new DefaultComboBoxModel<PrestacionView>(arrPrestaciones));
			cboServicios.setSelectedIndex(0);		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los servicios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
