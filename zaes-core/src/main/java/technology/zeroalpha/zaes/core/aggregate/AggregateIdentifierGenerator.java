package technology.zeroalpha.zaes.core.aggregate;

import java.util.UUID;

public interface AggregateIdentifierGenerator {
    default String generateNewIdentifier() {
        return UUID.randomUUID().toString();
    }
}
