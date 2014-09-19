package net.dotmarks.api;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import net.dotmarks.api.health.DotmarkHealthCheck;
import net.dotmarks.api.health.LocalCassandraHealthCheck;
import net.dotmarks.api.health.PingHealthCheck;
import net.dotmarks.api.resources.CasResource;
import net.dotmarks.api.resources.DotmarksResource;
import net.dotmarks.api.resources.PingResource;
import net.dotmarks.api.resources.UsersResource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.stuartgunter.dropwizard.cassandra.CassandraHealthCheck;

import com.datastax.driver.core.Cluster;

public class DotmarksApplication extends Application<DotmarksConfiguration> {
    public static void main(String[] args) throws Exception {
        new DotmarksApplication().run(args);
    }

    @Override
    public String getName() {
        return "dotmarks";
    }


    @Override
    public void initialize(Bootstrap<DotmarksConfiguration> bootstrap) {
    }
    
    @Override
    public void run(DotmarksConfiguration configuration, Environment environment) throws Exception {
    	
    	configureCors(environment);
    	
        Cluster cassandra = configuration.getCassandraFactory().build(environment);
        // Resources
        
        environment.jersey().register(new CasResource(cassandra));
        environment.jersey().register(new PingResource());
        environment.jersey().register(new DotmarksResource(cassandra));
        environment.jersey().register(new UsersResource(cassandra));

        // HealthChecks
        
        environment.healthChecks().register("Ping", new PingHealthCheck());
        environment.healthChecks().register("Local Cassandra", new LocalCassandraHealthCheck(cassandra));
        String validationQuery = "SELECT url FROM dotmarks.dotmarks";
        environment.healthChecks().register("Cassandra", new CassandraHealthCheck(cassandra, validationQuery));
        environment.healthChecks().register("DotMarks", new DotmarkHealthCheck(cassandra));

    }
    
    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
