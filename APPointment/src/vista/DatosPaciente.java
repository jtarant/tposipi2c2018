package vista;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controlador.AdminPacientes;
import controlador.CoberturaView;
import controlador.ItemCoberturaView;
import controlador.PacienteView;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class DatosPaciente extends JDialog {
	private Boolean modoEdicion;
	private Boolean cancelado;
	private JTextField txNombre;
	private JTextField txApellido;
	private JTextField txDNI;
	private JTextField txTelefono;
	private JTextField txEmail;
	private JFormattedTextField frTxFechaNac;
	private JTable table;
	private Hashtable<String,ItemCoberturaView> coberturas = new Hashtable<String,ItemCoberturaView>();
	private JCheckBox cbActivo;
	private JButton btnQuitar;
	private int idPaciente;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public DatosPaciente() {
		setResizable(false);
		setModal(true);
		setTitle("Paciente");
		setBounds(100, 100, 511, 470);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 23, 128, 14);
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(10, 54, 128, 14);
		JLabel lblNewLabel = new JLabel("DNI");
		lblNewLabel.setBounds(10, 85, 128, 14);
		JLabel lblNewLabel_1 = new JLabel("Fecha nacimiento");
		lblNewLabel_1.setBounds(10, 116, 112, 14);
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(10, 147, 121, 14);
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 178, 121, 14);
		JLabel lblActivo = new JLabel("Activo");
		lblActivo.setBounds(10, 213, 112, 14);
		JLabel lblCoberturaMedica = new JLabel("Cobertura Medica");
		lblCoberturaMedica.setBounds(10, 249, 128, 14);
		
		txNombre = new JTextField();
		txNombre.setBounds(145, 20, 249, 20);
		txNombre.setColumns(50);
		
		txApellido = new JTextField();
		txApellido.setBounds(145, 51, 249, 20);
		txApellido.setColumns(50);
		
		txDNI = new JTextField();
		txDNI.setBounds(145, 82, 249, 20);
		txDNI.setColumns(10);
		
		txTelefono = new JTextField();
		txTelefono.setBounds(145, 144, 249, 20);
		txTelefono.setColumns(15);
		
		txEmail = new JTextField();
		txEmail.setBounds(145, 175, 249, 20);
		txEmail.setColumns(254);
		
		cbActivo = new JCheckBox("");
		cbActivo.setSelected(true);
		cbActivo.setEnabled(false);
		cbActivo.setBounds(145, 213, 21, 21);
		
		JButton btnNewButton = new JButton("+");
		btnNewButton.setBounds(394, 245, 49, 23);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				agregarCobertura();
			}
		});
		
		try {
			frTxFechaNac = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		frTxFechaNac.setBounds(145, 113, 249, 20);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(303, 393, 86, 23);
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
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
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(394, 393, 95, 23);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelado = true;
				setVisible(false);
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(lblNombre);
		getContentPane().add(lblApellido);
		getContentPane().add(lblNewLabel);
		getContentPane().add(lblTelefono);
		getContentPane().add(lblEmail);
		getContentPane().add(lblActivo);
		getContentPane().add(lblCoberturaMedica);
		getContentPane().add(lblNewLabel_1);
		getContentPane().add(cbActivo);
		getContentPane().add(txTelefono);
		getContentPane().add(frTxFechaNac);
		getContentPane().add(txDNI);
		getContentPane().add(txApellido);
		getContentPane().add(txEmail);
		getContentPane().add(txNombre);
		getContentPane().add(btnNewButton);
		getContentPane().add(btnAceptar);
		getContentPane().add(btnCancelar);
		
		table = new JTable();
		table.setBounds(15, 274, 474, 108);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(table);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mntmEstablecerComoPrimaria = new JMenuItem("Establecer como primaria");
		mntmEstablecerComoPrimaria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				establecerPrimaria(table.getValueAt(table.getSelectedRow(), 3).toString());					
			}
		});
		popupMenu.add(mntmEstablecerComoPrimaria);
		scrollpane.add(table.getTableHeader());
		scrollpane.setBounds(15, 274, 474, 108);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() != -1)
				{
					btnQuitar.setEnabled(true);
				}
				else
				{
					btnQuitar.setEnabled(false);
				}
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		getContentPane().add(scrollpane);

		btnQuitar = new JButton("-");
		btnQuitar.setEnabled(false);
		btnQuitar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eliminarItem(table.getValueAt(table.getSelectedRow(), 3).toString());	
			}
		});
		btnQuitar.setBounds(448, 245, 41, 23);
		getContentPane().add(btnQuitar);
		llenarGrilla();
		cancelado = true;
	}

	public void setPaciente(PacienteView paciente)
	{
		this.setTitle("Modificar " + this.getTitle());
		this.modoEdicion = true;
		this.idPaciente = paciente.getId();
		this.cbActivo.setEnabled(true);
		this.txApellido.setText(paciente.getApellido());
		this.txNombre.setText(paciente.getNombre());
		this.txDNI.setText(paciente.getDNI() != null ? Integer.toString(paciente.getDNI()): "");
		this.frTxFechaNac.setText(new SimpleDateFormat("dd/MM/yyyy").format(paciente.getFechaNacimiento()));
		this.txTelefono.setText(paciente.getTelefono());
		this.txEmail.setText(paciente.getEmail());
		this.cbActivo.setSelected(paciente.getActivo());
		
		for (CoberturaView cv : paciente.getCoberturas())
		{
			ItemCoberturaView icv = new ItemCoberturaView(cv.getId(), cv.getPlan().getId(), cv.getNumeroCredencial(), cv.getPrimaria());
			icv.setNombrePlan(cv.getPlan().getNombre());
			icv.setNombreObraSocial(cv.getPlan().getObraSocial().getNombre());
			this.coberturas.put(icv.getNroCredencial(), icv);
		}
		llenarGrilla();
	}
	
	private String getValidacionesFallidas()
	{
		StringBuilder msgError = new StringBuilder("");
		
		if ((txNombre.getText().trim().isEmpty()) || (txApellido.getText().trim().isEmpty()))  msgError.append("* Debe ingresar nombre y apellido.\n");
		
		if (!txDNI.getText().isEmpty())
			if (!ValidadorTexto.esEnteroValido(txDNI.getText())) msgError.append("* El DNI ingresado no es válido.\n");
			
		if (!ValidadorTexto.esFechaValida(frTxFechaNac.getText())) msgError.append("* Debe ingresar una fecha de nacimiento valida.\n");
		
		if (txTelefono.getText().trim().isEmpty()) msgError.append("* Debe ingresar un telefono de contacto.\n");
		
		if (txEmail.getText().trim().isEmpty()) 
			msgError.append("* Debe ingresar un e-mail de contacto.\n");
		else
			if (!ValidadorTexto.esEmailValido(txEmail.getText().trim()))
				msgError.append("* Debe ingresar una direccion de email valida.\n");
		
		// TODO: Validar en la documentacion que puedo grabar un paciente sin cobertura (es el caso de particular)
		return msgError.toString();
	}
	
	private void eliminarItem(String nroCredencial)
	{
		ItemCoberturaView icv = this.coberturas.get(nroCredencial);
		if (icv != null)
		{
			this.coberturas.remove(nroCredencial);

			// Si estoy quitando la primara, designo como primaria la primera de la lista
			if (icv.getPrimaria() && this.coberturas.size() > 1)
			{
				this.coberturas.get(table.getValueAt(0, 3).toString()).setPrimaria(true);
			}
		}
		// Si solo queda 1 en la lista, la designo como primaria
		if (this.coberturas.size() == 1)
			this.coberturas.get(table.getValueAt(0, 3).toString()).setPrimaria(true);
		llenarGrilla();
	}
	
	private void establecerPrimaria(String nroCredencial)
	{
		for (ItemCoberturaView icv : this.coberturas.values())
		{
			if (icv.getNroCredencial().equals(nroCredencial))
				icv.setPrimaria(true);
			else
				icv.setPrimaria(false);
		}
		llenarGrilla();		
	}
	
	private void guardar()
	{
		String msgErrorValidaciones = getValidacionesFallidas();
		if (msgErrorValidaciones.length() > 0)
			JOptionPane.showMessageDialog(null, msgErrorValidaciones);
		else
		{
			try
			{
				int dni = txDNI.getText().trim().length() > 0? Integer.parseInt(txDNI.getText().trim()): null;
				Date fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(frTxFechaNac.getText());
				List<ItemCoberturaView> itemsCobertura = new ArrayList<ItemCoberturaView>(coberturas.values());
				
				if (!modoEdicion)
				{
					AdminPacientes.getInstancia().insertar(txApellido.getText(), 
							txNombre.getText(), 
							dni, 
							fechaNac, 
							txTelefono.getText(),
							txEmail.getText(), 
							cbActivo.isSelected(),
							itemsCobertura);
				}
				else
				{
					AdminPacientes.getInstancia().modificar(idPaciente, 
							txApellido.getText(), 
							txNombre.getText(), 
							dni, 
							fechaNac, 
							txTelefono.getText(),
							txEmail.getText(), 
							cbActivo.isSelected(),
							itemsCobertura);					
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error guardar el paciente:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}					
			cancelado = false;
			setVisible(false);
		}		
	}
	
	private void agregarCobertura()
	{
		DatosCobertura formCobertura = new DatosCobertura();
		formCobertura.setLocationRelativeTo(null);
		formCobertura.setVisible(true);
		if (!formCobertura.getCancelado())
		{
			// TODO: el flag de primaria hay que setearlo desde la grilla (menu contextual)
			ItemCoberturaView ic = new ItemCoberturaView(formCobertura.getIdNombrePlan().getId(), formCobertura.getNumeroSocio());
			
			// Los siguientes son opcionales
			ic.setNombreObraSocial(formCobertura.getIdNombreObraSocial().getNombre());
			ic.setNombrePlan(formCobertura.getIdNombrePlan().getNombre());
			
			if (this.coberturas.isEmpty()) 
				ic.setPrimaria(true);
			
			this.coberturas.put(ic.getNroCredencial(), ic);
			this.llenarGrilla();
		}
		formCobertura.dispose();
	}
	
	private void llenarGrilla()
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[5];
		table.removeAll();
		model.addColumn("ID");
		model.addColumn("Obra Social");
		model.addColumn("Plan");
		model.addColumn("Numero de Socio");
		model.addColumn("Primaria");

		for(ItemCoberturaView ic : this.coberturas.values()) 
		{
			fila[0] = ic.getId();
			fila[1] = ic.getNombreObraSocial();
			fila[2] = ic.getNombrePlan();
			fila[3] = ic.getNroCredencial();
			fila[4] = ic.getPrimaria() ? "Si": "No";
			model.addRow(fila);
		}
		table.setModel(model);
		table.getColumnModel().getColumn(0).setWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.validate();
	}
	
	public Boolean getCancelado() 
	{
		return cancelado;
	}

	public Integer getDNI() {
		return txDNI.getText().trim().length() > 0? Integer.parseInt(txDNI.getText().trim()): null;
	}
	
	public Date getFechaNac() throws ParseException {
		return (new SimpleDateFormat("dd/MM/yyyy").parse(frTxFechaNac.getText()));
	}
	
	public List<ItemCoberturaView> getItemsCobertura(){
		return (new ArrayList<ItemCoberturaView>(coberturas.values()));
	}
	
	public String getApellido() {
		return txApellido.getText();
	}
	
	public String getNombre() {
		return txNombre.getText();
	}
	
	public boolean getActivo() {
		return cbActivo.isSelected();
	}
	
	public String getEmail() {
		return txEmail.getText();
	}
	
	public String getTelefono() {
		return txTelefono.getText();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				if (((JTable)e.getComponent()).getSelectedRowCount()!=0)
					popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
