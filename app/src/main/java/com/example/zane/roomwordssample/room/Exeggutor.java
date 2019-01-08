package com.example.zane.roomwordssample.room;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Exeggutor {

	private static final Object LOCK = new Object();
	private static Exeggutor exeggutor;
	private final Executor diskIo;
	private final Executor networkIo;
	private final Executor mainThread;

	public Exeggutor(Executor diskIo, Executor networkIo, Executor mainThread) {
		this.diskIo = diskIo;
		this.networkIo = networkIo;
		this.mainThread = mainThread;
	}

	public static Exeggutor getExeggutor() {
		if (exeggutor == null) {
			synchronized (LOCK) {
				exeggutor = new Exeggutor(
								Executors.newSingleThreadExecutor(),
								Executors.newFixedThreadPool(3),
								new MainThreadExecutor());
			}
		}
		return exeggutor;
	}

	public Executor diskIo() {return diskIo;}
	public Executor mainThread() {return mainThread;}
	public Executor networkIo() {return networkIo;}

	private static class MainThreadExecutor implements Executor {
		private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

		@Override
		public void execute(@NonNull Runnable command) {
			mainThreadHandler.post(command);
		}
	}
}
