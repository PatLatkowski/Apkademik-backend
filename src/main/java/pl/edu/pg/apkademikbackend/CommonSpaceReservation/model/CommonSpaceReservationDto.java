package pl.edu.pg.apkademikbackend.CommonSpaceReservation.model;

import java.util.ArrayList;
import java.util.List;

public class CommonSpaceReservationDto {
    private long commonSpaceId;
    private List<CommonSpaceReservation> commonSpaceReservations = new ArrayList<>();

    public long getCommonSpaceId() {
        return commonSpaceId;
    }

    public void setCommonSpaceId(long commonSpaceId) {
        this.commonSpaceId = commonSpaceId;
    }

    public List<CommonSpaceReservation> getCommonSpaceReservations() {
        return commonSpaceReservations;
    }

    public void setCommonSpaceReservations(List<CommonSpaceReservation> commonSpaceReservations) {
        this.commonSpaceReservations = commonSpaceReservations;
    }
}
