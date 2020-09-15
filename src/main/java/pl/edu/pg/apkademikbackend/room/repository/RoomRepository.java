package pl.edu.pg.apkademikbackend.room.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.room.model.Room;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room,Integer> {
    public Room findById(long id);

}
