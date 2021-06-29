package com.bridgelabz.employeepayroll.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;

public class EmployeePayrollFileIOService {
	public static String PAYROLL_FILENAME = "payroll-file.txt";

	/**
	 * Writes bytes to a file. The options parameter specifies howthe file is
	 * created or opened. If no options are present then this methodworks as if the
	 * CREATE, TRUNCATE_EXISTING, and WRITE options are present. In other words,
	 * itopens the file for writing, creating the file if it doesn't exist,
	 * orinitially truncating an existing regular-file toa size of 0. All bytes in
	 * the byte array are written to the file.The method ensures that the file is
	 * closed when all bytes have beenwritten (or an I/O error or other runtime
	 * exception is thrown). If an I/Oerror occurs then it may do so after the file
	 * has been created ortruncated, or after some bytes have been written to the
	 * file.
	 * 
	 * Usage example: By default the method creates a new file oroverwrites an
	 * existing file. Suppose you instead want to append bytesto an existing file:
	 * Path path = ... byte[] bytes = ... Files.write(path, bytes,
	 * StandardOpenOption.APPEND);
	 * 
	 * 
	 * @param employeePayrollList
	 * @throws EmployeePayrollException
	 */
	public void writeData(List<EmployeePayroll> employeePayrollList) throws EmployeePayrollException {
		StringBuffer empBuffer = new StringBuffer();
		employeePayrollList.forEach(employee -> {
			String employeeDataString = employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});

		try {
			Files.write(Paths.get(PAYROLL_FILENAME), empBuffer.toString().getBytes());
		} catch (IOException e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}

	/**
	 * Read all lines from a file as a Stream. Bytes from the file are decoded into
	 * characters using the UTF-8 charset.
	 * 
	 * The returned stream contains a reference to an open file. The file is closed
	 * by closing the stream.
	 * 
	 * The file contents should not be modified during the execution of the terminal
	 * stream operation. Otherwise, the result of the terminal stream operation is
	 * undefined.
	 * 
	 * This method works as if invoking it were equivalent to evaluating the
	 * expression: Files.lines(path, StandardCharsets.UTF_8)
	 * 
	 * @throws EmployeePayrollException
	 */
	public void printData() throws EmployeePayrollException {
		try {
			Files.lines(new File("payroll-file.txt").toPath()).forEach(System.out::println);
		} catch (IOException e) {
			throw new EmployeePayrollException(e.getMessage());
		}
	}

	/**
	 * Returns the count of elements in this stream. This is a special case of a
	 * reduction and is equivalent to: return mapToLong(e -> 1L).sum();
	 * 
	 * This is a terminal operation.
	 * 
	 * @return
	 * @throws EmployeePayrollException
	 */
	public long countEntries() throws EmployeePayrollException {
		long entries = 0;
		try {
			entries = Files.lines(new File("payroll-file.txt").toPath()).count();
		} catch (IOException e) {
			throw new EmployeePayrollException(e.getMessage());
		}
		return entries;
	}
}
