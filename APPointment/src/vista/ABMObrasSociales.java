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

import controlador.AdminObrasSociales;
import controlador.ObraSocialView;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ABMObrasSociales extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public ABMObrasSociales() {
		setVisible(true);
		setModal(true);
		setResizable(false);
		setTitle("Administrador de Obras Sociales");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 582, 385);
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
		
		JButton btnAgregar = new JButton("Nueva...");
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
		model.addColumn("Obra Social");
		model.addColumn("Tel. Prestadores");
		model.addColumn("Activa");
		Object[] fila = new Object[3];

		try
		{
			List<ObraSocialView> lista = AdminObrasSociales.getInstancia().listarObrasSociales();

			for(ObraSocialView os : lista) {
				fila[0] = os.getNombre();
				fila[1] = os.getTelefonoPrestadores();
				fila[2] = os.getActivo()? "Si": "No";
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
