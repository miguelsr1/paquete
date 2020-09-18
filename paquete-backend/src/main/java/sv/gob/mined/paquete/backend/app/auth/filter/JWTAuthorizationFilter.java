package sv.gob.mined.paquete.backend.app.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import javax.crypto.SecretKey;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pulsar.broker.authentication.utils.AuthTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import sv.gob.mined.paquete.backend.app.auth.SimpleGrantedAuthorityMixin;
import sv.gob.mined.paquete.backend.auth.service.JWTService;
import sv.gob.mined.paquete.backend.auth.service.JWTServiceImpl;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
        String header = request.getHeader("Authorization");

        if (!requiresAuthentication(header)) {
            chain.doFilter(request, response);
        }

        SecretKey secretKey = AuthTokenUtils.createSecretKey(SignatureAlgorithm.HS256);
        Boolean validoToken = false;
        Claims token = null;
        try {
            token = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(header.replace("Bearer ", ""))
                    .getBody();

            validoToken = true;
        } catch (JwtException | IllegalArgumentException e) {

        }

        UsernamePasswordAuthenticationToken authentication = null;

        if (validoToken) {
            String username = token.getSubject();
            Object roles = token.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                    .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

            authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        /*

        if (jwtService.validate(header)) {
            authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
        }*/

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    protected boolean requiresAuthentication(String header) {
        //return !(header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX));

        return !(header == null || !header.startsWith("Bearer "));
    }

}
