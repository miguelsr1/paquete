/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.util;

/**
 *
 * @author misanchez
 */
public class ClaveAcceso {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println(String.format("%010", "4"));

        //System.out.println((new RC4Crypter()).encrypt("ha", "rarias14"));
        System.out.println((new RC4Crypter()).decrypt("ha", "4A4F4D5D76193563AE26"));
    }
}
