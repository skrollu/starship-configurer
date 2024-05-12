package org.starship.configurer.domain.model.exceptions;

public class NotConfiguredComponentException extends RuntimeException {
    public NotConfiguredComponentException(String errorMessage) {
        super(errorMessage);
    }
}
