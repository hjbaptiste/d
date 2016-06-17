package com.homeclouddrive.domain;

import java.util.Date;

public class FamilyTrash {

    private Integer idFamilyTrash;
    private String originalRelativePath;
    private String shareRelativePath;
    private boolean isDirectory;
    private Date dtLastUpdated;

    public Integer getIdFamilyTrash() {
        return idFamilyTrash;
    }

    public void setIdFamilyTrash(Integer idFamilyTrash) {
        this.idFamilyTrash = idFamilyTrash;
    }

    public String getOriginalRelativePath() {
        return originalRelativePath;
    }

    public void setOriginalRelativePath(String originalRelativePath) {
        this.originalRelativePath = originalRelativePath;
    }

    public String getShareRelativePath() {
        return shareRelativePath;
    }

    public void setShareRelativePath(String shareRelativePath) {
        this.shareRelativePath = shareRelativePath;
    }

    public boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public Date getDtLastUpdated() {
        return dtLastUpdated;
    }

    public void setDtLastUpdated(Date dtLastUpdated) {
        this.dtLastUpdated = dtLastUpdated;
    }

    
}
