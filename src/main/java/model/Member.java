package model;

public class Member extends User {

    private String address;
    private String number;

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

    public Member(String name, String email, String password, String address, String number) {
        super(name, email, password);
        this.address = address;
        this.number = number;
    }
}
