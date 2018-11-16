package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import controlador.AdminPacientes;
import controlador.AdminProfesionales;
import controlador.AdminTurnos;
import controlador.AlcanceCoberturaView;
import controlador.CoberturaView;
import controlador.ItemAdmisionView;
import controlador.PacienteView;
import controlador.PrestacionView;
import controlador.ProfesionalView;
import controlador.TurnoView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class DatosAdmision extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private TurnoView turno;
	private PacienteView paciente;
	private ProfesionalView profesional;
	private JComboBox<PrestacionView> cboServicios;
	private JComboBox<CoberturaView> cboCoberturas;
	private JRadioButton rdbtnObraSocial;
	private JRadioButton rdbtnParticular;
	private JTable tblAdmision;
	private JLabel lblAbonar;
	private JButton btnQuitar;
	private JButton okButton;
	private JLabel lblNombrepaciente;
	private JLabel lblNombreprofesional;
	private JLabel lblDiahoraturno;
	private JFormattedTextField ftfAbonado;
	private Boolean cancelado;
	private List<itemAdmision> itemsAdmision = new ArrayList<itemAdmision>();
	
	/**
	 * Create the dialog.
	 */
	public DatosAdmision(int idTurno) {
		setTitle("Admision");
		setModal(true);
		setBounds(100, 100, 753, 542);
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
		separator.setBounds(15, 124, 703, 2);
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
		
		rdbtnObraSocial = new JRadioButton("Obra Social");
		rdbtnObraSocial.setBounds(155, 185, 124, 29);
		contentPanel.add(rdbtnObraSocial);
		
		rdbtnParticular = new JRadioButton("Particular");
		rdbtnParticular.setBounds(155, 216, 128, 29);
		contentPanel.add(rdbtnParticular);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnObraSocial);
		group.add(rdbtnParticular);
		
		cboCoberturas = new JComboBox<CoberturaView>();
		cboCoberturas.setBounds(290, 186, 421, 26);
		contentPanel.add(cboCoberturas);
		
		tblAdmision = new JTable();
		tblAdmision.setBounds(15, 267, 709, 149);
		tblAdmision.setFillsViewportHeight(true);
		tblAdmision.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAdmision.setDefaultEditor(Object.class, null);
		tblAdmision.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(tblAdmision);
		scrollpane.add(tblAdmision.getTableHeader());
		scrollpane.setBounds(15, 266, 703, 132);
		tblAdmision.getTableHeader().setReorderingAllowed(false);
		tblAdmision.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (tblAdmision.getSelectedRow() != -1)
				{
					btnQuitar.setEnabled(true);
				}
				else
				{
					btnQuitar.setEnabled(false);
				}
			}
		});
		contentPanel.add(scrollpane);
		
		JButton btnAgregar = new JButton("+");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				agregarItem();
			}
		});
		btnAgregar.setBounds(622, 228, 45, 29);
		contentPanel.add(btnAgregar);
		
		JLabel lblImporteAAbonar = new JLabel("Importe a abonar:");
		lblImporteAAbonar.setBounds(15, 412, 141, 20);
		contentPanel.add(lblImporteAAbonar);
		
		lblAbonar = new JLabel("Abonar");
		lblAbonar.setBounds(171, 412, 128, 20);
		contentPanel.add(lblAbonar);
		
		JLabel lblImporteAbonado = new JLabel("Importe abonado:");
		lblImporteAbonado.setBounds(435, 412, 141, 20);
		contentPanel.add(lblImporteAbonado);
		
		ftfAbonado = new JFormattedTextField(new NumberFormatter());
		ftfAbonado.setBounds(591, 409, 125, 23);
		contentPanel.add(ftfAbonado);
		
		btnQuitar = new JButton("-");
		btnQuitar.setEnabled(false);
		btnQuitar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		    	eliminarItem((int)tblAdmision.getValueAt(tblAdmision.getSelectedRow(), 0));				
			}
		});
		btnQuitar.setBounds(672, 228, 45, 29);
		contentPanel.add(btnQuitar);
		
		lblNombrepaciente = new JLabel("NombrePaciente");
		lblNombrepaciente.setBounds(127, 16, 589, 20);
		contentPanel.add(lblNombrepaciente);
		
		lblNombreprofesional = new JLabel("NombreProfesional");
		lblNombreprofesional.setBounds(127, 52, 591, 20);
		contentPanel.add(lblNombreprofesional);
		
		lblDiahoraturno = new JLabel("DiaHoraTurno");
		lblDiahoraturno.setBounds(127, 88, 591, 20);
		contentPanel.add(lblDiahoraturno);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						if (!ValidadorTexto.esMonedaValida(ftfAbonado.getText()))
							JOptionPane.showMessageDialog(null, "El importe abonado no es valido.");
						else
						{
							cancelado = false;
							setVisible(false);
						}
					}
				});
				okButton.setEnabled(false);
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
		}
		inicializarFormulario(idTurno);
		cargarServicios();
		cargarCoberturas();
		setImporteAbonar(0);
		cancelado = true;
	}
	
	private void inicializarFormulario(int id)
	{
		try
		{
			this.turno = AdminTurnos.getInstancia().obtenerTurno(id);
			lblDiahoraturno.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(turno.getFechaHoraInicio()));
			
			this.profesional = AdminProfesionales.getInstancia().obtener(turno.getProfesional().getId());
			lblNombreprofesional.setText(turno.getProfesional().getNombre());
			
			this.paciente = AdminPacientes.getInstancia().obtener(turno.getPaciente().getId());
			lblNombrepaciente.setText(turno.getPaciente().getNombre());
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
	
	private void cargarCoberturas()
	{
		List<CoberturaView> coberturas = paciente.getCoberturas();
		if (coberturas.isEmpty())
		{
			cboCoberturas.setEnabled(false);
			rdbtnObraSocial.setEnabled(false);
			rdbtnParticular.setSelected(true);
		}
		else
		{
			CoberturaView[] arrCoberturas = coberturas.toArray(new CoberturaView[coberturas.size()]);
			cboCoberturas.setModel(new DefaultComboBoxModel<CoberturaView>(arrCoberturas));
			cboCoberturas.setSelectedIndex(0);
			rdbtnObraSocial.setSelected(true);
		}
	}
	
	public void agregarItem()
	{
		float abonar = 0;
		Boolean estaCubierto = false;
		PrestacionView prestacion = (PrestacionView)cboServicios.getSelectedItem();
		CoberturaView cobertura = null;
		if (rdbtnObraSocial.isSelected())
		{
			cobertura = (CoberturaView)cboCoberturas.getSelectedItem();
			List<AlcanceCoberturaView> alcance = cobertura.getPlan().getAlcanceCobertura();
			// Verificar si esta en el plan, y con que copago
			for (AlcanceCoberturaView a : alcance)
			{
				if (a.getServicio().getId() == prestacion.getIdServicio())
				{
					estaCubierto = true;
					abonar = a.getImporteCopago();
					break;
				}
			}
			if (!estaCubierto)
			{
				JOptionPane.showMessageDialog(null, "Esta prestacion no es cubierta por su plan. Abonar particular", "Atencion", JOptionPane.WARNING_MESSAGE);							
			}
		}
		else
		{
			abonar = prestacion.getImporteHonorarios();
		}
		if (rdbtnParticular.isSelected() || estaCubierto)
		{
			itemAdmision item = new itemAdmision(prestacion.getIdServicio(), prestacion, cobertura, abonar);
			if (itemsAdmision.contains(item))
			{
				JOptionPane.showMessageDialog(null, "Esta prestacion ya esta seleccionada", "Atencion", JOptionPane.INFORMATION_MESSAGE);											
			}
			else
			{
				itemsAdmision.add(item);
				llenarGrilla();
			}
		}
	}
	
	private void eliminarItem(int idServicio)
	{
		itemsAdmision.removeIf(e -> e.getIdServicio() == idServicio);
		llenarGrilla();
	}
	
	private void setImporteAbonar(float valor)
	{
		lblAbonar.setText(String.format("%.2f", valor));
	}
	
	public void llenarGrilla()
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[4];
		float abonar = 0;
		
		tblAdmision.removeAll();
		model.addColumn("ID");
		model.addColumn("Servicio");
		model.addColumn("Cobertura");
		model.addColumn("Abonar");

		for(itemAdmision item : this.itemsAdmision) 
		{
			fila[0] = item.getIdServicio();
			fila[1] = item.getPrestacion().getServicio();
			if (item.getCobertura() == null)
			{
				fila[2] = "";
			}
			else
			{
				fila[2] = item.getCobertura().getNumeroCredencial();
			}
			fila[3] = item.getImporteAbonar();
			abonar += item.getImporteAbonar();
			model.addRow(fila);
		}
		setImporteAbonar(abonar);
		tblAdmision.setModel(model);
		tblAdmision.getColumnModel().getColumn(0).setWidth(0);
		tblAdmision.getColumnModel().getColumn(0).setMinWidth(0);
		tblAdmision.getColumnModel().getColumn(0).setMaxWidth(0);
		tblAdmision.validate();
		okButton.setEnabled((tblAdmision.getRowCount()>0));
	}
	
	public Boolean getCancelado() 
	{
		return cancelado;
	}
	
	public List<ItemAdmisionView> getItemsAdmision()
	{
		List<ItemAdmisionView> dataAdmision = new ArrayList<ItemAdmisionView>();
		for(itemAdmision item : this.itemsAdmision)
		{
			Integer idCobertura;
			if (item.getCobertura() != null)
			{
				idCobertura = item.getCobertura().getId();
			}
			else
			{
				idCobertura = null;
			}
			dataAdmision.add(new ItemAdmisionView(item.getIdServicio(), idCobertura));
		}
		return dataAdmision;
	}
	
	public float getImporteAbonado()
	{
		return Float.parseFloat(ftfAbonado.getText());
	}
}

class itemAdmision
{
	private int idServicio;
	private PrestacionView prestacion;
	private CoberturaView cobertura;
	private float importeAbonar;
	
	public itemAdmision(int idServicio, PrestacionView prestacion, CoberturaView cobertura, float importeAbonar) {
		this.idServicio = idServicio;
		this.prestacion = prestacion;
		this.cobertura = cobertura;
		this.importeAbonar = importeAbonar;
	}
	public PrestacionView getPrestacion() {
		return prestacion;
	}
	public CoberturaView getCobertura() {
		return cobertura;
	}
	public int getIdServicio()
	{
		return idServicio;
	}
	public float getImporteAbonar()
	{
		return importeAbonar;
	}
	@Override
	public boolean equals(Object arg0) {
		return (idServicio == ((itemAdmision)arg0).getIdServicio());
	}
}