package edu.nuist.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import edu.nuist.entity.Student;
import edu.nuist.service.BackUserService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudentExcelListener extends AnalysisEventListener<Student> {

    @Resource
    private BackUserService backUserService;

    private static final int BATCH_COUNT = 10;

    private final List<Student> studentList = new ArrayList<>();

    public StudentExcelListener(BackUserService backUserService) {
        this.backUserService = backUserService;
    }

    /**
     * 此方法每一条数据解析都会来调用
     */
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        log.info("student: " + student);
        studentList.add(student);

        // 达到BATCH_COUNT，保存一次数据库，防止内存中数据过大
        if (studentList.size() >= BATCH_COUNT) {
            addStudents(studentList);
            studentList.clear();
        }
    }

    /**
     * 所有数据解析完成都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }

    /**
     * 保存到数据库
     */
    private void addStudents(List<Student> studentList) {
        backUserService.addStudents(studentList);
    }

}
