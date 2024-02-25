package infrastructure.repositories;

import domain.ElectionRepository;
import domain.ElectionRepositoryTest;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.AfterEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@QuarkusTest
class SQLElectionRepositoryTest extends ElectionRepositoryTest {

    @InjectMock
    SQLElectionRepository repository;
    @Inject
    EntityManager entityManager;

    @Override
    public ElectionRepository repository() {
        return repository;
    }

    @AfterEach
    @TestTransaction
    void tearDown() {
        entityManager.createNativeQuery("TRUNCATE TABLE election_candidate").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE elections").executeUpdate();
    }
}