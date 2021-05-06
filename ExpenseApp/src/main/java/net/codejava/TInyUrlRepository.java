package net.codejava;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Arrays;
import java.util.List;
@Repository
public interface TInyUrlRepository extends CrudRepository<TinyUrl,Long> {
     org.springframework.data.domain.Pageable sortByname=  PageRequest.of(0,3, Sort.by("mainurl").ascending());

     @Query(value = "SELECT * FROM TINYURL  where mainurl = ?1",nativeQuery = true)
    public  List<TinyUrl> findByMainUrl(@Param("mainUrl") String mainUrl);



}
