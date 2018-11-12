package team6.models;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import team6.repositories.QueryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QueryTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private QueryRepository queryRepository;

	@Test
    public void queryTest() throws Exception {
        String name = "English Speakers";
        Query query = new Query();
        query.setQueryName(name);
        assertThat(query.getQueryName(), equalTo(name));
    }

	@Test
    public void whenFindByName_thenReturnEmployee() {
        // given
		String name = "English Speakers";
        Query query = new Query();
        query.setQueryName(name);
        entityManager.persist(query);
        entityManager.flush();
    
        // when
        Optional<Query> found = queryRepository.findById(query.getId());

        // then
        assertTrue(found.isPresent());
        assertThat(found.get().getQueryName(),
            equalTo(query.getQueryName()));
    }
}
