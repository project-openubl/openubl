package io.github.project.openubl.representations.idm;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ErrorRepresentation {
    private String message;

    public ErrorRepresentation() {
    }

    public ErrorRepresentation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
