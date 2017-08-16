package fr.laforest.bankaccount.repository;

import fr.laforest.bankaccount.model.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
}
