import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Modification extends JFrame {

	private JFrame parent;
	private Client client;
	private int numCli;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_6;
	private JCheckBox chckbxNewCheckBox;
	private JButton btnRetour;
	private JLabel lblTva;
	private JTextField textField_7;
	private JCheckBox chckbxNonAssujetti;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// MenuClient frame = new MenuClient();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public Modification(Client cli, int numcli, JFrame p) {
		super("Inscription d'un nouveau client");
		parent = p;
		client = cli;
		numCli = numcli;
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomDuClient = new JLabel("Nom du client:");
		lblNomDuClient.setBounds(45, 55, 101, 28);
		contentPane.add(lblNomDuClient);

		JLabel lblRue = new JLabel("Adresse");
		lblRue.setBounds(45, 77, 101, 46);
		contentPane.add(lblRue);

		JLabel lblVille = new JLabel("Ville");
		lblVille.setBounds(45, 169, 78, 14);
		contentPane.add(lblVille);

		JLabel lblPays = new JLabel("Pays");
		lblPays.setBounds(45, 209, 46, 14);
		contentPane.add(lblPays);

		JLabel lblBoite = new JLabel("Boite");
		lblBoite.setBounds(45, 134, 46, 14);
		contentPane.add(lblBoite);

		textField = new JTextField();
		textField.setBounds(156, 59, 256, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(156, 90, 256, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(156, 131, 127, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(156, 166, 165, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		textField_6 = new JTextField();
		textField_6.setBounds(156, 206, 127, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);

		chckbxNewCheckBox = new JCheckBox("pas de boite");
		chckbxNewCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox box = (JCheckBox) e.getSource();
				if (box.isSelected())
					textField_3.setEditable(false);
				else
					textField_3.setEditable(true);
			}

		});
		chckbxNewCheckBox.setToolTipText("");
		chckbxNewCheckBox.setBounds(315, 130, 97, 23);
		contentPane.add(chckbxNewCheckBox);

		textField_7 = new JTextField();
		textField_7.setBounds(156, 237, 165, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);

		chckbxNonAssujetti = new JCheckBox("non assujetti");
		chckbxNonAssujetti.setBounds(327, 236, 134, 23);
		chckbxNonAssujetti.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox box = (JCheckBox) e.getSource();
				if (box.isSelected())
					textField_7.setEditable(false);
				else
					textField_7.setEditable(true);
			}

		});

		textField.setText(client.getName());
		textField_1.setText(client.getStreet());
		textField_3.setText(client.getBox());
		if (client.getBox().equals("")) {
			chckbxNewCheckBox.setSelected(true);
			textField_3.setEditable(false);
		}
		textField_4.setText(client.getCity());
		textField_6.setText(client.getCountry());
		textField_7.setText(client.getTva());
		if (client.getTva().equals("non assujetti")) {
			chckbxNonAssujetti.setSelected(true);
			textField_7.setText("");
			textField_7.setEditable(false);
		}

		JButton btnNewButton = new JButton("Enregistrer");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().equals("") || textField_1.getText().equals("")
						|| (textField_3.getText().equals("") && !chckbxNewCheckBox.isSelected())
						|| textField_4.getText().equals("") || textField_6.getText().equals("")
						|| (textField_7.getText().equals("") && !chckbxNonAssujetti.isSelected()))
					JOptionPane.showMessageDialog(null, "Il existe des champs non-remplis", "Erreur", 0);
				else {
					if (!client.getName().equals(textField.getText())) {
						if (checkDoubleClients(textField.getText())) {
							JOptionPane.showMessageDialog(null, "Il existe déjà  une personne du même nom", "Erreur", 0);
							return;
						}
					}
					String vat;
					if (chckbxNonAssujetti.isSelected())
						vat = "non assujetti";
					else
						vat = textField_7.getText();
					Client cli = new Client(textField.getText(), textField_1.getText(), textField_3.getText(),
							textField_4.getText(), textField_6.getText(), vat);
					ArrayList<Client> l = JDOM1.getClientsFromXML();
					System.out.println(cli.getName());
					l.set(numCli, cli);
					JDOM1.createXMl(l);
					JOptionPane.showMessageDialog(null, "Client modifié", "Modification réussie", 1);
					Window window = SwingUtilities.windowForComponent((JButton) arg0.getSource());
					if (window instanceof JFrame) {
						JFrame frame = (JFrame) window;

						frame.setVisible(false);
						frame.dispose();
					}
					((ModifierClient) parent).parent.setVisible(true);

				}
			}

		});
		btnNewButton.setBounds(341, 326, 127, 23);
		contentPane.add(btnNewButton);

		btnRetour = new JButton("Retour");
		btnRetour.setBounds(497, 326, 89, 23);
		btnRetour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.windowForComponent((JButton) e.getSource());
				if (window instanceof JFrame) {
					JFrame frame = (JFrame) window;

					frame.setVisible(false);
					frame.dispose();
				}
				parent.setVisible(true);
			}

		});
		contentPane.add(btnRetour);

		lblTva = new JLabel("TVA");
		lblTva.setBounds(45, 234, 46, 14);
		contentPane.add(lblTva);

		contentPane.add(chckbxNonAssujetti);
	}

	public boolean checkDoubleClients(String name) {
		ArrayList<Client> vect = JDOM1.getClientsFromXML();
		if (vect == null)
			return false;
		for (int i = 0; i < vect.size(); i++) {
			if (vect.get(i).getName().equals(name))
				return true;
		}
		return false;

	}
}
