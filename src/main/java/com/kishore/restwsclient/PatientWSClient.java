package com.kishore.restwsclient;

import javax.ws.rs.POST;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kishore.restwsclient.model.Patient;

public class PatientWSClient {

	private static final String PATIENT_SERVICE_URL = "http://localhost:8080/restws/services/patientservice";

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(PATIENT_SERVICE_URL).path("/patients").path("/{id}").resolveTemplate("id",
				123);
		Builder request = target.request();
		Patient patient = request.get(Patient.class);
		System.out.println(patient.getId());
		System.out.println(patient.getName());

		patient.setName("mayreddy");

		WebTarget putTarget = client.target(PATIENT_SERVICE_URL).path("/patients");
		Response updateResponse = putTarget.request().put(Entity.entity(patient, MediaType.APPLICATION_XML));
		System.out.println(updateResponse.getStatus());
		updateResponse.close();
		
		Patient newPatient = new Patient();
		newPatient.setName("reddy");
		WebTarget postTarget = client.target(PATIENT_SERVICE_URL).path("/patients");
		Patient createdPatient = postTarget.request().post(Entity.entity(newPatient, MediaType.APPLICATION_XML), Patient.class);
		System.out.println("Created Patient ID " + createdPatient.getId());
		
		
		WebTarget deleteTarget = client.target(PATIENT_SERVICE_URL).path("/patients").path("/123");
		Response deleteResponse = deleteTarget.request().delete();
		System.out.println("Deleted record for 123 " +deleteResponse.getStatus());
		
	}

}
