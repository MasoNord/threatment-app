package org.project.app.util;

public class ValidUUID {
    private final String schema = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    public boolean validate(String id) {
        return id.matches(schema);
    }
}
