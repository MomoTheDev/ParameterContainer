package me.mohammad.parametercontainer;

public class ParameterEntry {
	
	private String key;
	private Object value;
	
	protected ParameterEntry(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the stored key of the entry
	 * 
	 * @return returns the entry's key
	 * 
	 * */
	
	public String getKey() {
		return key;
	}
	
	/**
	 * Gets the stored value of the entry
	 * 
	 * @return returns the entry's value
	 * 
	 * */
	
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the entry
	 * 
	 * @param value the new value
	 * 
	 * */
	
	public void setValue(final Object value) {
		this.value = value;
	}
	
}
