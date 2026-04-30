package com.arthdroid1.clinica_api.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_appointment")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "appointment_date_time", nullable = false)
	private LocalDateTime dateTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "appointment_status", nullable = false)
	private AppointmentStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "appointment_type", nullable = false)
	private AppointmentType type;
	
	@Column(name = "cancellation_reason")
	private String cancellationReason;
	
	@ManyToOne
	@JoinColumn(name = "professional_id", nullable = false)
	private Professional professional;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	public Appointment() {
		
	}
	
    public Appointment(Patient patient, Professional professional, LocalDateTime dateTime, AppointmentType type){ 
    	this.patient = patient;
    	this.professional = professional;
    	this.dateTime = dateTime;
    	this.status = AppointmentStatus.SCHEDULED;
    	this.type = type;
    	
    }

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Professional getProfessional() {
		return professional;
	}

	public void setProfessional(Professional professional) {
		this.professional = professional;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}
	
	public String getCancellationReason() {
		return cancellationReason;
	}

	public void cancel(String reason) {
	    this.status = AppointmentStatus.CANCELED;
	    this.cancellationReason = reason;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(id, other.id);
	}
    
    

}
