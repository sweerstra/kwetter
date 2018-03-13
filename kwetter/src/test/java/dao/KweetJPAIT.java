package dao;

public class KweetJPAIT {
    /*private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static IKweetDao dao;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("KwetterTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        dao = new KweetDaoJPA(entityManager);
    }

    @Before
    public void setUp() {
        transaction = entityManager.getTransaction();
    }

    @Test
    public void kweetPersistsToDatabase() {
        transaction.begin();

        User user = new User("username", "password", User.Role.USER);

        Kweet kweet = new Kweet("@username hello there", user);
        dao.create(kweet);

        transaction.commit();
    }*/
}
