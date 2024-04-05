package src.model;

import java.util.UUID;

public class Employee extends BaseModel {
    protected UUID managerId;
    protected UUID companyId;
    protected String name;

    public UUID getManagerId() {
        return managerId;
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }
}
