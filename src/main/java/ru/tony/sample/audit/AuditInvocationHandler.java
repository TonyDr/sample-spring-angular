package ru.tony.sample.audit;

import ru.tony.sample.database.entity.AuditRecord;
import ru.tony.sample.database.repository.AuditRepository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

public class AuditInvocationHandler implements InvocationHandler {
    private final AuditRepository auditRepository;
    private final Object bean;
    private final Class type;

    public AuditInvocationHandler(Class type, Object bean, AuditRepository auditRepository) {
        this.type = type;
        this.bean = bean;
        this.auditRepository = auditRepository;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(bean, args);
        Method realMethod = type.getMethod(method.getName(), method.getParameterTypes());
        if (realMethod.isAnnotationPresent(AuditAction.class)) {
            AuditActionType auditType = realMethod.getAnnotation(AuditAction.class).type();
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setType(auditType);
            auditRecord.setRecordId(((AuditedEntity)invoke).getId());
            auditRecord.setObjectName(invoke.getClass().getName());
            auditRecord.setTime(new Timestamp(new Date().getTime()));
            auditRepository.save(auditRecord);
        }
        return invoke;
    }
}
