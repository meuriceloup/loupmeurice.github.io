import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class MenuClient extends JFrame {

	private JFrame parent;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JCheckBox chckbxNewCheckBox;
	private JButton btnRetour;
	private JLabel lblTva;
	private JTextField textField_7;
	private JCheckBox chckbxNonAssujetti;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MenuClient frame = new MenuClient();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MenuClient(JFrame p) {
		super("Inscription d'un nouveau client");
		parent = p;
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomDuClient = new JLabel("Nom du client:");
		lblNomDuClient.setBounds(45, 25, 101, 28);
		contentPane.add(lblNomDuClient);

		JLabel lblRue = new JLabel("Rue/Avenue");
		lblRue.setBounds(45, 51, 101, 46);
		contentPane.add(lblRue);

		JLabel lblN = new JLabel("n�");
		lblN.setBounds(45, 94, 111, 28);
		contentPane.add(lblN);

		JLabel lblVille = new JLabel("Ville");
		lblVille.setBounds(45, 169, 46, 14);
		contentPane.add(lblVille);

		JLabel lblCodePostal = new JLabel("Code postal");
		lblCodePostal.setBounds(45, 207, 89, 14);
		contentPane.add(lblCodePostal);

		JLabel lblPays = new JLabel("Pays");
		lblPays.setBounds(45, 250, 46, 14);
		contentPane.add(lblPays);

		JLabel lblBoite = new JLabel("Boite");
		lblBoite.setBounds(45, 134, 46, 14);
		contentPane.add(lblBoite);

		textField = new JTextField();
		textField.setBounds(156, 29, 256, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(156, 64, 256, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(156, 98, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(156, 131, 127, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(156, 166, 165, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		textField_5 = new JTextField();
		textField_5.setBounds(156, 204, 86, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);

		textField_6 = new JTextField();
		textField_6.setBounds(156, 247, 127, 20);
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

		JButton btnNewButton = new JButton("Enregistrer");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().equals("") || textField_1.getText().equals("")
						|| textField_2.getText().equals("")
						|| (textField_3.getText().equals("") && !chckbxNewCheckBox.isSelected())
						|| textField_4.getText().equals("") || textField_5.getText().equals("")
						|| textField_6.getText().equals("")
						||(textField_7.getText().equals("") && !chckbxNonAssujetti.isSelected()))
					JOptionPane.showMessageDialog(null, "Il existe des champs non-remplis", "Erreur", 0);
				else {
					if(checkDoubleClients(textField.getText())){
						JOptionPane.showMessageDialog(null, "Il existe déjà une personne du même nom", "Erreur", 0);
						return;
					}
					String vat;
					if(chckbxNonAssujetti.isSelected())vat = "non assujetti";
					else vat = textField_7.getText();
					Client cli = new Client(textField.getText(), textField_1.getText() + ", " + textField_2.getText(),
							textField_3.getText(), textField_5.getText() + " " + textField_4.getText(), textField_6
									.getText(),vat);
					JDOM1.addMapToXML(cli);
					JOptionPane.showMessageDialog(null, "Client ajouté", "Inscription réussie", 1);
					Window window = SwingUtilities.windowForComponent((JButton)arg0.getSource());
					if (window instanceof JFrame) {
						JFrame frame = (JFrame) window;
				 
						frame.setVisible(false);
						frame.dispose();
					}
					parent.setVisible(true);

				}
			}

		});
		btnNewButton.setBounds(341, 326, 127, 23);
		contentPane.add(btnNewButton);
		
		btnRetour = new JButton("Retour");
		btnRetour.setBounds(497, 326, 89, 23);
		btnRetour.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.windowForComponent((JButton)e.getSource());
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
		lblTva.setBounds(45, 284, 46, 14);
		contentPane.add(lblTva);
		
		textField_7 = new JTextField();
		textField_7.setBounds(156, 281, 165, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		chckbxNonAssujetti = new JCheckBox("non assujetti");
		chckbxNonAssujetti.setBounds(334, 280, 134, 23);
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
		contentPane.add(chckbxNonAssujetti);
	}
	
	public boolean checkDoubleClients(String name){
		ArrayList<Client> vect = JDOM1.getClientsFromXML();
		if(vect==null)return false;
		for(int i = 0; i < vect.size();i++){
			if(vect.get(i).getName().equals(name))return true;
		}
		return false;
		
	}
}
