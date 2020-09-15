package pl.edu.pg.apkademikbackend.commonSpace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceType;

import java.util.List;


public interface CommonSpaceRepository extends JpaRepository<CommonSpace,Integer> {
}