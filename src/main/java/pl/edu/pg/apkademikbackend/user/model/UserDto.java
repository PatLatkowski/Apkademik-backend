package pl.edu.pg.apkademikbackend.user.model;

public class UserDto {
    private String password;
    private String name;
    private String surname;
    private String email;
    private Long roomId;
    private Long dormId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getDormId() {
        return dormId;
    }

    public void setDormId(Long dormId) {
        this.dormId = dormId;
    }
}
