package com.android.qtproject.model;

import java.util.List;

/**
 * qt
 * 2019-09-28
 */
public class Titles {
    private String description;
    private List<Title> allTitles;
    private List<Title> defaults;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Title> getAllTitles() {
        return allTitles;
    }

    public void setAllTitles(List<Title> allTitles) {
        this.allTitles = allTitles;
    }

    public List<Title> getDefaults() {
        return defaults;
    }

    public void setDefaults(List<Title> defaults) {
        this.defaults = defaults;
    }
}
