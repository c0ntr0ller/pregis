//package ru.progmatik.java.pregis.connectiondb.localdb.organization;
//
//import org.junit.Test;
//import ru.progmatik.java.pregis.other.ResourcesUtil;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by andryha on 15.07.2016.
// */
//public class OrganizationDataSetTest {
//    @Test
//    public void equalsTest() throws Exception {
//        Organization dataSet = new Organization(1, "ООО \"ЦУЖФ\"", "ООО \"ЦУЖФ\"", "1125476111903",
//                "5404465096", "540201001", "8ba7cf1c-aafe-4547-9584-29aba22a8c96", "f66eafcd-8c5c-4d6d-bb87-8117f9dd83da",
//                ResourcesUtil.instance().getCompanyRole(), ResourcesUtil.instance().getCompanyGradId(), "Description");
//
//        Organization dataSetTest = new Organization("ООО \"ЦУЖФ\"", "ООО \"ЦУЖФ\"", "1125476111903",
//                "5404465096", "540201001", "8ba7cf1c-aafe-4547-9584-29aba22a8c96", "f66eafcd-8c5c-4d6d-bb87-8117f9dd83da",
//                ResourcesUtil.instance().getCompanyRole(), ResourcesUtil.instance().getCompanyGradId(), "Description");
//        System.out.println(dataSet.equals(dataSetTest));
//        assertEquals(dataSet, dataSetTest);
//    }
//
//    @Test
//    public void hashCodeTest() throws Exception {
//
//        Organization dataSet = new Organization(1, "ООО \"ЦУЖФ\"", "ООО \"ЦУЖФ\"", "1125476111903",
//                "5404465096", "540201001", "8ba7cf1c-aafe-4547-9584-29aba22a8c96", "f66eafcd-8c5c-4d6d-bb87-8117f9dd83da",
//                ResourcesUtil.instance().getCompanyRole(), ResourcesUtil.instance().getCompanyGradId(), "Description");
//
//        Organization dataSetTest = new Organization("ООО \"ЦУЖФ\"", "ООО \"ЦУЖФ\"", "1125476111903",
//                "5404465096", "540201001", "8ba7cf1c-aafe-4547-9584-29aba22a8c96", "f66eafcd-8c5c-4d6d-bb87-8117f9dd83da",
//                ResourcesUtil.instance().getCompanyRole(), ResourcesUtil.instance().getCompanyGradId(), "Description");
//        System.out.println(dataSet.hashCode() + " vs " + dataSetTest.hashCode());
//        assertEquals(dataSet.hashCode(), dataSetTest.hashCode());
//    }
//
//}