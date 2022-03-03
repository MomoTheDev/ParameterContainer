package me.mohammad.parametercontainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 
 * The main class of this library. Can be publicly constructed using {@link ParameterHandler.create(ParameterEntry...); }
 * or loaded from a file using {@link ParameterHandler.fromFile(File); }
 * 
 * @author Mohammad Alkhatib
 * 
 * */

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
	 * Checks if this {@link ParameterContainer } contains any values
	 * 
	 * @return returns true if there are no values contained, false otherwise
	 * 
	 * */
	
	public boolean isEmpty() {
		return parameters.isEmpty();
	}
	
	/**
	 * Maps all the map entry and returns them in a list of parameter entries
	 * 
	 * @apiNote this might take more time than <pre>getMapEntries()</pre> on a higher entry count
	 * 
	 * @see getMapEntries();
	 * 
	 * @return returns a copied list with all the parameter entries
	 * 
	 * */
	
	public List<ParameterEntry> getParameterEntries() {
		final List<ParameterEntry> entries = new ArrayList<>();
		for (final Map.Entry<String, Object> entry : parameters.entrySet())
			entries.add(ParameterHandler.entry(entry.getKey(), entry.getValue()));
		Collections.sort(entries, new Comparator<ParameterEntry>() {
			@Override
            public int compare(final ParameterEntry firstEntry, final ParameterEntry secondEntry) {
				return firstEntry.getKey().compareTo(secondEntry.getKey());
            }
		});
		return entries;
	}
	
	/**
	 * Get's a list with all the map entries
	 * 
	 * @see getParameterEntries();
	 * 
	 * @return returns a copied list with all the map entries
	 * 
	 * */
	
	public List<Map.Entry<String, Object>> getMapEntries() {
		final List<Map.Entry<String, Object>> entries = new ArrayList<>(parameters.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Object>>() {
			@Override
            public int compare(final Map.Entry<String, Object> firstEntry, final Map.Entry<String, Object> secondEntry) {
				return firstEntry.getKey().compareTo(secondEntry.getKey());
            }
		});
		return entries;
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
	
	public ParameterContainer setAll(final ParameterContainer container) {
		for (final Map.Entry<String, Object> entry : container.parameters.entrySet())
			set(entry.getKey(), entry.getValue());
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
	
	public ParameterContainer set(final ParameterEntry entry) {
		return set(entry.getKey(), entry.getValue());
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
	
	public ParameterContainer set(final String key, final Object object) {
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
	 * Returns the Object stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Object stored using the given key
	 * 
	 * */

	public Object get(final String key) {
		return parameters.get(key);
	}

	/**
	 * Returns the List stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List stored using the given key
	 * 
	 * */
	
	public List<?> getList(final String key) {
		return (List<?>) get(key);
	}
	
	/**
	 * Returns the Character stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Character stored using the given key
	 * 
	 * */
	
	public Character getCharacter(final String key) {
		return Character.valueOf(getString(key).charAt(0));
	}

	/**
	 * Returns the List of Characters stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Characters stored using the given key
	 * 
	 * */
	
	public List<Character> getCharacterList(final String key) {
		return (List<Character>) getList(key);
	}

	/**
	 * Returns the String stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the String stored using the given key
	 * 
	 * */
	
	public String getString(final String key) {
		return String.valueOf(get(key));
	}

	/**
	 * Returns the List of Strings stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Strings stored using the given key
	 * 
	 * */
	
	public List<String> getStringList(final String key) {
		return (List<String>) getList(key);
	}
	
	/**
	 * Returns the Boolean stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Boolean stored using the given key
	 * 
	 * */
	
	public Boolean getBoolean(final String key) {
		return Boolean.parseBoolean(getString(key));
	}

	/**
	 * Returns the List of Booleans stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Booleans stored using the given key
	 * 
	 * */
	
	public List<Boolean> getBooleanList(final String key) {
		return (List<Boolean>) getList(key);
	}
	
	/**
	 * Returns the Byte stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Byte stored using the given key
	 * 
	 * */

	public Byte getByte(final String key) {
		return Byte.parseByte(getString(key));
	}

	/**
	 * Returns the List of Bytes stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Bytes stored using the given key
	 * 
	 * */
	
	public List<Byte> getByteList(final String key) {
		return (List<Byte>) getList(key);
	}
	
	/**
	 * Returns the Short stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Short stored using the given key
	 * 
	 * */
	
	public Short getShort(final String key) {
		return Short.parseShort(getString(key));
	}

	/**
	 * Returns the List of Shorts stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Shorts stored using the given key
	 * 
	 * */
	
	public List<Short> getShortList(final String key) {
		return (List<Short>) getList(key);
	}

	/**
	 * Returns the Integer stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Integer stored using the given key
	 * 
	 * */
	
	public Integer getInteger(final String key) {
		return Integer.parseInt(getString(key));
	}

	/**
	 * Returns the List of Integers stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Integers stored using the given key
	 * 
	 * */
	
	public List<Integer> getIntegerList(final String key) {
		return (List<Integer>) getList(key);
	}
	
	/**
	 * Returns the Long stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Long stored using the given key
	 * 
	 * */

	public Long getLong(final String key) {
		return Long.parseLong(getString(key));
	}

	/**
	 * Returns the List of Longs stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Longs stored using the given key
	 * 
	 * */
	
	public List<Long> getLongList(final String key) {
		return (List<Long>) getList(key);
	}
	
	/**
	 * Returns the Float stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Float stored using the given key
	 * 
	 * */
	
	public Float getFloat(final String key) {
		return Float.parseFloat(getString(key));
	}
	
	/**
	 * Returns the List of Floats stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Floats stored using the given key
	 * 
	 * */
	
	public List<Float> getFloatList(final String key) {
		return (List<Float>) getList(key);
	}
	
	/**
	 * Returns the Double stored using that key
	 * 
	 * @param key the key to get the value of
	 * 
	 * @return returns the Doubles stored using the given key
	 * 
	 * */
	
	public Double getDouble(final String key) {
		return Double.parseDouble(getString(key));
	}
	
	/**
	 * Returns the List of Doubles stored using that key
	 * 
	 * @param key the key to get the list of
	 * 
	 * @return returns the List of Double stored using the given key
	 * 
	 * */
	
	public List<Double> getDoubleList(final String key) {
		return (List<Double>) getList(key);
	}
	
	/**
	 * Returns the values of the current ParameterContainer, stored into a new one
	 * 
	 * @return returns a clone of the current ParameterContainer
	 * 
	 * */
	
	public ParameterContainer clone() {
		return new ParameterContainer().setAll(this);
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
