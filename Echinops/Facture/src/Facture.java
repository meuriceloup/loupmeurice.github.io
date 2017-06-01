import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretListener;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Facture extends JFrame {

	private JFrame parent;
	private JPanel contentPane;
	private JButton print;
	private JButton back;
	private JTextField text6TVAC;
	private JTextField text21TVAC;
	private JTextField text6VAT;
	private JTextField text21VAT;
	private JTextField text6;
	private JTextField text21;
	private JTextField tot;
	private Client cli;
	private int num;
	public static String file = System.getProperty("user.dir" ) + "/data/NumFacture.properties";
	public static String attr = "NumFacture";

	/**
	 * Launch the application.
	 */
	 public static void main(String[] args) {
	 EventQueue.invokeLater(new Runnable() {
	 public void run() {
	 try {
		 Client cli = new Client("Meurice Loup", "Rue L√©opold, 11", "", "5500 Dinant", "BE", "non assujetti");
	 Facture frame = new Facture(cli,null);
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
	public JPanel getContent() {
		return contentPane;
	}

	public Facture(Client cli, JFrame fr) {
		
		
		super("Facture");
		parent = fr;
		setVisible(true);
		
		
		Properties prop = new Properties(); 
		try { 
		prop.load(new FileInputStream(file)); 
		String Num = prop.getProperty(attr);
		num = Integer.parseInt(Num);
		System.out.println(num);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Fichier properties absent, le programme a rencontr√© une erreur et va maintenant se terminer (/data/NumFacture.properties)", "Erreur", 0);
			System.exit(ERROR);
		}
		
		
		this.cli = cli;
		this.setResizable(false);

		int vertical = (int) (Toolkit.getDefaultToolkit().getScreenSize().height);
		int horizontal = (int) ((int) (vertical / 1.4143));
		// 1.4143 est le rapport entre les dimensions d'une feuille A4

		back = new JButton("Retour");
		back.addActionListener(new ActionListener() {

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
		
		back.setVisible(true);
		back.setBounds(horizontal - (horizontal / 2), 0, (horizontal / 4), vertical / 18);
		
		print = new JButton("Imprimer");
		print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				print();
			}

		});
		print.setVisible(true);
		print.setBounds(horizontal - (horizontal / 4), 0, (horizontal / 4), vertical / 18);
		

		System.out.println(horizontal);
		System.out.println(vertical);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, horizontal, vertical);
		contentPane = new JPanel();

		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(print);
		contentPane.add(back);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, horizontal / 3, vertical / 3);
		contentPane.add(panel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Facture.class.getResource("/image/logo_echinops.png")));
		GridBagConstraints gbc_logoLabel = new GridBagConstraints();
		gbc_logoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_logoLabel.gridx = 1;
		gbc_logoLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_logoLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, (int) (vertical / 3), (int) (horizontal / 2.5), vertical / 5);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel(
				"<html>FranÁoise Richard<br>\r\nRue LÈopold 11<br>\r\n5500 Dinant<br>\r\n<br>\r\nTÈl: 082/226539<br>\r\nTVA BE 722369886<br>\r\nRC. DINANT 40110<br>\r\nFORTIS&nbsp 001-2635597-86<br>\r\nIBAN BE78 0012 6355 9786<br>\r\n</html>\r\n");
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 9));
		lblNewLabel_1.setBounds(horizontal / 10, 0, horizontal / 3, (int) (vertical / 5));
		panel_1.add(lblNewLabel_1);

		JPanel paneAddr = new JPanel();
		paneAddr.setLayout(null);
		paneAddr.setBackground(new Color(240, 240, 240));
		paneAddr.setBounds((int) (vertical / 2.8), vertical / 6, (int) (vertical / 3), (int) (vertical / 7.5));
		contentPane.add(paneAddr);

		JTextArea panel_2 = new JTextArea(cli.getName()+"\r\n"+cli.getStreet()+"\r\n"+cli.getBox()+"\r\n"+cli.getCity()+"\r\n"+cli.getCountry());
		panel_2.setBackground(new Color(240, 240, 240));
		panel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_2.setBounds(horizontal / 40, vertical / 120, (int) (vertical / 3.5), (int) (vertical / 7.5));
		paneAddr.add(panel_2);

		JPanel paneTva = new JPanel();
		paneTva.setBackground(new Color(240, 240, 240));
		paneTva.setLayout(null);
		paneTva.setBounds((int) (vertical / 2.8), (int) (vertical / 3.3), (int) (vertical / 3), vertical / 12);
		contentPane.add(paneTva);
		JLabel tvaDest = new JLabel("  TVA :");
		tvaDest.setBounds(0, 0, (int) (vertical / 20), vertical / 12);
		paneTva.add(tvaDest);
		JTextField Tva = new JTextField("  "+cli.getTva());
		Tva.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tva.setBounds(horizontal / 10, vertical / 40, vertical / 5, vertical / 30);
		Tva.setBackground(new Color(240, 240, 240));
		paneTva.add(Tva);

		JLabel lblNewLabel_2 = new JLabel("FACTURE N∞");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNewLabel_2.setBounds((int) (horizontal / 1.7), (int) (vertical / 2.5), vertical / 5, vertical / 18);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("DINANT, LE");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNewLabel_3.setBounds((int) (horizontal / 1.7), (int) (vertical / 2.5), vertical / 5, vertical / 6);
		contentPane.add(lblNewLabel_3);

		Date actuelle = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yy");
		JTextField lblNewLabel_4 = new JTextField(num+dateFormat.format(actuelle));
		lblNewLabel_4.setEditable(true);
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds((int) (horizontal / 1.35), (int) (vertical / 2.42), vertical / 8, vertical / 36);
		contentPane.add(lblNewLabel_4);

		JTextField lblNewLabel_5 = new JTextField("??/??/????");
		lblNewLabel_5.setBackground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds((int) (horizontal / 1.35), (int) (vertical / 2.13), vertical / 8, vertical / 36);
		contentPane.add(lblNewLabel_5);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		panel_4.setBounds(horizontal / 10, (int) (vertical / 1.87), (int) (horizontal / 1.2), (int) (vertical / 7));
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		JTextArea textArea = new JTextArea("");
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textArea.setBounds(0, 0, (int) (horizontal / 1.2), (int) (vertical / 7));
		textArea.setBorder(BorderFactory.createTitledBorder(""));
		panel_4.add(textArea);

		JLabel lblTva = new JLabel("TVA%");
		lblTva.setBounds(horizontal / 10, (int) (vertical / 1.87), (int) (horizontal / 1.4), (int) (vertical / 3));
		contentPane.add(lblTva);

		JLabel lbl6 = new JLabel("6");
		lbl6.setBounds((int) (horizontal / 2.5), (int) (vertical / 1.87), (int) (horizontal / 1.4),
				(int) (vertical / 3));
		contentPane.add(lbl6);

		JLabel lbl21 = new JLabel("21");
		lbl21.setBounds((int) (horizontal / 1.5), (int) (vertical / 1.87), (int) (horizontal / 1.4),
				(int) (vertical / 3));
		contentPane.add(lbl21);
		
		JLabel lblTVAC = new JLabel("TVAC");
		lblTVAC.setBounds(horizontal / 10, (int) (vertical / 1.75), (int) (horizontal / 1.4), (int) (vertical / 3));
		contentPane.add(lblTVAC);
		
		text6TVAC = new JTextField("0");
		text6TVAC.setBounds((int) (horizontal / 2.5), (int) (vertical / 1.375), vertical / 9, (int) (vertical / 40));
		contentPane.add(text6TVAC);
		
		text21TVAC = new JTextField("0");
		text21TVAC.setBounds((int) (horizontal / 1.5), (int) (vertical / 1.375), vertical / 9, (int) (vertical / 40));
		contentPane.add(text21TVAC);

		JLabel lblBase = new JLabel("BASE");
		lblBase.setBounds(horizontal / 10, (int) (vertical / 1.65), (int) (horizontal / 1.4), (int) (vertical / 3));
		contentPane.add(lblBase);

		text6 = new JTextField("0");
		text6.setEditable(false);
		text6.setBounds((int) (horizontal / 2.5), (int) (vertical / 1.31), vertical / 9, (int) (vertical / 50));
		contentPane.add(text6);

		text21 = new JTextField("0");
		text21.setEditable(false);
		text21.setBounds((int) (horizontal / 1.5), (int) (vertical / 1.31), vertical / 9, (int) (vertical / 50));
		contentPane.add(text21);

		JLabel lblVAT = new JLabel("TVA");
		lblVAT.setBounds(horizontal / 10, (int) (vertical / 1.55), (int) (horizontal / 1.4), (int) (vertical / 3));
		contentPane.add(lblVAT);

		text6VAT = new JTextField("0");
		text6VAT.setEditable(false);
		text6VAT.setBounds((int) (horizontal / 2.5), (int) (vertical / 1.25), vertical / 9, (int) (vertical / 50));
		contentPane.add(text6VAT);

		text21VAT = new JTextField("0");
		text21VAT.setEditable(false);
		text21VAT.setBounds((int) (horizontal / 1.5), (int) (vertical / 1.25), vertical / 9, (int) (vertical / 50));
		contentPane.add(text21VAT);

		JLabel total = new JLabel("MONTANT A PAYER DANS LES 8 JOURS : TOTAL =");
		total.setBounds(horizontal / 10, (int) (vertical / 1.45), (int) (horizontal / 2), (int) (vertical / 3));
		total.setFont(new Font("Tahoma", Font.BOLD, 10));
		contentPane.add(total);

		tot = new JTextField("0");
		tot.setEditable(false);
		tot.setFont(new Font("Tahoma", Font.BOLD, 10));
		tot.setBounds((int) (horizontal / 1.5), (int) (vertical / 1.18), vertical / 15, (int) (vertical / 50));
		contentPane.add(tot);

		JLabel euro = new JLabel("EUROS");
		euro.setBounds((int) (horizontal / 1.3), (int) (vertical / 1.45), (int) (horizontal / 1.2),
				(int) (vertical / 3));
		contentPane.add(euro);

		String condition = "<html>CONDITIONS&nbsp GENERALES&nbsp DE&nbsp VENTE.<br>\r\nLE NON-PAIEMENT D'UNE FACTURE A SON ECHEANCE ENTRAINE DE PLEIN DROIT ET SANS QU'IL SOIT BESOIN D'UNE MISE<br>\r\n EN DEMEURE, LE DEBIT D'INTERETS&nbsp DE&nbsp RETARD AU TAUX&nbsp 1%&nbsp PAR MOIS ET D'UNE INDEMNITE FORFAITEREMENT FIXEE A 20%<br>\r\nDU MONTANT DE LA FACTURE AVEC UN MINIMUM DE 25 EUROS. EN CAS DE LITIGES, LES TRIBUNAUX DE DINANT SONT LES<br>\r\nSEULS&nbsp COMPETENTS.</html>";
		JLabel cond = new JLabel(condition);
		cond.setBounds(horizontal / 10, (int) (vertical / 1.2), horizontal, (int) (vertical / 3));
		cond.setFont(new Font("Tahoma", Font.BOLD, 7));
		contentPane.add(cond);

		// LISTENR SUR LA TVA
		CaretListener caretupdate = new CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent e) {
				JTextField text = (JTextField) e.getSource();
				try {
					if (text == null || text.getText().equals("")) {
						return;
					}
					//System.out.println(Double.parseDouble(text.getText().replaceAll(",", ".").replaceAll("\t", "")));
					DecimalFormat f = new DecimalFormat();
					f.setMaximumFractionDigits(2);
					f.setMinimumFractionDigits(2);
					text6.setText(f.format(Double.parseDouble(((Double.parseDouble(text6TVAC.getText()) / 106)*100)+"")));
					text21.setText(f.format(Double.parseDouble(((Double.parseDouble(text21TVAC.getText()) / 121)*100)+"")));
					
					text6VAT.setText(f.format(Double.parseDouble((((Double.parseDouble(text6TVAC.getText()) / 106)*100)*0.06) + ""))
							+ "");
					text6VAT.setText(text6VAT.getText().replaceAll(",", ".").replaceAll("\t", ""));
					
					text21VAT.setText(f.format(Double.parseDouble((((Double.parseDouble(text21TVAC.getText()) / 121)*100)*0.21) + ""))
							+ "");
					text21VAT.setText(text21VAT.getText().replaceAll(",", ".").replaceAll("\t", ""));
					
					tot.setText(f.format(Double.parseDouble(text6TVAC.getText())+Double.parseDouble(text21TVAC.getText())));
				} catch (java.lang.NumberFormatException e1) {
					System.out
							.println("Attention vous ne pouvez rentrer de lettre dans le montant TVA. Seul le point est accept√©.");
					JOptionPane.showMessageDialog( null, "Attention vous ne pouvez rentrer de lettre dans le montant TVA. Seul le point est accept√©.", "Erreur", 0 );
					// text.setText("0");
				}
			}
		};
		text6TVAC.addCaretListener(caretupdate);
		text21TVAC.addCaretListener(caretupdate);

	}

	public void print() {
		Properties props = new Properties();
		props.setProperty("awt.print.paperSize", "a4");
		PrintJob job = getToolkit().getPrintJob(this, "essai", props);
		if (job != null) {
			/** Recupere le Graphics dans lequel on va ecrire */
			Graphics g = job.getGraphics();
			if (g != null) {
				/** Sur le Container imprime l'ensemble de ses Components */
				getContent().remove(print);
				getContent().remove(back);
				getContent().printAll(g);
				getContent().add(print);
				getContent().add(back);

				g.dispose();
				
				Properties properties = new Properties();
				try {		
					String propertiesFilePath = (file);
					FileInputStream fis = new FileInputStream(propertiesFilePath);
					properties.load(fis);
					properties.setProperty(attr,(num+1)+"");
					num++;
					FileOutputStream fos = new FileOutputStream(propertiesFilePath);
					properties.store(fos,null);
				}
				catch(Exception e) {
					JOptionPane
					.showMessageDialog(
							null,
							"Fichier properties absent, le programme a rencontr√© une erreur et va maintenant se terminer (/data/NumFacture.properties)",
							"Erreur", 0);
			System.exit(ERROR);
				}


			}
			/** Finit le travail */
			job.end();
		}
	}
}
