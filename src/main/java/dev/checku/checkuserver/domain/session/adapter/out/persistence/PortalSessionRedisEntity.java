package dev.checku.checkuserver.domain.session.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@RedisHash("portal:session")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortalSessionRedisEntity {

    @Id
    private String id;

    private String sessionId;

    public PortalSessionRedisEntity(String id, String sessionId) {
        this.id = id;
        this.sessionId = sessionId;
    }
}
