package sv.gob.mined.seguridadapi.auth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author misanchez
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;
    
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        
        username = username.trim();
        
        if (username != null && password != null) {
            logger.info("Usuario: " + username);
            logger.info("Password: " + password);
        }
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        
        return authenticationManager.authenticate(authToken);
    }
    
}
