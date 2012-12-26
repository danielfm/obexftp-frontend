package net.sourceforge.obexftpfrontend.model;

import java.io.Serializable;

/**
 * This class represents a file category mapping in the application. An
 * example of such a category is 'Photo' which represents all image file
 * extensions.
 * @author Daniel F. Martins
 */
public class FileMapping implements Serializable {

    /** File category. */
    private FileCategory category;
    /** Mapping description. */
    private String name;
    /** File extensions, separated by spaces. */
    private String extensions;

    /**
     * Create a new instance of FileMapping.
     */
    public FileMapping() {
        super();
    }

    /**
     * Create a new instance of FileMapping.
     * @param category File category.
     * @param name Mapping description.
     * @param extensions File extensions.
     */
    public FileMapping(FileCategory category, String name, String extensions) {
        this();
        this.category = category;
        this.name = name;
        this.extensions = extensions;
    }

    /**
     * Get the file category.
     * @return File category.
     */
    public FileCategory getCategory() {
        return category;
    }

    /**
     * Set the file category.
     * @param category File category.
     */
    public void setCategory(FileCategory category) {
        this.category = category;
    }

    /**
     * Get the file mapping name, which can be considered as a text
     * description of the file category; something meaninful.
     * @return File mapping name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the file mapping name, which can be considered as a text
     * description of the file category; something meaninful.
     * @param name File mapping name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the file extensions that matches the category.
     * @return File extensions that matches the category, each one separated
     * by a space.
     */
    public String getExtensions() {
        return extensions;
    }

    /**
     * Set the file extensions that matches the category.
     * @param extensions File extensions that matches the category, each one
     * separated by a space.
     */
    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    /**
     * Return a clone of this object.
     * @return A clone.
     */
    public FileMapping copy() {
        FileMapping mapping = new FileMapping();

        mapping.setCategory(getCategory());
        mapping.setExtensions(getExtensions());
        mapping.setName(getName());

        return mapping;
    }
}