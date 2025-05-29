package me.amiralles.mcp.model;

import java.util.List;

public class Application {

    public Long id;
    public String name;
    public String surname;
    public String idCode;
    public List<String> filesIds;
    public ApplicationStatus status;
    public Long hits;
    public String country;

    public Application() {
    }

    public Application(String name, String surname, String idCode, String country) {
        this.name = name;
        this.surname = surname;
        this.idCode = idCode;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Application: {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", idCode='" + idCode + '\'' +
                ", status=" + status +
                ", hits=" + hits +
                ", country=" + country +
                '}';
    }
}