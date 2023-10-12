package org.project.app.patients;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.app.db.CreateConnection;

import org.project.app.modules.HealthProblem;
import org.project.app.patients.entities.Patient;
import org.project.app.util.ValidateData;
import spark.Request;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import java.time.LocalDate;

public class PatientService {
    private final ArrayList<Patient> patients = new ArrayList<Patient>();
    private final ObjectMapper m = new ObjectMapper();
    private final CreateConnection createConnection = new CreateConnection();
    private final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    public Patient create(Request request) throws IOException {
        JsonNode node = m.readTree(request.body());

        ValidateData v = new ValidateData(node.get("dob").asText(), node.get("sex").asText(), node.get("hp").get("degree").asInt());
        Patient patient = null;
        if (!v.validate())
            return null;

        try {
            Statement st = this.createConnection.getConnection().createStatement();
            HealthProblem hp = HealthProblem.builder()
                    .name(node.get("hp").get("name").asText())
                    .degree(Integer.parseInt(node.get("hp").get("degree").toString()))
                    .build();

            patient = Patient.builder()
                    .id(UUID.randomUUID().toString())
                    .dob(node.get("dob").asText())
                    .sex(node.get("sex").asText())
                    .hp(hp)
                    .name(node.get("name").asText())
                    .dc(currentTimestamp.toString())
                    .du(currentTimestamp.toString())
                    .build();

            st.execute("INSERT INTO patients(id, dob, sex, name, dc, du) VALUES ( " +
                    "'" + patient.getId() + "'" + "," +
                    "'" + patient.getDob() + "'" + "," +
                    "'" + patient.getSex() + "'" + "," +
                    "'" + patient.getName()+ "'" + "," +
                    "'" + patient.getDc()+ "'" + "," +
                    "'" + patient.getDu()+ "'"+
                    ")");

            st.execute("INSERT INTO HealthProblems(diseaseName, degree, ownerId) VALUES (" +
                    "'" +hp.getName()+ "'" + "," +
                    "'" +hp.getDegree()+ "'" + "," +
                    "'" +patient.getId() + "'"
                    + ")");

        }catch (NullPointerException e) {
            return null;
        }catch (SQLException e) {
            System.out.println(e);
        }

        return patient;
    }
    public ArrayList<Patient> getAll() {
        ArrayList<Patient> pat = new ArrayList<Patient>();

        try {
            Statement st = this.createConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients FULL OUTER JOIN HealthProblems ON HealthProblems.ownerId = patients.id");
            while (rs.next()) {
                HealthProblem hp = HealthProblem.builder()
                        .degree(rs.getInt("degree"))
                        .name(rs.getString("diseaseName"))
                        .build();

                Patient patient = Patient.builder()
                        .id(rs.getString("id"))
                        .name(rs.getString("name"))
                        .dob(rs.getString("dob"))
                        .hp(hp)
                        .sex(rs.getString("sex"))
                        .du(rs.getString("du"))
                        .dc(rs.getString("dc"))
                        .build();
                pat.add(patient);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return pat;
    }
    public Patient getByID(String id) {
        Patient patient = null;
        try {
            Statement st = this.createConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients JOIN HealthProblems ON HealthProblems.ownerId = patients.id WHERE id=" + "'" + id + "'");
            while (rs.next()) {
                HealthProblem hp = HealthProblem.builder()
                        .degree(rs.getInt("degree"))
                        .name(rs.getString("diseaseName"))
                        .build();
                patient = Patient.builder()
                           .id(rs.getString("id"))
                           .name(rs.getString("name"))
                           .dob(rs.getString("dob"))
                           .hp(hp)
                           .sex(rs.getString("sex"))
                           .du(rs.getString("du"))
                           .dc(rs.getString("dc"))
                           .build();
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        return patient;
    }
    public Patient update(Request request, String id) throws IOException {
        JsonNode node = m.readTree(request.body());
        Patient p = getByID(id);
        HealthProblem hp = HealthProblem.builder()
                .name(p.getHp().getName())
                .degree(p.getHp().getDegree())
                .build();
        if (node.get("hp") != null) {
             hp = HealthProblem.builder()
                    .name((node.get("hp").get("name") != null) ? node.get("hp").get("name").asText() : p.getHp().getName())
                    .degree((node.get("hp").get("degree") != null) ? node.get("hp").get("degree").asInt() : p.getHp().getDegree())
                    .build();
        }
        Patient updatedPatient = Patient.builder()
                .sex((node.get("sex") != null) ? node.get("sex").asText() : p.getSex())
                .dob((node.get("dob")!= null) ? node.get("dob").asText() : p.getDob())
                .hp(hp)
                .name((node.get("name") != null) ? node.get("name").asText(): p.getName())
                .du(currentTimestamp.toString())
                .id(p.getId())
                .dc(p.getDc())
                .build();

        ValidateData v = new ValidateData(updatedPatient.getDob(), updatedPatient.getSex(), hp.getDegree());

        if (!v.validate())
            return null;

        try {
            Statement st = this.createConnection.getConnection().createStatement();
            st.execute("UPDATE patients SET " +
                    "name = " + "'" + updatedPatient.getName() + "'," +
                    "du = " +  "'" + updatedPatient.getDu() + "'," +
                    "sex = " + "'" + updatedPatient.getSex() + "'," +
                    "dob = " + "'" + updatedPatient.getDob() + "'" +
                    "WHERE id = " + "'" + updatedPatient.getId() + "'");
            st.execute("UPDATE HealthProblems SET " +
                    "diseaseName = " + "'" + hp.getName() + "'," +
                    "degree = " + "'" + hp.getDegree() + "'" +
                    "WHERE ownerId = " + "'" + updatedPatient.getId() + "'");
        }catch (SQLException e) {
            System.out.println(e);
        }
        return updatedPatient;
    }
    public void delete(String id) {
        try {
            Statement st = this.createConnection.getConnection().createStatement();
            st.execute("DELETE FROM HealthProblems WHERE ownerId = " + "'" + id + "'");
            st.execute("DELETE FROM patients WHERE id = " + "'" + id + "'");
        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
