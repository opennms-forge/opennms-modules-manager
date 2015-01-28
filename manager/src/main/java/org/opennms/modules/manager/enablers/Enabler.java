package org.opennms.modules.manager.enablers;

import java.nio.file.Path;

public abstract class Enabler {

    private final Factory factory;
    private final Path file;

    public Enabler(final Factory factory,
                   final Path file) {
        this.factory = factory;
        this.file = file;
    }

    public final Factory getFactory() {
        return this.factory;
    }

    public final Path getFile() {
        return this.file;
    }

    public final String getSuffix() {
        return this.factory.getSuffix();
    }

    public abstract void enable();
    public abstract void disable();

    public String stripSuffix(final String s) {
        final String suffix = this.getSuffix();

        if (!s.endsWith(suffix)) {
            return s;
        }

        return s.substring(0, s.length() - suffix.length());
    }

    public static interface Factory {
        public abstract String getSuffix();
        public abstract Enabler create(final Path file);
    }
}
