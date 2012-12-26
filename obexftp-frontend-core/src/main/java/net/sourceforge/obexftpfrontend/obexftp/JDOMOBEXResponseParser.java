package net.sourceforge.obexftpfrontend.obexftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import net.sourceforge.obexftpfrontend.model.Configuration;
import net.sourceforge.obexftpfrontend.model.ConfigurationEvent;
import net.sourceforge.obexftpfrontend.model.ConfigurationListener;
import net.sourceforge.obexftpfrontend.model.OBEXElement;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * OBEX response parser implemented using JDOM API.
 * @author Daniel F. Martins
 */
public class JDOMOBEXResponseParser implements OBEXResponseParser, ConfigurationListener {

    /** Logger. */
    private static final Logger log = Logger.getLogger(JDOMOBEXResponseParser.class);
    /** Response processor chain used to remove clutter from the OBEX response. */
    private OBEXResponseProcessorChainMember processorChain;
    /** Entity resolver that is able to validate the OBEX response. */
    private final EntityResolver validationEntityResolver = new EntityResolver() {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(getClass().getResourceAsStream("/obex-folder-listing.dtd"));
        }
    };
    /** Entity resolver that is NOT able to validate the OBEX response. */
    private final EntityResolver plainEntityResolver = new EntityResolver() {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    };
    /** Used to translate the XML document into an object. */
    private SAXBuilder builder;

    /**
     * Create a new instance of JDOMOBEXResponseParser.
     */
    public JDOMOBEXResponseParser() {
        super();

        /* turn on validation */
        builder = new SAXBuilder(true);
        builder.setEntityResolver(validationEntityResolver);

        processorChain = new XMLVersionProcessor().setNextProcessor(new RemoveInvalidCharsProcessor());
    }

    @Override
    public OBEXElement parseResponse(BufferedReader stream, OBEXElement parent) throws OBEXException {
        Document document = null;
        OBEXElement element = new OBEXElement(parent.getName());

        try {
            log.debug("Loading the XML document");
            document = builder.build(processorChain.processResponse(this, stream));

            log.debug("Parsing its children elements");
            for (Object current : document.getRootElement().getChildren()) {
                Element e = (Element) current;
                OBEXElement child = new OBEXElement();

                Attribute name = e.getAttribute("name");
                if (name != null) {
                    child.setName(name.getValue());

                    if (e.getName().equals("file")) {
                        child.setType(OBEXElement.OBEXElementType.FILE);
                    }

                    /* link parent and child elements */
                    element.add(child);
                    child.setParent(element);

                    log.debug("Parsing the " + name + " element");
                }

                Attribute size = e.getAttribute("size");
                if (size != null) {
                    child.setSize(Long.parseLong(size.getValue()));
                }

                Attribute created = e.getAttribute("created");
                if (created != null) {
                    child.setCreated(created.getValue());
                }

                Attribute modified = e.getAttribute("modified");
                if (modified != null) {
                    child.setModified(modified.getValue());
                }

                Attribute accessed = e.getAttribute("accessed");
                if (accessed != null) {
                    child.setAccessed(accessed.getValue());
                }

                Attribute group = e.getAttribute("group");
                if (group != null) {
                    child.setGroup(group.getValue());
                }

                Attribute groupPerm = e.getAttribute("group-perm");
                if (groupPerm != null) {
                    child.setGroupPerm(groupPerm.getValue());
                }

                Attribute owner = e.getAttribute("owner");
                if (group != null) {
                    child.setOwner(owner.getValue());
                }

                Attribute userPerm = e.getAttribute("user-perm");
                if (userPerm != null) {
                    child.setOwnerPerm(userPerm.getValue());
                }

                Attribute otherPerm = e.getAttribute("other-perm");
                if (otherPerm != null) {
                    child.setOtherPerm(otherPerm.getValue());
                }

                log.debug("Element parsed");
            }
        } catch (Exception exc) {
            log.error("Cannot parse the obexftp response", exc);
            throw new OBEXException("Cannot parse the obexftp response", exc);
        }

        return element;
    }

    @Override
    public void configurationChanged(ConfigurationEvent event) {
        Configuration newConfig = event.getConfiguration();
        builder = new SAXBuilder(newConfig.isValidateListing());

        if (newConfig.isValidateListing()) {
            log.debug("Using the default XML entity resolver");
            builder.setEntityResolver(validationEntityResolver);
        } else {
            log.debug("Using the plain XML entity resolver");
            builder.setEntityResolver(plainEntityResolver);
        }
    }

    /**
     * Get the SAXBuilder instance. Used for testing only.
     * @return SAXBuilder object used to parse XML documents.
     */
    protected SAXBuilder getBuilder() {
        return builder;
    }
}