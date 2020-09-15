package pl.edu.pg.apkademikbackend.commonSpace.exception;

public class CommonSpaceNotFoundException extends RuntimeException {
    public CommonSpaceNotFoundException(Integer number) {
        super("Common Space not found " + number);
    }
}
