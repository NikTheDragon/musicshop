package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.MixRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.GetAllMixesSpecification;
import by.kurlovich.musicshop.repository.specification.GetMixByIdSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class MixReceiverImpl implements EntityReceiver<Mix> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixReceiverImpl.class);

    @Override
    public boolean addNewEntity(Mix item) throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            LOGGER.debug("trying to add mix: {}", item.getName());

            if (item.getName().isEmpty()) {
                return false;
            }

            switch (entityRepository.getStatus(item)) {
                case ACTIVE:
                    LOGGER.debug("found active mix: {}", item.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted mix: {}", item.getName());
                    entityRepository.undelete(item);
                    return true;
                case NA:
                    entityRepository.add(item);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewEntity of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public boolean deleteEntity(Mix item) throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            LOGGER.debug("trying to delete mix: {}", item.getName());

            if (item.getName().isEmpty()) {
                return false;
            }

            entityRepository.delete(item);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteEntity of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public boolean updateEntity(Mix item) throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            LOGGER.debug("trying to update mix: {}", item.getName());

            if (item.getName().isEmpty()) {
                return false;
            }

            entityRepository.update(item);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateEntity of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getAllEntities() throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            Specification specification = new GetAllMixesSpecification();
            LOGGER.debug("Trying to getId all mixes.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllEntities of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getSearchedEntities(SearchData searchData, String userId) throws ReceiverException {
        return Collections.emptyList();
    }

    @Override
    public List<Mix> getSpecifiedEntities(String mixId) throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            Specification specification = new GetMixByIdSpecification(mixId);
            LOGGER.debug("trying to getId specified mix with id {}.", mixId);

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedEntities of MixReceiverImpl.\n" + e, e);
        }
    }
}
