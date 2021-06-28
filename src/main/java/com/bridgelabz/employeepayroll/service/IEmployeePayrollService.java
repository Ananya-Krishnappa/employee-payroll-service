package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;

public interface IEmployeePayrollService {
	public void readEmployeePayrollData(EmployeePayroll employeePayroll) throws EmployeePayrollException;

	public void writeEmployeePayrollData() throws EmployeePayrollException;
}
