package com.optimagrowth.organization.service;

import com.optimagrowth.organization.events.source.SimpleSourceBean;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.optimagrowth.organization.events.source.Action.*;

@Service
public class OrganizationService {

    private final OrganizationRepository repository;
    private final SimpleSourceBean simpleSourceBean;

    public OrganizationService(OrganizationRepository repository,
                               SimpleSourceBean simpleSourceBean) {
        this.repository = repository;
        this.simpleSourceBean = simpleSourceBean;
    }

    public Organization findById(String organizationId) {
    	Optional<Organization> opt = repository.findById(organizationId);
        return opt.orElse(null);
    }

    public Organization create(Organization organization){
    	organization.setId( UUID.randomUUID().toString());
        organization = repository.save(organization);
        simpleSourceBean.publishOrganizationChange(CREATED, organization.getId());
        return organization;

    }

    public void update(Organization organization){
    	repository.save(organization);
        simpleSourceBean.publishOrganizationChange(UPDATED, organization.getId());
    }

    public void deleteById(String id){
    	repository.deleteById(id);
        simpleSourceBean.publishOrganizationChange(DELETED, id);
    }
}