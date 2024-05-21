package julielerche.capstone.dependencies;

import dagger.Component;
import julielerche.capstone.activity.CreateUserActivity;

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

//    /**
//     *Provides the relevant activity.
//     * @return GetUserActivity
//     */
//    GetUserActivity provideGetUserActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return AddAssetToUserActivity
//     */
//    AddAssetToUserActivity provideAddAssetToUserActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return SearchUserActivity
//     */
//    SearchUsersActivity provideSearchUsersActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return UpdateUserActivity
//     */
//    UpdateUserActivity provideUpdateUserActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return UpdateAssetInUserActivity
//     */
//    UpdateAssetInUserActivity provideUpdateAssetInUserActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetCurrentlyReadingActivity
//     */
//    GetCurrentlyReadingActivity provideGetCurrentlyReadingActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return SearchAssetsActivity
//     */
//    SearchAssetsActivity provideSearchAssetsActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return RemoveUserActivity
//     */
//    RemoveUserActivity provideRemoveUserActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return RemoveAssetFromUserActivity
//     */
//    RemoveAssetFromUserActivity provideRemoveAssetFromUserActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetUserAssetsActivity
//     */
//    GetUserAssetsActivity provideGetUserAssetsActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetUserUsersActivity
//     */
//    GetUserUsersActivity provideGetUserUsersActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetAssetFromUserActivity
//     */
//    GetAssetFromUserActivity provideGetAssetFromUserActivity();
}
