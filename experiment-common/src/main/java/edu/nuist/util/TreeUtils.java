package edu.nuist.util;

import edu.nuist.dto.LessonTreeDto;
import edu.nuist.dto.SideMenuDto;

import java.util.List;
import java.util.Objects;

/**
 * 生成目录树的工具类
 */
public class TreeUtils {

    public static SideMenuDto getMenuTrees(List<SideMenuDto> sideMenuDtoList) {
        SideMenuDto menuTree = null;

        for (SideMenuDto sideMenuDto : sideMenuDtoList) {
            if (sideMenuDto.getParentId() == 0) {
                menuTree = sideMenuDto;
            }

            for (SideMenuDto childMenu : sideMenuDtoList) {
                if (Objects.equals(childMenu.getParentId(), sideMenuDto.getId())) {
                    sideMenuDto.addChild(childMenu);
                }
            }
        }

        return menuTree;
    }

    public static LessonTreeDto getLessonFileMenuTrees(List<LessonTreeDto> lessonTreeDtoList) {
        LessonTreeDto lessonFileMenuTree = null;

        for (LessonTreeDto lessonTreeDto : lessonTreeDtoList) {
            if (lessonTreeDto.getParentId() == 0) {
                lessonFileMenuTree = lessonTreeDto;
            }

            for (LessonTreeDto childMenu : lessonTreeDtoList) {
                if (Objects.equals(childMenu.getParentId(), lessonTreeDto.getId())) {
                    lessonTreeDto.addChild(childMenu);
                }
            }
        }

        return lessonFileMenuTree;
    }

}
