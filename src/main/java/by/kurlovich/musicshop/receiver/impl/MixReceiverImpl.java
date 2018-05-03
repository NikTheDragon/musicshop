package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.MixRepository;
import by.kurlovich.musicshop.specification.GetAllAlbumsSpecification;
import by.kurlovich.musicshop.specification.GetAllMixesSpecification;
import by.kurlovich.musicshop.specification.GetMixByIdSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MixReceiverImpl implements EntityReceiver<Mix> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixReceiverImpl.class);

    @Override
    public boolean addNewEntity(Mix item) throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            LOGGER.debug("trying to add mix: {}", item.getName());

            if (item.getName().isEmpty()) {
                return false;
            }

            switch (repository.getStatus(item)) {
                case ACTIVE:
                    LOGGER.debug("found active mix: {}", item.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted mix: {}", item.getName());
                    repository.undelete(item);
                    return true;
                case NA:
                    repository.add(item);
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
            Repository<Mix> repository = new MixRepository();
            LOGGER.debug("trying to delete mix: {}", item.getName());

            if (item.getName().isEmpty()) {
                return false;
            }

            repository.delete(item);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteEntity of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public boolean updateEntity(Mix item) throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            LOGGER.debug("trying to update mix: {}", item.getName());

            if (item.getName().isEmpty()) {
                return false;
            }

            repository.update(item);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateEntity of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getAllEntities() throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            Specification specification = new GetAllMixesSpecification();
            LOGGER.debug("Trying to get all mixes.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllEntities of MixReceiver.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getSpecifiedEntities(String param) throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            Specification specification = new GetMixByIdSpecification(param);
            LOGGER.debug("trying to get specified mix with id {}.", param);

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedEntities of MixReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getEntitiesWithOwner(String ownerId) throws ReceiverException {
        return null;
    }
}
