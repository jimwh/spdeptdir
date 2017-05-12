package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.Level2;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface Level2Repository extends JpaRepository<Level2, Integer> {
    /**
     * @Query(value = "SELECT * FROM level2 WHERE directory_name LIKE %:searchTerm%", nativeQuery = true)
     * List<Level2> findByDirectoryNameLike(@Param("searchTerm") String searchTerm);
     *
     * Replace @Query with named query method
     *
     * @param searchTerm String with leading and trailing '%'
     * @return List of Level2
     */
    List<Level2> findByDirectoryNameLikeOrderByDirectoryName(String searchTerm);
}