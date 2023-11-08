package dev.checku.checkuserver.domain.session.domain;

import lombok.Getter;

@Getter
public class PortalSession {

    private final String value;

    public PortalSession(String value) {
        this.value = value;
    }
}
