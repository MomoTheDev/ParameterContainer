package me.mohammad.parametercontainer;

public class ParameterEntry {
	
	private String key;
	private Object value;
	
	protected ParameterEntry(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(final Object value) {
		this.value = value;
	}
	
}
