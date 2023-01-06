package tfip.day13workshop2.model;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact {

    @NotNull(message = "Name cannot be blank")
    @Size(min = 3, max = 64, message = "Name must be btwn 3-64 characters")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @Min(value = 8, message = "Phone Number must be at least 8 digits")
    private int phoneNumber;

    @Past(message = "Date of birth must not be future")
    @NotNull(message = "Please fill in date of birth")
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateOfBirth;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contact() {
        this.id = generateId(8);
    }

    public Contact(String name, String email, int phoneNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = generateId(8);
        this.dateOfBirth = dateOfBirth;
    }

    public Contact(String id, String name, String email, int phoneNumber, LocalDate dateofBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateofBirth;
    }

    private synchronized String generateId(int numOfChar) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numOfChar) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numOfChar);
    }

}
