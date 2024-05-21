package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateUserRequest;
import julielerche.capstone.activity.results.CreateUserResult;
import julielerche.capstone.dynamodb.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the CreateUserActivity for the TaskWarriorService's CreateUser API.
 * <p>
 * This API allows the customer to create a new user in the table.
 */
public class CreateUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new CreateUserActivity object.
     *
     * @param userDao UserDao to access the playlists table.
     */
    @Inject
    public CreateUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by persisting a new user
     * with the provided userId from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided user name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createUserRequest request object containing the customerId
     * @return createUserResult result object containing the API defined
     */

    public CreateUserResult handleRequest(final CreateUserRequest createUserRequest) {
        log.info("Received CreateUserRequest {}", createUserRequest);

        //TODO check string for valid characters

//        Set<String> booklistTags = null;
//        if (createUserRequest.getTags() != null) {
//            booklistTags = new HashSet<>(createUserRequest.getTags());
//        }
//
//        Booklist booklist = new Booklist();
//        booklist.setId(MusicPlaylistServiceUtils.generatePlaylistId());
//        booklist.setName(createUserRequest.getName());
//        booklist.setCustomerId(createUserRequest.getCustomerId());
//        booklist.setBookCount(0);
//        booklist.setTags(booklistTags);
//        booklist.setBooks(new ArrayList<>());
//
//        userDao.saveBooklist(booklist);
//
//        BooklistModel booklistModel = new ModelConverterCarbon().toBooklistModel(booklist);
//        return CreateUserResult.builder()
//                .withBooklist(booklistModel)
//                .build();

    }
}
