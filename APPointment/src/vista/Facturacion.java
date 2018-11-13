package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdminObrasSociales;
import controlador.AdminProfesionales;
import controlador.IdNombreView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionEvent;

public class Facturacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private BuscarPacientePanel pnlBuscarPaciente;
	private JComboBox<IdNombreView> cboProfesionales;
	private JComboBox<IdNombreView> cboObrasSociales;
	private IdNombreView idNombreProfesional;
	private IdNombreView idNombreObraSocial;
	
	/**
	 * Create the dialog.
	 */
	public Facturacion() {
		setTitle("Generar reporte de facturacion");
		setBounds(100, 100, 816, 439);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblMesao = new JLabel("Mes/a\u00F1o");
		lblMesao.setBounds(10, 21, 88, 14);
		contentPanel.add(lblMesao);
		
		JSpinner spMes = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
		spMes.setBounds(118, 18, 46, 20);
		contentPanel.add(spMes);
		
		JSpinner spAnio = new JSpinner(new SpinnerNumberModel(2018, 2018, 2030, 1));
		spAnio.setBounds(169, 18, 71, 20);
		contentPanel.add(spAnio);
		
		JLabel lblProfesional = new JLabel("Profesional:");
		lblProfesional.setBounds(10, 59, 88, 14);
		contentPanel.add(lblProfesional);
		
		cboProfesionales = new JComboBox<IdNombreView>();
        cboProfesionales.addActionListener(new ActionListener() {
        	@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
        		JComboBox<IdNombreView> combo = (JComboBox<IdNombreView>)e.getSource();
        		
        		idNombreProfesional = (IdNombreView)combo.getSelectedItem();
        	}
        });
		cboProfesionales.setBounds(118, 56, 289, 20);
		contentPanel.add(cboProfesionales);
		
		pnlBuscarPaciente = new BuscarPacientePanel();
		pnlBuscarPaciente.setBounds(118, 134, 670, 220);
		contentPanel.add(pnlBuscarPaciente);
				
		JLabel lblObraSocial = new JLabel("Obra Social:");
		lblObraSocial.setBounds(10, 99, 88, 14);
		contentPanel.add(lblObraSocial);
		
		cboObrasSociales = new JComboBox<IdNombreView>();
		cboObrasSociales.addActionListener(new ActionListener() {
        	@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
        		JComboBox<IdNombreView> combo = (JComboBox<IdNombreView>)e.getSource();
        		
        		idNombreObraSocial = (IdNombreView)combo.getSelectedItem();
        	}
        });
		cboObrasSociales.setBounds(118, 96, 289, 20);
		contentPanel.add(cboObrasSociales);
		
		JLabel lblPacienteopcional = new JLabel("Paciente (opcional):");
		lblPacienteopcional.setBounds(10, 134, 106, 26);
		contentPanel.add(lblPacienteopcional);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Generar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
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
		spMes.setValue(LocalDate.now().getMonthValue());
		spAnio.setValue(LocalDate.now().getYear());
		this.cargarProfesionales();
		this.cargarObrasSociales();
	}
	
	private void cargarProfesionales()
	{
		try
		{
			List<IdNombreView> profs = AdminProfesionales.getInstancia().listarPorIdNombre();
			IdNombreView[] items = profs.toArray(new IdNombreView[profs.size()]);
			cboProfesionales.setModel(new DefaultComboBoxModel<IdNombreView>(items));
			cboProfesionales.setSelectedIndex(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los profesionales:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}				
	}

	private void cargarObrasSociales()
	{
		try
		{
			List<IdNombreView> os = AdminObrasSociales.getInstancia().listarPorIdNombre();
			IdNombreView[] items = os.toArray(new IdNombreView[os.size()]);
			cboObrasSociales.setModel(new DefaultComboBoxModel<IdNombreView>(items));
			cboObrasSociales.setSelectedIndex(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las obras sociales:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}				
	}	
}
