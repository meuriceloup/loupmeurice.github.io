import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MainMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 200, 473, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAjouterUnNouveau = new JButton("Ajouter un nouveau client");
		btnAjouterUnNouveau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton but = (JButton) e.getSource();
				Window window = SwingUtilities.windowForComponent(but);
				if (window instanceof JFrame) {
					JFrame frame = (JFrame) window;
					frame.setVisible(false);
					MenuClient addCli = new MenuClient(frame);
				}
			}

		});
		btnAjouterUnNouveau.setBounds(87, 87, 205, 37);
		contentPane.add(btnAjouterUnNouveau);

		JButton btnModifierUnClient = new JButton("Modifier un client existant");
		btnModifierUnClient.setBounds(86, 135, 206, 37);
		btnModifierUnClient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton but = (JButton) e.getSource();
				Window window = SwingUtilities.windowForComponent(but);
				if (window instanceof JFrame) {
					JFrame frame = (JFrame) window;
					frame.setVisible(false);
					ModifierClient list = new ModifierClient(JDOM1.getClientsFromXML(), frame);
					list.setVisible(true);
				}
			}

		});
		contentPane.add(btnModifierUnClient);

		JButton btnEffectuerUneFacture = new JButton("Effectuer une facture");
		btnEffectuerUneFacture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton but = (JButton) e.getSource();
				Window window = SwingUtilities.windowForComponent(but);
				if (window instanceof JFrame) {
					JFrame frame = (JFrame) window;
					frame.setVisible(false);
					EffectuerFacture list = new EffectuerFacture(JDOM1.getClientsFromXML(), frame);
					list.setVisible(true);
				}

			}

		});
		btnEffectuerUneFacture.setBounds(87, 183, 205, 37);
		contentPane.add(btnEffectuerUneFacture);

		JLabel lblQueVoulezvousFaire = new JLabel("Que voulez-vous faire?");
		lblQueVoulezvousFaire.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblQueVoulezvousFaire.setBounds(47, 11, 177, 43);
		contentPane.add(lblQueVoulezvousFaire);

		JButton changeProp = new JButton("Changer le numéro de la prochaine facture");
		changeProp.setBounds(87, 230, 300, 37);
		changeProp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				try {
					prop.load(new FileInputStream(Facture.file));
					String Num = prop.getProperty(Facture.attr);
					int num = Integer.parseInt(Num);
					System.out.println(num);
					boolean ok = false;
					int res = 0;
					while (!ok) {
						String saisie = JOptionPane.showInputDialog("Le numéro actuel de la prochaine facture de l'année est "
								+ num + ".\nEntrez le nouveau numéro :");
						if(saisie==null) return;
						try {
							res = Integer.parseInt(saisie);
							ok = true;
						} catch (Exception ex) {
							System.out.println("not an integer");
						}
					}

					Properties properties = new Properties();

					String propertiesFilePath = (Facture.file);
					FileInputStream fis = new FileInputStream(propertiesFilePath);
					properties.load(fis);
					properties.setProperty(Facture.attr, (res) + "");
					FileOutputStream fos = new FileOutputStream(propertiesFilePath);
					properties.store(fos, null);

				} catch (Exception e1) {
					JOptionPane
							.showMessageDialog(
									null,
									"Fichier properties absent, le programme a rencontrÃ© une erreur et va maintenant se terminer (/data/NumFacture.properties)",
									"Erreur", 0);
					System.exit(ERROR);
				}

			}

		});
		changeProp.setVisible(true);
		contentPane.add(changeProp);

	}

}
