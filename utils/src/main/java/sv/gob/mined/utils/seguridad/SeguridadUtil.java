/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.utils.seguridad;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author MISanchez
 */
public class SeguridadUtil {
    public static String encriptar(String cadena) {
    return DigestUtils.md5Hex(cadena);
  }
}
