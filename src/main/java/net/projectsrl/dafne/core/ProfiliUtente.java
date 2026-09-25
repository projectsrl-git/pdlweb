
package net.projectsrl.dafne.core;

public enum ProfiliUtente {
    ADMINISTRATOR("ADM"),
    HUMAN_RESOURCES_MANAGER("HRM"),
    APPROVER("APP"),
    ADMIN_AZIENDA("AAZ"),
    DIPENDENTE("DIP"),
    RESPONSABILE("RES");

    private String code;

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    private ProfiliUtente(String code) {
        this.code = code;
    }
}