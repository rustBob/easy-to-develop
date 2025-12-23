package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Locations;
import com.easy.entity.dto.LocationsDTO;
import com.easy.entity.dto.pg.LocationsPageQueryDTO;
import com.easy.entity.vo.LocationsVO;
import com.easy.service.impl.LocationsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/locations")
public class LocationsController extends BaseController<Locations, LocationsDTO, LocationsVO, LocationsPageQueryDTO> {
    @Autowired
    public LocationsController(LocationsServiceImpl service){super("globalLocationsController",service);}
}
