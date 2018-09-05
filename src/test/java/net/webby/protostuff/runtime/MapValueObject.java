package net.webby.protostuff.runtime;

import io.protostuff.Tag;

public class MapValueObject {

    @Tag(1)
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
