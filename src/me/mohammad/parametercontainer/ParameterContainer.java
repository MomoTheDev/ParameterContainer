package me.mohammad.parametercontainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ParameterContainer {

	protected Map<String, Object> parameters;

	protected ParameterContainer() {
		parameters = new HashMap<>();
	}
	
	/**
	 * Loops through the parameter entries and accepts them with the consumer
	 * 
	 * @apiNote this might take more time than <pre>forEach(BiConsumer)</pre> on a higher entry count
	 * 
	 * @see forEach(BiConsumer);
	 * 
	 * */
	
	public void forEach(final Consumer<ParameterEntry> consumer) {
		for (final ParameterEntry entry : getParameterEntries())
			consumer.accept(entry);
	}
	
	/**
	 * Loops through the map entries and accepts them with the consumer
	 * 
	 * @see forEach(Consumer);
	 * 
	 * */
	
	public void forEach(final BiConsumer<String, Object> consumer) {
		for (final Map.Entry<String, Object> entry : getMapEntries())
			consumer.accept(entry.getKey(), entry.getValue());
	}
	
	/**
	 * Maps all the map entry and returns them in a list of parameter entries
	 * 
	 * @apiNote this might take more time than <pre>getMapEntries()</pre> on a higher entry count
	 * 
	 * @see getMapEntries();
	 * 
	 * @return returns a list with all the parameter entries
	 * 
	 * */
	
	public List<ParameterEntry> getParameterEntries() {
		final List<ParameterEntry> entries = new ArrayList<>();
		for (final Map.Entry<String, Object> entry : parameters.entrySet())
			entries.add(ParameterHandler.entry(entry.getKey(), entry.getValue()));
		return entries;
	}
	
	/**
	 * Get's an entry set with all the map entries
	 * 
	 * @see getParameterEntries();
	 * 
	 * @return returns a set with all the map entries
	 * 
	 * */
	
	public Set<Map.Entry<String, Object>> getMapEntries() {
		return parameters.entrySet();
	}

	/**
	 * Validates the keys and accepts a consumer if the keys are invalid.
	 * 
	 * <p>The consumer can be changed using: <blockquote><pre>ParameterHandler.setOnInvalid(Consumer<String>);</pre></blockquote></p>
	 * 
	 * @param keys the keys to validate
	 * 
	 * @return returns true if all the keys are contained, false if a key is missing
	 * 
	 * */
	
	public boolean validate(final String... keys) {
		for (final String key : keys) {
			if (!(contains(key))) {
				ParameterHandler.acceptInvalid(key);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds all the entries from the given ParameterContainer
	 * 
	 * @param container the ParameterContainer to add the keys from
	 * 
	 * @return returns the same container
	 * 
	 * */
	
	public ParameterContainer addAll(final ParameterContainer container) {
		for (final Map.Entry<String, Object> entry : container.parameters.entrySet())
			add(entry.getKey(), entry.getValue());
		return this;
	}
	
	/**
	 * Adds the key and value from the given ParameterEntry
	 * 
	 * @param entry the ParameterEntry to add
	 * 
	 * @return returns the same container
	 * 
	 * */
	
	public ParameterContainer add(final ParameterEntry entry) {
		return add(entry.getKey(), entry.getValue());
	}

	/**
	 * Adds the given values to the collection
	 * 
	 * @param key the key for the value
	 * @param object the value to set for the key
	 * 
	 * @return returns the same container
	 * 
	 * */
	
	public ParameterContainer add(final String key, final Object object) {
		parameters.put(key, object);
		return this;
	}
	
	/**
	 * Removes all the same entries from the ParameterContainer
	 * 
	 * @param container the ParameterContainer to get the entries from
	 * 
	 * @return returns the same container
	 * 
	 * */
	
	public ParameterContainer removeAll(final ParameterContainer container) {
		for (final Map.Entry<String, Object> entry : container.parameters.entrySet())
			remove(entry.getKey());
		return this;
	}

	/**
	 * Removes the entry, that's stored using the given key
	 * 
	 * @param key the key to remove the entry from
	 * 
	 * @return returns the same container
	 * 
	 * */
	
	public ParameterContainer remove(final String key) {
		parameters.remove(key);
		return this;
	}

	/**
	 * Checks if the collection contains the given key
	 * 
	 * @param key the key of the entry to check
	 * 
	 * @return returns true if the entry is contained, false if the entry is missing
	 * 
	 * */
	
	public boolean contains(final String key) {
		return parameters.containsKey(key);
	}
	
	/**
	 * Returns the value stored using that key as an Object
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as an Object
	 * 
	 * */

	public Object get(final String key) {
		return parameters.get(key);
	}

	/**
	 * Returns the value stored using that key as a parsed String
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed String
	 * 
	 * */
	
	public String getString(final String key) {
		return (String) get(key);
	}
	
	/**
	 * Returns the value stored using that key as a parsed Byte
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed Byte
	 * 
	 * */

	public Byte getByte(final String key) {
		return Byte.parseByte((String) get(key));
	}

	/**
	 * Returns the value stored using that key as a parsed Short
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed Short
	 * 
	 * */
	
	public Short getShort(final String key) {
		return Short.parseShort((String) get(key));
	}

	/**
	 * Returns the value stored using that key as a parsed Integer
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed Integer
	 * 
	 * */
	
	public Integer getInteger(final String key) {
		return Integer.parseInt((String) get(key));
	}
	
	/**
	 * Returns the value stored using that key as a parsed Long
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed Long
	 * 
	 * */

	public Long getLong(final String key) {
		return Long.parseLong((String) get(key));
	}

	/**
	 * Returns the value stored using that key as a parsed Float
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed Float
	 * 
	 * */
	
	public Float getFloat(final String key) {
		return Float.parseFloat((String) get(key));
	}

	/**
	 * Returns the value stored using that key as a parsed Double
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the value as a parsed Double
	 * 
	 * */
	
	public Double getDouble(final String key) {
		return Double.parseDouble((String) get(key));
	}
	
	/**
	 * Returns the values of the current ParameterContainer, stored into a new one
	 * 
	 * @return returns a clone of the current ParameterContainer
	 * 
	 * */
	
	public ParameterContainer clone() {
		return new ParameterContainer().addAll(this);
	}
	
	/**
	 * Clears the entries of the current ParameterContainer
	 * 
	 * @return returns the same container
	 * 
	 * */
	
	public ParameterContainer clear() {
		parameters.clear();
		return this;
	}

}
