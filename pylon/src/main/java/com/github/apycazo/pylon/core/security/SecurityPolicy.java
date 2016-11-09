package com.github.apycazo.pylon.core.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andres Picazo
 */
@Data
public class SecurityPolicy
{
    private String pattern;
    private List<String> requiredRoles = new ArrayList<>();

    public SecurityPolicy(String pattern)
    {
        if (StringUtils.isEmpty(pattern)) {
            throw new NullPointerException("RestSecurityPolicy pattern can not be null nor empty");
        } else {
            this.pattern = pattern;
        }
    }

    public SecurityPolicy setRequiredRoles(String ... roles)
    {
        if (roles != null) {
            Collections.addAll(requiredRoles, roles);
        }
        return this;
    }

    public boolean requiresRole ()
    {
        return requiredRoles != null && !requiredRoles.isEmpty();
    }

    public boolean allowsRoleToProceed (List<String> roles)
    {
        if (requiredRoles.isEmpty()) {
            return true;
        }
        else {
            Iterator<String> it = roles.iterator();
            boolean isAllowed = false;
            while (!isAllowed && it.hasNext()) {
                isAllowed = requiredRoles.contains(it.next());
            }

            return isAllowed;
        }
    }
}
