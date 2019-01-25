package com.gradingapp.service;

import com.gradingapp.domain.ActiveUsers;
import com.gradingapp.repository.ActiveUsersRepository;
import com.gradingapp.service.dto.ActiveUsersDTO;
import com.gradingapp.service.mapper.ActiveUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ActiveUsers.
 */
@Service
@Transactional
public class ActiveUsersService {

    private final Logger log = LoggerFactory.getLogger(ActiveUsersService.class);

    private final ActiveUsersRepository activeUsersRepository;

    private final ActiveUsersMapper activeUsersMapper;

    public ActiveUsersService(ActiveUsersRepository activeUsersRepository, ActiveUsersMapper activeUsersMapper) {
        this.activeUsersRepository = activeUsersRepository;
        this.activeUsersMapper = activeUsersMapper;
    }

    /**
     * Save a activeUsers.
     *
     * @param activeUsersDTO the entity to save
     * @return the persisted entity
     */
    public ActiveUsersDTO save(ActiveUsersDTO activeUsersDTO) {
        log.debug("Request to save ActiveUsers : {}", activeUsersDTO);

        ActiveUsers activeUsers = activeUsersMapper.toEntity(activeUsersDTO);
        activeUsers = activeUsersRepository.save(activeUsers);
        return activeUsersMapper.toDto(activeUsers);
    }

    /**
     * Get all the activeUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ActiveUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActiveUsers");
        return activeUsersRepository.findAll(pageable)
            .map(activeUsersMapper::toDto);
    }


    /**
     * Get one activeUsers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ActiveUsersDTO> findOne(Long id) {
        log.debug("Request to get ActiveUsers : {}", id);
        return activeUsersRepository.findById(id)
            .map(activeUsersMapper::toDto);
    }

    /**
     * Delete the activeUsers by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ActiveUsers : {}", id);
        activeUsersRepository.deleteById(id);
    }

    public Optional<ActiveUsersDTO> findActiveUserFromName(String name) {
        log.debug("Request to get active user with name {}", name);
        return activeUsersRepository.findOneByUsernameAndActiveIsTrue(name)
            .map(activeUsersMapper::toDto);
    }

    /**
     * Get all the activeUsers by username for single ID.
     *
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Optional<ActiveUsersDTO> findAllByUsername(String username) {
        log.debug("Request to get all ActiveUsers by username for a single ID");
        return activeUsersRepository.findOneByUsernameAndActiveIsTrue(username)
            .map(activeUsersMapper::toDto);
    }
}