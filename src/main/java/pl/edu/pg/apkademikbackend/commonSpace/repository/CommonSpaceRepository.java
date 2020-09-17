package pl.edu.pg.apkademikbackend.commonSpace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;

public interface CommonSpaceRepository extends JpaRepository<CommonSpace,Integer> {
    CommonSpace findById(long id);
}