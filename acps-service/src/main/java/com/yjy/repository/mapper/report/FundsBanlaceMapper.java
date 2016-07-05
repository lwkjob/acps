package com.yjy.repository.mapper.report;

import com.yjy.entity.report.FundsBanlace;
import com.yjy.entity.report.FundsBanlaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface FundsBanlaceMapper {
    int countByExample(FundsBanlaceExample example);

    int deleteByExample(FundsBanlaceExample example);

    @Delete({
        "delete from reportsaleronlinefundstobalance",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into reportsaleronlinefundstobalance (InvoiceType, ParentId, ",
        "InvoiceNumber, BuyerId, ",
        "Buyer_CompanyName, Buyer_LinkMan, ",
        "SalerId, HappenTime, ",
        "TotalGoodsPrice, TotalPostPrice, ",
        "AccGoodsPrice, AccPostPrice, ",
        "RedPacketGoodsPrice, RedPacketPostPrice, ",
        "PlatformCouponPrice, SalerCouponPrice, ",
        "SalerSpecialPrice, SalerAgioPrice, ",
        "SalerFullAmount, SalerChangeGoodsPrice, ",
        "SalerChangePostPrice, Balance)",
        "values (#{invoicetype,jdbcType=TINYINT}, #{parentid,jdbcType=INTEGER}, ",
        "#{invoicenumber,jdbcType=VARCHAR}, #{buyerid,jdbcType=INTEGER}, ",
        "#{buyerCompanyname,jdbcType=VARCHAR}, #{buyerLinkman,jdbcType=VARCHAR}, ",
        "#{salerid,jdbcType=INTEGER}, #{happentime,jdbcType=BIGINT}, ",
        "#{totalgoodsprice,jdbcType=DECIMAL}, #{totalpostprice,jdbcType=DECIMAL}, ",
        "#{accgoodsprice,jdbcType=DECIMAL}, #{accpostprice,jdbcType=DECIMAL}, ",
        "#{redpacketgoodsprice,jdbcType=DECIMAL}, #{redpacketpostprice,jdbcType=DECIMAL}, ",
        "#{platformcouponprice,jdbcType=DECIMAL}, #{salercouponprice,jdbcType=DECIMAL}, ",
        "#{salerspecialprice,jdbcType=DECIMAL}, #{saleragioprice,jdbcType=DECIMAL}, ",
        "#{salerfullamount,jdbcType=DECIMAL}, #{salerchangegoodsprice,jdbcType=DECIMAL}, ",
        "#{salerchangepostprice,jdbcType=DECIMAL}, #{balance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(FundsBanlace record);

    int insertSelective(FundsBanlace record);

    List<FundsBanlace> selectByExample(FundsBanlaceExample example);

    @Select({
        "select",
        "Id, InvoiceType, ParentId, InvoiceNumber, BuyerId, Buyer_CompanyName, Buyer_LinkMan, ",
        "SalerId, HappenTime, TotalGoodsPrice, TotalPostPrice, AccGoodsPrice, AccPostPrice, ",
        "RedPacketGoodsPrice, RedPacketPostPrice, PlatformCouponPrice, SalerCouponPrice, ",
        "SalerSpecialPrice, SalerAgioPrice, SalerFullAmount, SalerChangeGoodsPrice, SalerChangePostPrice, ",
        "Balance",
        "from reportsaleronlinefundstobalance",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    FundsBanlace selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FundsBanlace record, @Param("example") FundsBanlaceExample example);

    int updateByExample(@Param("record") FundsBanlace record, @Param("example") FundsBanlaceExample example);

    int updateByPrimaryKeySelective(FundsBanlace record);

    @Update({
        "update reportsaleronlinefundstobalance",
        "set InvoiceType = #{invoicetype,jdbcType=TINYINT},",
          "ParentId = #{parentid,jdbcType=INTEGER},",
          "InvoiceNumber = #{invoicenumber,jdbcType=VARCHAR},",
          "BuyerId = #{buyerid,jdbcType=INTEGER},",
          "Buyer_CompanyName = #{buyerCompanyname,jdbcType=VARCHAR},",
          "Buyer_LinkMan = #{buyerLinkman,jdbcType=VARCHAR},",
          "SalerId = #{salerid,jdbcType=INTEGER},",
          "HappenTime = #{happentime,jdbcType=BIGINT},",
          "TotalGoodsPrice = #{totalgoodsprice,jdbcType=DECIMAL},",
          "TotalPostPrice = #{totalpostprice,jdbcType=DECIMAL},",
          "AccGoodsPrice = #{accgoodsprice,jdbcType=DECIMAL},",
          "AccPostPrice = #{accpostprice,jdbcType=DECIMAL},",
          "RedPacketGoodsPrice = #{redpacketgoodsprice,jdbcType=DECIMAL},",
          "RedPacketPostPrice = #{redpacketpostprice,jdbcType=DECIMAL},",
          "PlatformCouponPrice = #{platformcouponprice,jdbcType=DECIMAL},",
          "SalerCouponPrice = #{salercouponprice,jdbcType=DECIMAL},",
          "SalerSpecialPrice = #{salerspecialprice,jdbcType=DECIMAL},",
          "SalerAgioPrice = #{saleragioprice,jdbcType=DECIMAL},",
          "SalerFullAmount = #{salerfullamount,jdbcType=DECIMAL},",
          "SalerChangeGoodsPrice = #{salerchangegoodsprice,jdbcType=DECIMAL},",
          "SalerChangePostPrice = #{salerchangepostprice,jdbcType=DECIMAL},",
          "Balance = #{balance,jdbcType=DECIMAL}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FundsBanlace record);
}