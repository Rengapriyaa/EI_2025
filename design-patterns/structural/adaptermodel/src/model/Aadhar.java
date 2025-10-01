package model;

public class Aadhar {
    private Person person;
    private String aadharNumber;

    public Aadhar(Person person, String aadharNumber) {
        if(aadharNumber == null || aadharNumber.length() != 12) {
            throw new IllegalArgumentException("Invalid Aadhar Number");
        }
        this.person = person;
        this.aadharNumber = aadharNumber;
    }

    public Person getPerson() { return person; }
    public String getAadharNumber() { return aadharNumber; }
}
