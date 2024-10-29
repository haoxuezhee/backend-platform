package com.hxt.project;
import java.util.ArrayList;
import java.util.Date;

import com.hxt.project.bean.*;
import com.hxt.project.common.Resp;
import com.hxt.project.mapper.*;
import com.hxt.project.service.DeptService;
import com.hxt.project.service.ProductService;
import com.hxt.project.service.RoleService;
import com.hxt.project.service.UserService;
import com.hxt.project.utils.WebUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class ProjectApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private DeptService deptService;

    @Test
    void contextLoads() {

    }

    @Test
    void testSelect(){
        User user = userMapper.queryByUsername("zs");
        System.out.println(user);

    }

    @Test
    void testQueryByUsernameAndPhone(){
        List<User> users = userMapper.queryByPhoneUsersAndUsername(null, "");
        System.out.println(users);
    }

    @Test
    void testUpdate(){
        User user = new User();
        user.setId(4L);
        user.setCompany("3333");
        user.setPassword("123456");
        user.setSex("女");
        user.setUpdateTime(new Date());
        user.setStatus(0);


      //  boolean b = userService.updateUser(user.getId());
        //System.out.println(b);
    }

    @Test
    void testDelete(){

        boolean b = userService.deleteUser(5L);
        System.out.println(b);
    }


    @Test
    void testDeptMapper(){
        Dept dept = new Dept();
        dept.setDeptName("大大大");
        dept.setDeptNo("1234");
        dept.setEditTime(new Date());

        dept.setEditUser("6L");


        int insert = deptMapper.insert(dept);
        System.out.println(insert);
    }


    @Test
    void testGetAllDept(){
        List<Dept> allByDeptNameAndDeptNo = deptMapper.getAllByDeptNameAndDeptNo("12", null);
        System.out.println(allByDeptNameAndDeptNo);
    }

    @Test
    void testSaveDept(){
        Dept dept = new Dept();
        dept.setDeptName("1111");
        dept.setDeptNo("1111");
        dept.setEditTime(new Date());
        boolean b = deptService.saveDept(dept);
        System.out.println(b);
    }
    @Autowired
    private MenuMapper menuMapper;
    @Test
    void testMapper(){
        List<Menu> menus = menuMapper.selectAll();
        System.out.println(menus);
    }


    @Autowired RoleMapper roleMapper;

    @Autowired
    RoleService roleService;

    @Test
    void testRoleMenu(){
        int conut = roleMapper.getRoleMenuCountByRoleId(1);
        System.out.println(conut);

    }

    @Autowired
    private GoodsMapper goodsMapper;
    @Test
    void testGoods(){
        List<Goods> goods = goodsMapper.selectAll();
        System.out.println(goods);

        List<Goods> goodsList = goodsMapper.selectByGoodsName("s");
        System.out.println(goodsList);
    }


    @Autowired
    private VariableMapper variableMapper;
    @Test
    void testVariable(){
        List<Variable> variables = variableMapper.selectAll();
        System.out.println(variables);
    }

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void testPermissions(){
        List<Permission> permissions = permissionMapper.selectAll();
        System.out.println(permissions);

        List<Permission> list = permissionMapper.getAllByNameAndCode("列", "");
        System.out.println(list);
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedisConnection() {


        // 尝试设置一个键值对
        Object o = redisTemplate.opsForValue().get("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYiLCJ1c2VybmFtZSI6Ind3Iiwic3RhdHVzIjoiMSIsImV4cCI6MTc1ODI5NTU0Mn0.SsDiakDcOOzZK8QIcngLL-k_J6oEPy8NF7IJvUb-Wno");
        System.out.println(o);
        // 然后获取这个键的值
    }

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;
    @Test
    void testProduct(){
//        List<Product> products = productMapper.selectAll();
//        System.out.println(products);

//        Product ww1 = productMapper.getByName("ww");
//        System.out.println(ww1);
//        int outBound = productMapper.outBound("ww", 1);
//        System.out.println(outBound);
//        int inBound = productMapper.inBound("ww", 2);
//        System.out.println(inBound);
        //boolean ww = productService.outBoundProduct("ww", 2);
        //System.out.println(ww);

        List<Product> topFiveOutBound = productService.getTopFiveOutBound();
        topFiveOutBound.forEach(System.out::println);
    }


    @Test
    void testUser(){
        List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);
    }



}
