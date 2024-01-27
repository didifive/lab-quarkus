package domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@QuarkusTest
class CandidateServiceTest {
    @Inject
    CandidateService service;

    @InjectMock
    CandidateRepository repository;

    @Test
    void save(){
        Candidate candidate = Instancio.create(Candidate.class);

        service.save(candidate);

        verify(repository).save(candidate);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        service.findAll();

        verify(repository).findAll();
    }

}