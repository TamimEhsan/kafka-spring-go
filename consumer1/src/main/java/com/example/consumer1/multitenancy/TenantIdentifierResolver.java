package com.example.consumer1.multitenancy;

import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

	private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> "public");


	public void setCurrentTenant(String tenant) {
		System.out.println("Setting tenant to " + tenant);
		currentTenant.set(tenant);
	}

	@Override
	public String resolveCurrentTenantIdentifier() {
		System.out.println("Resolving tenant to " + currentTenant.get()+ " in thread "+Thread.currentThread().threadId());
		return currentTenant.get();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}

	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
	}
}