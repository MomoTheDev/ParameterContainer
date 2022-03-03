package me.mohammad.parametercontainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	protected static boolean isList(final String argument) {
		if (!(argument.startsWith("LIST-[")))
			return false;
		if (!(argument.endsWith("]")))
			return false;
		return true;
	}
	
	private static void setList(final ParameterContainer container, final String key, final String value) {
		final List<Object> list = new ArrayList<>();
		final String[] splitted = value.substring(6, value.length() - 1).split("\\s*,\\s*");
		for (final String listObject : splitted) {
			if (isList(listObject)) {
				setList(list, listObject);
				continue;
			}
			list.add(listObject);
		}
		container.set(key, list);
	}
	
	private static void setList(final List<Object> list, final String value) {
		final String[] splitted = value.substring(6, value.length() - 1).split("\\s*,\\s*");
		for (final String listObject : splitted) {
			if (isList(listObject)) {
				setList(list, listObject);
				continue;
			}
			list.add(listObject);
		}
	}
	
	private static void writeObject(final FileWriter writer, final String key, final Object object, final String suffix) throws IOException {
		if (object instanceof List<?>) {
			writeList(writer, new StringBuilder("LIST-["), key, (List<?>) object);
			return;
		}
		writer.write(String.format("%s: %s%s", key, object, suffix));
	}
	
	private static void writeList(final FileWriter writer, final StringBuilder builder, final String key, final List<?> list) throws IOException {
		for (final Object object : list) {
			builder.append(String.format("%s, ", object));
		}
		builder.append("]");
		writeObject(writer, key, builder.toString(), "\n");
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
			if (nextLine.isBlank())
				continue;
			final String[] args = nextLine.split("\\s*:\\s*", 2);
			if (args.length != 2) {
				reader.close();
				System.out.printf("Invalid Syntax at: \"%s\"\n", nextLine);
				return container;
			}
			if (isList(args[1])) {
				setList(container, args[0], args[1]);
				continue;
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
			for (final Map.Entry<String, Object> entry : container.getMapEntries()) {
				writeObject(writer, entry.getKey(), entry.getValue(), "\n");
			}
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
