package com.yjy.repository.mapper;

import com.yjy.entity.Fundbookday;
import com.yjy.entity.FundbookdayExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

public interface FundbookdayMapper {
    int countByExample(FundbookdayExample example);

    int deleteByExample(FundbookdayExample example);

    @Insert({
        "insert into fundbookday (BookId, PlatformRole, ",
        "EntryUserRole, AccBookNumber, ",
        "UserId, AreaCode, ",
        "BookDate, PrevBalance, ",
        "HappenDebit, HappenCredit, ",
        "Balance)",
        "values (#{bookid,jdbcType=BIGINT}, #{platformrole,jdbcType=INTEGER}, ",
        "#{entryuserrole,jdbcType=INTEGER}, #{accbooknumber,jdbcType=INTEGER}, ",
        "#{userid,jdbcType=INTEGER}, #{areacode,jdbcType=INTEGER}, ",
        "#{bookdate,jdbcType=INTEGER}, #{prevbalance,jdbcType=DECIMAL}, ",
        "#{happendebit,jdbcType=DECIMAL}, #{happencredit,jdbcType=DECIMAL}, ",
        "#{balance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Fundbookday record);

    int insertSelective(Fundbookday record);

    List<Fundbookday> selectByExample(FundbookdayExample example);

    int updateByExampleSelective(@Param("record") Fundbookday record, @Param("example") FundbookdayExample example);

    int updateByExample(@Param("record") Fundbookday record, @Param("example") FundbookdayExample example);
}