import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifierClient extends JFrame {

	private JPanel contentPane;
	private ArrayList<Client> clientsList;
	private DefaultListModel listModel;
	private ArrayList<Integer> tabNum = new ArrayList<Integer>();
	private JTextField textField;
	private JList list;
	public JFrame parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifierClient frame = new ModifierClient(JDOM1.getClientsFromXML(), null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifierClient(ArrayList<Client> clientList, JFrame frame) {
		parent = frame;
		ArrayList<String> listName = new ArrayList<String>();
		for (int i = 0; i < clientList.size(); i++)
			listName.add(clientList.get(i).getName());
		// Collections.sort(listName);
		this.clientsList = clientList;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(66, 85, 292, 166);
		contentPane.add(scrollPane);
		listModel = new DefaultListModel();
		for (int i = 0; i < listName.size(); i++) {
			listModel.addElement(listName.get(i));
			tabNum.add(new Integer(i));
		}
		list = new JList(listModel);

		scrollPane.setViewportView(list);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				ArrayList<String> listName = new ArrayList<String>();
				tabNum = new ArrayList<Integer>();

				DefaultListModel model = new DefaultListModel();// creation dun
																// nouveau model
																// pour une
																// JList
				String enteredText = textField.getText(); // On recupere le
															// texte entree dans
															// le JtextField
				if (enteredText.equals("")) {
					for (int i = 0; i < clientsList.size(); i++) {
						listName.add(clientsList.get(i).getName());
						tabNum.add(new Integer(i));

					}
				} else {
					for (int i = 0; i < clientsList.size(); i++) {
						// Comparaison des elements contenu dans l ArrayList et
						// du texte entree
						if (clientsList.get(i).getName().indexOf(enteredText) != -1) {
							tabNum.add(new Integer(i));
							listName.add(clientsList.get(i).getName());// ajout
																		// de
																		// lelement
																		// dans
																		// le
																		// nouveau
																		// model
						}
					}
				}
				// Collections.sort(listName);
				for (int i = 0; i < listName.size(); i++)
					model.addElement(listName.get(i));
				listModel = model;// On definie ce nouveau model pour la JList
				list.setModel(listModel);

			}
		});
		textField.setBounds(33, 54, 184, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblSlectionnezUnClient = new JLabel("Sélectionnez un client:");
		lblSlectionnezUnClient.setBounds(10, 0, 156, 36);
		contentPane.add(lblSlectionnezUnClient);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 41, 414, 2);
		contentPane.add(separator);

		JButton btnSlectionner = new JButton("Modifier client");
		btnSlectionner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton but = (JButton) arg0.getSource();
				int[] tab = list.getSelectedIndices();
				if (tab.length != 1)
					JOptionPane.showMessageDialog(null, "Vous devez sélectionner un et un seul client.", "Erreur", 0);
				else {
					System.out.println(tabNum.get(tab[0]));
					Client cli = clientsList.get(tabNum.get(tab[0]));
					System.out.println("tav:"+cli.getTva());
					
					Window window = SwingUtilities.windowForComponent(but);
					if (window instanceof JFrame) {
						JFrame frame = (JFrame) window;
						frame.setVisible(false);
						Modification fact = new Modification(cli,tabNum.get(tab[0]),frame);
					}
				}
			}
		});
		btnSlectionner.setBounds(368, 85, 168, 36);
		contentPane.add(btnSlectionner);


		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton but = (JButton) e.getSource();
				Window window = SwingUtilities.windowForComponent(but);
				if (window instanceof JFrame) {
					JFrame frame = (JFrame) window;
					frame.setVisible(false);
					frame.dispose();
					parent.setVisible(true);
				}
			}
		});
		btnRetour.setBounds(368, 132, 168, 36);
		contentPane.add(btnRetour);
	}
}
