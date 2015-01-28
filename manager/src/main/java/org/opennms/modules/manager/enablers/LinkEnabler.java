package org.opennms.modules.manager.enablers;

import com.google.common.base.Throwables;
import com.google.common.escape.Escaper;
import com.google.common.net.PercentEscaper;
import org.opennms.modules.manager.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class LinkEnabler extends Enabler {

    private static final Escaper ESCAPER = new PercentEscaper("-_.~ ", false);

    private final Path relative;
    private final Path escaped;
    private final Path target;

    public LinkEnabler(final Factory factory,
                       final Path file) {
        super(factory,
              file);

        this.relative = Main.MODULES_STORE_DIR
                .relativize(file)
                .normalize();

        this.escaped = file
                .getFileSystem()
                .getPath(ESCAPER.escape(this.stripSuffix(relative.toString())) + this.getSuffix());

        this.target = Main.MODULES_TARGET_DIR
                .resolve(this.getTargetFolder())
                .resolve(this.escaped);
    }

    protected abstract Path getTargetFolder();

    public final Path getEscaped() {
        return this.escaped;
    }

    public final Path getTarget() {
        return this.target;
    }

    @Override
    public void enable() {
        if (Files.exists(this.getTarget())) {
            return;
        }

        try {
            Files.createSymbolicLink(this.getTarget(),
                                     this.getFile());

        } catch (final IOException ex) {
            throw Throwables.propagate(ex);
        }
    }

    @Override
    public void disable() {
        if (!Files.exists(this.getTarget())) {
            return;
        }

        try {
            Files.delete(this.getTarget());

        } catch (final IOException ex) {
            throw Throwables.propagate(ex);
        }
    }
}
