package vn.learn.core.test;

import org.hibernate.SessionFactory;
import org.testng.annotations.Test;
import vn.learn.core.common.utils.HibernateUtil;
import vn.learn.core.dao.RoleDao;
import vn.learn.core.daoimpl.RoleDaoImpl;
import vn.learn.core.persistence.entity.RoleEntity;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

public class RoleTest {
    @Test
    public void checkFindAll()
    {
        RoleDao roleDao=new RoleDaoImpl();
       List<RoleEntity> roleList=roleDao.findAll();
    }
    @Test
    public void checkUpdate()
    {
        RoleDao roleDao=new RoleDaoImpl();
        RoleEntity roleEntity=new RoleEntity();
        roleEntity.setRoleId(1);
        roleEntity.setName("USER_1");
        RoleEntity roleEntity1=roleDao.update(roleEntity);

    }
    @Test
    public void checkSave()
    {
        RoleDao roleDao=new RoleDaoImpl();
        RoleEntity roleEntity=new RoleEntity();

        roleEntity.setName("ADMIN");

        roleDao.save(roleEntity);
    }
    @Test
    public void checkFindId()
    {
        RoleDao roleDao=new RoleDaoImpl();
        RoleEntity roleEntity= roleDao.findById(1);
    }
    @Test
    public void checkFindProperty()
    {
        RoleDao roleDao=new RoleDaoImpl();
        String property="roleid";
        String value="1";
        String sortExpression=null;
        String sortDirection=null;
        Object object=roleDao.findByProperty(property,value,sortExpression,sortDirection);

    }
    @Test
    public void checkDelete() {
//        RoleDao roleDao=new RoleDaoImpl();
//        List<Integer> list=new ArrayList<Integer>();
//        list.add(1);
//        list.add(2);
//        int count=roleDao.delete(list);
    }


}
