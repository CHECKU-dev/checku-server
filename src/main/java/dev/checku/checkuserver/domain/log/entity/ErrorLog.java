package dev.checku.checkuserver.domain.log.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "error_log")
@Getter
@NoArgsConstructor
public class ErrorLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int status;

    @Lob
    private String errorMessage;



    @Builder
    public ErrorLog(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

}
