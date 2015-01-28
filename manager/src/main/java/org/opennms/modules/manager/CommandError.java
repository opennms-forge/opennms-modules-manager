package org.opennms.modules.manager;

public class CommandError extends RuntimeException {

    public CommandError(final String message) {
        super(message);
    }
}
