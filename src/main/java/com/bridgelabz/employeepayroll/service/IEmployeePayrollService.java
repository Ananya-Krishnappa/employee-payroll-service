package com.bridgelabz.employeepayroll.service;

import java.util.List;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.type.IOService;

public interface IEmployeePayrollService {
	public List<EmployeePayroll> readEmployeePayrollData(EmployeePayroll employeePayroll)
			throws EmployeePayrollException;

	public void writeEmployeePayrollData(IOService ioService) throws EmployeePayrollException;

	public void printData(IOService ioService) throws EmployeePayrollException;

	public long countEntries(IOService ioService) throws EmployeePayrollException;
}
