package me.mohammad.parametercontainer;

import java.util.function.Consumer;

public class ParameterHandler {
	
	private static Consumer<String> invalid;
	
	static {
		invalid = (key) -> {};
	}

	public static void setOnInvalid(final Consumer<String> invalid) {
		ParameterHandler.invalid = invalid;
	}
	
	protected static void acceptInvalid(final String key) {
		invalid.accept(key);
	}
	
	public static ParameterContainer create(final ParameterEntry... entries) {
		final ParameterContainer container = new ParameterContainer();
		for (final ParameterEntry entry : entries)
			container.add(entry);
		return container;
	}
	
	public static ParameterEntry entry(final String key, final Object value) {
		return new ParameterEntry(key, value);
	}
	
}
