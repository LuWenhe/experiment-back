package edu.nuist.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nuist.annotation.PermissionAnnotation;
import edu.nuist.dto.ClazzDto;
import edu.nuist.entity.Student;
import edu.nuist.enums.RoleEnum;
import edu.nuist.service.BackClazzService;
import edu.nuist.vo.BasicResultVO;
import edu.nuist.vo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/backClazz")
@PermissionAnnotation
public class BackClazzController {

    @Resource
    private BackClazzService backClazzService;

    @GetMapping("/getAllClazzList")
    public BasicResultVO<List<ClazzDto>> getAllClazzList() {
        List<ClazzDto> allClazzList = backClazzService.getAllClazzList();
        return BasicResultVO.success(allClazzList);
    }

    @PostMapping("/getClazzList")
    public BasicResultVO<PageInfo<ClazzDto>> getClazzList(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Integer roleId = pageRequest.getRoleId();
        Integer userId = pageRequest.getUserId();

        PageHelper.startPage(currentPage, pageSize);
        List<ClazzDto> clazzList;

        if (RoleEnum.ADMIN_ROLE.getCode().equals(roleId)) {
            clazzList = backClazzService.getAllClazzList();
        } else {
            clazzList = backClazzService.getClazzListByTeacherId(userId);
        }

        PageInfo<ClazzDto> pageInfo = new PageInfo<>(clazzList, pageSize);
        return BasicResultVO.success(pageInfo);
    }

    @GetMapping("/getStudents")
    public BasicResultVO<PageInfo<Student>> getStudentsByClazzId(
                                       @RequestParam("clazzId") Integer clazzId,
                                       @RequestParam("currentPage") Integer currentPage,
                                       @RequestParam("pageSize") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Student> students = backClazzService.getStudentsByClazzId(clazzId);
        PageInfo<Student> pageInfo = new PageInfo<>(students, pageSize);
        return BasicResultVO.success(pageInfo);
    }

    @PostMapping("/addClazz")
    public BasicResultVO<Void> addClazz(@RequestBody ClazzDto clazz) {
        try {
            backClazzService.addClazz(clazz);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @PostMapping("/updateClazz")
    public BasicResultVO<Void> editClazz(@RequestBody ClazzDto clazzDto) {
        try {
            backClazzService.updateClazz(clazzDto);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

    @GetMapping("/deleteClazz")
    public BasicResultVO<Void> deleteClazz(@RequestParam("clazzId") Integer clazzId) {
        try {
            backClazzService.deleteClazz(clazzId);
            return BasicResultVO.success();
        } catch (Exception e) {
            return BasicResultVO.fail();
        }
    }

}
