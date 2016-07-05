package com.yjy.repository.mapper.report;

import com.yjy.entity.report.FundsMarkup;
import com.yjy.entity.report.FundsMarkupExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface FundsMarkupMapper {
    int countByExample(FundsMarkupExample example);

    int deleteByExample(FundsMarkupExample example);

    @Delete({
        "delete from reportsaleronlinefundstomarkup",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into reportsaleronlinefundstomarkup (ParentId, InvoiceNumber, ",
        "BuyerId, Buyer_CompanyName, ",
        "Buyer_LinkMan, SalerId, ",
        "HappenTime, TotalGoodsPrice, ",
        "Balance, AccGoodsPrice, ",
        "SalerSpecialPrice, SalerAgioPrice, ",
        "SalerFullAmount, InvoiceType)",
        "values (#{parentid,jdbcType=INTEGER}, #{invoicenumber,jdbcType=VARCHAR}, ",
        "#{buyerid,jdbcType=INTEGER}, #{buyerCompanyname,jdbcType=VARCHAR}, ",
        "#{buyerLinkman,jdbcType=VARCHAR}, #{salerid,jdbcType=INTEGER}, ",
        "#{happentime,jdbcType=BIGINT}, #{totalgoodsprice,jdbcType=DECIMAL}, ",
        "#{balance,jdbcType=DECIMAL}, #{accgoodsprice,jdbcType=DECIMAL}, ",
        "#{salerspecialprice,jdbcType=DECIMAL}, #{saleragioprice,jdbcType=DECIMAL}, ",
        "#{salerfullamount,jdbcType=DECIMAL}, #{invoicetype,jdbcType=TINYINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(FundsMarkup record);

    int insertSelective(FundsMarkup record);

    List<FundsMarkup> selectByExample(FundsMarkupExample example);

    @Select({
        "select",
        "Id, ParentId, InvoiceNumber, BuyerId, Buyer_CompanyName, Buyer_LinkMan, SalerId, ",
        "HappenTime, TotalGoodsPrice, Balance, AccGoodsPrice, SalerSpecialPrice, SalerAgioPrice, ",
        "SalerFullAmount, InvoiceType",
        "from reportsaleronlinefundstomarkup",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    FundsMarkup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FundsMarkup record, @Param("example") FundsMarkupExample example);

    int updateByExample(@Param("record") FundsMarkup record, @Param("example") FundsMarkupExample example);

    int updateByPrimaryKeySelective(FundsMarkup record);

    @Update({
        "update reportsaleronlinefundstomarkup",
        "set ParentId = #{parentid,jdbcType=INTEGER},",
          "InvoiceNumber = #{invoicenumber,jdbcType=VARCHAR},",
          "BuyerId = #{buyerid,jdbcType=INTEGER},",
          "Buyer_CompanyName = #{buyerCompanyname,jdbcType=VARCHAR},",
          "Buyer_LinkMan = #{buyerLinkman,jdbcType=VARCHAR},",
          "SalerId = #{salerid,jdbcType=INTEGER},",
          "HappenTime = #{happentime,jdbcType=BIGINT},",
          "TotalGoodsPrice = #{totalgoodsprice,jdbcType=DECIMAL},",
          "Balance = #{balance,jdbcType=DECIMAL},",
          "AccGoodsPrice = #{accgoodsprice,jdbcType=DECIMAL},",
          "SalerSpecialPrice = #{salerspecialprice,jdbcType=DECIMAL},",
          "SalerAgioPrice = #{saleragioprice,jdbcType=DECIMAL},",
          "SalerFullAmount = #{salerfullamount,jdbcType=DECIMAL},",
          "InvoiceType = #{invoicetype,jdbcType=TINYINT}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FundsMarkup record);
}