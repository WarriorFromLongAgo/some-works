package com.orhon.smartcampus.modules.core.graphql.gql.service;

/**
 *
 * 整合第三方工具，整合springboot
 * 2019年
 * @Author M
 *
 */

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.orhon.smartcampus.modules.core.graphql.gql.fetcher.*;
import com.orhon.smartcampus.modules.core.graphql.gql.mutation.SystemOutPrintln;
import graphql.TypeResolutionEnvironment;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class SpringBootStart {

    protected final static String TYPE_QUERY = "Query";
    protected final static String TYPE_MUTATION = "Mutation";

    public final static String SCHEMA_ROOT = "graphql/root.graphqls";
    public final static String SCHEMA_BASIC = "graphql/basic.graphqls";
    public final static String SCHEMA_USER = "graphql/user.graphqls";
    public final static String SCHEMA_TEACHER = "graphql/teacher.graphqls";
    public final static String SCHEMA_STUDENT = "graphql/student.graphqls";
    public final static String SCHEMA_ECLASS = "graphql/eclass.graphqls";
    public final static String SCHEMA_MATERIAL = "graphql/material.graphqls";
    public final static String SCHEMA_SCHOOLBASEINFO= "graphql/schoolbaseinfo.graphqls";
    public final static String SCHEMA_SYSTEMCTL= "graphql/systemctl.graphqls";


    @Bean
    public GraphQLSchema schema() throws IOException {
        GraphQLSchema schema = loadSchma();
        return schema;
    }

    public void init(){

    }


    /**
     * 定义schema文件列表...
     */
    protected  String[] getSchemas(){
        return new String[]{SCHEMA_ROOT , SCHEMA_BASIC ,SCHEMA_USER , SCHEMA_TEACHER ,SCHEMA_STUDENT, SCHEMA_ECLASS,SCHEMA_MATERIAL, SCHEMA_SCHOOLBASEINFO,SCHEMA_SYSTEMCTL };
    }

    /**
     * 获取graphql schema结构...
     */
    private GraphQLSchema loadSchma() throws IOException {
        SchemaParser parser = new SchemaParser();
        SchemaGenerator generator = new SchemaGenerator();
        String[] fileList = getSchemas();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        for(String current: fileList) {
            String schemaSDL = loadSchemaFile(current);
            typeRegistry.merge(parser.parse(schemaSDL));
        }
        GraphQLSchema schema = generator.makeExecutableSchema(typeRegistry, buildDynamicRuntimeWiring());
        return schema;
    }

    /**
     * 加载schema文件
     * @param fileName
     * @return
     * @throws IOException
     */
    protected String loadSchemaFile(String fileName) throws IOException {
        URL url = Resources.getResource(fileName);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        return sdl;
    }


    /**
     * 自定义变量 ， 本办法在框架中被抛弃，需要重构　。。。
     */
    public static final GraphQLScalarType GQLDate = new GraphQLScalarType("Date" , "" , new Coercing(){

        @Override
        public Object serialize(Object dataFetcherResult) throws CoercingSerializeException {
            Date d = (Date)dataFetcherResult;
            Long orgData = d.getTime();
            return orgData / 1000;
        }

        @Override
        public Object parseValue(Object input) throws CoercingParseValueException {
            return input;
        }

        @Override
        public Object parseLiteral(Object input) throws CoercingParseLiteralException {
            return input;
        }
    });


    protected RuntimeWiring buildDynamicRuntimeWiring() {
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();

        builder.scalar(GQLDate);

        //基本hello world 测试...
        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("hello" , new StaticDataFetcher("world")));
        //用户列表接口绑定
        //builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("users" , UserBaseFetcher.usersDataFetcher));
        //用户详情接口绑定
        builder.type(TYPE_QUERY , typeWiring-> typeWiring.dataFetcher("user" , UserDetailsFetcher.detailFetcher));
        //届接口绑定
        //builder.type(TYPE_QUERY , typeWring->typeWring.dataFetcher("grade" , null));
        //学生列表接口绑定
        builder.type(TYPE_QUERY , typeWiring->typeWiring.dataFetcher("students" , StudentBaseFetcher.studentsFetcher));
        //教师列表接口绑定
        builder.type(TYPE_QUERY , typeWiring->typeWiring.dataFetcher("teachers" , TeacherBaseFetcher.teacherBaseFetcher));
        //届接口绑定
        builder.type(TYPE_QUERY , typeWiring->typeWiring.dataFetcher("arrives" , ArriveBaseFetcher.arrivesFetcher));
        //年级接口绑定
        builder.type(TYPE_QUERY , typeWiring->typeWiring.dataFetcher("grades" , GradeBaseFetcher.gradeFetcher));
        //学段接口绑定
        builder.type(TYPE_QUERY , typeWiring->typeWiring.dataFetcher("period" , PeriodBaseFetcher.periodFetcher));
        //学校信息接口
        builder.type(TYPE_QUERY , typeWiring->typeWiring.dataFetcher("school" , SchoolBaseFetcher.schoolFetcher));


        /**
         * 以下为mutation接口列表
         */
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("upvotePost" , SystemOutPrintln.systemOutPrintln));

        /**
         * 教师模块
         * editor : ths
         */
        // 教师新增/修改接口
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addTeacher" , SystemOutPrintln.addTeacher));
        // 教师删除接口
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteTeacher" , SystemOutPrintln.deleteTeacher));
        // 新增/修改教师办公室
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addTeacherOffice" , SystemOutPrintln.addTeacherOffice));
        // 删除教师办公室维护
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteTeacherOffice" , SystemOutPrintln.deleteTeacherOffice));
        // 新增/修改分配办公室信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addOfficeArrangeUser" , SystemOutPrintln.addOfficeArrangeUser));
        // 删除分配办公室信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteOfficeArrangeUser" , SystemOutPrintln.deleteOfficeArrangeUser));

        /**
         * 学生模块
         * editor : ths
         */
        // 学生新增/修改接口
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addStudent" , SystemOutPrintln.addStudent));
        // 学生删除接口
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteStudent" , SystemOutPrintln.deleteStudent));
        // 新增/修改学生学籍基本信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addLearninfo" , SystemOutPrintln.addLearninfo));
        // 删除学生学籍基本信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteLearninfo" , SystemOutPrintln.deleteLearninfo));
        // 新增/修改班级信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addEclass" , SystemOutPrintln.addEclass));
        // 删除班级信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteEclass" , SystemOutPrintln.deleteEclass));
        //！！！无对应的模型！！！
        // 设置班主任
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("setEclassHeadTeacher" , SystemOutPrintln.setEclassHeadTeacher));
//        // 删除班主任信息
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteEclassHeadTeacher" , SystemOutPrintln.deleteEclassHeadTeacher));

        /**
         * 基础信息模块
         * editor : ths
         */
        // 新增/修改数据字典
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addDictionary" , SystemOutPrintln.addDictionary));
        // 数据字典删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteDictionary" , SystemOutPrintln.deleteDictionary));
        // 新增/修改学科信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addSubject" , SystemOutPrintln.addSubject));
        // 学科信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteSubject" , SystemOutPrintln.deleteSubject));
        // 新增/修改届信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addArrives" , SystemOutPrintln.addArrives));
        // 届信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteArrives" , SystemOutPrintln.deleteArrives));
        // 新增/修改学段信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addPeriod" , SystemOutPrintln.addPeriod));
        // 学段信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deletePeriod" , SystemOutPrintln.deletePeriod));
        // 新增/修改学校信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addSchool" , SystemOutPrintln.addSchool));
        // 学校信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteSchool" , SystemOutPrintln.deleteSchool));
        // 新增/修改地区信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addRegion" , SystemOutPrintln.addRegion));
        // 地区信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteRegion" , SystemOutPrintln.deleteRegion));

        /**
         * 系统维护模块
         * editor : ths
         */
        // 新增/修改模块信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addModules" , SystemOutPrintln.addModules));
        // 模块信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteModules" , SystemOutPrintln.deleteModules));
        // 新增/修改菜单信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addMenus" , SystemOutPrintln.addMenus));
        // 菜单信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteMenus" , SystemOutPrintln.deleteMenus));
        // 新增/修改页面组件
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addWidgets" , SystemOutPrintln.addWidgets));
        // 页面组件删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteWidgets" , SystemOutPrintln.deleteWidgets));
        // 新增/修改操作权限
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addOperation" , SystemOutPrintln.addOperation));
        // 操作权限删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteOperation" , SystemOutPrintln.deleteOperation));

        /**
         * 学校基础信息-物资基础信息
         * editor : ths
         */
        // 房间管理
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addRoom" , SystemOutPrintln.addRoom));
        // 房间管删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteRoom" , SystemOutPrintln.deleteRoom));
