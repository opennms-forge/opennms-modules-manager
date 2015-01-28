package org.opennms.modules.manager.enablers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GraphEnabler extends LinkEnabler {
    public static final String SUFFIX = ".graph.properties";

    public static final Path TARGET_FOLDER = Paths.get("snmp-graph.properties.d/");

    public GraphEnabler(final Factory factory,
                        final Path file) {
        super(factory, file);
    }

    @Override
    protected Path getTargetFolder() {
        return TARGET_FOLDER;
    }

    public static class Factory implements Enabler.Factory {

        @Override
        public String getSuffix() {
            return SUFFIX;
        }

        @Override
        public Enabler create(final Path file) {
            return new GraphEnabler(this, file);
        }
    }
}
