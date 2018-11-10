package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.ErrorTurnoView;

public class ErroresReglas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tblResultados;

	/**
	 * Create the dialog.
	 */
	public ErroresReglas(List<ErrorTurnoView> errores) {
		setTitle("Errores de generacion Turnos Programados");
		setBounds(100, 100, 612, 464);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
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
		
		llenarGrilla(errores);
	}

	private void llenarGrilla(List<ErrorTurnoView> errores)
	{
		DefaultTableModel model = new DefaultTableModel();
		Object[] fila = new Object[3];
		
		tblResultados.removeAll();
		model.addColumn("Paciente");
		model.addColumn("Turno");
		model.addColumn("Causa");

		for(ErrorTurnoView error : errores) 
		{
			fila[0] = error.getPaciente().getNombre();
			fila[1] = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(error.getFechaHoraInicio());
			fila[2] = error.getCausa();
			model.addRow(fila);
		}
		tblResultados.setModel(model);
		tblResultados.validate();
	}
}
