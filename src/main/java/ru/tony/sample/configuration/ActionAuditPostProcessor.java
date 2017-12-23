package ru.tony.sample.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.tony.sample.audit.AuditedClass;
import ru.tony.sample.audit.AuditInvocationHandler;
import ru.tony.sample.database.repository.AuditRepository;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActionAuditPostProcessor implements BeanPostProcessor{

    private AuditRepository auditRepository;

    private Map<String, Class> classLocator = new HashMap<>();

    public ActionAuditPostProcessor(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        if (bean.getClass().isAnnotationPresent(AuditedClass.class)) {
            classLocator.put(name, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String name) throws BeansException {
        if (classLocator.containsKey(name)) {
            final Class type = classLocator.get(name);
            return Proxy.newProxyInstance(type.getClassLoader(),
                    bean.getClass().getInterfaces(), new AuditInvocationHandler(type, bean, auditRepository));
        }
        return bean;
    }
}
