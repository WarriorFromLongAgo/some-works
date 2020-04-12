package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgInformationDao;
import com.orhonit.modules.generator.entity.LeadershipOverseeEntity;
import com.orhonit.modules.generator.entity.ZgInformationEntity;
import com.orhonit.modules.generator.service.ZgInformationService;
import com.orhonit.modules.generator.vo.ZgInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.SunToolkit;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service("zgInformationService")
public class ZgInformationServiceImpl extends ServiceImpl<ZgInformationDao, ZgInformationEntity> implements ZgInformationService {

    @Autowired
    private ZgInformationDao zgInformationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ZgInformationEntity> page = this.selectPage(
                new Query<ZgInformationEntity>(params).getPage(),
                new EntityWrapper<ZgInformationEntity>()
        );
        page.setTotal(this.selectCount(new EntityWrapper<ZgInformationEntity>()));
        return new PageUtils(page);
    }

    @Override
    public PageUtils findAll(Map<String, Object> params) {

        int currPage = 1;
        int limits = 10;

        if(params.get("page") != null){
            currPage = Integer.parseInt((String)params.get("page"));
        }
        if(params.get("limit") != null){
            limits = Integer.parseInt((String)params.get("limit"));
        }
        int page = (currPage-1)*limits;
        List<ZgInformationEntity> findList = zgInformationDao.findList();
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ZgInformationEntity> infoList = new ArrayList<>();
        for (int i = 1; i < findList.size(); i++) {

            if (i-1 == 0){
                infoList.add(findList.get(0));
            }

            if (simpleDateFormat.format(findList.get(i-1).getCreateTime()).equals(simpleDateFormat.format(findList.get(i).getCreateTime()))){
                infoList.add(findList.get(i));

            }else {
                map.put(simpleDateFormat.format(findList.get(i-1).getCreateTime()),infoList);
                infoList = new ArrayList<>();
                infoList.add(findList.get(i));

            }
            if (i+1 == findList.size()){
                map.put(simpleDateFormat.format(findList.get(i).getCreateTime()),infoList);
            }
        }

        List<ZgInformationVO> list = new ArrayList<>();
        for (String s : map.keySet()) {
            ZgInformationVO zgInformationVO = new ZgInformationVO();
            zgInformationVO.setDate(s);
            zgInformationVO.setList((List<ZgInformationEntity>) map.get(s));
            list.add(zgInformationVO);
        }
        params.put("page", page);
        params.put("limit", limits);
        int skipNumber = (currPage - 1) * limits;
        List<ZgInformationVO> collect = list.stream().skip(skipNumber).limit(limits).collect(Collectors.toList());
        Collections.sort(collect, new Comparator<ZgInformationVO>() {
            @Override
            public int compare(ZgInformationVO o1, ZgInformationVO o2) {
                return Integer.parseInt(o2.getDate().replace("-","")) - Integer.parseInt(o1.getDate().replace("-",""));
            }
        });
        Page<ZgInformationVO> infPage = new Page<>(currPage,limits);
        infPage.setRecords(collect);
        infPage.setTotal(list.size());

        return new PageUtils(infPage);
    }

}