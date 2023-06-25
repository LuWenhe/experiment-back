package edu.nuist.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import edu.nuist.entity.UserExcel;
import edu.nuist.service.UserService;
import edu.nuist.util.EncryptUtil;
import edu.nuist.util.GetCurrentDate;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserExcelListener extends AnalysisEventListener<UserExcel> {

    @Resource
    private UserService userService;

    private static final int BATCH_COUNT = 5;

    public UserExcelListener(UserService userService) {
        this.userService = userService;
    }

    private final List<UserExcel> userExcelList = new ArrayList<>();

    @Override
    public void invoke(UserExcel userExcel, AnalysisContext analysisContext) {
        userExcelList.add(userExcel);

        //数据存储到list, 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (userExcelList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            userExcelList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成！");
    }

    // 保存用户信息
    private void saveData() {
        for (UserExcel userExcel : userExcelList) {
            userExcel.setPassword(new EncryptUtil().getEnpPassword(userExcel.getPhone()));
            userExcel.setCreated_time(new GetCurrentDate().getCurrentDate());
            userService.insertUserFromExcel(userExcel);
        }

        log.info("存储数据库成功！");
    }

}