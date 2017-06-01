import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;

public class JDOM1 {
	private static Element racine = new Element("clients");

	private static org.jdom.Document document = new Document(racine);
	private static String file = System.getProperty("user.dir" ) + "/data/clients.xml";

	public static void main(String[] args) {
		Client cli = new Client("d", "b", "c", "d", "e", "f");
		// addMapToXML(cli);
		getClientsFromXML();
	}

	private static void lirefichier(String path) {
		SAXBuilder sxb = new SAXBuilder();
		try {

			document = sxb.build(new File(path));
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Fichier xml absent, le programme a rencontré une erreur et va maintenant se terminer (/data/clients.xml):" + path,
							"Erreur", 0);
			System.out.println("1");
			System.exit(0);
		}
		racine = document.getRootElement();

	}

	public static void addMapToXML(Client cli) {
		lirefichier(file);

		Element client = new Element("client");
		racine.addContent(client);

		Element name = new Element("name");
		name.setText(cli.getName());
		client.addContent(name);

		Element street = new Element("street");
		street.setText(cli.getStreet());
		client.addContent(street);

		Element box = new Element("box");
		box.setText(cli.getBox());
		client.addContent(box);

		Element city = new Element("city");
		city.setText(cli.getCity());
		client.addContent(city);

		Element country = new Element("country");
		country.setText(cli.getCountry());
		client.addContent(country);

		Element tva = new Element("tva");
		tva.setText(cli.getTva());
		client.addContent(tva);

		affiche();
		enregistre(file);
	}

	static void affiche() {
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		} catch (java.io.IOException e) {
		}
	}

	static void enregistre(String fichier) {
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		} catch (java.io.IOException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Fichier xml absent, le programme a rencontrÃ© une erreur et va maintenant se terminer (/data/clients.xml)",
					"Erreur", 0);
	System.exit(0);
		}
	}

	public static ArrayList<Client> getClientsFromXML() {
		ArrayList<Client> clients = new ArrayList<Client>();
		lirefichier(file);
		List listmap;
		listmap = racine.getChildren("client");

		Iterator i = listmap.iterator();
		String name;
		String street;
		String box;
		String country;
		String city;
		String tva;
		Client cli;
		while (i.hasNext()) {

			box = "";
			Element courant = (Element) i.next();

			name = courant.getChild("name").getText();
			street = courant.getChild("street").getText();
			box = courant.getChild("box").getText();
			country = courant.getChild("country").getText();
			city = courant.getChild("city").getText();
			tva = courant.getChild("tva").getText();

			cli = new Client(name, street, box, city, country, tva);
			clients.add(cli);
			System.out.println(name + "," + street + "," + box + "," + city + "," + country + "," + tva);
		}

		return clients;

	}

	public static void createXMl(ArrayList<Client> list) {
		if (list.size() != 0) {
			racine = new Element("clients");
			document = new Document(racine);
			Client cli = list.get(0);
			Element client = new Element("client");
			racine.addContent(client);

			Element name = new Element("name");
			name.setText(cli.getName());
			client.addContent(name);

			Element street = new Element("street");
			street.setText(cli.getStreet());
			client.addContent(street);

			Element box = new Element("box");
			box.setText(cli.getBox());
			client.addContent(box);

			Element city = new Element("city");
			city.setText(cli.getCity());
			client.addContent(city);

			Element country = new Element("country");
			country.setText(cli.getCountry());
			client.addContent(country);

			Element tva = new Element("tva");
			tva.setText(cli.getTva());
			client.addContent(tva);
			affiche();
			enregistre(file);
		}
		for (int i = 1; i < list.size(); i++) {
			Client clien = list.get(i);
			addMapToXML(clien);
		}
	}

}
