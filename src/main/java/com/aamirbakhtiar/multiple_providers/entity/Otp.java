package com.aamirbakhtiar.multiple_providers.entity;

import javax.persistence.*;

@Entity
@Table(name = "otps")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String otp;

    public Otp(Long id, String username, String otp) {
        this.id = id;
        this.username = username;
        this.otp = otp;
    }

    public Otp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Otp otp1 = (Otp) o;

        if (id != null ? !id.equals(otp1.id) : otp1.id != null) return false;
        if (username != null ? !username.equals(otp1.username) : otp1.username != null) return false;
        return otp != null ? otp.equals(otp1.otp) : otp1.otp == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (otp != null ? otp.hashCode() : 0);
        return result;
    }
}
