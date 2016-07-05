package com.yjy.repository.mapper.report;

import com.yjy.entity.report.FundsSettle;
import com.yjy.entity.report.FundsSettleExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface FundsSettleMapper {
    int countByExample(FundsSettleExample example);

    int deleteByExample(FundsSettleExample example);

    @Delete({
        "delete from reportsaleronlinefundstosettle",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into reportsaleronlinefundstosettle (InvoiceType, ParentId, ",
        "InvoiceNumber, SalerId, ",
        "HappenTime, TotalGoodsPrice, ",
        "TotalPostPrice, AccGoodsPrice, ",
        "AccPostPrice, RedPacketGoodsPrice, ",
        "RedPacketPostPrice, PlatformCouponPrice, ",
        "SalerCouponPrice, SalerSpecialPrice, ",
        "SalerAgioPrice, SalerFullAmount, ",
        "SalerChangeGoodsPrice, SalerChangePostPrice, ",
        "Balance)",
        "values (#{invoicetype,jdbcType=TINYINT}, #{parentid,jdbcType=INTEGER}, ",
        "#{invoicenumber,jdbcType=VARCHAR}, #{salerid,jdbcType=INTEGER}, ",
        "#{happentime,jdbcType=BIGINT}, #{totalgoodsprice,jdbcType=DECIMAL}, ",
        "#{totalpostprice,jdbcType=DECIMAL}, #{accgoodsprice,jdbcType=DECIMAL}, ",
        "#{accpostprice,jdbcType=DECIMAL}, #{redpacketgoodsprice,jdbcType=DECIMAL}, ",
        "#{redpacketpostprice,jdbcType=DECIMAL}, #{platformcouponprice,jdbcType=DECIMAL}, ",
        "#{salercouponprice,jdbcType=DECIMAL}, #{salerspecialprice,jdbcType=DECIMAL}, ",
        "#{saleragioprice,jdbcType=DECIMAL}, #{salerfullamount,jdbcType=DECIMAL}, ",
        "#{salerchangegoodsprice,jdbcType=DECIMAL}, #{salerchangepostprice,jdbcType=DECIMAL}, ",
        "#{balance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(FundsSettle record);

    int insertSelective(FundsSettle record);

    List<FundsSettle> selectByExample(FundsSettleExample example);

    @Select({
        "select",
        "Id, InvoiceType, ParentId, InvoiceNumber, SalerId, HappenTime, TotalGoodsPrice, ",
        "TotalPostPrice, AccGoodsPrice, AccPostPrice, RedPacketGoodsPrice, RedPacketPostPrice, ",
        "PlatformCouponPrice, SalerCouponPrice, SalerSpecialPrice, SalerAgioPrice, SalerFullAmount, ",
        "SalerChangeGoodsPrice, SalerChangePostPrice, Balance",
        "from reportsaleronlinefundstosettle",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    FundsSettle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FundsSettle record, @Param("example") FundsSettleExample example);

    int updateByExample(@Param("record") FundsSettle record, @Param("example") FundsSettleExample example);

    int updateByPrimaryKeySelective(FundsSettle record);

    @Update({
        "update reportsaleronlinefundstosettle",
        "set InvoiceType = #{invoicetype,jdbcType=TINYINT},",
          "ParentId = #{parentid,jdbcType=INTEGER},",
          "InvoiceNumber = #{invoicenumber,jdbcType=VARCHAR},",
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
    int updateByPrimaryKey(FundsSettle record);
}