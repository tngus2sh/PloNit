package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.domain.rank.Ranking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RankingRepository extends CrudRepository<Ranking, String> {

    @Override
    List<Ranking> findAll();
    
    
}
