package classes;

import java.time.LocalDate;

public class Profile {
    private int id;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private double heightCm;
    private double weightKg;

    // Constructor

    public Profile(String name, String gender, LocalDate dob, double height, double weight) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dob;
        this.heightCm = height;
        this.weightKg = weight;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getHeightCm() {
        return this.heightCm;
    }

    public void setHeightCm(double heightCm) {
        this.heightCm = heightCm;
    }

    public double getWeightKg() {
        return this.weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    // NEWLY ADDED
    public int getId() {
        return this.id;
    }

    // NEWLY ADDED
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
