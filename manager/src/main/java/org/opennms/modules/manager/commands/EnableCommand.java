package org.opennms.modules.manager.commands;

import org.opennms.modules.manager.enablers.Enablers;

import java.nio.file.Path;

public class EnableCommand extends LocalCommand {

    @Override
    protected void execute(final Path modulePath) throws Exception {
        Enablers.enable(modulePath);
    }
}
