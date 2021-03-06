package com.cortez.samples.batchrealworld.business;

import com.cortez.samples.batchrealworld.configuration.Configuration;
import com.cortez.samples.batchrealworld.entity.*;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@SuppressWarnings("unchecked")
@Named
@Local
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BatchBusinessBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    @Configuration
    private List<String> fileExtensions;

    public List<Company> findAllCompanies() {
        return entityManager.createQuery("SELECT c FROM Company c").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCompanyFolder(CompanyFolder companyFolder) {
        entityManager.persist(companyFolder);
    }

    public CompanyFolder findCompanyFolderById(Integer companyId, FolderType folderType) {
        return entityManager.find(CompanyFolder.class, new CompanyFolder.CompanyFolderPK(companyId, folderType));
    }

    public List<CompanyFolder> findCompanyFolders() {
        return entityManager.createQuery("SELECT cf FROM CompanyFolder cf").getResultList();
    }

    public List<CompanyFolder> findCompanyFoldersByType(FolderType type) {
        return entityManager.createQuery("SELECT cf FROM CompanyFolder cf WHERE cf.id.folderType = :type")
                            .setParameter("type", type)
                            .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCompanyFile(CompanyFile companyFile) {
        entityManager.persist(companyFile);
    }

    public List<CompanyFile> findCompanyFiles(Integer companyId) {
        return entityManager.createQuery("SELECT cf FROM CompanyFile cf WHERE cf.companyId = :companyId")
                            .setParameter("companyId", companyId).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCompanyData(CompanyData companyData) {
        entityManager.persist(companyData);
    }

    public Long countCompanyDate() {
        return ((Long) entityManager.createQuery("SELECT COUNT(cd) FROM CompanyData cd").getSingleResult());
    }

    public List<CompanyData> findCompanyData(Integer companyId, Integer startPosition, Integer maxResults) {
        return entityManager.createQuery("SELECT cd FROM CompanyData cd WHERE cd.companyId = :companyId")
                            .setParameter("companyId", companyId)
                            .setFirstResult(startPosition)
                            .setMaxResults(maxResults)
                            .getResultList();
    }
}
