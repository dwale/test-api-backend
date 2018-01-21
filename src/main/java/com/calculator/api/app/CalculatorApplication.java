package com.calculator.api.app;

import com.calculator.api.auth.CalculatorAuthFilter;
import com.calculator.api.resource.CalculatorResource;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.calculator.api.config.AppConfiguration;
import com.calculator.api.config.CalculatorConfiguration;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.errors.EarlyEofExceptionMapper;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

import static jdk.nashorn.internal.objects.NativeFunction.bind;

public class CalculatorApplication extends Application<CalculatorConfiguration> {
    public static void main(String[] args) throws Exception {
        new CalculatorApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<CalculatorConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<CalculatorConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(CalculatorConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(CalculatorConfiguration config, Environment env) throws Exception {
        final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        ((DefaultServerFactory) config.getServerFactory()).setRegisterDefaultExceptionMappers(false);

        env.jersey().register(new EarlyEofExceptionMapper());

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, config.getDataSourceFactory(), "mariadb");

        Injector injector = createInjector(config, jdbi);
        env.jersey().register(injector.getInstance(CalculatorAuthFilter.class));
        env.jersey().register(injector.getInstance(CalculatorResource.class));

        //resources
    }

    private Injector createInjector(CalculatorConfiguration config, DBI jdbi) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(AppConfiguration.class).toInstance(config.getAppConfig());
            }
        });
    }
}
