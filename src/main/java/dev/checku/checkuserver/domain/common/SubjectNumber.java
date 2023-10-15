package dev.checku.checkuserver.domain.common;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubjectNumber {

    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 9999;

    @Column(name = "subject_number", nullable = false, unique = true)
    private String value;

    public SubjectNumber(String value) {
        validateNotNull(value);
        validateRange(value);
        this.value = value;
    }

    private void validateNotNull(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("SubjectNumber must not be null");
        }
    }

    private void validateRange(String value) {
        int number = Integer.parseInt(value);
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException(String.format("SubjectNumber must be between %d and %d", MIN_NUMBER, MAX_NUMBER));
        }
    }
}
