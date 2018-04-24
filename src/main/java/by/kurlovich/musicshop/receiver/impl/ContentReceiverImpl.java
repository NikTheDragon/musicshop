package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.ContentRepository;
import by.kurlovich.musicshop.specification.GetAllMixesContentSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ContentReceiverImpl implements EntityReceiver<Content> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentReceiverImpl.class);

    @Override
    public boolean addNewEntity(Content item) throws ReceiverException {
        try {
            Repository<Content> repository = new ContentRepository();

            if (item.getTrackName().isEmpty()) {
                return false;
            }

            switch (repository.getStatus(item)) {
                case ACTIVE:
                    LOGGER.debug("found active content for: {}", item.getTrackName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted content for: {}", item.getTrackName());
                    repository.undelete(item);
                    return true;
                case NA:
                    repository.add(item);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewEntity of ContentReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean deleteEntity(Content item) throws ReceiverException {
        return false;
    }

    @Override
    public boolean updateEntity(Content item) throws ReceiverException {
        return false;
    }

    @Override
    public List<Content> getAllEntities() throws ReceiverException {
        try {
            Repository<Content> repository = new ContentRepository();
            Specification specification = new GetAllMixesContentSpecification();
            LOGGER.debug("Trying to get content for the mix.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllEntities from ContentReceiverImpl.\n" + e, e);
        }
    }
}
