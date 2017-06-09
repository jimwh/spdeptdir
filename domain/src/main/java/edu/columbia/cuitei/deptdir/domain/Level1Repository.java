package edu.columbia.cuitei.deptdir.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Level1Repository extends JpaRepository<Level1, Integer> {

    @Query(value = "SELECT * FROM level1 WHERE id IN (:listOfId) ORDER BY name", nativeQuery = true)
    List<Level1> getListByIdList(@Param("listOfId") List<Integer> listOfId);

    List<Level1> findByNameLikeOrderByName(String searchTerm);
}
