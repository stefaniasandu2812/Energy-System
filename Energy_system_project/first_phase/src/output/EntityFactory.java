package output;

import input.ConsumerInput;
import input.DistributorInput;
import input.EntityInput;

public final class EntityFactory {
    /**
     * creates EntityOutput objects based on a string it receives as input
     * @param entityType the type of entity we want
     * @param entityInput the entity from input
     * @return a consumer or a distributor
     */
    public EntityOutput createEntity(final String entityType, final EntityInput entityInput) {
        if (entityType.equals("consumer")) {
            return new ConsumerOutput((ConsumerInput) entityInput);
        }
        return new DistributorOutput((DistributorInput) entityInput);
    }
}
