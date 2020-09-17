package pl.edu.pg.apkademikbackend.commonSpace.exception;

public class CommonSpaceNotFoundException extends RuntimeException {
    public CommonSpaceNotFoundException(long number) {
        super("Common Space not found " + number);
    }
}
