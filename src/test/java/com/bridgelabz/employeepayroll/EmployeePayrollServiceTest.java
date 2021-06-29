package com.bridgelabz.employeepayroll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.employeepayroll.dto.EmployeePayroll;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.service.impl.EmployeePayrollService;
import com.bridgelabz.employeepayroll.service.impl.FileWatchService;
import com.bridgelabz.employeepayroll.type.IOService;
import com.bridgelabz.employeepayroll.utils.FileUtils;

public class EmployeePayrollServiceTest {
	private static final String HOME = System.getProperty("user.home");
	private static final String PLAY_WITH_NIO = "TempPlayGround";
	EmployeePayrollService employeePayrollService;

	@Before
	public void initialize() {
		employeePayrollService = new EmployeePayrollService();
	}

	@Test
	public void givenPathWhenCheckedthenConfirm() throws IOException {
		Path homePath = Paths.get(HOME);
		Assert.assertTrue(Files.exists(homePath));

		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if (Files.exists(playPath)) {
			FileUtils.deleteFiles(playPath.toFile());
		}
		Assert.assertTrue(Files.notExists(playPath));

		Files.createDirectories(playPath);
		Assert.assertTrue(Files.exists(playPath));

		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(playPath + "/temp" + cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try {
				Files.createFile(tempFile);
			} catch (IOException e) {
			}
			Assert.assertTrue(Files.exists(tempFile));
		});

		Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
		Files.newDirectoryStream(playPath).forEach(System.out::println);
		Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
				.forEach(System.out::println);
	}

	@Test
	public void givenADirectorywhenWatchedListAllTheActivities() throws IOException {
		Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
		new FileWatchService(dir).processEvents();
	}

	@Test
	public void given3EmployeeWhenWrittenToFileShouldMatchEmployeeEntries() throws EmployeePayrollException {
		EmployeePayroll[] arrayOfEmps = { new EmployeePayroll(1, "Ananya", 1000.0),
				new EmployeePayroll(2, "Veena", 2000.0), new EmployeePayroll(3, "Krish", 3000.0) };
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}

	@Test
	public void givenFileOnReadingFromFileShouldMatchEmployeeCount() throws EmployeePayrollException {
		EmployeePayroll employeePayroll = new EmployeePayroll(4, "Anu", 1000.0);
		employeePayrollService.readEmployeePayrollData(employeePayroll);
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(1, entries);
	}
}
