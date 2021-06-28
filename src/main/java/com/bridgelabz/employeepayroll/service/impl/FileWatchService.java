package com.bridgelabz.employeepayroll.service.impl;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileWatchService {
	private static final Logger LOG = LogManager.getLogger(FileWatchService.class);
	private final WatchService watcher;
	private final Map<WatchKey, Path> dirWatchers;

	public FileWatchService(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dirWatchers = new HashMap<WatchKey, Path>();
		scanAndRegisterDirectories(dir);
	}

	/**
	 * Registers the given directory with the WatchService
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private void registerDirWatchers(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		dirWatchers.put(key, dir);
	}

	/**
	 * Register the given directory with the WatchService
	 * 
	 * @param start
	 * @throws IOException
	 */
	private void scanAndRegisterDirectories(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				registerDirWatchers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Process all events for keys queued to the watchers
	 */
	public void processEvents() {
		while (true) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				return;
			}
			Path dir = dirWatchers.get(key);
			if (dir == null) {
				continue;
			}
			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				Path name = ((WatchEvent<Path>) event).context();
				Path child = dir.resolve(name);
				LOG.debug("Event kind name and child " + event.kind().name(), child);

				if (kind == ENTRY_CREATE) {
					try {
						if (Files.isDirectory(child)) {
							scanAndRegisterDirectories(child);
						}
					} catch (IOException e) {

					}
				} else if (kind.equals(ENTRY_DELETE)) {
					if (Files.isDirectory(child)) {
						dirWatchers.remove(key);
					}
				}
				boolean valid = key.reset();
				if (!valid) {
					dirWatchers.remove(key);
					if (dirWatchers.isEmpty()) {
						break;
					}
				}
			}
		}
	}
}
