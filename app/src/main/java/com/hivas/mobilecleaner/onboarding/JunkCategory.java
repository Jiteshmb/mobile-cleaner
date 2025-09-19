package com.hivas.mobilecleaner.onboarding;

public class JunkCategory {
    private String name;
    private long size;
    private boolean unlocked;
    private int iconResId;
    private String description;

    public JunkCategory(String name, long size, boolean unlocked, int iconResId, String description) {
        this.name = name;
        this.size = size;
        this.unlocked = unlocked;
        this.iconResId = iconResId;
        this.description = description;
    }

    // Getters
    public String getName() { return name; }
    public long getSize() { return size; }
    public boolean isUnlocked() { return unlocked; }
    public int getIconResId() { return iconResId; }
    public String getDescription() { return description; }

    // Setters
    public void setSize(long size) { this.size = size; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
}
