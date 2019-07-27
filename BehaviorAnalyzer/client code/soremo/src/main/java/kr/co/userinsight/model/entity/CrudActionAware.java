package kr.co.userinsight.model.entity;

public interface CrudActionAware {
    CrudAction getCrudAction();
    void setCrudAction(CrudAction crudAction);
}
