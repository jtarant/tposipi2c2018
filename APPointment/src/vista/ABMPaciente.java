package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controlador.AdminPacientes;
import controlador.PacienteView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class ABMPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private BuscarPacientePanel pnlBuscarPaciente;
	private JTable tblPacientes;

	/**
	 * Create the dialog.
	 */
	public ABMPaciente() {
		setTitle("Administrador de pacientes");
		setModal(true);
		setBounds(100, 100, 833, 395);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		pnlBuscarPaciente = new BuscarPacientePanel();
		pnlBuscarPaciente.setBounds(105, 21, 673, 221);
		pnlBuscarPaciente.setSoloActivos(false);
		contentPanel.add(pnlBuscarPaciente);
		
		//AGREGA POPUPS PARA EDITAR ELIMINAR
		tblPacientes = pnlBuscarPaciente.getTblResultados();
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tblPacientes, popupMenu);
		
		JMenuItem mntmModificar = new JMenuItem("Modificar");
		mntmModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				modificarPaciente();
			}
		});
		popupMenu.add(mntmModificar);
		
		JMenuItem mntmEliminar = new JMenuItem("Eliminar");
		mntmEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eliminarPaciente();
			}
		});
		popupMenu.add(mntmEliminar);
		
		JLabel lblPaciente = new JLabel("Paciente:");
		lblPaciente.setBounds(10, 21, 69, 20);
		contentPanel.add(lblPaciente);
		
		JButton btnNueva = new JButton("Nuevo...");

		btnNueva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				crearPaciente();
			}
		});
		btnNueva.setBounds(663, 244, 115, 29);
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
	
	private void crearPaciente()
	{
		try
		{
			DatosPaciente formPaciente = new DatosPaciente();
			formPaciente.setLocationRelativeTo(null);
			formPaciente.setVisible(true);
			if (!formPaciente.getCancelado())
			{
				AdminPacientes.getInstancia().insertar(formPaciente.getApellido(), formPaciente.getNombre(), formPaciente.getDNI(), formPaciente.getFechaNac(),formPaciente.getTelefono(),formPaciente.getEmail(),formPaciente.getActivo(),formPaciente.getItemsCobertura());					
				pnlBuscarPaciente.actualizar();
			}
			formPaciente.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al crear paciente:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void eliminarPaciente()
	{
		try 
		{
			int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres eliminar el paciente? (se desactivara)", "Eliminar Paciente", JOptionPane.YES_NO_OPTION);
			if (opcion == JOptionPane.YES_OPTION)
			{
				AdminPacientes.getInstancia().eliminar(pnlBuscarPaciente.getIDSeleccionado());
				pnlBuscarPaciente.actualizar();	
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al eliminar el paciente:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void modificarPaciente()
	{
		try
		{
			PacienteView paciente = AdminPacientes.getInstancia().obtener(pnlBuscarPaciente.getIDSeleccionado());
			
			DatosPaciente formPaciente = new DatosPaciente();
			formPaciente.setPaciente(paciente);
			formPaciente.setLocationRelativeTo(null);
			formPaciente.setVisible(true);
			if (!formPaciente.getCancelado())
			{
				AdminPacientes.getInstancia().modificar(paciente.getId(),formPaciente.getApellido(), formPaciente.getNombre(), formPaciente.getDNI(), formPaciente.getFechaNac(),formPaciente.getTelefono(),formPaciente.getEmail(),formPaciente.getActivo(),formPaciente.getItemsCobertura());					
				pnlBuscarPaciente.actualizar();
			}
			formPaciente.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar los cambios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
