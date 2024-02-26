package infrastructure.resources;

import domain.Candidate;
import domain.ElectionCacheTest;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static domain.ElectionCacheTest.ELECTION_ID;
import static infrastructure.repositories.RedisElectionRepositoryTest.CANDIDATES_SIZE;
import static infrastructure.repositories.RedisElectionRepositoryTest.KEY;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusIntegrationTest
@TestHTTPEndpoint(VotingResource.class)
class VotingResourceIT {
    private final ElectionCacheTest election = ElectionCacheTest.create(Instancio.stream(Candidate.class).limit(CANDIDATES_SIZE).toList());
    private final List<api.dto.out.Election> elections = List.of(ElectionCacheTest.toElectionDtoOut(election));
    private final Map<String, Double> rank = election.votes()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(entry -> entry.getKey().id(),
                    entry -> entry.getValue().doubleValue()));
    @Inject
    RedisDataSource dataSource;

    @BeforeEach
    @TestTransaction
    void setup() {
        SortedSetCommands<String, String> commands = dataSource.sortedSet(String.class, String.class);
        commands.zadd(KEY + election.id(), rank);
    }

    @Test
    void candidates() {
        var response = given()
                .when().get()
                .then().statusCode(RestResponse.StatusCode.OK).extract().as(api.dto.out.Election[].class);

        assertEquals(elections, Arrays.stream(response).toList());
    }

    @Test
    void vote() {
        String candidateId = elections.get(0).candidates().get(0);

        given().contentType(MediaType.APPLICATION_JSON)
                .when().post("/elections/" + ELECTION_ID + "/candidates/" + candidateId)
                .then().statusCode(RestResponse.StatusCode.ACCEPTED);
    }

    @AfterEach
    @TestTransaction
    void tearDown() {
        dataSource.flushall();
    }
}