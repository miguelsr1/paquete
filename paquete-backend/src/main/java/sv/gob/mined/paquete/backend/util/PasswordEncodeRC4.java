/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author misanchez
 */
public class PasswordEncodeRC4 implements PasswordEncoder {
    RC4Crypter crypter = new RC4Crypter();

    @Override
    public String encode(CharSequence cs) {
        return crypter.encrypt("ha", cs.toString());
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return crypter.encrypt("ha", cs.toString()).equals(string);
    }

}
