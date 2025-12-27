package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.AfterSale;
import com.easy.entity.AfterSaleImage;
import com.easy.entity.dto.AfterSaleDTO;
import com.easy.entity.dto.pg.AfterSalePageQueryDTO;
import com.easy.entity.vo.AfterSaleVO;
import com.easy.mapper.AfterSaleImageMapper;
import com.easy.mapper.AfterSaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfterSaleServiceImpl extends BaseServiceImpl<AfterSale, AfterSaleDTO, AfterSaleVO, AfterSalePageQueryDTO>{
    @Autowired
    private AfterSaleImageMapper afterSaleImageMapper;

    @Autowired
    AfterSaleServiceImpl(AfterSaleMapper mapper) {
        super(mapper);
    }

    @Override
    protected void afterPost(AfterSale e, AfterSaleDTO afterSaleDTO) throws AppException {
        if(afterSaleDTO.getImages() != null){
            for (String image : afterSaleDTO.getImages()) {
                afterSaleImageMapper.insert(AfterSaleImage.builder()
                        .afterSaleId(e.getId())
                        .image(image)
                        .build()
                );
            }
        }
    }
}
