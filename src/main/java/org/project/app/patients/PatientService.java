package org.project.app.patients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.app.modules.HealthProblem;
import org.project.app.patients.entities.Patient;
import spark.Request;

import java.io.IOException;
import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class PatientService {
    private final ArrayList<Patient> patients = new ArrayList<Patient>();
    private final ObjectMapper m = new ObjectMapper();

    public Patient create(Request request) throws IOException {
        JsonNode node = m.readTree(request.body());
        HealthProblem hp;
        Patient patient;

        try {
            hp = HealthProblem.builder()
                    .name(node.get("hp").get("name").toString())
                    .degree(Integer.parseInt(node.get("hp").get("degree").toString()))
                    .build();

            patient = Patient.builder()
                    .id(UUID.randomUUID().toString())
                    .dob(node.get("dob").toString())
                    .sex(node.get("sex").toString())
                    .hp(hp)
                    .name(node.get("name").toString())
                    .dc(LocalDate.now().toString())
                    .du(LocalDate.now().toString())
                    .build();
        }catch (NullPointerException e) {
            return null;
        }
        this.patients.add(patient);

        return patient;
    }
    public ArrayList<Patient> getAll() {

        return this.patients;
    }
    public Patient getByID(String id) {
        AtomicReference<Patient> p = new AtomicReference<>(null);

        patients.forEach(patient -> {
            if (patient.getId().equals(id))
                p.set(patient);
        });
        return p.get();
    }
    public Patient update(Request request, String id) throws IOException {
        JsonNode node = m.readTree(request.body());
        Patient p = getByID(id);
        int pIndex = patients.indexOf(p);
        HealthProblem hp = HealthProblem.builder()
                .name(p.getHp().getName())
                .degree(p.getHp().getDegree())
                .build();

        if (node.get("hp") != null) {
             hp = HealthProblem.builder()
                    .name((node.get("hp").get("name") != null) ? node.get("hp").get("name").toString() : p.getHp().getName())
                    .degree((node.get("hp").get("degree") != null) ? Integer.parseInt(node.get("hp").get("degree").toString()) : p.getHp().getDegree())
                    .build();
        }

        Patient updatedPatient = Patient.builder()
                .sex((node.get("sex") != null) ? node.get("sex").toString() : p.getSex())
                .dob((node.get("dob")!= null) ? node.get("dob").toString() : p.getDob())
                .hp(hp)
                .name((node.get("name") != null) ? node.get("name").toString(): p.getName())
                .du(LocalDate.now().toString())
                .id(p.getId())
                .dc(p.getDc())
                .build();

        patients.set(pIndex, updatedPatient);
        return updatedPatient;
    }
    public void delete(String id) {
        Patient p = getByID(id);
        patients.remove(p);
    }
}
