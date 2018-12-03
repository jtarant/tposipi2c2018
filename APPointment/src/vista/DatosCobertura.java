package vista;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AdminObrasSociales;
import controlador.CoberturaView;
import controlador.IdNombreView;
import controlador.PlanView;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class DatosCobertura extends JDialog {

	private JPanel contentPane;
	private JTextField txNumeroSocio;
	private JComboBox<IdNombreView> cboObraSocial;
	private JComboBox<IdNombreView> cboPlan;
	private CoberturaView cobertura;
	private IdNombreView idNombreObraSocial;
	private IdNombreView idNombrePlan;
	private PlanView plan;
	private Boolean cancelado;
	private JCheckBox checkBoxPrimaria;

	/**
	 * Create the frame.
	 */
	public DatosCobertura() {
		setTitle("Cobertura m\u00E9dica");
		setModal(true);
		setBounds(100, 100, 434, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblObraSocial = new JLabel("Obra Social");
		lblObraSocial.setBounds(15, 27, 124, 14);
		
		JLabel lblPlan = new JLabel("Plan");
		lblPlan.setBounds(15, 58, 124, 14);
		
		JLabel lblNmeroDeSocio = new JLabel("N\u00FAmero de socio");
		lblNmeroDeSocio.setBounds(15, 89, 124, 14);
		
		JLabel lblPrimaria = new JLabel("Primaria");
		lblPrimaria.setBounds(15, 117, 38, 14);
		
		cboObraSocial = new JComboBox<IdNombreView>();
		cboObraSocial.setBounds(156, 24, 218, 20);
		cboObraSocial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<IdNombreView> comboOS = (JComboBox<IdNombreView>)e.getSource();
				idNombreObraSocial = (IdNombreView)comboOS.getSelectedItem();
				cargarPlanes();
			}
		});
		
		
		cboPlan = new JComboBox<IdNombreView>();
		cboPlan.setBounds(156, 55, 218, 20);
		cboPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<IdNombreView> comboOS = (JComboBox<IdNombreView>)e.getSource();
				idNombrePlan = (IdNombreView)comboOS.getSelectedItem();
			}
		});
		
		txNumeroSocio = new JTextField();
		txNumeroSocio.setBounds(156, 86, 218, 20);
		txNumeroSocio.setColumns(19);
		
		checkBoxPrimaria = new JCheckBox("");
		checkBoxPrimaria.setBounds(118, 117, 21, 21);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("Cancel");
		btnCancelar.setBounds(299, 172, 98, 23);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelado = true;
				setVisible(false);
			}
		});
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand("OK");
		btnAceptar.setBounds(196, 172, 93, 23);
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				if (txNumeroSocio.getText().trim().length() == 0)
					JOptionPane.showMessageDialog(null, "* Debe indicar el numero de credencial");
				else
				{
					cancelado = false;
					setVisible(false);
				}
			}
		});
		getRootPane().setDefaultButton(btnAceptar);
		contentPane.setLayout(null);
		contentPane.add(lblObraSocial);
		contentPane.add(lblPlan);
		contentPane.add(lblNmeroDeSocio);
		contentPane.add(lblPrimaria);
		contentPane.add(checkBoxPrimaria);
		contentPane.add(txNumeroSocio);
		contentPane.add(cboPlan);
		contentPane.add(cboObraSocial);
		contentPane.add(btnAceptar);
		contentPane.add(btnCancelar);
	
		this.cargarCoberturas();
		cancelado = true;
	}
	

	private void cargarCoberturas()
	{
		try
		{
			List<IdNombreView> coberturas = AdminObrasSociales.getInstancia().listarPorIdNombre();
			IdNombreView[] arrCoberturas = coberturas.toArray(new IdNombreView[coberturas.size()]);
			cboObraSocial.setModel(new DefaultComboBoxModel<IdNombreView>(arrCoberturas));
			cboObraSocial.setSelectedIndex(0);		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar las obras sociales:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	private void cargarPlanes()
	{
		try
		{
			List<IdNombreView> planes = AdminObrasSociales.getInstancia().listarPlanesPorIdNombre(idNombreObraSocial.getId());
			IdNombreView[] arrPlanes = planes.toArray(new IdNombreView[planes.size()]);
			cboPlan.setModel(new DefaultComboBoxModel<IdNombreView>(arrPlanes));
			cboPlan.setSelectedIndex(0);		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los planes asociados a una obra social:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	public Boolean getCancelado()
	{
		return cancelado;
	}

	public String getNumeroSocio() {
		return txNumeroSocio.getText().trim();
	}
	
	public IdNombreView getIdNombreObraSocial() {
		return idNombreObraSocial;
	}

	public IdNombreView getIdNombrePlan() {
		return idNombrePlan;
	}

	public JCheckBox getPrimaria() {
		return checkBoxPrimaria;
	}
}
