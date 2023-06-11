/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author rvallez
 */
@Entity
@Table(name = "Linies")
public class Linia implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "PRODUCTE", nullable = false)
    private String producte;
    
    @Column(name = "PREU", nullable = false)
    private double preu;
    
    @Column(name = "TOTAL", nullable = false)
    private double quantitat;
    
    @ManyToOne
    @JoinColumn(name = "FK_FACTURA", nullable = true, updatable = false)
    private Factura factura;

    public Linia() {
        
    }
    
    public Linia(String product, double preu, double quantitat) {
        this.producte = product;
        this.preu = preu;
        this.quantitat = quantitat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducte() {
        return producte;
    }

    public void setProducte(String producte) {
        this.producte = producte;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public double getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(double quantitat) {
        this.quantitat = quantitat;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.producte);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.preu) ^ (Double.doubleToLongBits(this.preu) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.quantitat) ^ (Double.doubleToLongBits(this.quantitat) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.factura);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Linia other = (Linia) obj;
        if (Double.doubleToLongBits(this.preu) != Double.doubleToLongBits(other.preu)) {
            return false;
        }
        if (Double.doubleToLongBits(this.quantitat) != Double.doubleToLongBits(other.quantitat)) {
            return false;
        }
        if (!Objects.equals(this.producte, other.producte)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.factura, other.factura)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Linia{" + "id=" + id + ", producte=" + producte + ", preu=" + preu + ", quantitat=" + quantitat + "}";
    }

    
}