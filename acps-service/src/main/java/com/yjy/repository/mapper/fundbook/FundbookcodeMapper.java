package com.yjy.repository.mapper.fundbook;

import com.yjy.entity.fundbook.Fundbookcode;
import com.yjy.entity.fundbook.FundbookcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface FundbookcodeMapper {
    int countByExample(FundbookcodeExample example);

    int deleteByExample(FundbookcodeExample example);

    @Delete({
        "delete from fundbookcode",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into fundbookcode (FundType, BookCodeOne, ",
        "RoleCode, BookCodeTwo, ",
        "BookCodeThree, BookCode, ",
        "BookCodeDesc)",
        "values (#{fundtype,jdbcType=INTEGER}, #{bookcodeone,jdbcType=INTEGER}, ",
        "#{rolecode,jdbcType=INTEGER}, #{bookcodetwo,jdbcType=INTEGER}, ",
        "#{bookcodethree,jdbcType=INTEGER}, #{bookcode,jdbcType=VARCHAR}, ",
        "#{bookcodedesc,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Fundbookcode record);

    int insertSelective(Fundbookcode record);

    List<Fundbookcode> selectByExample(FundbookcodeExample example);

    @Select({
        "select",
        "Id, FundType, BookCodeOne, RoleCode, BookCodeTwo, BookCodeThree, BookCode, BookCodeDesc",
        "from fundbookcode",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Fundbookcode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Fundbookcode record, @Param("example") FundbookcodeExample example);

    int updateByExample(@Param("record") Fundbookcode record, @Param("example") FundbookcodeExample example);

    int updateByPrimaryKeySelective(Fundbookcode record);

    @Update({
        "update fundbookcode",
        "set FundType = #{fundtype,jdbcType=INTEGER},",
          "BookCodeOne = #{bookcodeone,jdbcType=INTEGER},",
          "RoleCode = #{rolecode,jdbcType=INTEGER},",
          "BookCodeTwo = #{bookcodetwo,jdbcType=INTEGER},",
          "BookCodeThree = #{bookcodethree,jdbcType=INTEGER},",
          "BookCode = #{bookcode,jdbcType=VARCHAR},",
          "BookCodeDesc = #{bookcodedesc,jdbcType=VARCHAR}",
        "where Id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Fundbookcode record);
}