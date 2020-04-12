package com.orhon.smartcampus.modules.core.graphql.gql.model;

public class GQLType {

    public static enum WeekDay  {MON , TUS ,THT }


    public enum UserType {
        teacher,
        student,
        admin,
        parent,
        other

    }
    public static String GetUserType(String val){
        String type;
        switch(val){
            case "teacher":
                type = "教师";
                break;
            case "student":
                type = "学生";
                break;
            case "admin":
                type = "管理员";
                break;
            case "parent":
                type = "家长";
                break;
            default:
                type = "其他";
                break;
        }
        return type;
    }
}

