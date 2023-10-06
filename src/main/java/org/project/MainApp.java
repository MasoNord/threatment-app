package org.project;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args)  {
        get("api/hello", (request, response) -> "Hello world");
    }
}
