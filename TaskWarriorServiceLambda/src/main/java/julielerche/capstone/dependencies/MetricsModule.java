package julielerche.capstone.dependencies;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MetricsModule {

    /**
     * Provides CloudWatch client.
     *
     * @return instance for AmazonCloudWatchAsync
     */
    @Provides
    @Singleton
    static AmazonCloudWatch provideCloudWatch() {
        return AmazonCloudWatchAsyncClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}
