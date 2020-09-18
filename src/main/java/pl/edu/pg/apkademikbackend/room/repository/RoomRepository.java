package pl.edu.pg.apkademikbackend.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.room.model.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {
    Room findByResidents_email(String email);
    Room findById(long id);
}
