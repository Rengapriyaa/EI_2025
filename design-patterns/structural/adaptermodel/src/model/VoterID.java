package model;

public class VoterID {
    private String voterIDNumber;
    private Person person;

    public VoterID(String voterIDNumber, Person person) {
        this.voterIDNumber = voterIDNumber;
        this.person = person;
    }

    public void showVoterIDDetails() {
        System.out.println("VoterID: " + voterIDNumber + ", Name: " + person.getName() + ", Age: " + person.getAge());
    }
}
