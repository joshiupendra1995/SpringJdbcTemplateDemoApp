package com.upendra.springjdbctemplatedemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.upendra.springjdbctemplatedemo.model.Employee;
import com.upendra.springjdbctemplatedemo.repository.EmployeeDAO;

/**
 * @author Upendra
 *
 */
@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	EmployeeDAO employeedao;

	/**
	 * @param empname
	 * @return
	 * @throws AppException
	 */
	@GetMapping("/getdata/{id}")
	public ResponseEntity<Employee> getById(@PathVariable("id") int id) {

		Employee namelist = employeedao.getById(id);

		return new ResponseEntity<Employee>(namelist, HttpStatus.OK);

	}

	/**
	 * @return
	 */

	@RequestMapping(value = "/getAllEmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmp() {
		List<Employee> list = employeedao.getAll();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	/**
	 * @param employee
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		employeedao.update(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	/**
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "text/plain")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		employeedao.save(employee);
		return new ResponseEntity<String>("Success", HttpStatus.OK);

	}

	/**
	 * @param empid
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = "text/plain")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
		employeedao.deleteById(id);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}

}
