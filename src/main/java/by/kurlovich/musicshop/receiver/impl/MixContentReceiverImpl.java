package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.ContentReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.MixContentRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.GetMixContentByMixIdSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MixContentReceiverImpl implements ContentReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixContentReceiverImpl.class);

    @Override
    public boolean addNewEntity(Content item) throws ReceiverException {
        try {
            EntityRepository<Content> entityRepository = new MixContentRepositoryImpl();

            if (item.getTrackName().isEmpty()) {
                return false;
            }

            switch (entityRepository.getStatus(item)) {
                case ACTIVE:
                    LOGGER.debug("found active content for: {}", item.getTrackName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted content for: {}", item.getTrackName());
                    entityRepository.undelete(item);
                    return true;
                case NA:
                    entityRepository.add(item);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewEntity of MixContentReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean deleteEntity(Content item) throws ReceiverException {
        try {
            EntityRepository<Content> entityRepository = new MixContentRepositoryImpl();
            LOGGER.debug("trying to delete content from mix.");

            if (item.getTrackId().isEmpty()) {
                return false;
            }

            entityRepository.delete(item);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteEntity of MixContentReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Content> getSpecifiedEntities(String param) throws ReceiverException {
        try {
            EntityRepository<Content> entityRepository = new MixContentRepositoryImpl();
            Specification specification = new GetMixContentByMixIdSpecification(param);
            LOGGER.debug("trying to get specified content for mix with id {}.", param);

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedEntities of MixContentReceiverImpl.\n" + e, e);
        }
    }
}
