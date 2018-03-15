package com.rentit.reservation.domain.repository;

import com.rentit.reservation.domain.model.PlantReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Created by musabir on 04/11/17.
 */
@Repository
public interface PlantReservationRepository extends JpaRepository<PlantReservation, String>,CustomPlantReservationRepository{

}
