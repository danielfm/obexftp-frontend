package net.sourceforge.obexftpfrontend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import net.sourceforge.obexftpfrontend.util.StringUtils;
import org.apache.log4j.Logger;

/**
 * Abstraction of files and directories retrieved by an OBEX-enabled device.
 * @author Daniel F. Martins
 */
public class OBEXElement extends DefaultMutableTreeNode {

    /** Logger. */
    private static final Logger log = Logger.getLogger(OBEXElement.class);
    /** OBEX date formatter. */
    private static final OBEXDateFormatter formatter = new OBEXDateFormatter();
    /** Element name. */
    private String name = "";
    /** Element type. */
    private OBEXElementType type = OBEXElementType.FOLDER;
    /** Element size, in bytes */
    private long size;
    /** Creation date. */
    private Date created;
    /** Last modified date. */
    private Date modified;
    /** Last accessed date. */
    private Date accessed;
    /** Group name. */
    private String group = "";
    /** Group permissions. */
    private String groupPerm = "";
    /** Owner name. */
    private String owner = "";
    /** Owner permissions. */
    private String ownerPerm = "";
    /** Other permissions. */
    private String otherPerm = "";

    /**
     * Create a new instance of OBEXElement.
     */
    public OBEXElement() {
        super();
    }

    /**
     * Create a new instance of OBEXElement.
     * @param name Element name.
     */
    public OBEXElement(String name) {
        setName(name);
    }

    /**
     * Get the element name.
     * @return Element name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the element name.
     * @param name Element name.
     */
    public void setName(String name) {

        if (name == null) {
            name = "";
        }

        this.name = name;
    }

    /**
     * Get the element size, in bytes.
     * @return Element size, in bytes.
     */
    public long getSize() {
        return size;
    }

    /**
     * Set the element size, in bytes.
     * @param size Element size, in bytes.
     */
    public void setSize(long size) {
        if (size < 0) {
            size = 0;
        }

        this.size = size;
    }

    /**
     * Get the element type.
     * @return Element type.
     */
    public OBEXElementType getType() {
        return type;
    }

    /**
     * Set the element type.
     * @param type Element type.
     */
    public void setType(OBEXElementType type) {
        if (type == null) {
            type = OBEXElementType.FOLDER;
        }

        this.type = type;
    }

    /**
     * Get the parent element.
     * @return The parent element.
     */
    @Override
    public OBEXElement getParent() {
        return (OBEXElement) super.getParent();
    }

    /**
     * Get a list that contains the children elements.
     * @return List object that contains the children elements.
     */
    public List<OBEXElement> getChildren() {
        List<OBEXElement> list = new ArrayList<OBEXElement>();

        if (children != null) {
            for (Object o : children) {
                list.add((OBEXElement) o);
            }
        }

        return list;
    }

    /**
     * Get the creation date.
     * @return Creation date.
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Set the creation date.
     * @param created Creation date, in a format determined by the
     * OBEX specification.
     */
    public void setCreated(String created) {
        try {
            this.created = null;
            this.created = formatter.parse(created);
        } catch (Exception exc) {
            log.error("Cannot format the given date", exc);
        }
    }

    /**
     * Get the last modified date.
     * @return Last modified date.
     */
    public Date getModified() {
        return modified;
    }

    /**
     * Set the last modified date.
     * @param modified Last modified date, in a format determined by the
     * OBEX specification.
     */
    public void setModified(String modified) {
        try {
            this.modified = null;
            this.modified = formatter.parse(modified);
        } catch (Exception exc) {
            log.error("Cannot format the given date", exc);
        }
    }

    /**
     * Get the last accessed date.
     * @return Last accessed date.
     */
    public Date getAccessed() {
        return accessed;
    }

    /**
     * Set the last accessed date.
     * @param accessed Last accessed date, in a format determined by the
     * OBEX specification.
     */
    public void setAccessed(String accessed) {
        try {
            this.accessed = null;
            this.accessed = formatter.parse(accessed);
        } catch (Exception exc) {
            log.error("Cannot format the given date", exc);
        }
    }

    /**
     * Get the group name.
     * @return Group name.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Set the group name
     * @param group Group name.
     */
    public void setGroup(String group) {
        if (group == null) {
            group = "";
        }

        this.group = group;
    }

    /**
     * Get the group permissions.
     * @return Group permissions.
     */
    public String getGroupPerm() {
        return groupPerm;
    }

    /**
     * Set the group permissions.
     * @param groupPerm Group permissions.
     */
    public void setGroupPerm(String groupPerm) {
        this.groupPerm = parsePerm(groupPerm);
    }

    /**
     * Get the owner name.
     * @return Owner name.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the owner name.
     * @param owner The owner name.
     */
    public void setOwner(String owner) {
        if (owner == null) {
            owner = "";
        }

        this.owner = owner;
    }

    /**
     * Get the owner permissions.
     * @return Owner permissions.
     */
    public String getOwnerPerm() {
        return ownerPerm;
    }

    /**
     * Set the owner permissions.
     * @param ownerPerm Owner permissions.
     */
    public void setOwnerPerm(String ownerPerm) {
        this.ownerPerm = parsePerm(ownerPerm);
    }

    /**
     * Get the other permissions.
     * @return Other permissions.
     */
    public String getOtherPerm() {
        return otherPerm;
    }

    /**
     * Set the other permissions.
     * @param otherPerm Other permisions.
     */
    public void setOtherPerm(String otherPerm) {
        this.otherPerm = parsePerm(otherPerm);
    }

    /**
     * Get an array representation of the current path of this element.
     * @return A String array that represent the path of this
     * element.
     */
    public String[] getStringPath() {
        TreeNode[] path = getPath();
        String[] result = new String[path.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = ((OBEXElement) path[i]).getName();
        }

        return result;
    }

    /**
     * Return a valid permission descriptor based on the given descriptor.
     * @param perm Permission descriptor to parse.
     * @return A valid permission descriptor based on the given descriptor.
     */
    private String parsePerm(String perm) {
        String newPerm = "";

        if (perm == null) {
            perm = "";
        }

        perm = perm.toUpperCase();

        if (perm.length() > 3) {
            perm = perm.substring(0, 3);
        }

        for (int i = 0; i < perm.length(); i++) {
            char ch = perm.charAt(i);
            if ((ch == 'R' || ch == 'W' || ch == 'D') && newPerm.indexOf(ch) == -1) {
                newPerm += ch;
            }
        }

        return newPerm;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OBEXElement other = (OBEXElement) obj;
        if (this.getName() != other.getName() && (this.getName() == null || !this.getName().equals(other.getName()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return new StringBuffer().append(getName()).append(getType() == OBEXElementType.FOLDER ? "" : new StringBuffer().append(" (").append(StringUtils.getCompactFileSize(getSize())).append(")")).toString();
    }

    /**
     * An OBEX element can be classified as files or folders.
     * @author Daniel F. Martins
     */
    public enum OBEXElementType {

        /** File element */
        FILE,
        /** Folder element */
        FOLDER
    }
}