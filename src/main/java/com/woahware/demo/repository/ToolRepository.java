package com.woahware.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.woahware.demo.models.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ToolRepository extends CrudRepository<Tool, Long> {

    // Note:  This is using HQL and interface projections to populate an overall object that we want.
    //           Although a more optimized approach would be to create a custom DTO and instead have this repo return that,
    //           having this current set up is easier to read and maintain in the future potentially. 
    //           This is the trade-off for abstractions and ORM frameworks in general (performance vs ease of use).
    @Query("SELECT t as tool, tt as toolType, ttc as toolTypeCharge " +
                    "FROM Tool t " +
                    "INNER JOIN ToolType tt ON t.toolType = tt.id " +
                    "INNER JOIN ToolTypeCharge ttc ON ttc.toolType = tt.id " +
                    "WHERE t.toolCode = :toolCode " +
                         "AND tt.deprecatedOn IS NULL AND ttc.deprecatedOn IS NULL")
    ToolChargeProjection getChargeInfoByToolCode(@Param("toolCode") String toolCode);
    
}

