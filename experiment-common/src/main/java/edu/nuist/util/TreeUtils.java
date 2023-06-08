package edu.nuist.util;

import edu.nuist.dto.MenuDto;

import java.util.List;
import java.util.Objects;

/**
 * 生成目录树的工具类
 */
public class TreeUtils {

    public static MenuDto getMenuTrees(List<MenuDto> menuDtoList) {
        MenuDto menuTree = null;

        for (MenuDto menuDto : menuDtoList) {
            if (menuDto.getParentId() == 0) {
                menuTree = menuDto;
            }

            for (MenuDto childMenu : menuDtoList) {
                if (Objects.equals(childMenu.getParentId(), menuDto.getId())) {
                    menuDto.addChild(childMenu);
                }
            }
        }

        return menuTree;
    }

}
