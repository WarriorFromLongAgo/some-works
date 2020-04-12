package com.orhon.smartcampus.modules.attendance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orhon.smartcampus.framework.service.impl.BaseServiceImpl;
import com.orhon.smartcampus.modules.attendance.entity.*;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceDataMapper;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceStatisticsMonthMapper;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceStatisticsSemesterMapper;
import com.orhon.smartcampus.modules.attendance.mapper.AttendanceTeacherMapper;
import com.orhon.smartcampus.modules.attendance.service.*;
import com.orhon.smartcampus.modules.baseinfo.entity.Holidays;
import com.orhon.smartcampus.modules.baseinfo.service.IHolidaysService;
import com.orhon.smartcampus.modules.core.service.InfoService;
import com.orhon.smartcampus.modules.security.entity.Devices;
import com.orhon.smartcampus.modules.security.service.IDevicesService;
import com.orhon.smartcampus.modules.teacher.service.TIInformationService;
import com.orhon.smartcampus.modules.user.entity.Users;
import com.orhon.smartcampus.modules.user.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 考勤数据表 服务实现类
 * </p>
 *
 * @author Orhon
 */
@Service
public class AttendanceDataServiceImpl extends BaseServiceImpl<AttendanceDataMapper, AttendanceData> implements IAttendanceDataService {

    @Autowired
    private IDevicesService devicesService;
    @Autowired
    private IAttendanceTeacherService attendanceTeacherService;
    @Autowired
    private AttendanceTeacherMapper attendanceTeacherMapper;
    @Autowired
    private IAttendanceGroupTimeService attendanceGroupTimeService;
    @Autowired
    private AttendanceDataMapper attendanceDataMapper;
    @Autowired
    private IUsersService usersService;
    @Autowired
    private IHolidaysService holidaysService;
    @Autowired
    private IAttendanceSpecialDateService attendanceSpecialDateService;
    @Autowired
    private IAttendanceSpecialPersonnelService attendanceSpecialPersonnelService;
    @Autowired
    private IAttendanceNoNeedAttendancePersonnelService attendanceNoNeedAttendancePersonnelService;
    @Autowired
    private IAttendanceTeacherLeaveService attendanceTeacherLeaveService;
    @Autowired
    private IAttendanceWeekService attendanceWeekService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private TIInformationService tiInformationService;
    @Autowired
    private AttendanceStatisticsSemesterMapper attendanceStatisticsSemesterMapper;
    @Autowired
    private AttendanceStatisticsMonthMapper attendanceStatisticsMonthMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String insertAttendanceData(HashMap<String, Object> maps) {
        QueryWrapper<Devices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", maps.get("ip"));
        //1 验证设备是否绑定

        Devices resultDevices = devicesService.getOne(queryWrapper);
        if (resultDevices == null) {
            return "设备未注册";
        }

        //使用身份证获取用户User_id;
        Users user = usersService.userInfo(maps);
        Long user_id = user.getId();
        //todo 0 : 判断是否已经存在打卡信息，如果存在跳过
        Boolean isExists = this.isExists(maps, user_id);
        if (isExists) {
            return "重复打卡";
        }

        //todo 1 : 判断是否是假期，如果是假期不需要打卡
        Boolean isHolidays = this.isHolidays(maps);
        if (isHolidays) {
            return "假期全体无需打卡";
        }

        //todo 2 : 判断今天是否是特殊需要上班的日期
        Boolean isSpecialDate = this.isSpecialDate(maps);
        if (isSpecialDate) {
            //是特殊上班日期，无需考虑考勤周
            //todo 3 : 判断是否是外出人员或者特殊人员，如果是无需打卡
            Boolean isSpecialPersonnel = this.isSpecialPersonnel(maps, user_id);
            if (!isSpecialPersonnel) {
                //todo 4 : 判断是否是特殊不需要打卡的人员
                Boolean isNoNeedAttendancePersonnel = this.isNoNeedAttendancePersonnel(user_id);
                if (!isNoNeedAttendancePersonnel) {
                    Boolean result = this.addAttendanceData(maps, user_id, 0);
                    if (result) {
                        return "记录成功";
                    } else {
                        return "记录失败";
                    }
                } else {
                    //todo 6 : 判断该用户是否请假
                    Boolean isLeave = this.isLeave(maps, user_id);
                    if (!isLeave) {
                        Boolean result = this.addAttendanceData(maps, user_id, 0);
                        if (result) {
                            return "记录成功";
                        } else {
                            return "记录失败";
                        }
                    } else {
                        Boolean result = this.addAttendanceData(maps, user_id, 5);
                        if (result) {
                            return "记录成功";
                        } else {
                            return "记录失败";
                        }
                    }
                }
            } else {
                Boolean result = this.addAttendanceData(maps, user_id, 6);
                if (result) {
                    return "记录成功";
                } else {
                    return "记录失败";
                }
            }
        } else {
            //不是特殊上班日期，需要考虑考勤周
            Boolean isWeek = this.isWeek(maps, user_id);
            if (!isWeek) {
                //todo 3 : 判断是否是外出人员或者特殊人员，如果是无需打卡
                Boolean isSpecialPersonnel = this.isSpecialPersonnel(maps, user_id);
                if (!isSpecialPersonnel) {
                    //todo 4 : 判断是否是特殊不需要打卡的人员
                    Boolean isNoNeedAttendancePersonnel = this.isNoNeedAttendancePersonnel(user_id);
                    if (!isNoNeedAttendancePersonnel) {
                        Boolean result = this.addAttendanceData(maps, user_id, 0);
                        if (result) {
                            return "记录成功";
                        } else {
                            return "记录失败";
                        }
                    } else {
                        //todo 6 : 判断该用户是否请假
                        Boolean isLeave = this.isLeave(maps, user_id);
                        if (!isLeave) {
                            //需要插入数据
                            Boolean result = this.addAttendanceData(maps, user_id, 0);
                            if (result) {
                                return "记录成功";
                            } else {
                                return "记录失败";
                            }
                        } else {
                            Boolean result = this.addAttendanceData(maps, user_id, 5);
                            if (result) {
                                return "记录成功";
                            } else {
                                return "记录失败";
                            }
                        }
                    }
                } else {
                    Boolean result = this.addAttendanceData(maps, user_id, 6);
                    if (result) {
                        return "记录成功";
                    } else {
                        return "记录失败";
                    }
                }
            } else {
                Boolean result = this.addAttendanceData(maps, user_id, 6);
                if (result) {
                    return "记录成功";
                } else {
                    return "记录失败";
                }
            }
        }

    }

