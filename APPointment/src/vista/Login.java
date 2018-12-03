package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.AdminUsuarios;

public class Login extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdUsuario;
	private JPasswordField pwdPassword;
	private JButton btnRecuperar;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try 
				{	
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void habilitarRegistracion(Boolean r)
	{
		btnRecuperar.setVisible(r);
	}
	
	/**
	 * Create the dialog.
	 */
	public Login() {
		setTitle("APPoinment  - Inicio de sesion");
		setModal(true);
		setBounds(100, 100, 436, 169);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Email");
		lblUsuario.setBounds(10, 11, 39, 14);
		contentPanel.add(lblUsuario);
		
		txtIdUsuario = new JTextField();
		txtIdUsuario.setBounds(77, 8, 278, 20);
		contentPanel.add(txtIdUsuario);
		txtIdUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 45, 59, 14);
		contentPanel.add(lblPassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(77, 42, 278, 20);
		contentPanel.add(pwdPassword);
		
		JLabel lblMensajeError = new JLabel("");
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setBounds(132, 70, 278, 14);
		contentPanel.add(lblMensajeError);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						try 
						{
							if (!AdminUsuarios.getInstancia().autenticar(txtIdUsuario.getText().trim().toLowerCase(), String.valueOf(pwdPassword.getPassword())))
							{
								lblMensajeError.setText("Usuario o contrase�a no validos.");
								habilitarRegistracion(true);
							}
							else
							{
								VentanaPrincipal formPrincipal = new VentanaPrincipal();
								setVisible(false);
								formPrincipal.setVisible(true);
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error al autenticar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
		
				btnRecuperar = new JButton("Recuperar contrase�a");
				buttonPane.add(btnRecuperar);
				btnRecuperar.setVisible(false);
				btnRecuperar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						try 
						{
							AdminUsuarios.getInstancia().recuperarContrasena(txtIdUsuario.getText().trim().toLowerCase());
							JOptionPane.showMessageDialog(null, "Se ha generado una nueva contrase�a y ha sido enviada a su casilla de email", "APPointment - Recuperacion de contrase�a", JOptionPane.INFORMATION_MESSAGE);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error al recuperar la contrase�a:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}				
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
