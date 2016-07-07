package com.yjy.repository.mapper.report;

import com.yjy.common.entity.report.GoodsRefundPost;
import com.yjy.common.entity.report.GoodsRefundPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface GoodsRefundPostMapper {
    int countByExample(GoodsRefundPostExample example);

    int deleteByExample(GoodsRefundPostExample example);

    @Delete({
        "delete from reportsaleronlinegoodstoredrefundpost",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into reportsaleronlinegoodstoredrefundpost (InvoiceType, ParentId, ",
        "InvoiceNumber, BuyerId, ",
        "Buyer_CompanyName, Buyer_LinkMan, ",
        "SalerId, HappenTime, ",
        "TotalPostPrice, AccReturnPostPrice, ",
        "RedpacketReturnPostPrice, ChangePostPrice, ",
        "Balance)",
        "values (#{invoicetype,jdbcType=TINYINT}, #{parentid,jdbcType=INTEGER}, ",
        "#{invoicenumber,jdbcType=VARCHAR}, #{buyerid,jdbcType=INTEGER}, ",
        "#{buyerCompanyname,jdbcType=VARCHAR}, #{buyerLinkman,jdbcType=VARCHAR}, ",
        "#{salerid,jdbcType=INTEGER}, #{happentime,jdbcType=BIGINT}, ",
        "#{totalpostprice,jdbcType=DECIMAL}, #{accreturnpostprice,jdbcType=DECIMAL}, ",
        "#{redpacketreturnpostprice,jdbcType=DECIMAL}, #{changepostprice,jdbcType=DECIMAL}, ",
        "#{balance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(GoodsRefundPost record);

    int insertSelective(GoodsRefundPost record);

    List<GoodsRefundPost> selectByExample(GoodsRefundPostExample example);

    @Select({
        "select",
        "Id, InvoiceType, ParentId, InvoiceNumber, BuyerId, Buyer_CompanyName, Buyer_LinkMan, ",
        "SalerId, HappenTime, TotalPostPrice, AccReturnPostPrice, RedpacketReturnPostPrice, ",
        "ChangePostPrice, Balance",
        "from reportsaleronlinegoodstoredrefundpost",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    GoodsRefundPost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GoodsRefundPost record, @Param("example") GoodsRefundPostExample example);

    int updateByExample(@Param("record") GoodsRefundPost record, @Param("example") GoodsRefundPostExample example);

    int updateByPrimaryKeySelective(GoodsRefundPost record);

    @Update({
        "update reportsaleronlinegoodstoredrefundpost",
        "set InvoiceType = #{invoicetype,jdbcType=TINYINT},",
          "ParentId = #{parentid,jdbcType=INTEGER},",
          "InvoiceNumber = #{invoicenumber,jdbcType=VARCHAR},",
          "BuyerId = #{buyerid,jdbcType=INTEGER},",
          "Buyer_CompanyName = #{buyerCompanyname,jdbcType=VARCHAR},",
          "Buyer_LinkMan = #{buyerLinkman,jdbcType=VARCHAR},",
          "SalerId = #{salerid,jdbcType=INTEGER},",
          "HappenTime = #{happentime,jdbcType=BIGINT},",
          "TotalPostPrice = #{totalpostprice,jdbcType=DECIMAL},",
          "AccReturnPostPrice = #{accreturnpostprice,jdbcType=DECIMAL},",
          "RedpacketReturnPostPrice = #{redpacketreturnpostprice,jdbcType=DECIMAL},",
          "ChangePostPrice = #{changepostprice,jdbcType=DECIMAL},",
          "Balance = #{balance,jdbcType=DECIMAL}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(GoodsRefundPost record);
}