    /**
     * 插入考勤数据
     *
     * @param maps
     * @param user_id
     * @param alert
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Boolean addAttendanceData(HashMap<String, Object> maps, Long user_id, Integer alert) {
        //获取数据
        Integer school_id = infoService.getCurrentUserSchoolID();
        Integer semester_id = (Integer) infoService.getCurruentUsresSemster().get("id");
        String auth_time = (String) maps.get("auth_time");
        String month = auth_time.substring(0, auth_time.length() - 12);
        Integer attendanceGroupId = attendanceTeacherService.selectAttendanceGroupId(user_id);
        AttendanceGroupTime attendanceGroupTime = attendanceGroupTimeService.selectAttendanceGroupTimeId(maps, attendanceGroupId);
        Integer getAttendance_type;
        if (alert != 0) {
            getAttendance_type = alert;
        } else {
            getAttendance_type = attendanceGroupTime.getAttendance_type();
        }
        Integer attendanceGroupTimeId = attendanceGroupTime.getId();
        //设置参数
        AttendanceData attendanceData = new AttendanceData();
        attendanceData.setSchool_id(school_id);
        attendanceData.setSchoolyear_id(1);
        attendanceData.setSemester_id(semester_id);
        attendanceData.setUser_id(user_id);
        attendanceData.setIdcard((String) maps.get("idcard"));
        attendanceData.setDevice_ip((String) maps.get("ip"));
        attendanceData.setAuth_time(auth_time);
        attendanceData.setMonth(month);
        attendanceData.setAlert(getAttendance_type);
        attendanceData.setAttendance_group_id(attendanceGroupId);
        attendanceData.setAttendance_group_time_id(attendanceGroupTimeId);
        Boolean save = this.save(attendanceData);
        if (save) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 今日出勤率
     *
     * @return 今日出勤率
     */
    @Override
    public HashMap<String, Object> attendancetoday() {
        QueryWrapper<AttendanceTeacher> queryWrapper = new QueryWrapper<>();
        Double teachers = (double) attendanceTeacherMapper.selectCount(queryWrapper);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        QueryWrapper<AttendanceData> querydata = new QueryWrapper<>();
        querydata.like("auth_time", dateFormat.format(new Date()));
        querydata.in("alert", 1, 2);
        Double datas = (double) attendanceDataMapper.selectCount(querydata);
        String rate = String.valueOf((datas / teachers) * 100) + '%';
        HashMap<String, Object> data = new HashMap<>();
        data.put("should", teachers);
        data.put("actual", datas);
        data.put("rate", rate);
        return data;
    }

