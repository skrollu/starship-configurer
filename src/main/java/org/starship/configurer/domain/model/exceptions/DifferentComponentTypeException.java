package org.starship.configurer.domain.model.exceptions;

public class DifferentComponentTypeException extends RuntimeException {
    public DifferentComponentTypeException(String errorMessage) {
        super(errorMessage);
    }
}
