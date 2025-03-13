package org.example.expert.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum UserRole implements GrantedAuthority {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String userRole;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UerRole"));
    }

    @Override
    public String getAuthority() {
        return name();
    }


    public static class Authority {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }
}
