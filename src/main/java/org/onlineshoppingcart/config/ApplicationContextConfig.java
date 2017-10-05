package org.onlineshoppingcart.config;
 
import java.util.Properties;
 
import javax.sql.DataSource;
 
import org.hibernate.SessionFactory;
import org.onlineshoppingcart.dao.AccountDAO;
import org.onlineshoppingcart.dao.OrderDAO;
import org.onlineshoppingcart.dao.ProductDAO;
import org.onlineshoppingcart.dao.impl.AccountDAOImpl;
import org.onlineshoppingcart.dao.impl.OrderDAOImpl;
import org.onlineshoppingcart.dao.impl.ProductDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
 
@Configuration
@ComponentScan("org.onlineshoppingcart.*")
@EnableTransactionManagement

@PropertySource("classpath:ds-hibernate-cfg.properties")
public class ApplicationContextConfig {
 
    @Autowired
    private Environment env;
 
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();

        rb.setBasenames(new String[] { "messages/validator" });
        return rb;
    }
 
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
                  
        return commonsMultipartResolver;
    }
 
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
        dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
        dataSource.setUrl(env.getProperty("ds.url"));
        dataSource.setUsername(env.getProperty("ds.username"));
        dataSource.setPassword(env.getProperty("ds.password"));
         
        System.out.println("## getDataSource: " + dataSource);
         
        return dataSource;
    }
 
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();
 
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("current_session_context_class", env.getProperty("current_session_context_class"));
         
 
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
         
        factoryBean.setPackagesToScan(new String[] { "org.onlineshoppingcart.entity" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        //
        SessionFactory sf = factoryBean.getObject();
        return sf;
    }
 
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }
 
    @Bean(name = "productDAO")
    public ProductDAO getProductDAO() {
        return new ProductDAOImpl();
    }
 
    @Bean(name = "orderDAO")
    public OrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }
     
    @Bean(name = "accountDAO")
    public AccountDAO getAccountDAO()  {
        return new AccountDAOImpl();
    }
 
}