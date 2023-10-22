package edu.nuist.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import edu.nuist.entity.Student;
import edu.nuist.entity.UserRole;
import edu.nuist.service.BackUserService;
import edu.nuist.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudentExcelListener extends AnalysisEventListener<Student> {

    private static final int BATCH_COUNT = 5;

    private final Integer clazzId;

    private final List<Student> studentList = new ArrayList<>();

    private final List<UserRole> userRoleList = new ArrayList<>();

    @Resource
    private BackUserService backUserService;

    @Resource
    private SysRoleService sysRoleService;

    public StudentExcelListener(Integer clazzId, BackUserService backUserService, SysRoleService sysRoleService) {
        this.clazzId = clazzId;
        this.backUserService = backUserService;
        this.sysRoleService = sysRoleService;
    }

    /**
     * 每一条数据解析都会调用该方法
     */
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(student));

        student.setClazzId(clazzId);
        student.setRole(3);
        UserRole userRole = new UserRole(null, student.getId(), 3);

        studentList.add(student);
        userRoleList.add(userRole);

        // 达到BATCH_COUNT，保存一次数据库，防止内存中数据过大
        if (studentList.size() >= BATCH_COUNT) {
            savaData();
            studentList.clear();
            userRoleList.clear();
        }
    }

    /**
     * 所有数据解析完成都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 最后遗留的数据也需要存储到数据库中
        savaData();
        log.info("所有数据解析完成！");
    }

    /**
     * 保存到数据库
     */
    private void savaData() {
        log.info("{}条数据，开始存储数据库！", studentList.size());
        backUserService.addStudents(studentList);
        sysRoleService.addUserAndRoles(userRoleList);
        log.info("存储数据库成功！");
    }

}
