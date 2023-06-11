/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.exceptions;

/**
 *
 * @author rvallez
 */
public class ElementNoExists extends Exception {
    
    public ElementNoExists() {
        super();
    }
    
    public ElementNoExists(String msg) {
        super(msg);
    }
}
