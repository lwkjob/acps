package com.yjy.repository.mapper;

import com.yjy.entity.Fundbook;
import com.yjy.entity.FundbookExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

public interface FundbookMapper {
    int countByExample(FundbookExample example);

    int deleteByExample(FundbookExample example);

    @Insert({
        "insert into fundbook (EntryId, PlatformRole, ",
        "EntryUserRole, AccBookNumber, ",
        "EntryNumber, UserId, ",
        "AreaCode, InvoiceNumber, ",
        "HappenTime, Debit, ",
        "Credit, Balance)",
        "values (#{entryid,jdbcType=BIGINT}, #{platformrole,jdbcType=INTEGER}, ",
        "#{entryuserrole,jdbcType=INTEGER}, #{accbooknumber,jdbcType=INTEGER}, ",
        "#{entrynumber,jdbcType=VARCHAR}, #{userid,jdbcType=INTEGER}, ",
        "#{areacode,jdbcType=INTEGER}, #{invoicenumber,jdbcType=VARCHAR}, ",
        "#{happentime,jdbcType=BIGINT}, #{debit,jdbcType=DECIMAL}, ",
        "#{credit,jdbcType=DECIMAL}, #{balance,jdbcType=DECIMAL})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="bookid", before=false, resultType=Long.class)
    int insert(Fundbook record);

    int insertSelective(Fundbook record);

    List<Fundbook> selectByExample(FundbookExample example);

    int updateByExampleSelective(@Param("record") Fundbook record, @Param("example") FundbookExample example);

    int updateByExample(@Param("record") Fundbook record, @Param("example") FundbookExample example);
}