package com.h8.howlong.repositories;

import com.h8.howlong.domain.StartWorkTimestamp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StartWorkTimestampRepository extends CrudRepository<StartWorkTimestamp, Long> {

    Optional<StartWorkTimestamp> findTopByTimestampAfter(LocalDateTime startOfTheDay);

}
