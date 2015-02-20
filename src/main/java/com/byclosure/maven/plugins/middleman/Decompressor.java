package com.byclosure.maven.plugins.middleman;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;

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
			}
		}

		/** Close the input stream **/

		tarIn.close();
		System.out.println("untar completed successfully!!");
	}
}
