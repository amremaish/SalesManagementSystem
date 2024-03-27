package com.sales.dao.entites;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sales.util.DateFormat;
import com.sales.validation.UniquePhoneNumber;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@NotNull(message = "password must not be empty")
	@JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@UniquePhoneNumber
	@NotNull(message = "phone number must not be empty")
	@Pattern(regexp="^[0-9]{9,15}$", message="Phone number must contain only numeric characters and be between 9 and 15 digits")
	private String phoneNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.DATE_TIME)
	@Column(name = "created_at")
	private Date created_at;

	public User() {
		this.created_at = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
}
