package com.yjy.common.entity.fundbook;

import java.util.ArrayList;
import java.util.List;

public class FundbookcodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FundbookcodeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFundtypeIsNull() {
            addCriterion("FundType is null");
            return (Criteria) this;
        }

        public Criteria andFundtypeIsNotNull() {
            addCriterion("FundType is not null");
            return (Criteria) this;
        }

        public Criteria andFundtypeEqualTo(Integer value) {
            addCriterion("FundType =", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeNotEqualTo(Integer value) {
            addCriterion("FundType <>", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeGreaterThan(Integer value) {
            addCriterion("FundType >", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("FundType >=", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeLessThan(Integer value) {
            addCriterion("FundType <", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeLessThanOrEqualTo(Integer value) {
            addCriterion("FundType <=", value, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeIn(List<Integer> values) {
            addCriterion("FundType in", values, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeNotIn(List<Integer> values) {
            addCriterion("FundType not in", values, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeBetween(Integer value1, Integer value2) {
            addCriterion("FundType between", value1, value2, "fundtype");
            return (Criteria) this;
        }

        public Criteria andFundtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("FundType not between", value1, value2, "fundtype");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneIsNull() {
            addCriterion("BookCodeOne is null");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneIsNotNull() {
            addCriterion("BookCodeOne is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneEqualTo(Integer value) {
            addCriterion("BookCodeOne =", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneNotEqualTo(Integer value) {
            addCriterion("BookCodeOne <>", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneGreaterThan(Integer value) {
            addCriterion("BookCodeOne >", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookCodeOne >=", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneLessThan(Integer value) {
            addCriterion("BookCodeOne <", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneLessThanOrEqualTo(Integer value) {
            addCriterion("BookCodeOne <=", value, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneIn(List<Integer> values) {
            addCriterion("BookCodeOne in", values, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneNotIn(List<Integer> values) {
            addCriterion("BookCodeOne not in", values, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeOne between", value1, value2, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andBookcodeoneNotBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeOne not between", value1, value2, "bookcodeone");
            return (Criteria) this;
        }

        public Criteria andRolecodeIsNull() {
            addCriterion("RoleCode is null");
            return (Criteria) this;
        }

        public Criteria andRolecodeIsNotNull() {
            addCriterion("RoleCode is not null");
            return (Criteria) this;
        }

        public Criteria andRolecodeEqualTo(Integer value) {
            addCriterion("RoleCode =", value, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeNotEqualTo(Integer value) {
            addCriterion("RoleCode <>", value, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeGreaterThan(Integer value) {
            addCriterion("RoleCode >", value, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("RoleCode >=", value, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeLessThan(Integer value) {
            addCriterion("RoleCode <", value, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeLessThanOrEqualTo(Integer value) {
            addCriterion("RoleCode <=", value, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeIn(List<Integer> values) {
            addCriterion("RoleCode in", values, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeNotIn(List<Integer> values) {
            addCriterion("RoleCode not in", values, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeBetween(Integer value1, Integer value2) {
            addCriterion("RoleCode between", value1, value2, "rolecode");
            return (Criteria) this;
        }

        public Criteria andRolecodeNotBetween(Integer value1, Integer value2) {
            addCriterion("RoleCode not between", value1, value2, "rolecode");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoIsNull() {
            addCriterion("BookCodeTwo is null");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoIsNotNull() {
            addCriterion("BookCodeTwo is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoEqualTo(Integer value) {
            addCriterion("BookCodeTwo =", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoNotEqualTo(Integer value) {
            addCriterion("BookCodeTwo <>", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoGreaterThan(Integer value) {
            addCriterion("BookCodeTwo >", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookCodeTwo >=", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoLessThan(Integer value) {
            addCriterion("BookCodeTwo <", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoLessThanOrEqualTo(Integer value) {
            addCriterion("BookCodeTwo <=", value, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoIn(List<Integer> values) {
            addCriterion("BookCodeTwo in", values, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoNotIn(List<Integer> values) {
            addCriterion("BookCodeTwo not in", values, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeTwo between", value1, value2, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodetwoNotBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeTwo not between", value1, value2, "bookcodetwo");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeIsNull() {
            addCriterion("BookCodeThree is null");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeIsNotNull() {
            addCriterion("BookCodeThree is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeEqualTo(Integer value) {
            addCriterion("BookCodeThree =", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeNotEqualTo(Integer value) {
            addCriterion("BookCodeThree <>", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeGreaterThan(Integer value) {
            addCriterion("BookCodeThree >", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeGreaterThanOrEqualTo(Integer value) {
            addCriterion("BookCodeThree >=", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeLessThan(Integer value) {
            addCriterion("BookCodeThree <", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeLessThanOrEqualTo(Integer value) {
            addCriterion("BookCodeThree <=", value, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeIn(List<Integer> values) {
            addCriterion("BookCodeThree in", values, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeNotIn(List<Integer> values) {
            addCriterion("BookCodeThree not in", values, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeThree between", value1, value2, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodethreeNotBetween(Integer value1, Integer value2) {
            addCriterion("BookCodeThree not between", value1, value2, "bookcodethree");
            return (Criteria) this;
        }

        public Criteria andBookcodeIsNull() {
            addCriterion("BookCode is null");
            return (Criteria) this;
        }

        public Criteria andBookcodeIsNotNull() {
            addCriterion("BookCode is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodeEqualTo(String value) {
            addCriterion("BookCode =", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeNotEqualTo(String value) {
            addCriterion("BookCode <>", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeGreaterThan(String value) {
            addCriterion("BookCode >", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeGreaterThanOrEqualTo(String value) {
            addCriterion("BookCode >=", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeLessThan(String value) {
            addCriterion("BookCode <", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeLessThanOrEqualTo(String value) {
            addCriterion("BookCode <=", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeLike(String value) {
            addCriterion("BookCode like", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeNotLike(String value) {
            addCriterion("BookCode not like", value, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeIn(List<String> values) {
            addCriterion("BookCode in", values, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeNotIn(List<String> values) {
            addCriterion("BookCode not in", values, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeBetween(String value1, String value2) {
            addCriterion("BookCode between", value1, value2, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodeNotBetween(String value1, String value2) {
            addCriterion("BookCode not between", value1, value2, "bookcode");
            return (Criteria) this;
        }

        public Criteria andBookcodedescIsNull() {
            addCriterion("BookCodeDesc is null");
            return (Criteria) this;
        }

        public Criteria andBookcodedescIsNotNull() {
            addCriterion("BookCodeDesc is not null");
            return (Criteria) this;
        }

        public Criteria andBookcodedescEqualTo(String value) {
            addCriterion("BookCodeDesc =", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescNotEqualTo(String value) {
            addCriterion("BookCodeDesc <>", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescGreaterThan(String value) {
            addCriterion("BookCodeDesc >", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescGreaterThanOrEqualTo(String value) {
            addCriterion("BookCodeDesc >=", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescLessThan(String value) {
            addCriterion("BookCodeDesc <", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescLessThanOrEqualTo(String value) {
            addCriterion("BookCodeDesc <=", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescLike(String value) {
            addCriterion("BookCodeDesc like", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescNotLike(String value) {
            addCriterion("BookCodeDesc not like", value, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescIn(List<String> values) {
            addCriterion("BookCodeDesc in", values, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescNotIn(List<String> values) {
            addCriterion("BookCodeDesc not in", values, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescBetween(String value1, String value2) {
            addCriterion("BookCodeDesc between", value1, value2, "bookcodedesc");
            return (Criteria) this;
        }

        public Criteria andBookcodedescNotBetween(String value1, String value2) {
            addCriterion("BookCodeDesc not between", value1, value2, "bookcodedesc");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}