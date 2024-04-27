package org.starship.configurer.domain.models.exceptions;

public class NotConfiguredComponentException extends RuntimeException {
    public NotConfiguredComponentException(String errorMessage) {
        super(errorMessage);
    }
}
