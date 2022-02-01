package me.mohammad.parametercontainer;

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
			container.add(entry);
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
