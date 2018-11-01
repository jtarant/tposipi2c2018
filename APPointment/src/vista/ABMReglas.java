package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controlador.AdminProfesionales;
import controlador.AdminReglas;
import controlador.IdNombreView;
import controlador.ReglaView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class ABMReglas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private BuscarPacientePanel pnlBuscarPaciente;
	private JComboBox<IdNombreView> cboProfesionales;
	private IdNombreView idNombreProfesional;
	private JTable tblReglas;

	/**
	 * Create the dialog.
	 */
	public ABMReglas() {
		setTitle("Reglas de programacion de turnos");
		setModal(true);
		setBounds(100, 100, 833, 630);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblProfesional = new JLabel("Profesional:");
		lblProfesional.setBounds(15, 16, 104, 20);
		contentPanel.add(lblProfesional);
		
		cboProfesionales = new JComboBox<IdNombreView>();
        cboProfesionales.addActionListener(new ActionListener() {
        	@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
        		JComboBox<IdNombreView> combo = (JComboBox<IdNombreView>)e.getSource();
        		
        		idNombreProfesional = (IdNombreView)combo.getSelectedItem();
        		pnlBuscarPaciente.limpiar();
        	}
        });
		cboProfesionales.setBounds(116, 15, 337, 23);
		contentPanel.add(cboProfesionales);
		
		pnlBuscarPaciente = new BuscarPacientePanel();
		pnlBuscarPaciente.setBounds(116, 55, 673, 221);
		contentPanel.add(pnlBuscarPaciente);
		
		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setBounds(15, 52, 69, 20);
		contentPanel.add(lblPaciente);
		
		JButton btnVerReglas = new JButton("Ver reglas");
		btnVerReglas.setEnabled(false);
		btnVerReglas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					llenarGrilla();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnVerReglas.setBounds(116, 285, 115, 29);
		contentPanel.add(btnVerReglas);
		
		tblReglas = new JTable();
		tblReglas.setBounds(15, 334, 781, 185);
		tblReglas.setFillsViewportHeight(true);
		tblReglas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblReglas.setDefaultEditor(Object.class, null);
		tblReglas.setAutoscrolls(true);
		JScrollPane scrollpane = new JScrollPane(tblReglas);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tblReglas, popupMenu);
		
		JMenuItem mntmModificar = new JMenuItem("Modificar");
		mntmModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				modificarRegla();
			}
		});
		popupMenu.add(mntmModificar);
		
		JMenuItem mntmEliminar = new JMenuItem("Eliminar");
		mntmEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eliminarRegla();
			}
		});
		popupMenu.add(mntmEliminar);
		scrollpane.add(tblReglas.getTableHeader());
		scrollpane.setBounds(15, 334, 781, 185);
		tblReglas.getTableHeader().setReorderingAllowed(false);
		contentPanel.add(scrollpane);		
		
		JButton btnNueva = new JButton("Nueva...");
		btnNueva.setEnabled(false);
		btnNueva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if (pnlBuscarPaciente.getIDSeleccionado() == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un paciente.");
				}
				else crearRegla();
			}
		});
		btnNueva.setBounds(670, 285, 115, 29);
		contentPanel.add(btnNueva);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cerrar");
				buttonPane.add(cancelButton);
			}
		}
		pnlBuscarPaciente.addSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (pnlBuscarPaciente.getIDSeleccionado() != -1)
				{
					btnVerReglas.setEnabled(true);
					btnNueva.setEnabled(true);	
				}
				else
				{
					btnVerReglas.setEnabled(false);
					btnNueva.setEnabled(false);
				}
			}
		});		
		this.cargarProfesionales();
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
	
	private void crearRegla()
	{
		try
		{
			DatosRegla formRegla = new DatosRegla();
			formRegla.setNombrePaciente(pnlBuscarPaciente.getApellido() + ", " + pnlBuscarPaciente.getNombre());
			formRegla.setNombreProfesional(idNombreProfesional.getNombre());
			formRegla.setFechaInicio(new Date());
			formRegla.setActiva(true);
			formRegla.setLocationRelativeTo(null);
			formRegla.setVisible(true);
			if (!formRegla.getCancelado())
			{
				AdminReglas.getInstancia().insertar(idNombreProfesional.getId(), pnlBuscarPaciente.getIDSeleccionado(), formRegla.getFechaInicio(), formRegla.getFechaFin(), formRegla.getRepiteDias(), formRegla.getHora(), formRegla.getRepiteCada());
				llenarGrilla();
			}
			formRegla.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar los cambios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modificarRegla()
	{
		try
		{
			ReglaView regla = AdminReglas.getInstancia().obtener((int)tblReglas.getValueAt(tblReglas.getSelectedRow(), 0));
			DatosRegla formRegla = new DatosRegla();
			formRegla.setId(regla.getId());
			formRegla.setNombrePaciente(pnlBuscarPaciente.getApellido() + ", " + pnlBuscarPaciente.getNombre());
			formRegla.setNombreProfesional(idNombreProfesional.getNombre());
			formRegla.setFechaInicio(regla.getFechaInicio());
			formRegla.setFechaFin(regla.getFechaFin());
			formRegla.setHora(regla.getHora());
			formRegla.setRepiteDias(DayOfWeek.of(regla.getRepiteDia()));
			formRegla.setRepiteCada(regla.getRepiteCada());
			formRegla.setActiva(regla.getActivo());
			formRegla.setLocationRelativeTo(null);
			formRegla.setVisible(true);
			if (!formRegla.getCancelado())
			{
				AdminReglas.getInstancia().modificar(regla.getId(), formRegla.getFechaInicio(), formRegla.getFechaFin(), formRegla.getRepiteDias(), formRegla.getHora(), formRegla.getRepiteCada(), formRegla.getActiva());
				llenarGrilla();
			}
			formRegla.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar los cambios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void eliminarRegla()
	{
		try 
		{
			int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres eliminar la regla? (se desactivara)", "Eliminar regla", JOptionPane.YES_NO_OPTION);
			if (opcion == JOptionPane.YES_OPTION)
			{
				AdminReglas.getInstancia().eliminar((int)tblReglas.getValueAt(tblReglas.getSelectedRow(), 0));
				llenarGrilla();	
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar los datos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}

	private void llenarGrilla() throws Exception
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[6];
		
		tblReglas.removeAll();
		model.addColumn("ID");
		model.addColumn("Inicio");
		model.addColumn("Fin");
		model.addColumn("Dia");
		model.addColumn("Hora");
		model.addColumn("Activa");

		List<ReglaView> reglas;
		reglas = AdminReglas.getInstancia().obtenerReglas(idNombreProfesional.getId(), pnlBuscarPaciente.getIDSeleccionado());
			
		for(ReglaView r : reglas) 
		{
			fila[0] = r.getId();
			fila[1] = new SimpleDateFormat("dd/MM/yyyy").format(r.getFechaInicio());
			if (r.getFechaFin() != null)
				fila[2] = new SimpleDateFormat("dd/MM/yyyy").format(r.getFechaFin());
			else
				fila[2] = "";
			fila[3] = DayOfWeek.of(r.getRepiteDia()).toString();
			fila[4] = new SimpleDateFormat("HH:mm").format(r.getHora());
			fila[5] = r.getActivo() ? "Si": "No";
			model.addRow(fila);
		}
		tblReglas.setModel(model);
		tblReglas.getColumnModel().getColumn(0).setWidth(0);
		tblReglas.getColumnModel().getColumn(0).setMinWidth(0);
		tblReglas.getColumnModel().getColumn(0).setMaxWidth(0);
		tblReglas.validate();
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
