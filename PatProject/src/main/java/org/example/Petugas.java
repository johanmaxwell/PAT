package org.example;

public class Petugas {

    private String username;
    private String password;
    private String nip;
    private String email;

    public Petugas(String username, String password, String nip, String email) {
        this.username = username;
        this.password = password;
        this.nip = nip;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Petugas{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nip='" + nip + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
