package ru.progmatik.java.pregis.model;

/**
 * Класс перечисления, содержит в себе выпадающий список для листа "Основные сведения",
 * столбца "Вид документа, удостоверяющего личность".
 */
public enum  DocumentType {

    PASSPORT_RF("Паспорт гражданина Российской Федерации"),
    PASSPORT_USSR("Паспорт гражданина СССР"),
    PASSPORT_FOREIGN_STATE("Паспорт гражданина иностранного государства"),
    ORDINARY_PASSPORT("Общегражданский заграничный паспорт"),
    PASSPORT_DEPARTMENT_OF_THE_NAVY("Заграничный паспорт Министерства морского флота"),
    DIPLOMATIC_PASSPORT("Дипломатический паспорт"),
    PASSPORT_SEAMAN("Паспорт моряка (удостоверение личности моряка)"),
    MILITARY_ID_SOLDIER("Военный билет военнослужащего"),
    TEMPORARY_ID_CARD_ISSUED_INSTEAD_OF_MILITARY("Временное удостоверение, выданное взамен военного билета"),
    IDENTITY_CARD_OFFICER_MORF_AND_MVDRF_AND_OTHER_MILITARY_UNITS("Удостоверение личности офицера Министерства" +
            " обороны Российской Федерации, Министерства внутренних дел Российской Федерации и других воинских формирований" +
            " с приложением справки о прописке (регистрации)"),
    BIRTH_CERTIFICATE("Свидетельство о рождении"),
    CERTIFICATE_EXAMINATION_OF_AN_APPLICATION_FOR_REFUGEE_STATUS_OF_RF("Свидетельство о рассмотрении ходатайства о признании" +
            " беженцем на территории Российской Федерации по существу"),
    RESIDENCE_PERMIT("Вид на жительство иностранного гражданина или лица без гражданства"),
    REFERENCE_FOR_THE_RELEASE_FROM_PRISON("Справка об освобождении из мест лишения свободы"),
    TEMPORARY_IDENTITY_CARD_CITIZEN_RF("Временное удостоверение личности гражданина Российской Федерации"),
    IDENTITY_IDP("Удостоверение вынужденного переселенца"),
    RVP_RF("Разрешение на временное проживание в Российской Федерации"),
    ID_CARD_REFUGEE_RF("Удостоверение беженца в Российской Федерации"),
    CERTIFICATE_RECOGNITION_OF_A_PERSON_IDP("Свидетельство о рассмотрении ходатайства о признании лица вынужденным переселенцем"),
    CERTIFICATE_OF_TEMPORARY_ASYLUM_RF("Свидетельство о предоставлении временного убежища на территории Российской Федерации"),
    OTHER_IDENTITY_DOCUMENTS("Иные документы, предусмотренные законодательством Российской Федерации или признаваемые" +
            " в соответствии с международным договором Российской Федерации в качестве документов, удостоверяющих личность");

    private String typeDocument;

    DocumentType(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public static DocumentType getTypeDocument(String typeDocument) {

        if (typeDocument.equalsIgnoreCase("паспорт")) {
            return PASSPORT_RF;
        }

        DocumentType[] types = DocumentType.values();

        for (DocumentType type : types) {
            if (typeDocument.equalsIgnoreCase(type.getTypeDocument())) {
                return type;
            }
        }
        return null;
    }

    public String getTypeDocument() {
        return typeDocument;
    }
}
