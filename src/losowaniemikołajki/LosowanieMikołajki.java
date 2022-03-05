/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package losowaniemikołajki;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.internal.ws.wsdl.writer.document.Message;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;

/**
 *
 * @author Eduardo
 */
public class LosowanieMikołajki {

    public static List<Osoba> lista;
    public static List<Osoba> listaLosowanie;
    public static List<Osoba> listaLosowanie2;
    
    public static void main(String[] args) throws MessagingException {
        boolean var = false;
        while(!var){
            lista = new ArrayList<>();
            listaLosowanie = new ArrayList<>();
//            listaLosowanie2 = new ArrayList<>();
            pobierzListe();
            var = losowanie();
        }
        String msg="\n\n\n";
        for (Osoba osoba : lista) {
            msg+= osoba.getNazwa() + " (" + osoba.getWylosowana1().getNazwa() + ")\n"; //", " + osoba.getWylosowana2().getNazwa()+")\n";
        }
        System.out.println(msg);
        
        JOptionPane.showMessageDialog(new JFrame(), "Czy wysłać emaile?");
        
        for (Osoba osoba : lista) {
            sendMail(osoba);
            //+= osoba.getNazwa() + " (" + osoba.getWylosowana1().getNazwa() + ", " + osoba.getWylosowana2().getNazwa()+")\n"
                   // + "Wylosowany przez " + osoba.getWylosowanyPrzez1().getNazwa()+ ", " + osoba.getWylosowanyPrzez2().getNazwa()+("\n");
        }
    }
    
    public static void pobierzListe(){
        try {
            File listaPlik = new File("lista.txt");
            if (!listaPlik.exists()) {
                JOptionPane.showMessageDialog(new JFrame(), "File baseIP.txt not found!");
                return;
            }
            
            String linia;
            String nazwa;
            String email;
            Scanner record = new Scanner(listaPlik);
            while (record.hasNextLine()) {
                linia = record.nextLine();
                
                if(linia.contains("<mrg>")){
                    linia = record.nextLine();
                    nazwa = linia.substring(0,linia.indexOf(";"));
                    email = linia.substring(linia.indexOf(";")+1);
                    Osoba os1 = new Osoba(nazwa, email, true);
                    linia = record.nextLine();
                    nazwa = linia.substring(0,linia.indexOf(";"));
                    email = linia.substring(linia.indexOf(";")+1);
                    Osoba os2 = new Osoba(nazwa, email, true);
                    os1.setMalzonek(os2);
                    os2.setMalzonek(os1);
                    lista.add(os1);
                    listaLosowanie.add(os1);
//                    listaLosowanie2.add(os1);
                    lista.add(os2);
                    listaLosowanie.add(os2);
//                    listaLosowanie2.add(os2);
                } else {
                    nazwa = linia.substring(0,linia.indexOf(";"));
                    email = linia.substring(linia.indexOf(";")+1);
                    Osoba os1 = new Osoba(nazwa, email, false);
                    lista.add(os1);
                    listaLosowanie.add(os1);
//                    listaLosowanie2.add(os1);
                    
                }
            }
            record.close();
        } catch (FileNotFoundException ex) {
            System.out.println("PobierzListe: "+ex);
        }
        
    }
    
    public static boolean losowanie(){
        Random generator = new Random();
        int liczba;
        for (Osoba osoba : lista) {
            int i = 0;
                Osoba o1, o2;
//            do{
            do {
                liczba = generator.nextInt(listaLosowanie.size());
                i++;
            }while(i < 5 && (listaLosowanie.get(liczba) == osoba || listaLosowanie.get(liczba)== osoba.getMalzonek()));
            if(i >= 5) return false;
            o1 = listaLosowanie.get(liczba);
            
//            i = 0;
//            do {
//                liczba = generator.nextInt(listaLosowanie2.size());
//                i++;
//            }while(i < 5 && (listaLosowanie2.get(liczba) == osoba || listaLosowanie2.get(liczba)== osoba.getMalzonek() || osoba.getWylosowana1() == listaLosowanie2.get(liczba) ) );
//            if(i >= 5) return false;
//            
//            o2 = listaLosowanie2.get(liczba);
//            }while( o1.getMalzonek() == o2);
            
            osoba.setWylosowana1(o1);
            listaLosowanie.remove(o1);
            
//            osoba.setWylosowana2(o2);
//            listaLosowanie2.remove(o2);
        }
        return true;
    }
    
     public static void sendMail(Osoba osoba) throws MessagingException {    
        Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("swmikolajbojarskich@gmail.com","Haslozydow");
				}
			});

		try {

			MimeMessage message =  new MimeMessage(session);
			message.setFrom(new InternetAddress("Gwiazdka<swmikolajbojarskich@gmail.com>"));
			message.setRecipients(RecipientType.TO,
					InternetAddress.parse(osoba.getEmail()));
			message.setSubject("POPRAWIONA Gwiazdka w rodzinie Błaszczaków");
                        String msg1 = "Osoba wylosowana to " + osoba.getWylosowana1().getNazwa() + ".";
			message.setText(msg1);

			Transport.send(message);

			System.out.println(osoba.getEmail() + " - WYSŁANO");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
   }
    
}
