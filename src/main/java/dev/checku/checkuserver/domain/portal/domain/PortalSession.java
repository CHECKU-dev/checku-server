package dev.checku.checkuserver.domain.portal.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash("portal:session")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortalSession {

    @Id
    private String id;

    private String sessionId;

    public PortalSession(String id, String sessionId) {
        this.id = id;
        this.sessionId = sessionId;
    }
}
