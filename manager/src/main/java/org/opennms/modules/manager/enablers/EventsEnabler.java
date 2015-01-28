package org.opennms.modules.manager.enablers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EventsEnabler extends LinkEnabler {
    public static final String SUFFIX = ".events.xml";

    public static final Path TARGET_FOLDER = Paths.get("events");

    public EventsEnabler(final Factory factory,
                         final Path file) {
        super(factory, file);
    }

    @Override
    protected Path getTargetFolder() {
        return TARGET_FOLDER;
    }

    @Override
    public void enable() {
        super.enable();

        System.out.println("Add the following line(s) to 'eventconf.xml':");
        System.out.println("  <event-file>" + TARGET_FOLDER + '/' + this.getEscaped() + "</event-file>");
        System.out.println();
    }

    @Override
    public void disable() {
        super.disable();

        System.out.println("Remove the following line(s) from 'eventconf.xml':");
        System.out.println("  <event-file>" + TARGET_FOLDER + '/' + this.getEscaped() + "</event-file>");
        System.out.println();
    }

    public static class Factory implements Enabler.Factory {

        @Override
        public String getSuffix() {
            return SUFFIX;
        }

        @Override
        public Enabler create(final Path file) {
            return new EventsEnabler(this, file);
        }
    }
}
