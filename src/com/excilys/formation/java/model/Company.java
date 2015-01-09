package com.excilys.formation.java.model;

public class Company {
	/*Attributes*/
	private long id;
	private String name;
	
	/*Constructors*/
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(String name, long id) {
		super();
		this.name = name;
		this.id = id;
	}

	/*Getters and Setters*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/*hashCode, equals & toString*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", id=" + id + "]";
	}
	
}
