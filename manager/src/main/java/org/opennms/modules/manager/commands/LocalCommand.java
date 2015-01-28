package org.opennms.modules.manager.commands;

import org.kohsuke.args4j.Argument;
import org.opennms.modules.manager.CommandError;
import org.opennms.modules.manager.Main;

import java.nio.file.Files;
import java.nio.file.Path;

public abstract class LocalCommand extends Command {

    @Argument(metaVar = "module",
              required = true)
    private String module;

    @Override
    public void execute() throws Exception {
        final Path path = Main.MODULES_STORE_DIR
                .resolve(this.module);
        if (!Files.exists(path)) {
            throw new CommandError("Module is not installed: " + this.module);
        }

        this.execute(path);
    }

    protected abstract void execute(final Path modulePath) throws Exception;
}
