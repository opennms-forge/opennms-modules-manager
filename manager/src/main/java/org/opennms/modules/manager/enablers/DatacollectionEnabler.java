package org.opennms.modules.manager.enablers;

import com.google.common.base.Throwables;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatacollectionEnabler extends LinkEnabler {
    public static final String SUFFIX = ".dc.xml";

    public static final Path TARGET_FOLDER = Paths.get("datacollection");

    public static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    public static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();

    public DatacollectionEnabler(final Factory factory,
                                 final Path file) {
        super(factory, file);
    }

    @Override
    protected Path getTargetFolder() {
        return TARGET_FOLDER;
    }

    private String elictGroupName() {
        try (final InputStream is = Files.newInputStream(this.getFile())) {
            final Document document = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().parse(is);
            final XPathExpression expression = X_PATH_FACTORY.newXPath().compile("/datacollection-group/@name");

            return expression.evaluate(document);

        } catch (final IOException | ParserConfigurationException | SAXException | XPathExpressionException ex) {
            throw Throwables.propagate(ex);
        }
    }

    @Override
    public void enable() {
        super.enable();

        System.out.println("Add the following line(s) to 'datacollection-config.xml':");
        System.out.println("  <include-collection dataCollectionGroup=\"" + this.elictGroupName() + "\"/>");
        System.out.println();
    }

    @Override
    public void disable() {
        super.disable();

        System.out.println("Remove the following line(s) from 'datacollection-config.xml':");
        System.out.println("  <include-collection dataCollectionGroup=\"" + this.elictGroupName() + "\"/>");
        System.out.println();
    }

    public static class Factory implements Enabler.Factory {

        @Override
        public String getSuffix() {
            return SUFFIX;
        }

        @Override
        public Enabler create(final Path file) {
            return new DatacollectionEnabler(this, file);
        }
    }
}
