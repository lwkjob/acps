package com.yjy.repository.mapper.report;

import com.yjy.common.entity.report.GoodsMonth;
import com.yjy.common.entity.report.GoodsMonthExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface GoodsMonthMapper {
    int countByExample(GoodsMonthExample example);

    int deleteByExample(GoodsMonthExample example);

    @Delete({
        "delete from reportsaleronlinegoodstomonth",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into reportsaleronlinegoodstomonth (CurYear, CurMonth, ",
        "SalerId, PrevBalance, ",
        "CurOutPrice, CurReceiptPrice, ",
        "CurRedPrice, CurBalance)",
        "values (#{curyear,jdbcType=INTEGER}, #{curmonth,jdbcType=INTEGER}, ",
        "#{salerid,jdbcType=INTEGER}, #{prevbalance,jdbcType=DECIMAL}, ",
        "#{curoutprice,jdbcType=DECIMAL}, #{curreceiptprice,jdbcType=DECIMAL}, ",
        "#{curredprice,jdbcType=DECIMAL}, #{curbalance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(GoodsMonth record);

    int insertSelective(GoodsMonth record);

    List<GoodsMonth> selectByExample(GoodsMonthExample example);

    @Select({
        "select",
        "Id, CurYear, CurMonth, SalerId, PrevBalance, CurOutPrice, CurReceiptPrice, CurRedPrice, ",
        "CurBalance",
        "from reportsaleronlinegoodstomonth",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    GoodsMonth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GoodsMonth record, @Param("example") GoodsMonthExample example);

    int updateByExample(@Param("record") GoodsMonth record, @Param("example") GoodsMonthExample example);

    int updateByPrimaryKeySelective(GoodsMonth record);

    @Update({
        "update reportsaleronlinegoodstomonth",
        "set CurYear = #{curyear,jdbcType=INTEGER},",
          "CurMonth = #{curmonth,jdbcType=INTEGER},",
          "SalerId = #{salerid,jdbcType=INTEGER},",
          "PrevBalance = #{prevbalance,jdbcType=DECIMAL},",
          "CurOutPrice = #{curoutprice,jdbcType=DECIMAL},",
          "CurReceiptPrice = #{curreceiptprice,jdbcType=DECIMAL},",
          "CurRedPrice = #{curredprice,jdbcType=DECIMAL},",
          "CurBalance = #{curbalance,jdbcType=DECIMAL}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(GoodsMonth record);
}