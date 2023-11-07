package dev.checku.checkuserver.domain.session.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.session.application.port.in.UpdateSessionUseCase;
import dev.checku.checkuserver.domain.session.application.port.out.UpdateSessionPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateSessionService implements UpdateSessionUseCase {

    private final UpdateSessionPort updateSessionPort;

    @Override
    public void update() {
//        getSessionId();
        updateSessionPort.update("");
    }
}
