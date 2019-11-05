package org.amit.elasticsearch.model;

public class Person {
	 
    private String personId;
    private String name;
 
    //standard getters and setters
 
    public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public String toString() {
        return String.format("Person{personId='%s', name='%s'}", 
            personId, name);
    }
}