package test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Main;

class MainTest {
	Scanner in;
	Main main1;
	InputStream sysInBackup;

	@BeforeEach
	void setUp() throws Exception {
		sysInBackup = System.in;
	}

	@AfterEach
	void tearDown() throws Exception {
		in.close();
		System.setIn(sysInBackup);
	}

	@Test
	void testGetGui1() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream("T\n".getBytes());
		System.setIn(inputStream);
		in = new Scanner(System.in);
		main1 = new Main();
		assert (!main1.getGui(in));
	}

	@Test
	void testGetGui2() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream("A\nT\n".getBytes());
		System.setIn(inputStream);
		in = new Scanner(System.in);
		main1 = new Main();
		assert (!main1.getGui(in));

	}

	@Test
	void testGetGui3() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream("G\n".getBytes());
		System.setIn(inputStream);
		in = new Scanner(System.in);
		main1 = new Main();
		assert (main1.getGui(in));
	}
}
