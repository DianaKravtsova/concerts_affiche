package by.kravts.concerts_affiche.repository;

import by.kravts.concerts_affiche.entity.Concert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public interface ConcertRepository extends PagingAndSortingRepository<Concert, Long> {
    Optional<Concert> findConcertByName(String name);
    Collection<Concert> findAll();
    Optional<Collection<Concert>> getConcertsByPlaceId(Long placeId);
    @Query("SELECT e from CONCERTS e where (?1 is null or e.place.country = ?1) and (?2 is null or e.place.name = ?2) and (?3 is null or e.startDate > ?3) and (?4 is null or e.finishDate < ?4)")
    Optional<Collection<Concert>> getConcertsByParams(String country, String place, Date startDate, Date finishDate);
    void deleteConcertById(Long id);
}
