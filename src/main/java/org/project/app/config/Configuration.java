package org.project.app.config;


import io.github.cdimascio.dotenv.Dotenv;
import lombok.Value;

@Value
public class Configuration {
    Dotenv dotenv = Dotenv.load();

    int app_port = Integer.parseInt(dotenv.get("APP_PORT"));
    String db_user = dotenv.get("DB_USER");
    String db_password = dotenv.get("DB_PASSWORD");
    String db_url = dotenv.get("DB_URL");
}
