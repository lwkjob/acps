package com.yjy.service;

import com.yjy.entity.Fundbookcode;
import com.yjy.entity.FundbookcodeExample;
import com.yjy.repository.mapper.FundbookcodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
@Service("fundbookcodeService")
public class FundbookcodeService {

        @Resource
    private FundbookcodeMapper fundbookcodeMapper;

    public List<Fundbookcode> getFundbookcodes(){
        return     fundbookcodeMapper.selectByExample(null);
    }

    public List<Fundbookcode> getFundbookcodesByExample(FundbookcodeExample fundbookcodeExample){
        return     fundbookcodeMapper.selectByExample(fundbookcodeExample);
    }


}
