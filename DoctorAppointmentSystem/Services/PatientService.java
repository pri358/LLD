package Services;

import java.util.*;
import Model.*; 

public class PatientService {
    public static Map<String, Patient> patients; 
    // singleton
    private static PatientService patientService; 

    private PatientService()
    {
        patients = new HashMap<>();
    }

    public static synchronized PatientService getInstance()
    {
        if(patientService == null) patientService = new PatientService();
        return patientService; 
    }

    public void registerPatient(String name)
    {
        Patient patient = new Patient(name); 
        patients.put(patient.getName(), patient);
        System.out.println("Patient: " + patient.getName() + " registerd!!");
    }

    public Patient getPatient(String name)
    {
        if(!patients.containsKey(name)) 
        {
            System.out.println("Patient doesnot exist, please rgeister first!");
            return null;
        }
        return patients.get(name);
    }

}
