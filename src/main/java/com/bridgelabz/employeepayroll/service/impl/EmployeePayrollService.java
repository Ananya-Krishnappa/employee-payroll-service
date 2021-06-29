package com.bridgelabz.employeepayroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.service.IEmployeePayrollService;
import com.bridgelabz.employeepayroll.type.IOService;

public class EmployeePayrollService implements IEmployeePayrollService {
	private static final Logger LOG = LogManager.getLogger(EmployeePayrollService.class);
	private List<EmployeePayroll> employeePayrollList = new ArrayList<EmployeePayroll>();
	private EmployeePayrollFileIOService employeePayrollFileIOService = new EmployeePayrollFileIOService();

	public EmployeePayrollService() {

	}

	public EmployeePayrollService(List<EmployeePayroll> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}

	/**
	 * This function reads the file from the console
	 */
	@Override
	public List<EmployeePayroll> readEmployeePayrollData(EmployeePayroll employeePayroll)
			throws EmployeePayrollException {
		try {
			employeePayrollList.add(employeePayroll);
			return employeePayrollList;
		} catch (Exception e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}

	/**
	 * This function writes the file to the console.
	 */
	@Override
	public void writeEmployeePayrollData(IOService ioService) throws EmployeePayrollException {
		try {
			switch (ioService) {
			case CONSOLE_IO:
				LOG.debug("Employee payroll data " + employeePayrollList.toString());
				break;
			case FILE_IO:
				employeePayrollFileIOService.writeData(employeePayrollList);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}

	@Override
	public void printData(IOService ioService) throws EmployeePayrollException {
		try {
			switch (ioService) {
			case FILE_IO:
				employeePayrollFileIOService.printData();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}

	@Override
	public long countEntries(IOService ioService) throws EmployeePayrollException {
		try {
			switch (ioService) {
			case FILE_IO:
				return employeePayrollFileIOService.countEntries();
			default:
				break;
			}
		} catch (Exception e) {
			throw new EmployeePayrollException(e.getMessage());
		}
		return 0;

	}
}
