package model;

public class Member extends User {
    private String address;
    private String number;

    // Constructeur
    public Member(String id, String name, String email, String password, String role, String address, String number) {
        super(id, name, email, password, role);
        this.address = address;
        this.number = number;
    }

    // Getters et Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
