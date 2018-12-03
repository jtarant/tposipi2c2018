package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controlador.AdminPacientes;
import controlador.PacienteEncontradoView;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.util.List;
import java.awt.event.ActionEvent;

public class BuscarPacientePanel extends JPanel {
	private JTextField txtBuscar;
	private JTable tblResultados;

	/**
	 * Create the panel.
	 */
	public BuscarPacientePanel() {
		setLayout(null);
		
		AbstractAction action = new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					llenarGrilla();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al buscar los pacientes:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
		};
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(0, 0, 343, 26);
		txtBuscar.addActionListener(action);
		txtBuscar.setColumns(10);
		add(txtBuscar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(action);
		btnBuscar.setBounds(358, -1, 115, 29);
		add(btnBuscar);				
		
		tblResultados = new JTable();
		tblResultados.setBounds(0, 42, 667, 176);
		tblResultados.setFillsViewportHeight(true);
		tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblResultados.setDefaultEditor(Object.class, null);
		tblResultados.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(tblResultados);
		scrollpane.add(tblResultados.getTableHeader());
		scrollpane.setBounds(0, 42, 667, 176);
		tblResultados.getTableHeader().setReorderingAllowed(false);
		add(scrollpane);
	}
	
	private void llenarGrilla() throws Exception
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[5];
		
		tblResultados.removeAll();
		model.addColumn("ID");
		model.addColumn("Apellido");
		model.addColumn("Nombre");
		model.addColumn("DNI");
		model.addColumn("Telefono");

		List<PacienteEncontradoView> pacientes;
		pacientes = AdminPacientes.getInstancia().buscarPorApellido(txtBuscar.getText());
			
		for(PacienteEncontradoView p : pacientes) 
		{
			fila[0] = p.getId();
			fila[1] = p.getApellido();
			fila[2] = p.getNombre();
			fila[3] = p.getDNI();
			fila[4] = p.getTelefono();
			model.addRow(fila);
		}
		tblResultados.setModel(model);
		tblResultados.getColumnModel().getColumn(0).setWidth(0);
		tblResultados.getColumnModel().getColumn(0).setMinWidth(0);
		tblResultados.getColumnModel().getColumn(0).setMaxWidth(0);
		tblResultados.validate();
	}
	
	public Boolean isPacienteSeleccionado()
	{
		return (tblResultados.getSelectedRow() != -1);
	}
	public String getApellido()
	{
		return tblResultados.getValueAt(tblResultados.getSelectedRow(), 1).toString();
	}
	public String getNombre()
	{
		return tblResultados.getValueAt(tblResultados.getSelectedRow(), 2).toString();
	}
	public int getDNI()
	{
		return Integer.parseInt(tblResultados.getValueAt(tblResultados.getSelectedRow(), 3).toString());
	}
	public Integer getIDSeleccionado()
	{
		if (isPacienteSeleccionado())
		{
			return Integer.parseInt(tblResultados.getValueAt(tblResultados.getSelectedRow(), 0).toString());
		}
		else return -1;
	}
	public void setFocus()
	{
		txtBuscar.requestFocusInWindow();
	}
	public void limpiar()
	{
		tblResultados.removeAll();
	}
	public void addSelectionListener(ListSelectionListener lsl)
	{
		tblResultados.getSelectionModel().addListSelectionListener(lsl);
	}
}
