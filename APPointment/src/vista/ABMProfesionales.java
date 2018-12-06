package vista;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import controlador.AdminProfesionales;
import controlador.ItemProfesionalView;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ABMProfesionales extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public ABMProfesionales() {
		setVisible(true);
		setModal(true);
		setResizable(false);
		setTitle("Administrador de Profesionales");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 585, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

	
		table = new JTable();
	
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(12, 51, 551, 246);
		scrollpane.add(table.getTableHeader());
		table.getTableHeader().setReorderingAllowed(false);
		contentPane.setLayout(null);
		contentPane.add(scrollpane);
		
		JButton btnAgregar = new JButton("Nuevo...");
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAgregar.setBounds(471, 13, 92, 25);
		contentPane.add(btnAgregar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnCerrar.setBounds(469, 310, 92, 25);
		contentPane.add(btnCerrar);
		
		this.LlenarGrilla();
	}
	
	private void LlenarGrilla()
	{
		table.removeAll();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Apellido");
		model.addColumn("Nombre");
		model.addColumn("Especialidad");
		model.addColumn("Activo");
		Object[] fila = new Object[4];

		try
		{
			List<ItemProfesionalView> lista = AdminProfesionales.getInstancia().listarProfesionales();

			for(ItemProfesionalView u : lista) {
				fila[0] = u.getApellido();
				fila[1] = u.getNombre();
				fila[2] = u.getEspecialidad();
				fila[3] = u.getActivo()? "Si": "No";
				model.addRow(fila);
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		table.setModel(model);
		table.validate();
	}
}