    /**
     * 返回7日数组
     *
     * @param past
     * @return
     */
    public ArrayList<Object> getPastDate(int past) {
        ArrayList<Object> datas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= past; i++) {
            Date today = calendar.getTime();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String result = format.format(today);
            HashMap<String, Object> ret = new HashMap<>();
            //去数据表中按照日期统计上午的出勤人数，包括正常和迟到
            QueryWrapper<AttendanceData> querydata = new QueryWrapper<>();
            querydata.like("auth_time", result);
            querydata.in("alert", 1, 2);
            Integer peoplecount = attendanceDataMapper.selectCount(querydata);
            ret.put("peopleconut", peoplecount);
            ret.put("date", result);
            datas.add(ret);

        }
        return datas;
    }

    /**
     * 最近7日考勤人数
     *
     * @return
     */
    @Override
    public ArrayList<Object> attendancenearsevenday() {
        return this.getPastDate(7);
    }

    /**
     * 考勤统计
     *
     * @param maps
     * @return
     */
    @Override
    public List attendancedatastatistics(HashMap<String, Object> maps) {
        //从考勤教师表中拿到所有的老师。这些老师需要有部门的筛选，如果传入部门ID，则按照部门ID筛选。不如传入，则取全校老师
        List<AttendanceTeacher> teachers = attendanceTeacherService.teacher(maps);
        //拿着教师列表去视图中取数据
        ArrayList<Object> data = new ArrayList<>();
        for (AttendanceTeacher teacher : teachers) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(teacher));
            //获取教师名称
            String teacher_name = tiInformationService.getTeacherName(teacher.getUser_id());
            jsonObject.put("teacher_name", teacher_name);
            //获取考勤统计数据
            Integer semester_id = (Integer) maps.get("semester_id");
            String month = (String) maps.get("month");
            if (semester_id != null) {
                AttendanceStatisticsSemester attendanceData = attendanceStatisticsSemesterMapper.getAttendanceData(teacher.getUser_id(), semester_id);
                jsonObject.put("attendancedata", attendanceData);
                data.add(jsonObject);
            } else {
                AttendanceStatisticsMonth attendanceData = attendanceStatisticsMonthMapper.getAttendanceData(teacher.getUser_id(), month);
                jsonObject.put("attendancedata", attendanceData);
                data.add(jsonObject);
            }
        }
        return data;
    }

    /**
     * 是否考勤日期
     *
     * @param user_id
     * @return
     */

    private Boolean isWeek(HashMap<String, Object> maps, Long user_id) {
        Integer attendanceGroupId = attendanceTeacherService.selectAttendanceGroupId(user_id);
        String now = (String) maps.get("auth_time");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(now);//
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        QueryWrapper<AttendanceWeek> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attendance_group_id", attendanceGroupId);
        queryWrapper.eq("week", week_index);
        AttendanceWeek week = attendanceWeekService.getOne(queryWrapper);
        if (week == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否请假
     *
     * @param maps
     * @param user_id
     * @return
     */
    private Boolean isLeave(HashMap<String, Object> maps, Long user_id) {
        QueryWrapper<AttendanceTeacherLeave> queryWrapper = new QueryWrapper<>();
        String auth_time = (String) maps.get("auth_time");
        queryWrapper.eq("user_id", user_id);
        queryWrapper.ge("end_at", auth_time);
        queryWrapper.le("start_at", auth_time);
        AttendanceTeacherLeave leave = attendanceTeacherLeaveService.getOne(queryWrapper);
        if (leave == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 领导班子
     *
     * @param user_id
     * @return
     */
    private Boolean isNoNeedAttendancePersonnel(Long user_id) {
        QueryWrapper<AttendanceNoNeedAttendancePersonnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id);
        AttendanceNoNeedAttendancePersonnel noNeedAttendancePersonnel = attendanceNoNeedAttendancePersonnelService.getOne(queryWrapper);
        if (noNeedAttendancePersonnel == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 外出人员
     *
     * @param maps
     * @param user_id
     * @return
     */
    private Boolean isSpecialPersonnel(HashMap<String, Object> maps, Long user_id) {
        QueryWrapper<AttendanceSpecialPersonnel> queryWrapper = new QueryWrapper<>();
        String auth_time = (String) maps.get("auth_time");
        queryWrapper.eq("user_id", user_id);
        queryWrapper.ge("end_at", auth_time);
        queryWrapper.le("start_at", auth_time);
        AttendanceSpecialPersonnel SpecialPersonnelService = attendanceSpecialPersonnelService.getOne(queryWrapper);
        if (SpecialPersonnelService == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否特使考勤日期
     *
     * @param maps
     * @return 是特使上班日期返回true  不是特殊上班日期返回false
     */
    private Boolean isSpecialDate(HashMap<String, Object> maps) {
        QueryWrapper<AttendanceSpecialDate> queryWrapper = new QueryWrapper<>();
        String auth_time = (String) maps.get("auth_time");
        String authTime = auth_time.substring(0, auth_time.length() - 9);
        queryWrapper.eq("specificdate", authTime);
        AttendanceSpecialDate SpecialDate = attendanceSpecialDateService.getOne(queryWrapper);
        if (SpecialDate == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否是假期
     *
     * @param maps
     * @return 是假期返回treu，不是假期返回false
     */
    private Boolean isHolidays(HashMap<String, Object> maps) {
        QueryWrapper<Holidays> queryWrapper = new QueryWrapper<>();
        String auth_time = (String) maps.get("auth_time");
        String authTime = auth_time.substring(0, auth_time.length() - 9);
        queryWrapper.eq("specificdate", authTime);
        Holidays holidays = holidaysService.getOne(queryWrapper);
        if (holidays == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测是否已打卡
     *
     * @param maps
     * @return 找到返回true  未找到返回flase
     */
    @Override
    public Boolean isExists(HashMap<String, Object> maps, Long user_id) {
        //获取当前用户考勤组ID
        Integer attendanceGroupId = attendanceTeacherService.selectAttendanceGroupId(user_id);
        //获取当前打卡时间的时间段ID
        AttendanceGroupTime attendanceGroupTime = attendanceGroupTimeService.selectAttendanceGroupTimeId(maps, attendanceGroupId);
        Integer attendanceGroupTimeId = attendanceGroupTime.getId();
        //去考勤数据表中按照当前考勤组，当前考勤时间段ID。当前用户，查看是否有数据，如果有，返回true，如果没有，返回false
        String auth_time = (String) maps.get("auth_time");
        String authTime = auth_time.substring(0, auth_time.length() - 9);
        AttendanceData attendanceData = attendanceDataMapper.selectAttendnceData(authTime, attendanceGroupId, attendanceGroupTimeId, user_id);
        if (attendanceData == null) {
            return false;
        } else {
            return true;
        }
    }
}
