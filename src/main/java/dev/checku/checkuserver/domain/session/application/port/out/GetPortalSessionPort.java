package dev.checku.checkuserver.domain.session.application.port.out;

import dev.checku.checkuserver.domain.session.domain.PortalSession;

public interface GetPortalSessionPort {

    PortalSession get();
}
