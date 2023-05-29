package org.example.model;

public class Employee {
    private int id;
    private final String firstname;
    private final String lastname;
    private final String street;
    private final String zipcode;
    private final String city;
    private byte[] photo;

    // Konstruktory, getters i setters

    public Employee(int id, String firstname, String lastname, String street, String zipcode, String city, byte[] photo) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.photo = photo;
    }

    public Employee(String firstname, String lastname, String street, String zipcode, String city, byte[] photo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.photo = photo;
    }

    public Employee(int id, String firstname, String lastname, String street, String zipcode, String city) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    // Getters i setters dla wszystkich pól

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public byte[] getPhoto() {
        return photo;
    }
}