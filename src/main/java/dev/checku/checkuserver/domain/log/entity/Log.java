package dev.checku.checkuserver.domain.log.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "log")
@Getter
@NoArgsConstructor
public class Log extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String methodName;

    @Lob
    private String params;

    private Long executionTime;

    @Builder
    public Log(String methodName, String params, Long executionTime) {
        this.methodName = methodName;
        this.params = params;
        this.executionTime = executionTime;
    }

}
