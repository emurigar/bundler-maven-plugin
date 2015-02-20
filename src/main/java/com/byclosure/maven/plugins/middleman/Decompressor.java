package com.byclosure.maven.plugins.middleman;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

public class Decompressor {
	final static int BUFFER = 2048;

	public static void decompress(String sourceFile, String destinationDir) throws IOException {
		/** create a TarArchiveInputStream object. **/
		FileInputStream fin = new FileInputStream(sourceFile);
		BufferedInputStream in = new BufferedInputStream(fin);
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
		TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn);

		TarArchiveEntry entry = null;

		/** Read the tar entries using the getNextEntry method **/

		while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {

			System.out.println("Extracting: " + entry.getName());

			/** If the entry is a directory, create the directory. **/
			if (entry.isDirectory()) {

				File f = new File(destinationDir, entry.getName());
				f.mkdirs();
			}
			/**
			 * If the entry is a file,write the decompressed file to the disk
			 * and close destination stream.
			 **/
			else {
				final File file = new File(destinationDir, entry.getName());
				file.getParentFile().mkdirs();

				int count;
				byte data[] = new byte[BUFFER];

				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);
				while ((count = tarIn.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.close();

				applyPermissions(entry.getMode(), file);
			}
		}

		/** Close the input stream **/

		tarIn.close();
		System.out.println("untar completed successfully!!");
	}

	private static void applyPermissions(int mode, File file) throws IOException {
		final Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();

		String octal = Integer.toOctalString(mode);
		octal = octal.substring(octal.length() -3);

		String binary = Integer.toBinaryString(Integer.parseInt(octal.substring(0, 1)));
		binary += Integer.toBinaryString(Integer.parseInt(octal.substring(1, 2)));
		binary += Integer.toBinaryString(Integer.parseInt(octal.substring(2, 3)));

		if (binary.charAt(0) == '1') {
			perms.add(PosixFilePermission.OWNER_READ);
		}

		if (binary.charAt(1) == '1') {
			perms.add(PosixFilePermission.OWNER_WRITE);
		}

		if (binary.charAt(2) == '1') {
			perms.add(PosixFilePermission.OWNER_EXECUTE);
		}

		if (binary.charAt(3) == '1') {
			perms.add(PosixFilePermission.GROUP_READ);
		}

		if (binary.charAt(4) == '1') {
			perms.add(PosixFilePermission.GROUP_WRITE);
		}

		if (binary.charAt(5) == '1') {
			perms.add(PosixFilePermission.GROUP_EXECUTE);
		}

		if (binary.charAt(6) == '1') {
			perms.add(PosixFilePermission.OTHERS_READ);
		}

		if (binary.charAt(7) == '1') {
			perms.add(PosixFilePermission.OTHERS_WRITE);
		}

		if (binary.charAt(8) == '1') {
			perms.add(PosixFilePermission.OTHERS_EXECUTE);
		}

		Files.setPosixFilePermissions(Paths.get(file.getPath()), perms);
	}
}
