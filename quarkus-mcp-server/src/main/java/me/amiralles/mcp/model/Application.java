package me.amiralles.mcp.model;

public class Application {

    public Long id;
    public String name;
    public String surname;
    public String personalId;
    public ApplicationStatus status;
    public String country;

    public Application() {
    }

    public Application(String name, String surname, String personalId, String country) {
        this.name = name;
        this.surname = surname;
        this.personalId = personalId;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Application: {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", idCode='" + personalId + '\'' +
                ", status=" + status +
                ", country=" + country +
                '}';
    }
}