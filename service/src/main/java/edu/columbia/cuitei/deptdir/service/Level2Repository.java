package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level2;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface Level2Repository extends JpaRepository<Level2, Integer> {

    @Query(value = "SELECT * FROM level2 WHERE directory_name LIKE %:searchTerm%", nativeQuery = true)
    List<Level2> findAllByDirectoryNameLike(@Param("searchTerm") String searchTerm);
}