//        // 楼层管理
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addFloor" , SystemOutPrintln.addFloor));
//        // 楼层管删除
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteFloor" , SystemOutPrintln.deleteFloor));
//        // 单元管理
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addUnit" , SystemOutPrintln.addUnit));
//        // 单元管删除
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteUnit" , SystemOutPrintln.deleteUnit));
//        // 建筑管理
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addBuilding" , SystemOutPrintln.addBuilding));
//        // 建筑管删除
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteBuilding" , SystemOutPrintln.deleteBuilding));
//        // 场地管理
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addSite" , SystemOutPrintln.addSite));
//        // 场地管删除
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteSite" , SystemOutPrintln.deleteSite));
//        // 校区管理
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addCampus" , SystemOutPrintln.addCampus));
//        // 校区管删除
//        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteCampus" , SystemOutPrintln.deleteCampus));

        /**
         * 学校基础信息维护
         * editor : ths
         */
        // 新增/修改学校设定信息
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addSchoolSettings" , SystemOutPrintln.addSchoolSettings));
        // 学校设定信息删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteSchoolSettings" , SystemOutPrintln.deleteSchoolSettings));
        // 新增/修改部门添加
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("addDepartment" , SystemOutPrintln.addDepartment));
        // 部门删除
        builder.type(TYPE_MUTATION , typeWiring->typeWiring.dataFetcher("deleteDepartment" , SystemOutPrintln.deleteDepartment));
        return builder.build();
    }
}
