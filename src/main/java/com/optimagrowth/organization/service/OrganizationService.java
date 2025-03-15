package com.optimagrowth.organization.service;

import com.optimagrowth.organization.events.source.SimpleSourceBean;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.ScopedSpan;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.optimagrowth.organization.events.source.Action.*;

@Service
public class OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository repository;
    private final SimpleSourceBean simpleSourceBean;
    private final Tracer tracer;

    public OrganizationService(OrganizationRepository repository,
                               SimpleSourceBean simpleSourceBean,
                               Tracer tracer) {
        this.repository = repository;
        this.simpleSourceBean = simpleSourceBean;
        this.tracer = tracer;
    }

    public Organization findById(String organizationId) {
        Optional<Organization> opt;
        ScopedSpan newSpan = tracer.startScopedSpan("getOrgDBCall");
        try {
            opt = repository.findById(organizationId);
            if (opt.isEmpty()) {
                String message = String.format("Unable to find an organization with the Organization id %s", organizationId);
                logger.error(message);
                throw new IllegalArgumentException(message);
            }
            logger.debug("Retrieving Organization Info: {}", opt.get());
        } finally {
            newSpan.tag("peer.service", "postgres");
            newSpan.name("Client received");
            newSpan.end();
        }
        return opt.get();
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        simpleSourceBean.publishOrganizationChange(CREATED, organization.getId());
        return organization;

    }

    public void update(Organization organization) {
        repository.save(organization);
        simpleSourceBean.publishOrganizationChange(UPDATED, organization.getId());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
        simpleSourceBean.publishOrganizationChange(DELETED, id);
    }
}