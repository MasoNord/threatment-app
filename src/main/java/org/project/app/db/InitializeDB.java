package org.project.app.db;

import org.project.app.util.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class InitializeDB {
    public void RunScript(Connection connection) {
        try {
            String workingDir = System.getProperty("user.dir");
            ScriptRunner runner = new ScriptRunner(connection, false, true );
            runner.runScript(new BufferedReader(new FileReader(workingDir + "/src/main/java/org/project/app/db/queries/init.sql")));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
