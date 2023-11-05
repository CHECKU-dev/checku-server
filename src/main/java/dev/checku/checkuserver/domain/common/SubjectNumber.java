package dev.checku.checkuserver.domain.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class SubjectNumber {

    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 9999;

    private  String value;

    public SubjectNumber(String value) {
        validateNotNull(value);
        validateRange(value);
        this.value = value;
    }

    public SubjectNumber() {
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
