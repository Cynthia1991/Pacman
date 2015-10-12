package pacman372.dementiaaid.CreateAccount;

import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by jieliang on 12/10/2015.
 */
public class CareEntity extends TableServiceEntity
{
    public CareEntity(String name) {
        this.partitionKey = name;
        this.rowKey = name;
    }
    public CareEntity()
    {}
    String name;
    Integer phone;
    public String getName()
    {
        return this.name;
    }
    public Integer getPhone()
    {
        return this.phone;
    }

    public void setName(String name)
    {
        this.name=name;
    }
    public  void setPhone(Integer phone)
    {
        this.phone=phone;
    }
}
