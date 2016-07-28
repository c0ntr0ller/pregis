package ru.progmatik.java.pregis.connectiondb.localdb.organization;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Тест, для класса OrganizationDAO.
 */
public class OrganizationDAOTest {
    @Test
    public void getAllOrganizations() throws Exception {
        OrganizationDAO dao = new OrganizationDAO();
        ArrayList<OrganizationDataSet> allOrganizations = dao.getAllOrganizations();

        assertEquals(allOrganizations.get(0).getOrgPPAGUID(), dao.getOrgPPAGUID());

    }

}