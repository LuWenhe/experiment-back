package edu.nuist.service;

import edu.nuist.vo.DashBoardCount;

public interface BackDashBoardService {

    DashBoardCount getDashBoardInfo(Integer roleId, Integer userId);

}
