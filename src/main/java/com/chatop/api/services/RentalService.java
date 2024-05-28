package com.chatop.api.services;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.dto.RentalRequestDTO;
import com.chatop.api.dto.RentalsDTO;
import com.chatop.api.dto.ResponseDTO;
import com.chatop.api.dto.TokenDTO;
import com.chatop.api.exceptions.DatabaseException;
import com.chatop.api.exceptions.NotFoundException;
import com.chatop.api.models.Rental;
import com.chatop.api.repositories.IRentalRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Service
public class RentalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalService.class);
    private final UserService userService;
    private final IRentalRepository rentalRepository;

    /**
     * Retrieves all rentals from the database.
     *
     * @return a {@link RentalsDTO} object containing a list of all rentals.
     * @throws DatabaseException if an error occurs while retrieving the
     * rentals.
     */
    public RentalsDTO getAllRentals() {

        LOGGER.info("Debut de la recuperation de tous les logements");
        RentalsDTO rentalsDTO = new RentalsDTO();
        rentalsDTO.setRentals(new ArrayList<>());
        try {
            Iterable<Rental> rentals = rentalRepository.findAll();
            rentals.forEach((rental) -> {
                rentalsDTO.getRentals().add(RentalDTO.builder().id(rental.getId()).name(rental.getName())
                        .surface(rental.getSurface()).price(rental.getPrice()).description(rental.getDescription())
                        .picture(rental.getPicture()).build());
            });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

        LOGGER.info("Fin de la recuperation de toutes les locations, liste : {}", rentalsDTO.getRentals());
        return rentalsDTO;
    }

    /**
     * Retrieves a rental by its id.
     *
     * @param id the id of the rental to retrieve
     * @return a {@link RentalDTO} object containing the details of the rental
     * @throws DatabaseException if an error occurs while retrieving the rental
     * @throws NotFoundException if the rental with the given id is not found
     */
    public RentalDTO getRentalById(int id) {
        try {
            Rental rental = this.findRentalById(id);

            RentalDTO rentalDTO = RentalDTO.builder()
                    .id(rental.getId())
                    .name(rental.getName())
                    .surface(rental.getSurface())
                    .price(rental.getPrice())
                    .description(rental.getDescription())
                    .picture(rental.getPicture())
                    .createdAt(rental.getCreatedAt().toLocalDateTime())
                    .updatedAt(rental.getUpdatedAt().toLocalDateTime())
                    .build();

            return rentalDTO;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());

            if (e instanceof NotFoundException) {
                throw e;
            } else {
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    /**
     * Creates a new rental.
     *
     * @param tokenDTO the token containing the user's id
     * @param rentalDTO the details of the rental to be created
     * @return a {@link ResponseDTO} object containing a message indicating the
     * success of the operation
     * @throws DatabaseException if an error occurs while creating the rental
     */
    public ResponseDTO createRental(TokenDTO tokenDTO, RentalRequestDTO rentalDTO) {
        try {
            Integer ownerId = userService.getUserFromToken(tokenDTO).getId();

            Rental rental = Rental.builder()
                    .name(rentalDTO.getName())
                    .surface(rentalDTO.getSurface())
                    .price(rentalDTO.getPrice())
                    .description(rentalDTO.getDescription())
                    .picture(rentalDTO.getPicture())
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .updatedAt(new Timestamp(System.currentTimeMillis()))
                    .ownerId(ownerId)
                    .build();

            rentalRepository.save(rental);
            return ResponseDTO.builder().message("Rental created").build();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    /**
     * Updates an existing rental.
     *
     * @param id the id of the rental to update
     * @param rentalDTO the details of the rental to be updated
     * @return a {@link ResponseDTO} object containing a message indicating the
     * success of the operation
     * @throws DatabaseException if an error occurs while updating the rental
     * @throws NotFoundException if the rental with the given id is not found
     */
    public ResponseDTO updateRental(Integer id, RentalRequestDTO rentalDTO) {
        try {
            Rental rental = this.findRentalById(id);
            rental.setName(rentalDTO.getName());
            rental.setDescription(rentalDTO.getDescription());
            rental.setSurface(rentalDTO.getSurface());
            rental.setPrice(rentalDTO.getPrice());
            rental.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            rentalRepository.save(rental);
            return ResponseDTO.builder().message("Rental updated").build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    private Rental findRentalById(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new NotFoundException("Rental not found"));
    }
}
