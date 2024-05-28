package julielerche.capstone.dependencies;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import dagger.Component;
import julielerche.capstone.activity.AddTaskToUserActivity;
import julielerche.capstone.activity.CreateAssetActivity;
import julielerche.capstone.activity.CreateUserActivity;
import julielerche.capstone.activity.GetUserActivity;
import julielerche.capstone.activity.requests.AddTaskToUserRequest;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Asset Tracker Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return CreateUserActivity
     */
    CreateUserActivity provideCreateUserActivity();

    /**
     * Provides the relevant activity.
     * @return GetUserActivity
     */
    GetUserActivity provideGetUserActivity();

    /**
     * Provides the relevant activity.
     * @return CreateAssetActivity
     */
    CreateAssetActivity provideCreateAssetActivity();

    AddTaskToUserActivity provideAddTaskToUserActivity();
}
