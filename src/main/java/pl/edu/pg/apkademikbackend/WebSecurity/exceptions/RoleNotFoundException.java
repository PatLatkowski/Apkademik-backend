package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String roleName){
        super("Role not found "+roleName);
    }
    public RoleNotFoundException(long id){
        super("Role not found "+ id);
    }
}
