package com.yjy.repository.mapper.report;

import com.yjy.entity.report.FundsMonth;
import com.yjy.entity.report.FundsMonthExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface FundsMonthMapper {
    int countByExample(FundsMonthExample example);

    int deleteByExample(FundsMonthExample example);

    @Delete({
        "delete from reportsaleronlinefundstomonth",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into reportsaleronlinefundstomonth (CurYear, CurMonth, ",
        "SalerId, PrevBalance, ",
        "CurReceiptPrice, CurRedPrice, ",
        "CurMarkupPrice, CurSettlePrice, ",
        "CurBalance)",
        "values (#{curyear,jdbcType=INTEGER}, #{curmonth,jdbcType=INTEGER}, ",
        "#{salerid,jdbcType=INTEGER}, #{prevbalance,jdbcType=DECIMAL}, ",
        "#{curreceiptprice,jdbcType=DECIMAL}, #{curredprice,jdbcType=DECIMAL}, ",
        "#{curmarkupprice,jdbcType=DECIMAL}, #{cursettleprice,jdbcType=DECIMAL}, ",
        "#{curbalance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(FundsMonth record);

    int insertSelective(FundsMonth record);

    List<FundsMonth> selectByExample(FundsMonthExample example);

    @Select({
        "select",
        "Id, CurYear, CurMonth, SalerId, PrevBalance, CurReceiptPrice, CurRedPrice, CurMarkupPrice, ",
        "CurSettlePrice, CurBalance",
        "from reportsaleronlinefundstomonth",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    FundsMonth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FundsMonth record, @Param("example") FundsMonthExample example);

    int updateByExample(@Param("record") FundsMonth record, @Param("example") FundsMonthExample example);

    int updateByPrimaryKeySelective(FundsMonth record);

    @Update({
        "update reportsaleronlinefundstomonth",
        "set CurYear = #{curyear,jdbcType=INTEGER},",
          "CurMonth = #{curmonth,jdbcType=INTEGER},",
          "SalerId = #{salerid,jdbcType=INTEGER},",
          "PrevBalance = #{prevbalance,jdbcType=DECIMAL},",
          "CurReceiptPrice = #{curreceiptprice,jdbcType=DECIMAL},",
          "CurRedPrice = #{curredprice,jdbcType=DECIMAL},",
          "CurMarkupPrice = #{curmarkupprice,jdbcType=DECIMAL},",
          "CurSettlePrice = #{cursettleprice,jdbcType=DECIMAL},",
          "CurBalance = #{curbalance,jdbcType=DECIMAL}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FundsMonth record);
}