package dev.checku.checkuserver.domain.portal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash("portalSession")
@Getter
@AllArgsConstructor
public class PortalSession {

    @Id
    private String id;

    private String session;

    public void updateSession(String session) {
        this.session = session;
    }
}
