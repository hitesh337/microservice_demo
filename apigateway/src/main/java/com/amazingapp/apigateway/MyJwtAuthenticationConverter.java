package com.amazingapp.apigateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.StringUtils;

@Configuration
public class MyJwtAuthenticationConverter extends JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String SCOPE_AUTHORITY_PREFIX = "SCOPE_";

    private static final Collection<String> WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES =
            Arrays.asList("scope", "scp", "authorities"); // added authorities


    
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.getScopes(jwt)
                        .stream()
                        .map(authority -> SCOPE_AUTHORITY_PREFIX + authority)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }

    private Collection<String> getScopes(Jwt jwt) {
      Collection<String> authorities = new ArrayList<>();
        // add to collection instead of returning early
        for ( String attributeName : WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES ) {
            Object scopes = jwt.getClaims().get(attributeName);
            if (scopes instanceof String) {
                if (StringUtils.hasText((String) scopes)) {
                    authorities.addAll(Arrays.asList(((String) scopes).split(" ")));
                }
            } else if (scopes instanceof Collection) {
                authorities.addAll((Collection<String>) scopes);
            }
        }

        return authorities;
    }
}