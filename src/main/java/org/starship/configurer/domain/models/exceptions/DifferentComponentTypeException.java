package org.starship.configurer.domain.models.exceptions;

public class DifferentComponentTypeException extends RuntimeException {
    public DifferentComponentTypeException(String errorMessage) {
        super(errorMessage);
    }
}
