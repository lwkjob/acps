package com.yjy.repository.mapper;

import com.yjy.entity.Bookcode;
import com.yjy.entity.BookcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface BookcodeMapper {
    int countByExample(BookcodeExample example);

    int deleteByExample(BookcodeExample example);

    @Delete({
        "delete from bookcode",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into bookcode (PlatFormRole, EntryUserRole, ",
        "AccBookNumber, PName, ",
        "EName, AName, Idx)",
        "values (#{platformrole,jdbcType=INTEGER}, #{entryuserrole,jdbcType=INTEGER}, ",
        "#{accbooknumber,jdbcType=INTEGER}, #{pname,jdbcType=VARCHAR}, ",
        "#{ename,jdbcType=VARCHAR}, #{aname,jdbcType=VARCHAR}, #{idx,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Bookcode record);

    int insertSelective(Bookcode record);

    List<Bookcode> selectByExample(BookcodeExample example);

    @Select({
        "select",
        "Id, PlatFormRole, EntryUserRole, AccBookNumber, PName, EName, AName, Idx",
        "from bookcode",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Bookcode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bookcode record, @Param("example") BookcodeExample example);

    int updateByExample(@Param("record") Bookcode record, @Param("example") BookcodeExample example);

    int updateByPrimaryKeySelective(Bookcode record);

    @Update({
        "update bookcode",
        "set PlatFormRole = #{platformrole,jdbcType=INTEGER},",
          "EntryUserRole = #{entryuserrole,jdbcType=INTEGER},",
          "AccBookNumber = #{accbooknumber,jdbcType=INTEGER},",
          "PName = #{pname,jdbcType=VARCHAR},",
          "EName = #{ename,jdbcType=VARCHAR},",
          "AName = #{aname,jdbcType=VARCHAR},",
          "Idx = #{idx,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Bookcode record);
}