package org.epsg.openconfigurator.model;

public enum PlkOperationMode {
    NORMAL("NORMAL"), CHAINED("CHAINED"), MULTIPLEXED("MULTIPLEXED");

    private final String text;

    private PlkOperationMode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
