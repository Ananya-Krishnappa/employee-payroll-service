/**
 * Purpose:Employee Payroll Service
 * @author Ananya K
 * @version 1.0
 * @since 28/06/2021
 * 
 */
package com.bridgelabz.employeepayroll;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.service.impl.EmployeePayrollService;
import com.bridgelabz.employeepayroll.type.IOService;

public class EmployeePayrollServiceApp {
	private static final Logger LOG = LogManager.getLogger(EmployeePayrollService.class);
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws EmployeePayrollException {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(readEmployeePayrollData());
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
	}

	/**
	 * read employee payroll data
	 * 
	 * @return employeePayroll
	 */
	private static EmployeePayroll readEmployeePayrollData() {
		EmployeePayroll employeePayroll = new EmployeePayroll();
		LOG.debug("Enter Employee id");
		int id = scanner.nextInt();
		employeePayroll.setId(id);
		LOG.debug("Enter Employee name");
		String name = scanner.next();
		employeePayroll.setName(name);
		LOG.debug("Enter Employee salary");
		Double salary = scanner.nextDouble();
		employeePayroll.setSalary(salary);
		return employeePayroll;
	}
}
