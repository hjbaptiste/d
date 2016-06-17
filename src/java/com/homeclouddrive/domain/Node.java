package com.homeclouddrive.domain;

import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonBackReference;

public class Node {

    @JsonBackReference
    private Node parent;
    
    private String name;
    private boolean isDirectory;
    private boolean isFile;
    private String extension;    
    private String lastModified;
    private String dtCreated;
    private ArrayList<Node> fileChildren = new ArrayList();
    private ArrayList<Node> directoryChildren = new ArrayList();
    private String[] extensionIcon;
    private int numFiles;
    private int numDirectories;
    private String path;
    
    

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public boolean isIsFile() {
        return isFile;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(String dtCreated) {
        this.dtCreated = dtCreated;
    }

    public ArrayList<Node> getFileChildren() {
        return fileChildren;
    }

    public void setFileChildren(ArrayList<Node> fileChildren) {
        this.fileChildren = fileChildren;
    }

    public ArrayList<Node> getDirectoryChildren() {
        return directoryChildren;
    }

    public void setDirectoryChildren(ArrayList<Node> directoryChildren) {
        this.directoryChildren = directoryChildren;
    }

    public void addFileChild(Node child) {
        fileChildren.add(child);
    }
    
    public void addDirectoryChild(Node child) {
        directoryChildren.add(child);
    }

    public String[] getExtensionIcon() {
        return extensionIcon;
    }

    public void setExtensionIcon(String[] extensionIcon) {
        this.extensionIcon = extensionIcon;
    }

    public int getNumFiles() {
        return numFiles;
    }

    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

    public int getNumDirectories() {
        return numDirectories;
    }

    public void setNumDirectories(int numDirectories) {
        this.numDirectories = numDirectories;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
