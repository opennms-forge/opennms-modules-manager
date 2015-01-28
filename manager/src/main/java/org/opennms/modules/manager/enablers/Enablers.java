package org.opennms.modules.manager.enablers;

import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.function.Consumer;

public class Enablers {
    public final static Set<Enabler.Factory> ENABLERS = ImmutableSet.<Enabler.Factory>builder()
                                                                    .add(new EventsEnabler.Factory())
                                                                    .add(new DatacollectionEnabler.Factory())
                                                                    .add(new GraphEnabler.Factory())
                                                                    .build();

    private static void walk(final Path modulePath,
                             final Consumer<Enabler> action) throws IOException {
        Files.walkFileTree(modulePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                ENABLERS.stream()
                        .filter(factory -> file.toString().endsWith(factory.getSuffix()))
                        .map(factory -> factory.create(file))
                        .forEach(action);

                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void enable(final Path modulePath) throws IOException {
        Enablers.walk(modulePath,
                      enabler -> enabler.enable());
    }

    public static void disable(final Path modulePath) throws IOException {
        Enablers.walk(modulePath,
                      enabler -> enabler.disable());
    }
}
