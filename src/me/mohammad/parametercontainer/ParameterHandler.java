package me.mohammad.parametercontainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class ParameterHandler {
	
	private static Consumer<String> invalid;
	
	static {
		invalid = (key) -> {};
	}

	/**
	 * Sets the invalid consumer of the validation process
	 * 
	 * The invalid consumer will be accepted whenever the ParameterContainer.validate(String...) method returns false
	 * 
	 * @param invalid the new consumer to set
	 * 
	 * */

	public static void setOnInvalid(final Consumer<String> invalid) {
		ParameterHandler.invalid = invalid;
	}
	
	/**
	 * Accepts the invalidation (or "invalid") consumer, with the given key
	 * 
	 * @param key the key to accept the consumer with.
	 * 
	 * */
	
	protected static void acceptInvalid(final String key) {
		invalid.accept(key);
	}
	
	/**
	 * Creates a ParameterContainer using the entries in the file
	 * 
	 * @param file the file to get the entries from
	 * 
	 * @return returns a new ParameterContainer with the entries
	 * 
	 * */
	
	public static ParameterContainer fromFile(final File file) throws IOException {
		if (!(file.exists()))
			return create();
		final ParameterContainer container = create();
		final FileReader fileReader = new FileReader(file);
		String nextLine;
		final BufferedReader reader = new BufferedReader(fileReader);
		while ((nextLine = reader.readLine()) != null) {
			final String[] args = nextLine.split(" : ");
			if (args.length != 2) {
				reader.close();
				System.out.printf("Invalid Syntax at: \"%s\"", nextLine);
				return container;
			}
			container.set(args[0], args[1]);
		}
		reader.close();
		return container;
	}
	
	/**
	 * Saves the given ParameterContainer to the file
	 * 
	 * @param container the container to save
	 * @param file the file to save to
	 * 
	 * @return returns the file that contains the entries
	 * 
	 * */
	
	public static File toFile(final ParameterContainer container, final File file) {
		try {
			file.delete();
			file.createNewFile();
			final FileWriter writer = new FileWriter(file);;
			for (final Map.Entry<String, Object> entry : container.getMapEntries())
				writer.write(String.format("%s : %s\n", entry.getKey(), entry.getValue()));
			writer.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return file;
		}
	}
	
	/**
	 * Creates a ParameterContainer using the given entries
	 * 
	 * @param entries the entries to add to the newly constructed container
	 * 
	 * @return returns a new ParameterContainer with the entries
	 * 
	 * */
	
	public static ParameterContainer create(final ParameterEntry... entries) {
		final ParameterContainer container = new ParameterContainer();
		for (final ParameterEntry entry : entries)
			container.set(entry);
		return container;
	}
	
	/**
	 * Creates a new entry with the given key and the given value
	 * 
	 * @param key the key of the entry
	 * @param value the value of the entry
	 * 
	 * @return constructs a new ParameterEntry with the given key and value
	 * 
	 * */
	
	public static ParameterEntry entry(final String key, final Object value) {
		return new ParameterEntry(key, value);
	}
	
}
