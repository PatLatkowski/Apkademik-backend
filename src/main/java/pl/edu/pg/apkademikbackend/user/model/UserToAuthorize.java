package pl.edu.pg.apkademikbackend.user.model;

public class UserToAuthorize {
    private UserDto user;
    private String oldPassword;

    public UserToAuthorize(UserDto user, String oldPassword) {
        this.user = user;
        this.oldPassword = oldPassword;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
