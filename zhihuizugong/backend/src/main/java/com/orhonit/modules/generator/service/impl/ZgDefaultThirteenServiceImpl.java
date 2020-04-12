package com.orhonit.modules.generator.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.orhonit.common.utils.PageUtils;
import com.orhonit.common.utils.Query;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDao;
import com.orhonit.modules.generator.dao.ZgDefaultScoreDeptDao;
import com.orhonit.modules.generator.dao.ZgDefaultThirteenDao;
import com.orhonit.modules.generator.entity.ZgDefaultThirteenEntity;
import com.orhonit.modules.generator.service.ZgDefaultThirteenService;
import com.orhonit.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("zgDefaultThirteenService")
public class ZgDefaultThirteenServiceImpl extends ServiceImpl<ZgDefaultThirteenDao, ZgDefaultThirteenEntity> implements ZgDefaultThirteenService {

    @Autowired
    private ZgDefaultThirteenDao zgDefaultThirteenDao;

    @Autowired
    private ZgDefaultScoreDao zgDefaultScoreDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("userId") != null){
            Long userId = Long.parseLong(params.get("userId").toString());
            Page<ZgDefaultThirteenEntity> page = this.selectPage(
                    new Query<ZgDefaultThirteenEntity>(params).getPage(),
                    new EntityWrapper<ZgDefaultThirteenEntity>().where("user_id = "+userId)
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgDefaultThirteenEntity>().where("user_id = "+userId)));
            return new PageUtils(page);
        }else {
            Page<ZgDefaultThirteenEntity> page = this.selectPage(
                    new Query<ZgDefaultThirteenEntity>(params).getPage(),
                    new EntityWrapper<ZgDefaultThirteenEntity>()
            );
            page.setTotal(this.selectCount(new EntityWrapper<ZgDefaultThirteenEntity>()));
            return new PageUtils(page);
        }
    }

    @Override
    public void save(ZgDefaultThirteenEntity zgDefaultThirteen) {
        List<ZgDefaultThirteenEntity> list = zgDefaultThirteenDao.findThList(zgDefaultThirteen.getUserId());
        SysUserEntity userInfo = zgDefaultScoreDao.findUserInfo(zgDefaultThirteen.getUserId());
        if (list != null && list.size() > 0){
            zgDefaultThirteen.setId(list.get(0).getId());
            zgDefaultThirteen.setLowerId(userInfo.getLowerId());
            zgDefaultThirteen.setUserName(userInfo.getUserTrueName());
            zgDefaultThirteen.setLowerName(userInfo.getLowerName());
            zgDefaultThirteen.setUpdateTime(new Date());
            zgDefaultThirteenDao.updateById(zgDefaultThirteen);
        }else {
            zgDefaultThirteen.setId(UUID.randomUUID().toString().replace("-",""));
            zgDefaultThirteen.setLowerId(userInfo.getLowerId());
            zgDefaultThirteen.setUserName(userInfo.getUserTrueName());
            zgDefaultThirteen.setLowerName(userInfo.getLowerName());
            zgDefaultThirteen.setUpdateTime(new Date());
            zgDefaultThirteenDao.save(zgDefaultThirteen);
        }

    }

}