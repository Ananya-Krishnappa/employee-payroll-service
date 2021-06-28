package com.bridgelabz.employeepayroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.service.IEmployeePayrollService;

public class EmployeePayrollService implements IEmployeePayrollService {
	private static final Logger LOG = LogManager.getLogger(EmployeePayrollService.class);
	private List<EmployeePayroll> employeePayrollList = new ArrayList<EmployeePayroll>();

	public EmployeePayrollService() {

	}

	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}

	/**
	 * This function reads the file from the console
	 */
	@Override
	public void readEmployeePayrollData(EmployeePayroll employeePayroll) throws EmployeePayrollException {
		try {
			employeePayrollList.add(employeePayroll);
		} catch (Exception e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}

	/**
	 * This function writes the file to the console.
	 */
	@Override
	public void writeEmployeePayrollData() throws EmployeePayrollException {
		try {
			LOG.debug("Employee payroll data " + employeePayrollList.toString());
		} catch (Exception e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}
}
