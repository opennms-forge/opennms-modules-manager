package org.opennms.modules.manager.commands;

import com.google.common.base.Throwables;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GitHub;
import org.opennms.modules.manager.Main;

import java.io.IOException;

public abstract class Command {

    private final GitHub connection;

    private final GHOrganization organization;

    protected Command() {
        try {
            this.connection = GitHub.connectAnonymously();

            this.organization = this.connection.getOrganization(Main.GITHUB_ORGANISATION_NAME);
            if (this.organization == null) {
                throw new RuntimeException("No such GitHub organisation: " + Main.GITHUB_ORGANISATION_NAME);
            }

        } catch (final IOException ex) {
            throw Throwables.propagate(ex);
        }
    }

    public abstract void execute() throws Exception;

    protected GHOrganization getOrganization() {
        return this.organization;
    }
}
