/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ClientController;
import jpa.controllers.*;
import jpa.entities.*;
import jpa.exceptions.ElementNoExists;

/**
 *
 * @author rvallez
 */
public class JpaRun {

    EntityManagerFactory emf;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        JpaRun jpa = new JpaRun();
        jpa.start();
        jpa.menu();
        jpa.end();
    }

    private void menu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean end = false;

        Client client = null;
        Factura factura = null;
        Linia linia = null;

        ClientController cctrl = null;
        FacturaController fctrl = null;
        LiniaController lctrl = null;

        printMenu();

        while (!end) {
            System.out.print("> ");
            String line = "";
            try {
                line = br.readLine().toLowerCase();
            } catch (IOException ex) {
                System.err.println("Error al llegir la comanda");
            }
            if (line == null) {
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length == 0) {
                continue;
            }

            switch (parts[0]) {
                case "?":
                    printMenu();
                    break;

                case "addclient":
                    cctrl = new ClientController(this.emf);
                    client = new Client(parts[1]);
                    cctrl.addClient(client);
                    System.out.println("Client " + parts[1] + " afegit");
                    break;

                case "getclients":
                    cctrl = new ClientController(this.emf);
                    System.out.println(cctrl.getClients());
                    break;

                case "deleteclient":
                    cctrl = new ClientController(this.emf);
                    try {
                        cctrl.deleteClient(parts[1]);
                        System.out.println("Client " + parts[1] + " esborrat");
                    } catch (ElementNoExists ex) {
                        System.out.println("No existeix " + parts[1]);
                    }
                    break;
                case "addfactura":
                    if (parts.length <= 2) {
                        System.out.println("Falten parametres");
                        continue;
                    }

                    String nom = parts[1];
                    String data = parts[2];

                    fctrl = new FacturaController(this.emf);
                    try {
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                        fctrl.addFactura(nom, date);
                    } catch (ParseException ex) {
                        System.out.println("La data no té el format correcte");
                    }
                    break;
                case "getfactures":
                    fctrl = new FacturaController(this.emf);
                    System.out.println(fctrl.getFactures());
                    break;
                case "getfacturesde":
                    try {
                        fctrl = new FacturaController(this.emf);
                        System.out.println(fctrl.getFacturesByClient(parts[1]));
                    } catch (ElementNoExists ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "addlinia":
                    Long id = Long.parseLong(parts[1]);
                    String prod = parts[2];
                    Double preu = Double.parseDouble(parts[3]);
                    Double quantitat = Double.parseDouble(parts[4]);

                    lctrl = new LiniaController(this.emf);
                    try {
                        lctrl.addLinia(id, prod, preu, quantitat);
                    } catch (ElementNoExists ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                case "getlinies":
                    lctrl = new LiniaController(this.emf);
                    List<Linia> liniaList = lctrl.getLinies(Long.parseLong(parts[1]));
                    for (Linia liniaFact : liniaList) {
                        System.out.println(liniaFact);
                    }
                    break;
                case "modifylinia":
                    Long idLinia = Long.parseLong(parts[1]);
                    String prodLinia = parts[2];
                    Double preuLinia = Double.parseDouble(parts[3]);
                    Double quantitatLinia = Double.parseDouble(parts[4]);
                    lctrl = new LiniaController(this.emf);
                    lctrl.modifyLinia(idLinia, prodLinia, preuLinia, quantitatLinia);
                    break;
                case "exit":
                    end = true;
                    break;
                default:
                    System.out.println("ajuda? (? ajuda)");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("MENU PRINCIPAL");
        System.out.println("addclient [nom]| getclients | deleteclient [nom]");
        System.out.println("addfactura [nom] [data]| getfactures | getfacturesde [nom]");
        System.out.println("addlinia [id] [prod] [quantitat] [preu]| getlinies [id] | modifylinia [id] [newProd] [newQuant] [newPreu] ");
    }


    private void start() {
        this.emf = Persistence.createEntityManagerFactory("02f_JPA_FacturesPU");
    }

    private void end() {
        emf.close();
        System.out.println("Conexió entity manager factory finalizada");
    }

}
