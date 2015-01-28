package org.opennms.modules.manager.commands;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.opennms.modules.manager.enablers.Enablers;

import java.io.PrintWriter;
import java.nio.file.Path;

public class UpdateCommand extends LocalCommand {

    @Override
    public void execute(final Path modulePath) throws Exception {
        Enablers.disable(modulePath);

        Git.open(modulePath.toFile())
           .pull()
           .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
           .call();

        Enablers.enable(modulePath);
    }
}
