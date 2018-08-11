package com.upendra.springjdbctemplatedemo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Upendra
 *
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid")
	private int pid;

	@Column(name = "projectName")
	private String projectName;
	
	private Employee employee;

	public Project() {

	}

	public Project(int pid, String projectName) {
		super();
		this.pid = pid;
		this.projectName = projectName;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	@OneToMany(mappedBy="Project")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Project [pid=" + pid + ", projectName=" + projectName + "]";
	}

}
