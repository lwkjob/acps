package com.yjy.repository.mapper;

import com.yjy.entity.Fundbook;
import com.yjy.entity.FundbookExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface FundbookMapper {
    int countByExample(FundbookExample example);

    int deleteByExample(FundbookExample example);

    @Delete({
        "delete from fundbook",
        "where BookId = #{bookid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long bookid);

    @Insert({
        "insert into fundbook (EntryId, PlatformRole, ",
        "EntryUserRole, AccBookNumber, ",
        "EntryNumber, UserId, ",
        "AreaCode, InvoiceNumber, ",
        "HappenTime, Goods, ",
        "Money, Balance)",
        "values (#{entryid,jdbcType=BIGINT}, #{platformrole,jdbcType=INTEGER}, ",
        "#{entryuserrole,jdbcType=INTEGER}, #{accbooknumber,jdbcType=INTEGER}, ",
        "#{entrynumber,jdbcType=VARCHAR}, #{userid,jdbcType=INTEGER}, ",
        "#{areacode,jdbcType=INTEGER}, #{invoicenumber,jdbcType=VARCHAR}, ",
        "#{happentime,jdbcType=BIGINT}, #{goods,jdbcType=DECIMAL}, ",
        "#{money,jdbcType=DECIMAL}, #{balance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="bookid", before=false, resultType=Long.class)
    int insert(Fundbook record);

    int insertSelective(Fundbook record);

    List<Fundbook> selectByExample(FundbookExample example);

    @Select({
        "select",
        "BookId, EntryId, PlatformRole, EntryUserRole, AccBookNumber, EntryNumber, UserId, ",
        "AreaCode, InvoiceNumber, HappenTime, Goods, Money, Balance",
        "from fundbook",
        "where BookId = #{bookid,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    Fundbook selectByPrimaryKey(Long bookid);

    int updateByExampleSelective(@Param("record") Fundbook record, @Param("example") FundbookExample example);

    int updateByExample(@Param("record") Fundbook record, @Param("example") FundbookExample example);

    int updateByPrimaryKeySelective(Fundbook record);

    @Update({
        "update fundbook",
        "set EntryId = #{entryid,jdbcType=BIGINT},",
          "PlatformRole = #{platformrole,jdbcType=INTEGER},",
          "EntryUserRole = #{entryuserrole,jdbcType=INTEGER},",
          "AccBookNumber = #{accbooknumber,jdbcType=INTEGER},",
          "EntryNumber = #{entrynumber,jdbcType=VARCHAR},",
          "UserId = #{userid,jdbcType=INTEGER},",
          "AreaCode = #{areacode,jdbcType=INTEGER},",
          "InvoiceNumber = #{invoicenumber,jdbcType=VARCHAR},",
          "HappenTime = #{happentime,jdbcType=BIGINT},",
          "Goods = #{goods,jdbcType=DECIMAL},",
          "Money = #{money,jdbcType=DECIMAL},",
          "Balance = #{balance,jdbcType=DECIMAL}",
        "where BookId = #{bookid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Fundbook record);
}