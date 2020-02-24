package test;

import network.Connect4Server;

public class Connect4ServerRunner implements Runnable {

	private Thread worker;

	public void start() {
		worker = new Thread(this);
		worker.start();
	}

	public void run() {
		new Connect4Server().run();
	}
}
