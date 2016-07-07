package com.yjy.service.fundbook;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.FundbookcodeExample;
import com.yjy.repository.mapper.fundbook.FundbookcodeMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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


    public Map<Integer,List<Fundbookcode>> cacheFndbookcode(){
        Map<Integer,List<Fundbookcode>> map=new HashedMap();
        FundbookcodeExample example=new FundbookcodeExample();
        example.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_BUYER);
        map.put(FundConstant.TYPEID_BUYER,  getFundbookcodesByExample(example));


        FundbookcodeExample example2=new FundbookcodeExample();
        example2.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_SALES);
        map.put(FundConstant.TYPEID_SALES,  getFundbookcodesByExample(example2));

        FundbookcodeExample example3=new FundbookcodeExample();
        example3.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_PLATFORM);
        map.put(FundConstant.TYPEID_PLATFORM,  getFundbookcodesByExample(example3));

        return map;
    }


}
