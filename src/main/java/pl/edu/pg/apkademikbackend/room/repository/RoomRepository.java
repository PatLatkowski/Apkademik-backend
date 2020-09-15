package pl.edu.pg.apkademikbackend.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Room findByResidents_email(String email);
}
