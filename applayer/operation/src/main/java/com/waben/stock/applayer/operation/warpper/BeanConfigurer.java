package com.waben.stock.applayer.operation.warpper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.mail.util.MailSSLSocketFactory;
import com.waben.stock.applayer.operation.exception.AuthMethodNotSupportedException;
import com.waben.stock.applayer.operation.service.security.InvestorUserDetailService;
import com.waben.stock.applayer.operation.service.security.ManagerUserDetailService;
import com.waben.stock.applayer.operation.warpper.auth.provider.InvestorAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.ManagerAuthenticationProvider;
import com.waben.stock.interfaces.exception.ExecptionHandler;
import com.waben.stock.interfaces.pojo.ExceptionInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletResponse;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Created by yuyidi on 2017/9/19.
 * @desc
 */
@Configuration
public class BeanConfigurer {

    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private Integer port;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InvestorUserDetailService investorDetailService;

    @Autowired
    private ManagerUserDetailService managerUserDetailService;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return jackson2HttpMessageConverter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(investorDetailService);
//        return  daoAuthenticationProvider;
//    }

    @Bean
    public InvestorAuthenticationProvider investorAuthenticationProvider() {
        InvestorAuthenticationProvider investorAuthenticationProvider = new InvestorAuthenticationProvider
                (passwordEncoder());
        investorAuthenticationProvider.setUserDetailsService(investorDetailService);
        return investorAuthenticationProvider;
    }

    //
    @Bean
    public ManagerAuthenticationProvider managerAuthenticationProvider() {
        ManagerAuthenticationProvider managerAuthenticationProvider = new ManagerAuthenticationProvider
                (passwordEncoder());
        managerAuthenticationProvider.setUserDetailsService(managerUserDetailService);
        return managerAuthenticationProvider;
    }

//    @Bean
//    public RoleVoter roleVoter() {
//        RoleVoter roleVoter = new RoleVoter();
//        roleVoter.setRolePrefix("");
//        return roleVoter;
//    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages_zh_CN");
        return messageSource;
    }

    @Bean
    public ExecptionHandler execptionHandler() {
        ExecptionHandler execptionHandler = new ExecptionHandler();
        execptionHandler.extendException(Arrays.asList(new ExceptionInformation(AuthMethodNotSupportedException.class,
                HttpServletResponse.SC_FORBIDDEN, "403")));
        return execptionHandler;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        logger.info("host,username:{}{}", connectionFactory.getHost(), connectionFactory.getUsername());
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }


    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 创建 委托申请买入队列
     */
    @Bean(name = "entrustApplyBuyIn")
    public Queue entrustBuyInQueue() {
        return new Queue("entrustApplyBuyIn");
    }
    /**
     * 创建 委托申请卖出队列
     */
    @Bean(name = "entrustApplySellOut")
    public Queue entrustSellOutQueue() {
        return new Queue("entrustApplySellOut");
    }

    @Bean("buyRecord")
    public TopicExchange buyRecordExchange() {
        return new TopicExchange("buyRecord");
    }


    /**绑定申请买入卖出路由与队列*/
    @Bean
    public Binding bindingExchangEntrustBuyIn(@Qualifier("entrustApplyBuyIn") Queue queue,
                                         @Qualifier("buyRecord") TopicExchange buyRecordExchange) {
        return BindingBuilder.bind(queue).to(buyRecordExchange).with("applyBuyIn");
    }
    @Bean
    public Binding bindingExchangEntrustSellOut(@Qualifier("entrustApplySellOut") Queue queue,
                                         @Qualifier("buyRecord") TopicExchange buyRecordExchange) {
        return BindingBuilder.bind(queue).to(buyRecordExchange).with("applySellOut");
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties properties = new Properties();
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
//        Session session = Session.getDefaultInstance(properties, new MailAuthenricator(username, password));
//        javaMailSender.setSession(session);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }


}
