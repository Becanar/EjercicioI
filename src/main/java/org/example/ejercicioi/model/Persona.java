package org.example.ejercicioi.model;

/**
 * Clase que representa una persona con nombre, apellidos y edad.
 * Esta clase se utiliza para almacenar la información básica de una persona.
 */
public class Persona {
	private int id;
	private String nombre;
	private String apellidos;
	private int edad;

	/**
	 * Constructor de la clase Persona.
	 *
	 * @param nombre    El nombre de la persona.
	 * @param id    El id de la persona.
	 * @param apellidos Los apellidos de la persona.
	 * @param edad      La edad de la persona.
	 */
	public Persona(int id,String nombre, String apellidos, int edad) {
		this.id=id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}
	/**
	 * Constructor de la clase Persona.
	 *
	 * @param nombre    El nombre de la persona.
	 * @param apellidos Los apellidos de la persona.
	 * @param edad      La edad de la persona.
	 */
	public Persona(String nombre, String apellidos, int edad) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}
	/**
	 * Obtiene el id de la persona.
	 *
	 * @return El id de la persona.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Establece el id de la persona.
	 *
	 * @param id El nuevo id de la persona.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de la persona.
	 *
	 * @return El nombre de la persona.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la persona.
	 *
	 * @param nombre El nuevo nombre de la persona.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene los apellidos de la persona.
	 *
	 * @return Los apellidos de la persona.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece los apellidos de la persona.
	 *
	 * @param apellidos Los nuevos apellidos de la persona.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Obtiene la edad de la persona.
	 *
	 * @return La edad de la persona.
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * Establece la edad de la persona.
	 *
	 * @param edad La nueva edad de la persona.
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * Compara esta persona con otra para determinar si son iguales.
	 * Dos personas se consideran iguales si tienen el mismo nombre, apellidos y edad.
	 *
	 * @param obj El objeto con el que se va a comparar.
	 * @return true si son iguales, false en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Persona persona = (Persona) obj;
		return getNombre().equals(persona.getNombre()) &&
				getApellidos().equals(persona.getApellidos()) &&
				getEdad() == persona.getEdad();
	}
}