package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

//    @Autowired
//    private static Environment environment;
//
//    @BeforeClass
//    public static void checkProfiles() throws Exception {
//        Assume.assumeTrue(isJdbc());
//    }
//
//    private static boolean isJdbc() {
//        for (String profile : environment.getActiveProfiles()) {
//
//        }
//
//        return false;
//    }
}