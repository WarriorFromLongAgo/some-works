package com.orhon.smartcampus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    //递归获取树型结构数据
    public static List<Map<String, Object>> recursion(Integer id, List<HashMap<String, Object>> modules) throws Exception{
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        Iterator it = modules.iterator();
        while (it.hasNext()){
            Map<String, Object> map = (Map<String, Object>)it.next();
            Map<String , Object> map2 = new HashMap<String, Object>();
            if (map.get("parent_id") == id){
                treeList.add(map);
                //map2.put("level" , 1);
                //map2.put("title" , map.get("title"));
                //使用Iterator，以便在迭代时把listData中已经添加到treeList的数据删除，迭代次数
                it.remove();
            }
        }
        for(Map<String, Object> map:treeList){
            map.put("children", recursion((Integer)map.get("id"), modules));
        }
        return treeList;
    }

}
